package springbook.user.sqlservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class HashMapSqlRegistry implements SqlRegistry {
  private Map<String, String> sqlMap = new HashMap<String, String>();

  @Override
  public void registerSql(String key, String sql) {
    sqlMap.put(key, sql);
  }

  @Override
  public String findSql(String key) throws SqlNotFoundException {
    String sql = sqlMap.get(key);
    if (StringUtils.isBlank(sql)) {
      throw new SqlNotFoundException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
    } else {
      return sql;
    }
  }
}
