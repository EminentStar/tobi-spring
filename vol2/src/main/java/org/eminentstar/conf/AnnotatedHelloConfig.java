package org.eminentstar.conf;

import org.eminentstar.ioc.bean.AnnotatedHello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AnnotatedHelloConfig {

  /**
   * @Bean이 붙은 메소드 하나가 하나의 빈을 정의함.
   * 메소드 이름이 등록되는 빈의 이름이 됨.
   */
  @Bean
  public AnnotatedHello annotatedHello() {
    // 자바 코드를 이용해 빈 오브젝트를 만들고, 초기화한 후에 리턴해줌. 컨테이너는 이 리턴 오브젝트를 빈으로 활용.
    return new AnnotatedHello();
  }

  @Bean
  @Scope("prototype")
  public AnnotatedHello annotatedHello2() {
    return new AnnotatedHello();
  }
}
