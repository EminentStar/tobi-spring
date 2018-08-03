package org.eminentstar.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eminentstar.ioc.bean.Hello;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

public class GenericXmlApplicationContextTest {
  @Test
  public void test() {
    // Given
    GenericXmlApplicationContext ac =
      new GenericXmlApplicationContext("genericApplicationContext.xml");

    // When
    Hello hello = ac.getBean("hello", Hello.class);
    hello.print();

    // Then
    assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
  }
}
