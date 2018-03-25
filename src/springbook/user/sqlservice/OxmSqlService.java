package springbook.user.sqlservice;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Unmarshaller;

import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

public class OxmSqlService implements SqlService {
  private final BaseSqlService baseSqlService = new BaseSqlService();

  /**
   * final이브로 변경 불가능. OxmSqlServiec와 OxmSqlReader는 강하게 결합돼서 하나의 빈으로 등록되고 한 번에 설정할 수 있음.
   *
   * 이렇게 두 개의 클래스(OxmSqlService - OxmSqlReader)를 강하게 결합해서 확장이나 변경을 제한하는 이유는
   * OXM을 이용하는 서비스 구조로 최적화하기 위해서임.
   */
  private final OxmSqlReader oxmSqlReader = new OxmSqlReader();

  /**
   * oxmSqlReader와 달리 단지 디폴트 오브젝트로 만들어진 프로퍼티. 따라서 필요에 따라 DI를 통해 교체 가능.
   */
  private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

  public void setSqlRegistry(SqlRegistry sqlRegistry) {
    this.sqlRegistry = sqlRegistry;
  }

  /**
   * # setUnmarshaller(), setSqlmapFile()
   *
   * OxmSqlReader의 공개된 프로퍼티를 통해 DI 받은 것을 그대로 멤버 클래스의 오브젝트에 전달.
   * 이 setter들은 단일 빈 설정구조를 위한 창구 역할을 할 뿐임.
   */
  public void setUnmarsaller(Unmarshaller unmarsaller) {
    this.oxmSqlReader.setUnmarshaller(unmarsaller);
  }

  public void setSqlmapFile(String sqlmapFile) {
    this.oxmSqlReader.setSqlmapFile(sqlmapFile);
  }

  /**
   * OxmSqlService의 프로퍼티를 통해서 초기화된 SqlReader와 SqlRegistry를 실제 작업을 위임할 대상인 baseSqlService에 주입함.
   */
  @PostConstruct
  public void loadSql() {
    this.baseSqlService.setSqlReader(this.oxmSqlReader);
    this.baseSqlService.setSqlRegistry(this.sqlRegistry);

    this.baseSqlService.loadSql(); // SQL을 등록하는 초기화 작업을 baseSqlService에 위임.
  }

  @Override
  public String getSql(String key) throws SqlRetrievalFailureException {
    return this.baseSqlService.getSql(key); // SQL을 찾는 작업도 baseSqlService에 위임.
  }

  /**
   * private 멤버 클래스로 정의. top level class인 OxmSqlService만이 사용가능.
   */
  private class OxmSqlReader implements SqlReader {
    private final static String DEFAULT_SQLMAP_FILE = "sqlmap.xml";

    private Unmarshaller unmarshaller;
    private String sqlmapFile = DEFAULT_SQLMAP_FILE;

    public void setUnmarshaller(Unmarshaller unmarshaller) {
      this.unmarshaller = unmarshaller;
    }

    public void setSqlmapFile(String sqlmapFile) {
      this.sqlmapFile = sqlmapFile;
    }

    @Override
    public void read(SqlRegistry sqlRegistry) {

      try {
        Source source = new StreamSource(
          UserDao.class.getResourceAsStream(this.sqlmapFile));
        Sqlmap sqlmap = (Sqlmap)this.unmarshaller.unmarshal(source);

        for (SqlType sql : sqlmap.getSql()) {
          sqlRegistry.registerSql(sql.getKey(), sql.getValue());
        }
      } catch (IOException e) {
        throw new IllegalArgumentException(this.sqlmapFile + "을 가져올 수 없습니다.", e);
      }
    }
  }

}
