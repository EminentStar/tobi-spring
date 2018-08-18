package org.eminentstar.conf;

import org.eminentstar.ioc.bean.Hello;
import org.eminentstar.ioc.bean.Printer;
import org.eminentstar.ioc.bean.StringPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

  /**
   * @Bean이 붙은 메소드는 기본적으로 @Autowired가 붙은 메소드처럼 동작함.
   */
  @Bean
  public Hello hello(Printer printer) {
    Hello hello = new Hello();
    hello.setPrinter(printer);

    return hello;
  }

  /**
   * hello(Printer printer)의 printer 파라미터로 지정 시,
   * @Autowired한 것과 동일하게 파라미터로 Printer 타입의 빈 정보가 제공됨.
   */
  @Bean
  public Printer printer() {
    return new StringPrinter();
  }

}
