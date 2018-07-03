package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlservice.SqlService;

@Repository("userDao")
public class UserDaoJdbc implements UserDao {
  /**
   * UserDaoJdbc는 SqlService 인터페이스를 통해 필요한 SQL을 가져와 사용할 수 있게 만듬.
   */

  /**
   * 수정자 메소드를 거치지 않고 직접 필드에빈 오브젝트를 넣도록 만들어도 무방.
   *
   * 자바에서는 private field에 클래스 외부에서 값을 넣을 수 없게 되어 있지만, 스프링은 리플렉션 API를 이용해 제약조건을 우회해서 값을 넣어줌.
   * 필드에 직접 값을 넣을 수 있다면 수정자 메소드는 없어도 됨.
   */
  @Autowired
  private SqlService sqlService;

  // RowMapper 콜백 오브젝트에는 상태정보가 없음. 따라서 하나의 콜백 오브젝트를 멀티 스레드에서 동시에 사용해도 문제가 되지 않음.
  private RowMapper<User> userMapper =
    new RowMapper<User>() {
      @Override
      public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        user.setEmail(rs.getString("email"));
        return user;
      }
    };

  private JdbcTemplate jdbcTemplate;

  /**
   * 주어진 오브젝트를 그대로 필드에 저장하는 대신 JdbcTemplate을 생성해서 저장해줌.
   */
  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /**
   * JdbcTemplate: 스프링이 제공하는 JDBC 코드용 기본 템플릿.
   *
   * JdbcTemplate을 생성하면서 직접 DI 해주기 위해 필요한 DataSource를 받아야하니 setter method는 남겨놓음.
   *  - 이렇게 setter method에서 다른 오브젝트를 생성하는 경우는 종종 있으니 익숙해질 것.
   */
  //  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
  //    this.jdbcTemplate = jdbcTemplate;
  //  }

  /**
   * UseDaoJdbc는 SqlService 인터페이스를 통해 필요한 SQL을 가져와 사용할 수 있게 만듬.
   */
  public void setSqlService(SqlService sqlService) {
    this.sqlService = sqlService;
  }

  /**
   *  내부 클래스에서 외부의 변수를 사용할 때는 외부 변수는 반드시 final로 선언해줘야 함.
   */

  @Override
  public void add(final User user) {
    this.jdbcTemplate.update(
      this.sqlService.getSql("userAdd"),
      user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(),
      user.getRecommend(), user.getEmail());
  }

  @Override
  public User get(String id) {
    // queryForObject() 는 SQL을 실행하면 한 개의 로우만 얻을 것이라 기대
    return this.jdbcTemplate.queryForObject(
      this.sqlService.getSql("userGet"),
      new Object[] {id}, // SQL에 바인딩할 파라미터 값. 가변인자 대신 배열을 사용
      userMapper);
  }

  @Override
  public List<User> getAll() {
    return this.jdbcTemplate.query(
      this.sqlService.getSql("userGetAll"),
      userMapper);
  }

  @Override
  public int getCount() {
    return this.jdbcTemplate.queryForInt(
      this.sqlService.getSql("userGetCount")
    );
  }

  @Override
  public void update(User user) {
    this.jdbcTemplate.update(
      this.sqlService.getSql("userUpdate"),
      user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(),
      user.getRecommend(), user.getEmail(), user.getId());
  }

  @Override
  public void delete(String id) {
    this.jdbcTemplate.update(
      this.sqlService.getSql("userDelete"),
      id);
  }

  @Override
  public void deleteAll() {
    this.jdbcTemplate.update(
      this.sqlService.getSql("userDeleteAll")
    );
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

