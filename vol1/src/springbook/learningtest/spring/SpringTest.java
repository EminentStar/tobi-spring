package springbook.learningtest.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/***
 * TODO: 학습테스트 예정
 *
 * - 스프링이 정말 싱글톤 방식으로 빈 오브젝트를 생성하는가?
 * - 스프링 테스트 컨텍스트에서 @Autowired로 가져온 빈 오브젝트가
 *   정말 애플리케이션 컨텍스트에서 직접 getBean()으로 가져오는 것과 동일한가?
 * - XML에서 스트링 타입의 프로퍼티 값을 설정한 것이 정말 빈에 잘 주입되는가?
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "spring.xml")
public class SpringTest {
}
