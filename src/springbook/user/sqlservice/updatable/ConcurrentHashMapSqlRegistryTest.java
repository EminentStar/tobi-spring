package springbook.user.sqlservice.updatable;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import springbook.user.sqlservice.SqlNotFoundException;
import springbook.user.sqlservice.SqlUpdateFailureException;
import springbook.user.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest {
  UpdatableSqlRegistry sqlRegistry;

  @Before
  public void setUp() {
    sqlRegistry = new ConcurrentHashMapSqlRegistry();
    sqlRegistry.registerSql("KEY1", "SQL1");
    sqlRegistry.registerSql("KEY2", "SQL2");
    sqlRegistry.registerSql("KEY3", "SQL3");
  }

  @Test
  public void find() {
  }

  private void checkFindResult(String expected1, String expected2, String expected3) {
    assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
    assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
    assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
  }

  /**
   * 주어진 키에 해당하는 SQL을 찾을 수 없을 때 예외가 발생하는지 확인.
   * 예외상황에 대한 테스트는 빼먹기가 쉽기 때문에 항상 의식적으로 넣으려고 노력해야함.
   */
  @Test(expected = SqlNotFoundException.class)
  public void unknownKey() {
    sqlRegistry.findSql("SQL9999!@#$");
  }

  @Test
  public void updateSingle() {
    sqlRegistry.updateSql("KEY2", "Modified2");
    checkFindResult("SQL1", "Modified2", "SQL3");
  }

  @Test
  public void updateMulti() {
    Map<String, String> sqlmap = new HashMap<String, String>();
    sqlmap.put("KEY1", "Modified1");
    sqlmap.put("KEY3", "Modified3");

    sqlRegistry.updateSql(sqlmap);
    checkFindResult("Modified1", "SQL2", "Modified3");
  }

  @Test(expected = SqlUpdateFailureException.class)
  public void updateWithNotExistingKey() {
    sqlRegistry.updateSql("SQL9999!@#$", "Modified2");
  }

}
