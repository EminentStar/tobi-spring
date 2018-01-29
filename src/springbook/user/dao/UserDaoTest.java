package springbook.user.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class UserDaoTest {
  @Test
  public void addAndGet() throws SQLException {
    // classpath를 시작하는 / 는 넣을 수도 있고 생략할 수도 있음.
    // 시작하는 / 가 없는 경우 항상 루트에서부터 시작하는 classpath라는 점을 기억.
    ApplicationContext context =
      new GenericXmlApplicationContext("applicationContext.xml");

    UserDao dao = context.getBean("userDao", UserDao.class);
    User user1 = new User("junk3843", "박준규", "123!@#");
    User user2 = new User("eminent", "박준규", "123!@#");

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
    ApplicationContext context =
      new GenericXmlApplicationContext("applicationContext.xml");

    UserDao dao = context.getBean("userDao", UserDao.class);
    User user1 = new User("junk3843", "박준규", "123!@#");
    User user2 = new User("eminent", "박준규", "123!@#");
    User user3 = new User("eminent2", "박준규", "123!@#");

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
    ApplicationContext context =
      new GenericXmlApplicationContext("applicationContext.xml");

    UserDao dao = context.getBean("userDao", UserDao.class);
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.get("unknown_id");
  }
}
