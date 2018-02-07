package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

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
    return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
    // 아래의 콜벡 오브젝트 코드를 재사용하여 위의 코드를 만들 수 있음.
    //    return this.jdbcTemplate.query(new PreparedStatementCreator() { // 첫번째 콜백. Statement 생성
    //      @Override
    //      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
    //        return con.prepareStatement("SELECT COUNT(*) FROM users");
    //      }
    //    }, new ResultSetExtractor<Integer>() { // 두번쨰 콜백. ResultSet으로부터 값 추출
    //      // ResultSet에서 추출할 수 있는 값의 타입은 다양학 때문에 제네릭스 타입파라미터를 사용.
    //      @Override
    //      public Integer extractData(ResultSet rs) throws SQLException {// ,DataAccessException {
    //        rs.next();
    //        return rs.getInt(1);
    //      }
    //    });
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

