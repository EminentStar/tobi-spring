package springbook.user.sqlservice.updatable;

import static org.junit.Assert.fail;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import springbook.user.sqlservice.SqlUpdateFailureException;
import springbook.user.sqlservice.UpdatableSqlRegistry;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
  EmbeddedDatabase db;

  @Override
  protected UpdatableSqlRegistry createUpdateableSqlRegistry() {
    db = new EmbeddedDatabaseBuilder()
      .setType(HSQL)
      .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
      .build();

    EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
    embeddedDbSqlRegistry.setDataSource(db);

    return embeddedDbSqlRegistry;
  }

  @After
  public void tearDown() {
    db.shutdown();
  }

  /**
   * 트랜잭션이 적용됬는지 테스트
   */
  @Test
  public void transactionalUpdate() {
    checkFindResult("SQL1", "SQL2", "SQL3");

    Map<String, String> sqlmap = new HashMap<String, String>();
    sqlmap.put("KEY1", "Modified1");
    sqlmap.put("KEY9999!@#$", "Modified9999"); // 존재하지 않는 키로 지정. 이로 인해 롤백이 일어나는지 확인할 수 있음.

    try {
      sqlRegistry.updateSql(sqlmap);
      fail();
    } catch (SqlUpdateFailureException e) {
    }
    checkFindResult("SQL1", "SQL2", "SQL3"); // 롤백됬는지 확인
  }
}
