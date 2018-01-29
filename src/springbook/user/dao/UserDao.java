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

    if (user == null){
      /* TODO: EmptyResultDataAccessException으로 교체해야함. */
      throw new NoSuchElementException();
    }

    return user;
  }

  public int getCount() throws SQLException {
    Connection c = dataSource.getConnection();

    PreparedStatement ps = c.prepareStatement("select COUNT(*) from users");

    ResultSet rs = ps.executeQuery();
    rs.next();
    int count = rs.getInt(1);

    rs.close();
    ps.close();
    c.close();

    return count;
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
    Connection c = dataSource.getConnection();

    PreparedStatement ps = c.prepareStatement("DELETE FROM users");
    ps.executeUpdate();

    ps.close();
    c.close();
  }

}

