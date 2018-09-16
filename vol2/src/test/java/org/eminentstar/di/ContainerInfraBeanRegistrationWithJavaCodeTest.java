package org.eminentstar.di;

import org.eminentstar.conf.AppConfig;
import org.eminentstar.ioc.bean.stereotype.Hello;
import org.eminentstar.ioc.bean.stereotype.exclude.ExcludeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContainerInfraBeanRegistrationWithJavaCodeTest {

  private ApplicationContext ctx;

  @Before
  public void setUp() {
    ctx = new AnnotationConfigApplicationContext(AppConfig.class);
  }

  @Test(expected = NoSuchBeanDefinitionException.class)
  public void excludeAnnotationTest() {
    // Given

    // When
    ExcludeService excludeService = ctx.getBean("excludeService", ExcludeService.class);

    // Then
  }

  @Test(expected = NoSuchBeanDefinitionException.class)
  public void excludeSpecificClassTest() {
    // Given

    // When
    Hello hello = ctx.getBean("hello", Hello.class);

    // Then
  }

}
