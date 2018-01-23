package springbook.user.domain;

import java.sql.SQLException;

public class UserDaoTest {
  public static void main(String[] args)throws ClassNotFoundException, SQLException {
    ConnectionMaker connectionMaker = new DConnectionMaker(); // UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만든다.
//    ConnectionMaker connectionMaker = new NConnectionMaker(); // D 사의 ConnectionMaker로도 자유롭게 변경가능

    UserDao dao = new UserDao(connectionMaker);

    User user = new User();
    user.setId("2junk3843");
    user.setName("2박준규");
    user.setPassword("123!@#");

    dao.add(user);

    System.out.println(user.getId() + " 등록 성공");

    User user2 = dao.get(user.getId());
    System.out.println(user2.getName());
    System.out.println(user2.getPassword());

    System.out.println(user2.getId() + " 조회 성공 ");

  }
}
