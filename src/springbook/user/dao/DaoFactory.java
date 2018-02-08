package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration // ApplicationContext or BeanFactory가 사용할 설정정보라는 표시
public class DaoFactory {
  /**
   * 팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지를 결정함.
   * @return UserDao
   */
  // Object 생성을 담당하는 IoC용 메소드라는 표시
  @Bean      // -------------------------------> <bean
  public UserDao userDao() {       // -----> id="userDao"
    UserDao userDao = new UserDao();
    //    userDao.setDataSource(dataSource()); // -> <property name="connectionMaker" ref="connectionMaker/>
    userDao.setJdbcTemplate(jdbcTemplate()); // -> <property name="connectionMaker" ref="connectionMaker/>
    return userDao; // ---------------------> class="springbook..UserDao" />
  }

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    /* 코드를 통한 DB 연결정보 주입 */
    // DB 연결정보를 수정자 메소드를 통해 넣어줌.
    // 이렇게 하면 오브젝트 레벨에서 DB 연결 방식을 변경할 수 있음.
    dataSource.setDriverClass(org.h2.Driver.class);
    dataSource.setUrl("jdbc:h2:~/test");
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

  @Bean      // -------------------------------------------> <bean
  public ConnectionMaker connectionMaker() {       // -----> id="connectionMaker"
    // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
    return new DConnectionMaker(); // ---------------------> class="springbook..DConnectionMaker" />
  }
}
