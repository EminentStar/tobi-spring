package org.eminentstar.conf;

import org.eminentstar.ioc.bean.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleConfig {
  @Autowired
  public
  Hello hello;

  @Bean
  public Hello hello() {
    return new Hello();
  }
}
