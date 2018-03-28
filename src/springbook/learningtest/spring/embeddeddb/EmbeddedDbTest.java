package springbook.learningtest.spring.embeddeddb;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.*;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class EmbeddedDbTest {
  EmbeddedDatabase db;
  SimpleJdbcTemplate template; // JdbcTemplate을 더 편리하게 사용할 수 있게 확장한 템플릿

  /**
   * 내장형 DB 빌더는 DB 엔진을 생성하고 초기화 스크립트를 실행해서 테이블과 초기 데이터를 준비한 뒤에
   * DB에 접근할 수 있는 Connection을 생성해주는 DataSource 오브젝트를 돌려줌.
   */
  @Before
  public void setUp() {
    db = new EmbeddedDatabaseBuilder() // 빌더 오브젝트 생성
      .setType(HSQL) //EmbeddedDatabaseType의 HSQL, DERBY, H2 중에서 하나를 선택.
      .addScript("classpath:/springbook/learningtest/spring/embeddeddb/schema.sql") // 테이블 생성과 테이블 초기화를 위해 사용할 SQL 문장을 담은 SQL 스크립트의 위치를 지정.(하나 이상 지정 가능)
      .addScript("classpath:/springbook/learningtest/spring/embeddeddb/data.sql")
      .build(); // 주어진 조건에 맞는 내장형 DB를 준비하고 초기화 스크립트를 모두 실행한 뒤에 이에 접근할 수 있는 EmbeddedDatabase를 돌려줌.

    // EmbeddedDatabase는 DataSource의 서브 인터페이스이므로 DataSource를 필요로 하는 SimpleJdbcTemplate을 만들 때 사용할 수 있음.
    template = new SimpleJdbcTemplate(db);
  }

  /**
   * 매 테스트를 진행한 뒤에 DB를 종료.
   * 내장형 메모리 DB는 따로 저장하지 않는 한 애플리케이션과 함께 매번 새로운 DB가 만들어지고 제거되는 생명주기를 갖음.
   */
  @After
  public void tearDown() {
    db.shutdown();
  }

  @Test
  public void initData() {
    assertThat(template.queryForInt("SELECT COUNT(*) FROM sqlmap"), is(2));

    List<Map<String, Object>> list = template.queryForList("SELECT * fROM sqlmap ORDER BY key_");
    assertThat(((String)list.get(0).get("key_")), is("KEY1"));
    assertThat(((String)list.get(0).get("sql_")), is("SQL1"));
    assertThat(((String)list.get(1).get("key_")), is("KEY2"));
    assertThat(((String)list.get(1).get("sql_")), is("SQL2"));
  }

  @Test
  public void insert() {
    template.update("INSERT INTO sqlmap(key_, sql_) VALUES(?,?)", "KEY3", "SQL3");

    assertThat(template.queryForInt("SELECT COUNT(*) FROM sqlmap"), is(3));
  }
}
