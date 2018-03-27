package springbook.user.sqlservice;

import java.util.Map;

public interface UpdatableRegistry extends SqlRegistry {
  public void updateSql(String key, String sql) throws SqlUpdateFailureException;

  public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException;
}
