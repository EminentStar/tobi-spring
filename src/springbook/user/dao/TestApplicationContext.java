package springbook.user.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @Configuration: DI 정보로 사용될 자바 클래스로 지정
 * (Chap 1.5 에서 DaoFactory 클래스를 스프링 컨테이너가 사용하는 IoC/DI 정보로 활용되게 할 때 이미 사용)
 */
@Configuration
@ImportResource("/test-applicationContext.xml") // 현재 이 클래스엔 아무 DI 정보도 없으니 우선 xml로부터 설정을 import
public class TestApplicationContext {
}
