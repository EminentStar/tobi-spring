package org.eminentstar.ioc.beanscanning;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNull.notNullValue;

import org.eminentstar.ioc.bean.AnnotatedHello;
import org.eminentstar.ioc.bean.AnnotatedHello2;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScanningTest {

  private ApplicationContext ctx;

  @Before
  public void setUp() {
    ctx = new AnnotationConfigApplicationContext("org.eminentstar.ioc.bean");
  }

  @Test
  public void simpleBeanScanning() {
    // Given

    // When
    AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);

    // Then
    assertThat(hello, is(notNullValue()));
  }

  @Test
  public void simpleBeanScanningWithBeanName() {
    // Given

    // When
    AnnotatedHello2 hello = ctx.getBean("myAnnotatedHello", AnnotatedHello2.class);

    // Then
    assertThat(hello, is(notNullValue()));
  }

}
