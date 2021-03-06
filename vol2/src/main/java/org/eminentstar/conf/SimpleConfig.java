package org.eminentstar.conf;

import org.eminentstar.conf.extend.EnableHelloWithElementOfAnnotation;
import org.eminentstar.ioc.bean.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableHelloWithElementOfAnnotation(name = "eminent.star.importaware")
@Configuration
public class SimpleConfig {
  @Autowired
  public
  Hello hello;

  @Bean(name = "hello2")
  public Hello hello() {
    return new Hello();
  }

}
