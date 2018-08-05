package org.eminentstar;

import org.eminentstar.ioc.bean.Hello;
import org.eminentstar.ioc.bean.Printer;
import org.eminentstar.ioc.bean.StringPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

  @Bean
  public Hello hello() {
    Hello hello = new Hello();
    hello.setName("Spring");
    hello.setPrinter(printer()); // 동일한 빈 돌려받음.

    return hello;
  }

  @Bean
  public Hello hello2() {
    Hello hello = new Hello();
    hello.setName("Spring2");
    hello.setPrinter(printer()); // 동일한 빈 돌려받음.

    return hello;
  }

  /**
   * 디폴트 메타정보 항목에 따라 이 메소들 정의되는 빈은 싱글톤.
   * 스프링의 특별한 조작을 통해 컨테이너에 등록된 HelloConfig 빈의
   * printer() 메소드는 매번 동일한 인스턴스를 리턴하도록 만들어짐.
   */
  @Bean
  public Printer printer() {
    return new StringPrinter();
  }
}
