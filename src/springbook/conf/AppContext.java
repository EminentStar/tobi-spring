package springbook.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springbook.user.dao.UserDao;

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
  SqlServiceContext.class,
  TestAppContext.class,
  ProductionAppContext.class
})
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

}
