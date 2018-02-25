package springbook.user.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.Level;
import springbook.user.domain.User;

// 스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장기능 지정
@RunWith(SpringJUnit4ClassRunner.class)
// 테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트의 위치 지정
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {
  @Autowired
  // 테스트 오브젝트가 만들어지고 나면 스프링 컨텍스트 테스트에 의해 자동으로 값이 주입
  private ApplicationContext context;
  @Autowired // @Autowired는 스프링의 컨텍스트 내에서 정의된 빈 중에서 인스턴스 변수에 주입 가능한 타입의 빈을 찾아줌.
  private UserDao dao; // UserDaoJdbc TYPE의 빈을 직접 DI받음.

  private User user1;
  private User user2;
  private User user3;

  /***
   * @Before: JUnit 제공 애노테이션. @Test 메소드가 실행되기 전에 먼저 실행돼야하는 메소드를 정의
   */
  @Before
  public void setUp() {
    this.user1 = new User("unk3843", "박준규", "123!@#", Level.BASIC, 1, 0, "junk3843@naver.com");
    this.user2 = new User("junk384", "박준규", "123!@#", Level.SILVER, 55, 10, "pjk3843@gmail.com");
    this.user3 = new User("aminent2", "박준규", "123!@#", Level.GOLD, 100, 40, "wnsrb3843@naver.com");

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
  public void addAndGet() {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.add(user1);
    dao.add(user2);
    assertThat(dao.getCount(), is(2));

    User userget1 = dao.get(user1.getId());
    checkSameUser(userget1, user1);

    User userget2 = dao.get(user2.getId());
    checkSameUser(userget2, user2);
  }

  @Test
  public void getAll() {
    dao.deleteAll();

    List<User> users0 = dao.getAll();
    assertThat(users0.size(), is(0));

    dao.add(user1);
    List<User> users1 = dao.getAll();
    assertThat(users1.size(), is(1));
    checkSameUser(user1, users1.get(0));

    dao.add(user2);
    List<User> users2 = dao.getAll();
    assertThat(users2.size(), is(2));
    checkSameUser(user1, users2.get(1));
    checkSameUser(user2, users2.get(0));

    dao.add(user3);
    List<User> users3 = dao.getAll();
    assertThat(users3.size(), is(3));
    checkSameUser(user3, users3.get(0));
    checkSameUser(user1, users3.get(2));
    checkSameUser(user2, users3.get(1));

  }

  @Test
  public void count() {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.add(user1);
    assertThat(dao.getCount(), is(1));

    dao.add(user2);
    assertThat(dao.getCount(), is(2));

    dao.add(user3);
    assertThat(dao.getCount(), is(3));
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void getUserFailure() throws SQLException {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.get("unknown_id");
  }

  @Test
  public void delete() {
    // Given
    dao.deleteAll();
    dao.add(user1);
    dao.add(user2);
    dao.add(user3);
    assertThat(dao.getCount(), is(3));

    // When
    // Then
    dao.delete(user1.getId());
    assertThat(dao.getCount(), is(2));

    dao.delete(user2.getId());
    assertThat(dao.getCount(), is(1));

    dao.delete(user3.getId());
    assertThat(dao.getCount(), is(0));
  }

  @Test(expected = DuplicateKeyException.class)
  public void duplicateKey() {
    dao.deleteAll();

    dao.add(user1);
    dao.add(user1);
  }

  @Test
  public void update() {
    // Given
    dao.deleteAll();

    // When
    dao.add(user1);
    dao.add(user2);

    user1.setName("오민규");
    user1.setPassword("springno6");
    user1.setLevel(Level.GOLD);
    user1.setLogin(1000);
    user1.setRecommend(999);
    dao.update(user1);

    // Then
    User user1update = dao.get(user1.getId());
    checkSameUser(user1, user1update);
    User user2same = dao.get(user2.getId());
    checkSameUser(user2, user2same);
  }

  /**
   * User 오브젝트의 내용을 비교하는 검증 코드.
   *
   * 테스트에서 반복적으로 사용되므로 분리해놓음.
   */
  private void checkSameUser(User user1, User user2) {
    assertThat(user1.getId(), is(user2.getId()));
    assertThat(user1.getName(), is(user2.getName()));
    assertThat(user1.getPassword(), is(user2.getPassword()));
    assertThat(user1.getLevel(), is(user2.getLevel()));
    assertThat(user1.getLogin(), is(user2.getLogin()));
    assertThat(user1.getRecommend(), is(user2.getRecommend()));
  }
}
