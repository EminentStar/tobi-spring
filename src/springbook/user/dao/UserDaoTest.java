package springbook.user.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.User;

// 스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장기능 지정
@RunWith(SpringJUnit4ClassRunner.class)
// 테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트의 위치 지정
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {
  @Autowired
  // 테스트 오브젝트가 만들어지고 나면 스프링 컨텍스트 테스트에 의해 자동으로 값이 주입
  private ApplicationContext context;
  @Autowired
  private UserDao dao; // UserDao TYPE의 빈을 직접 DI받음.

  private User user1;
  private User user2;
  private User user3;

  /***
   * @Before: JUnit 제공 애노테이션. @Test 메소드가 실행되기 전에 먼저 실행돼야하는 메소드를 정의
   */
  @Before
  public void setUp() {
    this.user1 = new User("junk3843", "박준규", "123!@#");
    this.user2 = new User("eminent", "박준규", "123!@#");
    this.user3 = new User("eminent2", "박준규", "123!@#");

    // context 확인용
    System.out.println(this.context);
    System.out.println(this);
    /*
      org.springframework.context.support.GenericApplicationContext@71e7a66b: startup date [Tue Jan 30 07:29:36 KST 2018]; root of context hierarchy
      springbook.user.dao.UserDaoTest@76b0bfab org.springframework.context.support.GenericApplicationContext@71e7a66b: startup date [Tue Jan 30 07:29:36 KST 2018]; root of context hierarchy
      springbook.user.dao.UserDaoTest@61dd025
      org.springframework.context.support.GenericApplicationContext@71e7a66b: startup date [Tue Jan 30 07:29:36 KST 2018]; root of context hierarchy
      springbook.user.dao.UserDaoTest@6dbb137d

      보는 것처럼 context는 3 테스트 오브젝트 모두 동일
      테스트 오브젝트는 매번 달라짐.(새로 생성됨)
     */
  }

  @Test
  public void addAndGet() throws SQLException {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.add(user1);
    dao.add(user2);
    assertThat(dao.getCount(), is(2));

    User userget1 = dao.get(user1.getId());
    assertThat(userget1.getName(), is(user1.getName()));
    assertThat(userget1.getPassword(), is(user1.getPassword()));

    User userget2 = dao.get(user2.getId());
    assertThat(userget2.getName(), is(user2.getName()));
    assertThat(userget2.getPassword(), is(user2.getPassword()));
  }

  @Test
  public void count() throws SQLException {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.add(user1);
    assertThat(dao.getCount(), is(1));

    dao.add(user2);
    assertThat(dao.getCount(), is(2));

    dao.add(user3);
    assertThat(dao.getCount(), is(3));
  }

  //  @Test(expected = IncorrectResultSetColumnCountException.class)

  /**
   * TODO:
   * @Test(expected = EmptyResultDataAccessException.class) // 테스트 중에 발생할 것으로 기대되는 예외 클래스를 지정.
   * 해당 exception class를 스프링에서 찾을 수 없네. 3.1 부터 있나?
   *  */
  @Test(expected = NoSuchElementException.class)
  public void getUserFailure() throws SQLException {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.get("unknown_id");
  }
}
