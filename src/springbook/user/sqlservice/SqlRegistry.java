package springbook.user.sqlservice;

/**
 * SQL을 제공받아 등록해뒀다가 키로 검색해서 돌려주는 기능을 담당.
 */
public interface SqlRegistry {
  void registerSql(String key, String sql); // SQL을 키와 함께 등록

  String findSql(String key) throws RuntimeException; // 키로 SQL을 검색. 검색이 실패하면 예외를 던짐.
}
