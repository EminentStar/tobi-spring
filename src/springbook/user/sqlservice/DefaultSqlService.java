package springbook.user.sqlservice;

public class DefaultSqlService extends BaseSqlService {

  /**
   * 생ㅅ어자에서 디폴트 의존 오브젝트를 직접 만들어서 스스로 DI 해줌.
   */
  public DefaultSqlService() {
    setSqlReader(new JaxbXmlSqlReader());
    setSqlRegistry(new HashMapSqlRegistry());
  }
}
