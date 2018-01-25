package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ObjectIdentityTest {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    testObjectIdentity();
  }

  public static void testObjectIdentity() {
    testObjectIdentityByDaoFactory();
    testObjectIdentityBySpringContext();
  }

  public static void testObjectIdentityByDaoFactory() {
    DaoFactory factory = new DaoFactory();
    UserDao dao1 = factory.userDao();
    UserDao dao2 = factory.userDao();

    System.out.println("objects directly created by DaoFactory");
    System.out.println(dao1); // (example) springbook.user.dao.UserDao@3764951d
    System.out.println(dao2); // (example) springbook.user.dao.UserDao@4b1210ee
    // 서로 다른 값을 가진 동일하지 않은 오브젝트임
  }

  public static void testObjectIdentityBySpringContext() {
    ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

    UserDao dao3 = context.getBean("userDao", UserDao.class);
    UserDao dao4 = context.getBean("userDao", UserDao.class);

    System.out.println("objects got from spring context.");
    System.out.println(dao3); // (example) springbook.user.dao.UserDao@1ce92674
    System.out.println(dao4); // (example) springbook.user.dao.UserDao@1ce92674
    // 동일한 오브젝트
  }
}
