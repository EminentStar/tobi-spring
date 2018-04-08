package springbook.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.user.service.DummyMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceTest;

import javax.sql.DataSource;
import java.sql.Driver;

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
@Import({
  DataBaseConfig.class,
  SqlServiceContext.class
}) // 스태틱 중첩클래스로 넣은 @Configuration 클래스는 스프링이 자동으로 포함해줌.
@PropertySource("/database.properties")
public class AppContext {
  @Value("${db.driverClass}")
  Class<? extends Driver> driverClass;
  @Value("${db.url}")
  String url;
  @Value("${db.username}")
  String username;
  @Value("${db.password}")
  String password;

  /**
   * 빈 팩토리 후처리기로 사용되는 빈을 정의해주는 것인데 이 빈 설정 메소드는 반드시 스태틱 메소드로 선언해야 함
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
      return new PropertySourcesPlaceholderConfigurer();
  }

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

    dataSource.setDriverClass(this.driverClass);
    dataSource.setUrl(this.url);
    dataSource.setUsername(this.username);
    dataSource.setPassword(this.password);

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
  public SqlMapConfig sqlMapConfig() {
    return new UserSqlMapConfig();
  }

  @Configuration
  @Profile("production")
  public static class ProductionAppContext {
    @Bean
    public MailSender mailSender() {
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
      mailSender.setHost("localhost");
      return mailSender;
    }
  }

  @Configuration
  @Profile("test")
  public static class TestAppContext {
    @Bean
    public UserService testUserService() {
      return new UserServiceTest.TestUserService();
    }

    @Bean
    public MailSender mailSender() {
      return new DummyMailSender();
    }
  }
}
