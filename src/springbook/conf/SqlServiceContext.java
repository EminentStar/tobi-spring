package springbook.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import springbook.user.dao.UserDao;
import springbook.user.sqlservice.OxmSqlService;
import springbook.user.sqlservice.SqlRegistry;
import springbook.user.sqlservice.SqlService;
import springbook.user.sqlservice.updatable.EmbeddedDbSqlRegistry;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
public class SqlServiceContext {
  @Bean
  public SqlService sqlService() {
    OxmSqlService oxmSqlService = new OxmSqlService();

    oxmSqlService.setUnmarsaller(unmarshaller());
    oxmSqlService.setSqlRegistry(sqlRegistry());
    oxmSqlService.setSqlmap(new ClassPathResource("sqlmap.xml", UserDao.class));

    return oxmSqlService;
  }

  @Bean
  public Unmarshaller unmarshaller() {
    Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
    unmarshaller.setContextPath("springbook.user.sqlservice.jaxb");
    return unmarshaller;
  }

  @Bean
  public SqlRegistry sqlRegistry() {
    EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
    sqlRegistry.setDataSource(embeddedDatabase());

    return sqlRegistry;
  }

  @Bean
  public DataSource embeddedDatabase() {
    return new EmbeddedDatabaseBuilder() // 빌더 오브젝트 생성
      .setName("embeddedDatabase")
      .setType(HSQL) //EmbeddedDatabaseType의 HSQL, DERBY, H2 중에서 하나를 선택.
      .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql") // 테이블 생성과 테이블 초기화를 위해 사용할 SQL 문장을 담은 SQL 스크립트의 위치를 지정.(하나 이상 지정 가능)
      .build(); // 주어진 조건에 맞는 내장형 DB를 준비하고 초기화 스크립트를 모두 실행한 뒤에 이에 접근할 수 있는 EmbeddedDatabase를 돌려줌.
  }

}
