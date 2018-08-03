package org.eminentstar.ioc;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.eminentstar.ioc.bean.Hello;
import org.eminentstar.ioc.bean.StringPrinter;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

public class StaticAndGenericApplicationContextTest {

  @Test
  public void registerBeanToApplicationContextDirectly() {
    // Given
    StaticApplicationContext ac = new StaticApplicationContext(); // 컨테이너 생성
    ac.registerSingleton("hello1", Hello.class); // Hello 클래스를 hello1이라는 이름의 싱글톤 빈으로 컨테이너에 등록.

    // When
    Hello hello1 = ac.getBean("hello1", Hello.class);

    // Then
    assertThat(hello1, is(notNullValue()));
  }

  @Test
  public void registerBeanToApplicationContextWithBeanDefinitionObject() {
    // Given
    StaticApplicationContext ac = new StaticApplicationContext(); // 컨테이너 생성

    BeanDefinition helloDef = new RootBeanDefinition(Hello.class); // 빈 메타정보를 담은 오브젝트 생성.
    helloDef.getPropertyValues().addPropertyValue("name", "Spring");

    // When
    ac.registerBeanDefinition("hello2", helloDef);
    Hello hello2 = ac.getBean("hello2", Hello.class);

    // Then
    assertThat(hello2.sayHello(), is("Hello Spring"));
  }

  @Test
  public void test() {
    // Given
    StaticApplicationContext ac = new StaticApplicationContext(); // 컨테이너 생성

    ac.registerSingleton("hello1", Hello.class); // Hello 클래스를 hello1이라는 이름의 싱글톤 빈으로 컨테이너에 등록.

    BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
    helloDef.getPropertyValues().addPropertyValue("name", "Spring");
    ac.registerBeanDefinition("hello2", helloDef);

    // When
    Hello hello1 = ac.getBean("hello1", Hello.class);
    Hello hello2 = ac.getBean("hello2", Hello.class);

    // Then
    assertThat(hello1, is(not(hello2)));
    assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
  }

  @Test
  public void registerBeanWithDependency() {
    // Given
    StaticApplicationContext ac = new StaticApplicationContext();

    ac.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));

    BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
    helloDef.getPropertyValues().addPropertyValue("name", "Spring");
    helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

    ac.registerBeanDefinition("hello", helloDef);

    // When
    Hello hello = ac.getBean("hello", Hello.class);
    hello.print();

    // Then
    assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
  }

  @Test
  public void genericApplicationContextTest() {
    // Given
    GenericApplicationContext ac = new GenericApplicationContext();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
    reader.loadBeanDefinitions("genericApplicationContext.xml");

    ac.refresh(); // 모든 메타정보가 등록이 완료됬으니 애플리케이션 컨테이너를 초기화하라는 명령.

    // When
    Hello hello = ac.getBean("hello", Hello.class);
    hello.print();

    // Then
    assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
  }

}
