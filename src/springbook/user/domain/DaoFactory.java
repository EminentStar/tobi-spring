package springbook.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // ApplicationContext or BeanFactory가 사용할 설정정보라는 표시
public class DaoFactory {
  /**
   * 팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지를 결정함.
   * @return UserDao
   */
  @Bean // Object 생성을 담당하는 IoC용 메소드라는 표시
  public UserDao userDao() {
    return new UserDao(connectionMaker());
  }

  @Bean
  public ConnectionMaker connectionMaker() {
    // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
    return new DConnectionMaker();
  }
}
