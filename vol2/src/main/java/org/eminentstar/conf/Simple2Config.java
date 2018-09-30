package org.eminentstar.conf;

import org.eminentstar.conf.extend.EnableHelloWithConfigurer;
import org.eminentstar.conf.extend.HelloConfigurer;
import org.eminentstar.ioc.bean.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableHelloWithConfigurer
@Configuration
public class Simple2Config implements HelloConfigurer {
  @Autowired
  public
  Hello hello;

  @Bean(name = "hello2")
  public Hello hello() {
    return new Hello();
  }

  @Override
  public void configName(Hello hello) {
    hello.setName("eminent.star.configurer");
  }
}
