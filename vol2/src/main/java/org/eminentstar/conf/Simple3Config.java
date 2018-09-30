package org.eminentstar.conf;

import org.eminentstar.conf.extend.EnableHelloWithImportSelector;
import org.eminentstar.ioc.bean.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableHelloWithImportSelector(mode = "mode1")
@Configuration
public class Simple3Config {
  @Autowired
  public Hello hello;

  @Bean(name = "hello2")
  public Hello hello() {
    return new Hello();
  }

}
