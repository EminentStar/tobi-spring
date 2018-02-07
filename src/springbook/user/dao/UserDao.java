package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import springbook.user.domain.User;

public class UserDao {
  private DataSource dataSource;
  private JdbcTemplate jdbcTemplate;

  /**
   * JdbcTemplate: 스프링이 제공하는 JDBC 코드용 기본 템플릿.
   */
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);

    this.dataSource = dataSource;
  }

  /**
   *  내부 클래스에서 외부의 변수를 사용할 때는 외부 변수는 반드시 final로 선언해줘야 함.
   */
  public void add(final User user) throws SQLException {
    this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?,?,?)",
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
    this.jdbcTemplate.executeSql("DELETE FROM users WHERE id = ?", id);
  }

  public void deleteAll() throws SQLException {
    this.jdbcTemplate.update("DELETE FROM users");
    // 아래와 같이 콜백을 넘겨주는 형태로도 가능 (JdbcTemplate의 콜백은 PreparedStatementCreator 인터페이스의
    // createPreparedStatement() 임.
    //    this.jdbcTemplate.update(
    //      new PreparedStatementCreator() {
    //        @Override
    //        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    //          return con.prepareStatement("DELETE FROM users");
    //        }
    //      }
    //    );
  }

}

