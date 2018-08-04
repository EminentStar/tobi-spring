package org.eminentstar.ioc;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNull.notNullValue;

import org.eminentstar.ioc.bean.Hello;
import org.eminentstar.ioc.bean.Printer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ContextLayeredArchitectureTest {
  private ApplicationContext parent;
  private GenericApplicationContext child;
  private XmlBeanDefinitionReader reader;

  @Before
  public void initializeParentAndChildApplicationContext() {
    // Given
    parent = new GenericXmlApplicationContext("parentContext.xml");

    child = new GenericApplicationContext(parent); // parent를 부모 컨텍스트로 가짐.
    reader = new XmlBeanDefinitionReader(child);
    reader.loadBeanDefinitions("childContext.xml");

    child.refresh(); // 이때 컨텍스트롤 초기화해주면서 DI를 진행함. child에서 필요한 빈이 존재하지 않으면 parent 컨텍스트에게 빈 검색 요청함.
  }

  @Test
  public void testWhenBeanDoesNotExistInChildApplicationContext() {
    // Given

    // When
    Printer printer = child.getBean("printer", Printer.class);

    // Then
    assertThat(printer, is(notNullValue()));
  }

  @Test
  public void testGettingBeanFromChildApplicationContext() {
    // Given

    // When
    Printer printer = child.getBean("printer", Printer.class);
    Hello hello = child.getBean("hello", Hello.class);

    // Then
    assertThat(hello, is(notNullValue()));

    hello.print();
    assertThat(printer.toString(), is("Hello Child"));
  }

}
