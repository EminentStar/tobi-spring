package springbook.user.domain;

import java.sql.SQLException;

public class NUserDao extends UserDao {
  public NUserDao(ConnectionMaker connectionMaker) {
    super(connectionMaker);
  }

  /***
   * N 사의 DB connection 생성 코드
   * @return
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  //  @Override
  //  public Connection getConnection() throws ClassNotFoundException, SQLException {
  //        Class.forName("org.h2.Driver");
  //        Connection c = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
  //
  //        return c;
  //  }
}
