package springbook.user.domain;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
      // write your code here
      UserDao dao = new NUserDao();

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
