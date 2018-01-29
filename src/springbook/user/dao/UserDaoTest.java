package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class UserDaoTest {
  @Test
  public void addAndGet() throws SQLException, ClassNotFoundException {
    // classpath를 시작하는 / 는 넣을 수도 있고 생략할 수도 있음.
    // 시작하는 / 가 없는 경우 항상 루트에서부터 시작하는 classpath라는 점을 기억.
    ApplicationContext context =
      new GenericXmlApplicationContext("applicationContext.xml");
    UserDao dao = context.getBean("userDao", UserDao.class);

    String id = "junk3843";

    dao.delete(id);

    User user = new User();
    user.setId(id);
    user.setName("박준규");
    user.setPassword("123!@#");

    dao.add(user);

    System.out.println(user.getId() + " 등록 성공");

    User user2 = dao.get(user.getId());

    assertThat(user2.getName(), is(user.getName()));
    assertThat(user2.getPassword(), is(user.getPassword()));
  }
}
