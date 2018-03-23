package springbook.user.sqlservice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService {
  private Map<String, String> sqlMap = new HashMap<String, String>();

  /**
   * 스프링이 오브젝트를 만드는 시점에서 SQL을 읽어오도록 생성자를 이용.
   */
  public XmlSqlService() {
    String contextPath = Sqlmap.class.getPackage().getName();

    try {
      JAXBContext context = JAXBContext.newInstance(contextPath);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      InputStream is = UserDao.class.getResourceAsStream("sqlmap.xml"); // UserDao와 같은 클래스패스의 sqlmap.xml 파일을 변환함.
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
