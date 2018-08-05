package org.eminentstar.ioc.bean.hello.service.correct;

import org.eminentstar.ioc.bean.Hello;
import org.eminentstar.ioc.bean.Printer;
import org.eminentstar.ioc.bean.StringPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CorrectHelloService {

  private Printer printer;

  /**
   * TODO: 이렇게 setter method가 있으면 빈 검색 후 DI를 하는 건가? 이 동작원리를 아직 잘 파악 못했음.
   */
  public void setPrinter(Printer printer) {
    this.printer = printer;
  }

  @Bean
  public Hello hello() {
    Hello hello = new Hello();
    hello.setName("Spring");
    hello.setPrinter(this.printer);

    return hello;
  }

  @Bean
  public Hello hello2() {
    Hello hello = new Hello();
    hello.setName("Spring2");
    hello.setPrinter(this.printer);

    return hello;
  }

  @Bean
  private Printer printer() {
    return new StringPrinter();
  }

}
