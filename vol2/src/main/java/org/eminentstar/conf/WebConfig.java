package org.eminentstar.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(
  basePackages = {
    "org.eminentstar.learningtest.spring.controllers",
    "org.eminentstar.mvc.controller",
    "org.eminentstar.mvc.service",
    "org.eminentstar.modelbinding.propertyeditor"
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

  /***
   * WebBindingInitializer를 구현해서 만든 클래스를 빈으로 등록하고
   * @Controller를 담당하는 AnnotationMethodHandlerAdapter의 webBindingInitializer 프로퍼티에 DI 해줌.
   * 프로퍼티 설정을 위해선 AnnotationMethodHandlerAdapter 또한 빈으로 직접 등록해줘야함.
   */
  @Bean
  public AnnotationMethodHandlerAdapter getAnnotationMethodHandlerAdapter() {
    AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
    adapter.setWebBindingInitializer(getMyWebBindingInitializer());

    return adapter;
  }

  @Bean
  public MyWebBindingInitializer getMyWebBindingInitializer() {
    return new MyWebBindingInitializer();
  }
}
