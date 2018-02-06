package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import springbook.user.domain.User;

public class UserDao {
  private DataSource dataSource;
  private JdbcContext jdbcContext;

  /**
   * 수정자 메소드이면서 JdbcContext에 대한 생성, DI 작업을 동시에 수행
   */
  public void setDataSource(DataSource dataSource) { // DI 컨테이너가 DataSource 오브젝트를 주입해줄 때 호출됨.
    this.jdbcContext = new JdbcContext(); // JdbcContext 생성 (IoC)

    this.jdbcContext.setDataSource(dataSource); // 의존 오브젝트 주입(DI)

    this.dataSource = dataSource; // 아직 JdbcContext를 적용하지 않은 메소드를 위해 저장해둠.
  }

  /**
   *  내부 클래스에서 외부의 변수를 사용할 때는 외부 변수는 반드시 final로 선언해줘야 함.
   */
  public void add(final User user) throws SQLException {
    this.jdbcContext.executeSql("INSERT INTO users(id, name, password) VALUES(?,?,?)",
      user.getId(), user.getName(), user.getPassword());
  }

  public User get(String id) throws SQLException {
    Connection c = dataSource.getConnection();

    PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
    ps.setString(1, id);
    ResultSet rs = ps.executeQuery();

    User user = null;
    if (rs.next()) {
      user = new User();
      user.setId(rs.getString("id"));
      user.setName(rs.getString("name"));
      user.setPassword(rs.getString("password"));
    }

    rs.close();
    ps.close();
    c.close();

    if (user == null) {
      /* TODO: EmptyResultDataAccessException으로 교체해야함. */
      throw new NoSuchElementException();
    }

    return user;
  }

  public int getCount() throws SQLException {
    Connection c = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      c = dataSource.getConnection();

      ps = c.prepareStatement("select COUNT(*) from users");

      rs = ps.executeQuery();
      rs.next();
      int count = rs.getInt(1);

      return count;
    } catch (SQLException e) {
      throw e;
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
        }
      }

      if (ps != null) {
        try {
          ps.close();
        } catch (SQLException e) {
        }
      }

      if (c != null) {
        try {
          c.close();
        } catch (SQLException e) {
        }
      }
    }
  }

  public void delete(String id) throws SQLException {
    this.jdbcContext.executeSql("DELETE FROM users WHERE id = ?", id);
  }

  public void deleteAll() throws SQLException {
    this.jdbcContext.executeSql("DELETE FROM users");
  }

}

