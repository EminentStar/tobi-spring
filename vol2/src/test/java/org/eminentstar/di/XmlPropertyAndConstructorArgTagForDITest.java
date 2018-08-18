package org.eminentstar.di;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eminentstar.ioc.bean.Hello;
import org.eminentstar.ioc.bean.Printer;
import org.eminentstar.ioc.bean.StringPrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/di/applicationContextWithXmlPropertyAndConstructorArgTagForDI.xml")
public class XmlPropertyAndConstructorArgTagForDITest {
  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void test() {
    // Given
    Hello hello = applicationContext.getBean("hello", Hello.class);

    // When
    String name = hello.getName();
    Printer printer = hello.getPrinter();

    // Then
    assertThat(name, is("Spring"));
    assertThat(printer instanceof StringPrinter, is(true));
  }
}
