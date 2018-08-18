package org.eminentstar.di;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class AnnotationConfigForDITest {

  @Test
  public void simpleAtAutowired() {
    // Given
    AbstractApplicationContext ac = new AnnotationConfigApplicationContext(BeanA.class, BeanB.class);

    // When
    BeanA beanA = ac.getBean(BeanA.class);

    // Then
    assertThat(beanA.beanB, is(notNullValue()));
  }


  private static class BeanA {
    @Autowired
    BeanB beanB;
  }

  private static class BeanB {
  }

}
