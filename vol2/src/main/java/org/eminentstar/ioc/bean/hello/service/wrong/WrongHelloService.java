package org.eminentstar.ioc.bean.hello.service.wrong;

import org.eminentstar.ioc.bean.Hello;
import org.eminentstar.ioc.bean.Printer;
import org.eminentstar.ioc.bean.StringPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class WrongHelloService {

  @Bean
  public Hello hello() {
    Hello hello = new Hello();
    hello.setName("Spring");
    hello.setPrinter(printer());

    return hello;
  }

  @Bean
  public Hello hello2() {
    Hello hello = new Hello();
    hello.setName("Spring2");
    hello.setPrinter(printer());

    return hello;
  }

  @Bean
  public Printer printer() {
    return new StringPrinter();
  }
}
