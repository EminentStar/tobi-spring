package springbook.user.sqlservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

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

  public void setSqlmap(Resource sqlmap) {
    this.oxmSqlReader.setSqlmap(sqlmap);
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
    private Unmarshaller unmarshaller;
    private Resource sqlmap = new ClassPathResource("/sqlmap.xml");

    public void setUnmarshaller(Unmarshaller unmarshaller) {
      this.unmarshaller = unmarshaller;
    }

    public void setSqlmap(Resource sqlmap) {
      this.sqlmap = sqlmap;
    }

    /**
     * 클래스패스로부터 리소스를 가져오기 위해 ClassLoader 클래스의 getResourceAsStream() 메소드를 사용.
     *
     * - 파일 시스템이나 웹상의 HTTP를 통해 접근 가능한 파일로 바꾸려면 URL 클래스를 사용하도록 코드를 변경해야함.
     * - 서블릿 컨텍스트 내의 리소스를 가져오려면 ServletContext의 getResourceAsStream()을 사용해야 함.
     *
     * 위와 같이 목적은 동일하지만 사용법이 각기 다른 여러 가지 기술이 존재한다고 볼 수 있음.
     *
     * -> 스프링은 자바에 존재하는 일관성 없는 리소스 접근 API를 추상화해서 Resource라는 추상화 인터페이스를 정의.
     * 애플리케이션 컨텍스트가 사용할 설정정보 파일을 지정하는 것 부터 시작해서 스프링의 거의 모든 API는
     * 외부의 리소스 정보가 필요할 때 항상 이 Resource 추상화를 이용.
     *
     * Resource는 스프링에서 Bean이 아니라 값으로 취금됨.
     *
     * 문자열로 정의된 리소스를 Resource 타입 오브젝트로 변환해주는 ResourceLoader를 제공함.
     * ResourceLoader의 대표적인 예가 스프링의 애플리케이션 컨텍스트.(ResourceLoader를 상속함.)
     *
     * Resource 타입은 빈으로 등록하지 않고 <property> 태그의 value를 사용해 문자열로 값을 넣는데,
     * 이 문자열로 된 리소스 정보를 Resource 오브젝트로 변환해서 프로퍼티에 주입할 때도 애플리케이션 컨텍스트 자신이 리소스 로더로서 변환과 로딩 기능을 담당함.
     *
     * Resource 오브젝트가 실제 리소스는 아니라는 점을 주의; 단지 리소스에 접근할 수 있는 추상화된 핸들러.
     */
    @Override
    public void read(SqlRegistry sqlRegistry) {

      try {
        Source source = new StreamSource(sqlmap.getInputStream()); // 리소스의 종류에 상관없이 스트림으로 가져올 수 있음.
        Sqlmap sqlmap = (Sqlmap)this.unmarshaller.unmarshal(source);

        for (SqlType sql : sqlmap.getSql()) {
          sqlRegistry.registerSql(sql.getKey(), sql.getValue());
        }
      } catch (IOException e) {
        throw new IllegalArgumentException(this.sqlmap.getFilename() + "을 가져올 수 없습니다.", e);
      }
    }
  }

}
