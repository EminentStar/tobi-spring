package springbook.conf;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.mail.MailSender;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springbook.user.dao.UserDao;
import springbook.user.service.DummyMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceTest;
import springbook.user.sqlservice.OxmSqlService;
import springbook.user.sqlservice.SqlRegistry;
import springbook.user.sqlservice.SqlService;
import springbook.user.sqlservice.updatable.EmbeddedDbSqlRegistry;

/**
 * @Configuration: DI 정보로 사용될 자바 클래스로 지정
 * (Chap 1.5 에서 DaoFactory 클래스를 스프링 컨테이너가 사용하는 IoC/DI 정보로 활용되게 할 때 이미 사용)
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(
  basePackages = {
    "springbook.user"
  },
  useDefaultFilters = true,
  excludeFilters = @ComponentScan.Filter({
  }))
@Import(DataBaseConfig.class)
public class AppContext {
  @Autowired
  private UserDao userDao;

  /**
   * <bean>에 대응
   *   - method name: <bean>의 id
   *   - return type: 빈을 주입받아 사용하는 빈이 어떤 타입으로 이 빈의 존재를 알고 있는지 확인이 필요.
   *      - 구현 클래스로 return type을 지정해버리면 추후 구현 클래스 변경시 문제 발생 가능. 인터페이스로 유연하게 대응.
   *
   * @Bean 메소드에선 빈 인스턴스 생성과 프로퍼티 설정 등을 모두 실제 동작하는 코드로 만들 필요가 있음.
   */
  @Bean
  public DataSource dataSource() {
    // 1. bean object 생성 (<bean>의 class에 나와있는 클래스 오브젝트)
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    // XML에서는 스프링 컨테이너가 빈의 프로퍼티 타입을 보고 "org.h2.Driver" 문자열을 Class 타입의 org.h2.Driver.class로 알아서 변환해줬었음.
    dataSource.setDriverClass(org.h2.Driver.class);
    dataSource.setUrl("jdbc:h2:~/testdb");
    dataSource.setUsername("sa");
    dataSource.setPassword("");

    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.setDataSource(dataSource());

    return jdbcTemplate;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource());

    return transactionManager;
  }

  @Bean
  public MailSender mailSender() {
    return new DummyMailSender();
  }

  /**
   * TestUserService가 public Access Modifer를 가지고 있지 않기 때문이였음.
   *
   * 스프링의 <bean>에 넣는 클래스는 굳이 public이 아니어도 됨.
   * (내부적으로 Reflection API를 이용하기 때문에 private으로 접근을 제한해도 빈의 클래스로 사용가능)
   *
   * 하지만 자바 코드에서 참조할 때는 패키지가다르면 public으로 접근 제한자를 바꿔줘야 함.
   */
  @Bean
  public UserService testUserService() {
    UserServiceTest.TestUserService testUserService = new UserServiceTest.TestUserService();
    testUserService.setUserDao(this.userDao);
    testUserService.setMailSender(mailSender());
    return testUserService;
  }

  @Bean
  public SqlService sqlService() {
    OxmSqlService oxmSqlService = new OxmSqlService();

    oxmSqlService.setUnmarsaller(unmarshaller());
    oxmSqlService.setSqlRegistry(sqlRegistry());

    return oxmSqlService;
  }

  @Bean
  public Unmarshaller unmarshaller() {
    Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
    unmarshaller.setContextPath("springbook.user.sqlservice.jaxb");
    return unmarshaller;
  }

  @Bean
  public SqlRegistry sqlRegistry() {
    EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
    sqlRegistry.setDataSource(embeddedDatabase());

    return sqlRegistry;
  }

  @Bean
  public DataSource embeddedDatabase() {
    return new EmbeddedDatabaseBuilder() // 빌더 오브젝트 생성
      .setName("embeddedDatabase")
      .setType(HSQL) //EmbeddedDatabaseType의 HSQL, DERBY, H2 중에서 하나를 선택.
      .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql") // 테이블 생성과 테이블 초기화를 위해 사용할 SQL 문장을 담은 SQL 스크립트의 위치를 지정.(하나 이상 지정 가능)
      .build(); // 주어진 조건에 맞는 내장형 DB를 준비하고 초기화 스크립트를 모두 실행한 뒤에 이에 접근할 수 있는 EmbeddedDatabase를 돌려줌.
  }

}
