package springbook.user.sqlservice;

/**
 * SQL을 제공해주는 기능을 독립.
 *
 * SQL에 대한 키 값을 전달하면 그에 해당하는 SQL을 리턴.
 */
public interface SqlService {
  /**
   *
   * @throws SqlRetrievalFailureException Runtime 예외이므로 특별히 복구해야 할 필요가 없다면 무시해도 됨.
   */
  String getSql(String key) throws SqlRetrievalFailureException;
}
