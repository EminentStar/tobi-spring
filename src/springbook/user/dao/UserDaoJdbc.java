package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;

public class UserDaoJdbc implements UserDao {
  // RowMapper 콜백 오브젝트에는 상태정보가 없음. 따라서 하나의 콜백 오브젝트를 멀티 스레드에서 동시에 사용해도 문제가 되지 않음.
  private RowMapper<User> userMapper =
    new RowMapper<User>() {
      @Override
      public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        return user;
      }
    };

  private JdbcTemplate jdbcTemplate;

  /**
   * JdbcTemplate: 스프링이 제공하는 JDBC 코드용 기본 템플릿.
   *
   * JdbcTemplate을 생성하면서 직접 DI 해주기 위해 필요한 DataSource를 받아야하니 setter method는 남겨놓음.
   *  - 이렇게 setter method에서 다른 오브젝트를 생성하는 경우는 종종 있으니 익숙해질 것.
   */
  //  public void setDataSource(DataSource dataSource) {
  //    this.jdbcTemplate = new JdbcTemplate(dataSource);
  //  }
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   *  내부 클래스에서 외부의 변수를 사용할 때는 외부 변수는 반드시 final로 선언해줘야 함.
   */
  public void add(final User user) {
    this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?,?,?)",
      user.getId(), user.getName(), user.getPassword());
  }

  public User get(String id) {
    // queryForObject() 는 SQL을 실행하면 한 개의 로우만 얻을 것이라 기대
    return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
      new Object[] {id}, // SQL에 바인딩할 파라미터 값. 가변인자 대신 배열을 사용
      userMapper);
  }

  public List<User> getAll() {
    return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", userMapper);
  }

  public int getCount() {
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

  public void delete(String id) {
    this.jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
  }

  public void deleteAll() {
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

  /**
   * 예외 전환 기능을 가진 DAO 메소드
   *
   *
   * */
//  public void addForChap4(User user) throws DuplicateUserIdException, SQLException {
//    try {
//      // JDBC를 이용해 user 정보를 DB에 추가하는 코드 또는
//      // 그런 기능을 가진 다른 SQLException을 던지는 메소드를 호출하는 코드
//    } catch (SQLException e) {
//      // ErrorCode가 MySQL의 "Duplicate Entry(1062)"이면 예외 전환
//      if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
//        // 보통 전환하는 예외에 원래 발생한 예외를 담아서 중첩 예외로 만드는 것이 좋음.
//        throw new DuplicateUserIdException(e);  // 예외 전환
//        //        throw DuplicateUserIdException().initCause(e); // 혹은 wrapping. 주로 예외처리를 강제하는 체크 예외를 언체크 예외인 런타임 예외로 바꾸는 경우에 사용.
//      } else {
//        throw new RuntimeException(e); // 예외 포장
//      }
//    }
//    // JDBC API
//  }
}

