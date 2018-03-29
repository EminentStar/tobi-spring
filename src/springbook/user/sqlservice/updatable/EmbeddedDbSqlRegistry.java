package springbook.user.sqlservice.updatable;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import springbook.user.sqlservice.SqlNotFoundException;
import springbook.user.sqlservice.SqlUpdateFailureException;
import springbook.user.sqlservice.UpdatableSqlRegistry;

public class EmbeddedDbSqlRegistry implements UpdatableSqlRegistry {
  SimpleJdbcTemplate jdbc;
  TransactionTemplate transactionTemplate; // JdbcTemplate과 트랜잭션을 동기화해주는 트랜잭션 템플릿. 멀티스레드 환경에서 공유 가능.

  public void setDataSource(DataSource dataSource) {
    jdbc = new SimpleJdbcTemplate(dataSource);
    transactionTemplate = new TransactionTemplate(
      new DataSourceTransactionManager(dataSource)); // dataSource로 TransactionManager를 만들고 이를 이용해 TransactionTemplate 생성
  }

  @Override
  public void registerSql(String key, String sql) {
    jdbc.update("INSERT INTO sqlmap(key_, sql_) VALUES(?,?)", key, sql);
  }

  @Override
  public String findSql(String key) throws SqlNotFoundException {
    try {
      return jdbc.queryForObject("SELECT sql_ FROM sqlmap WHERE key_ = ?", String.class, key);
    } catch (EmptyResultDataAccessException e) { // 결과가 없으면 예외 발생
      throw new SqlNotFoundException(key + "에 해당하는 SQL을 찾을 수 없습니다.", e);
    }
  }

  @Override
  public void updateSql(String key, String sql) throws SqlUpdateFailureException {
    int affected = jdbc.update("UPDATE sqlmap SET sql_ = ? WHERE key_ = ?", sql, key);

    if (affected == 0) {
      throw new SqlUpdateFailureException(key + "에 해당하는 SQL을 찾을 수 없습니다.");
    }
  }

  @Override
  public void updateSql(final Map<String, String> sqlmap) throws SqlUpdateFailureException { //익명 내부클래스로 만들어지는 콜백 오브젝트 안에서 사용되는 것이라 final로 선언해줘야 함.

    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) { // 트랜잭션 템플릿이 만드는 트랜잭션 경계 안에서 동작할 코드를 콜백 형태로 만들고 TransactionTemplate의 execute() 메소드에 전달함.
        for (Map.Entry<String, String> entry : sqlmap.entrySet()) {
          updateSql(entry.getKey(), entry.getValue());
        }
      }
    });
  }

}
