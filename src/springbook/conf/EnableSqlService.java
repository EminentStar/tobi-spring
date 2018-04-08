package springbook.conf;

import org.springframework.context.annotation.Import;

/**
 * 이와 유사한 애노테이션으로 <tx:annotation-driven />과 가능이 동일한 @EnableTransactionManagement
 *
 * @EnableSqlService를 사용한다는 것은 결국 SqlServiceContext 설정 클래스를 @Import하는 셈임.
 *
 * 또한 @EnableSqlService("classpath:/springbook/user/sqlmap.xml") 과 같이 수정해서
 * SqlMapConfig 인터페이스를 통해 SqlServiceContext에 SQL 매핑파일을 전달하게 했던 방식을 간결히 만들 수도 있다.
 */
@Import(value=SqlServiceContext.class)
public @interface EnableSqlService {
}
