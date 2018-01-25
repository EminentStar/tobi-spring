package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
  @Bean
  public UserDao userDao() {
    return new UserDao(connectionMaker());
  }

  @Bean
  public ConnectionMaker connectionMaker() {
    // 모든 DAO는 여전히 connectionMaker()에서 만들어지는 오브젝트를 DI받음.
    return new CountingConnectionMaker(realConnectionMaker());
  }

  @Bean
  public ConnectionMaker realConnectionMaker() {
    return new DConnectionMaker();
  }
}
