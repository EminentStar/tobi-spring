package org.eminentstar.ioc.beanscanning;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNull.notNullValue;

import org.eminentstar.conf.AnnotatedHelloConfig;
import org.eminentstar.ioc.bean.AnnotatedHello;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanRegistrationWithJavaCodeTest {

  private ApplicationContext ctx;

  @Before
  public void setUp() {
    ctx = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
  }

  @Test
  public void test() {
    // Given

    // When
    AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);

    // Then
    assertThat(hello, is(notNullValue()));
  }

  @Test
  public void configurationClassBeanTest() {
    // Given

    // When
    AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);

    // Then
    assertThat(config, is(notNullValue()));
  }

  @Test
  public void objectOfBeanAnnotationMethodIsSingleton() {
    // Given
    AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);

    // When
    AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
    AnnotatedHello hello2 = config.annotatedHello();

    // Then
    assertThat(hello, is(sameInstance(hello2)));
  }

  @Test
  public void objectOfBeanAnnotationMethodIsPrototype() {
    // Given
    AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);

    // When
    AnnotatedHello hello = ctx.getBean("annotatedHello2", AnnotatedHello.class);
    AnnotatedHello hello2 = config.annotatedHello2();

    // Then
    assertThat(hello, is(not(sameInstance(hello2))));
  }

  @Test
  public void beanObjectCreationOutsideSpringContainer() {
    // Given
    AnnotatedHelloConfig config = new AnnotatedHelloConfig();

    // When
    AnnotatedHello hello = config.annotatedHello();
    AnnotatedHello hello2 = config.annotatedHello();

    // Then
    assertThat(hello, is(not(sameInstance(hello2))));
  }

}
