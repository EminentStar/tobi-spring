package springbook.user.sqlservice;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SimpleSqlService implements SqlService {
  private Map<String, String> sqlMap;

  public void setSqlMap(Map<String, String> sqlMap) {
    this.sqlMap = sqlMap;
  }

  @Override
  public String getSql(String key) throws SqlRetrievalFailureException {
    String sql = sqlMap.get(key);
    if (StringUtils.isBlank(sql)) {
      throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다.");
    } else {
      return sql;
    }
  }
}
