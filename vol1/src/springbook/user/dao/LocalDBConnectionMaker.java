package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDBConnectionMaker implements ConnectionMaker {
  /***
   * D사의 독자적인 방법으로 Connection을 생성하는 코드
   * @return
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  @Override
  public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    Connection c = DriverManager.getConnection(
      "jdbc:h2:~/test", "sa", "");

    return c;
  }
}
