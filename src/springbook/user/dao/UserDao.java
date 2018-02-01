package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import springbook.user.domain.User;

public class UserDao {
  private ConnectionMaker connectionMaker; // UserDao의 프로퍼티
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void add(User user) throws SQLException {
    Connection c = dataSource.getConnection();

    PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
    ps.setString(1, user.getId());
    ps.setString(2, user.getName());
    ps.setString(3, user.getPassword());

    ps.executeUpdate();

    ps.close();
    c.close();

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
    Connection c = dataSource.getConnection();

    PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE id = ?");
    ps.setString(1, id);

    ps.executeUpdate();

    ps.close();
    c.close();
  }

  public void deleteAll() throws SQLException {
    Connection c = null;
    PreparedStatement ps = null;

    try { // 예외가 발생할 가능성이 있는 코드를 모두 try 블록으로 묶어준다.
      c = dataSource.getConnection();
      ps = c.prepareStatement("DELETE FROM users");
      ps.executeUpdate();
    } catch (SQLException e) {
      throw e;
    } finally {
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

}

