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

public class XmlSqlService implements SqlService {
  private Map<String, String> sqlMap = new HashMap<String, String>();

  private String sqlmapFile;

  public void setSqlmapFile(String sqlmapFile) {
    this.sqlmapFile = sqlmapFile;
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
    String contextPath = Sqlmap.class.getPackage().getName();

    try {
      JAXBContext context = JAXBContext.newInstance(contextPath);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile); // UserDao와 같은 클래스패스의 sqlmap.xml 파일을 변환함.
      Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(is);

      for (SqlType sql : sqlmap.getSql()) {
        sqlMap.put(sql.getKey(), sql.getValue());
      }

    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public String getSql(String key) throws SqlRetrievalFailureException {
    String sql = sqlMap.get(key);

    if (StringUtils.isBlank(sql)) {
      throw new SqlRetrievalFailureException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
    } else {
      return sql;
    }
  }
}
