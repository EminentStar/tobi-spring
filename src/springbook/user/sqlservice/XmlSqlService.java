package springbook.user.sqlservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService, SqlRegistry, SqlReader {
  private SqlReader sqlReader;
  private SqlRegistry sqlRegistry; // 의존 오브젝트를 DI 받을 수 있도록 인터페이스 타입의 프로퍼티로 선언해둠.

  private Map<String, String> sqlMap = new HashMap<String, String>();

  private String sqlmapFile;

  public void setSqlmapFile(String sqlmapFile) {
    this.sqlmapFile = sqlmapFile;
  }

  public void setSqlReader(SqlReader sqlReader) {
    this.sqlReader = sqlReader;
  }

  public void setSqlRegistry(SqlRegistry sqlRegistry) {
    this.sqlRegistry = sqlRegistry;
  }

  public XmlSqlService() {
  }

  /**
   * @PostContruct를 초기화 작업을 수행할 메소드에 부여해주면
   * 스프링은 XmlSqlService 클래스로 등록된 빈의 오브젝트를 생성하고 DI 작업을 마친 뒤에 @PostContruct가 붙은 메소드를 자동으로 실행해줌.
   *
   * 생성자와는 달리 프로퍼티까지 모두 준비된 후에 실행된다는 면에서 @PostConstruct 초기화 메소드는 매우 유용함.
   */
  @PostConstruct // 빈의 초기화 메소드로 지정함.
  public void loadSql() {
    this.sqlReader.read(this.sqlRegistry);
  }

  @Override
  public String getSql(String key) throws SqlRetrievalFailureException {
    try {
      return this.sqlRegistry.findSql(key);
    } catch (RuntimeException e) {
      throw new SqlRetrievalFailureException(e.getMessage());
    }
  }

  @Override
  public String findSql(String key) throws RuntimeException {
    String sql = sqlMap.get(key);

    if (StringUtils.isBlank(sql)) {
      throw new RuntimeException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
    } else {
      return sql;
    }
  }

  @Override
  public void registerSql(String key, String sql) {
    sqlMap.put(key, sql);
  }

  @Override
  public void read(SqlRegistry sqlRegistry) { // loadSql()에 있던 코드를 SqlReader 메소드로 가져온다. 초기화를 위해 무엇을 할 것인가와 SQL을 어떻게 읽는지를 분리.
    String contextPath = Sqlmap.class.getPackage().getName();

    try {
      JAXBContext context = JAXBContext.newInstance(contextPath);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile); // UserDao와 같은 클래스패스의 sqlmap.xml 파일을 변환함.
      Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);

      for (SqlType sql : sqlmap.getSql()) {
        sqlRegistry.registerSql(sql.getKey(), sql.getValue()); // SQL 저장 로직 구현에 독립적인 인터페이스 메소드를 통해 읽어들인 SQL과 키를 전달.
      }

    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }
}
