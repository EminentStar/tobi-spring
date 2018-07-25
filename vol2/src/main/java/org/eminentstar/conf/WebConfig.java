package org.eminentstar.conf;

import java.util.HashSet;
import java.util.Set;

import org.eminentstar.modelbinding.converter.LevelToStringConverter;
import org.eminentstar.modelbinding.converter.StringToLevelConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
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
   * PropertyEditor의 모든 컨트롤러 일괄 적용을 원할때.
   *
   * WebBindingInitializer를 구현해서 만든 클래스를 빈으로 등록하고
   * @Controller를 담당하는 AnnotationMethodHandlerAdapter의 webBindingInitializer 프로퍼티에 DI 해줌.
   * 프로퍼티 설정을 위해선 AnnotationMethodHandlerAdapter 또한 빈으로 직접 등록해줘야함.
   *
   * -----
   * ConverterService는 따로 클래스를 만들 필요없이 빈으로 등록 후 어뎁터에 넣으면 됨.
   *
   */
  @Bean
  public AnnotationMethodHandlerAdapter getAnnotationMethodHandlerAdapter() {
    AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
    //    adapter.setWebBindingInitializer(getMyWebBindingInitializer());
    adapter.setWebBindingInitializer(getConfigurableWebBindingInitializer());
    adapter.setMessageConverters(new HttpMessageConverter[] {new MappingJacksonHttpMessageConverter()});

    return adapter;
  }

  @Bean
  public MyWebBindingInitializer getMyWebBindingInitializer() {
    return new MyWebBindingInitializer();
  }

  @Bean
  public ConversionServiceFactoryBean getConversionServiceFactoryBean() {
    ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();

    Set converters = new HashSet();
    converters.add(getLevelToStringConverter());
    converters.add(getStringToLevelConverter());

    conversionServiceFactoryBean.setConverters(converters);
    return conversionServiceFactoryBean;
  }

  @Bean
  public LevelToStringConverter getLevelToStringConverter() {
    return new LevelToStringConverter();
  }

  @Bean
  public StringToLevelConverter getStringToLevelConverter() {
    return new StringToLevelConverter();
  }

  @Bean
  public ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer() {
    ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
    initializer.setConversionService(getConversionService());

    return initializer;
  }

  @Bean
  public ConversionService getConversionService() {
    GenericConversionService conversionService = new GenericConversionService();
    conversionService.addConverter(getLevelToStringConverter());
    conversionService.addConverter(getStringToLevelConverter());

    return conversionService;
  }

}
