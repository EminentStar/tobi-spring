package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
  private ConnectionMaker connectionMaker; // UserDao의 프로퍼티

  public UserDao() {
  }

  /**
   * 의존관계 주입을 위한 코드
   */
  public UserDao(ConnectionMaker connectionMaker) {
    this.connectionMaker = connectionMaker;
  }

  public void add(User user) throws ClassNotFoundException, SQLException {
    Connection c = connectionMaker.makeNewConnection();

    PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
    ps.setString(1, user.getId());
    ps.setString(2, user.getName());
    ps.setString(3, user.getPassword());

    ps.executeUpdate();

    ps.close();
    c.close();

  }

  public User get(String id) throws ClassNotFoundException, SQLException {
    Connection c = connectionMaker.makeNewConnection();

    PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
    ps.setString(1, id);
    ResultSet rs = ps.executeQuery();
    rs.next();

    User user = new User();
    user.setId(rs.getString("id"));
    user.setName(rs.getString("name"));
    user.setPassword(rs.getString("password"));

    rs.close();
    ps.close();
    c.close();

    return user;
  }

  public void delete(String id) throws ClassNotFoundException, SQLException {
    Connection c = connectionMaker.makeNewConnection();

    PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE id = ?");
    ps.setString(1, id);

    ps.executeUpdate();

    ps.close();
    c.close();
  }

  /**
   * setter method DI의 전형적인 코드. 잘 기억할 것.
   *
   * e.g. userDao.setConnectionMaker(connectionMaker());
   * userDao.setConnectionMaker()는 userDao빈의 connectionMAker 프로퍼티를 이용해 의존관계를 주입한다는 뜻임.
   * connectionMaker()는 connectionMaker() 메소드를 호출해서 리턴하는 오브젝트를 주입하라는 의미
   */
  public void setConnectionMaker(ConnectionMaker connectionMaker) {
    this.connectionMaker = connectionMaker;
  }

  //  public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
  //    Class.forName("org.h2.Driver");
  //    Connection c = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
  //
  //    return c;
  //  }
}

