package org.eminentstar.di;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

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
@ContextConfiguration("/di/applicationContextWithXmlAutowiringForDI.xml")
public class XmlAutowiringForDITest {
  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void test() {
    // Given
    Hello hello = applicationContext.getBean("hello", Hello.class);

    // When
    Printer printer = hello.getPrinter();

    // Then
    assertThat(printer instanceof StringPrinter, is(true));
  }
}
