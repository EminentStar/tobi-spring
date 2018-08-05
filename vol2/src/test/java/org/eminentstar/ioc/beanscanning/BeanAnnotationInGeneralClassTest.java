package org.eminentstar.ioc.beanscanning;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.eminentstar.ioc.bean.Hello;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanAnnotationInGeneralClassTest {

  @Test
  public void testBeanMethodIsNotOperatedAsSingleton() {
    // Given
    ApplicationContext ctx = new AnnotationConfigApplicationContext(
      "org.eminentstar.ioc.bean.hello.service.wrong"
    );

    // When
    Hello hello = ctx.getBean("hello", Hello.class);
    Hello hello2 = ctx.getBean("hello2", Hello.class);

    // Then
    assertThat(hello.getPrinter(), is(not(sameInstance(hello2.getPrinter()))));
  }

  @Test
  public void testBeanMethodIsOperatedAsSingleton() {
    // Given
    ApplicationContext ctx = new AnnotationConfigApplicationContext(
      "org.eminentstar.ioc.bean.hello.service.correct"
    );

    // When
    Hello hello = ctx.getBean("hello", Hello.class);
    Hello hello2 = ctx.getBean("hello2", Hello.class);

    // Then
    assertThat(hello.getPrinter(), is(sameInstance(hello2.getPrinter())));
  }
}
