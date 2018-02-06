package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
  /**
   * DataSource 타입 빈을 DI 받을 수 있게 준비.
   * (Connection을 필요로 하는 코드는 JdbcContext안에 있기에)
   */
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   *
   * @param stmt : 클라이언트가 컨텍스트를 호출할 때 넘겨줄 전략 파라미터
   * @throws SQLException
   */
  public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
    Connection c = null;
    PreparedStatement ps = null;

    try { // 예외가 발생할 가능성이 있는 코드를 모두 try 블록으로 묶어준다.
      c = dataSource.getConnection();

      ps = stmt.makePreparedStatement(c);

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

  public void executeSql(final String query) throws SQLException {
    workWithStatementStrategy(
      new StatementStrategy() {
        @Override
        public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
          return c.prepareStatement(query);
        }
      }
    ); // 컨텍스트 호출. 전략 오브젝트 전달
  }

}
