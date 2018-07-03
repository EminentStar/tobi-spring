package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoConnectionCountingTest {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    AnnotationConfigApplicationContext context = null;
//      new AnnotationConfigApplicationContext(CountingDaoFactory.class);
    UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);

    //
    // DAO 사용 코드
    //
    /* DL을 사용하면 이름을 이용해 어떤 빈이든 가져올 수 있다. */
    CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
    System.out.println("Connection counter : " + ccm.getCounter());

  }
}
