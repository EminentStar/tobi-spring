package org.eminentstar.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(
  basePackages = {
    "org.eminentstar.learningtest.spring.controllers",
    "org.eminentstar.mvc.controller",
    "org.eminentstar.mvc.service"
  }
)
public class WebConfig {
  @Bean
  public ViewResolver getInternalResourceViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/view/");
    resolver.setSuffix(".jsp");

    return resolver;
  }
}
