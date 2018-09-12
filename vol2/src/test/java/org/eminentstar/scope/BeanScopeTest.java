package org.eminentstar.scope;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class BeanScopeTest {

  @Test
  public void singletonScope() {
    // Given
    ApplicationContext ac = new AnnotationConfigApplicationContext(
      SingletonBean.class, SingletonClientBean.class
    );
    Set<SingletonBean> beans = new HashSet<SingletonBean>();

    // When
    beans.add(ac.getBean(SingletonBean.class));
    beans.add(ac.getBean(SingletonBean.class));

    // Then
    assertThat(beans.size(), is(1));
  }

  @Test
  public void prototypeScope() {
    // Given
    ApplicationContext ac = new AnnotationConfigApplicationContext(
      PrototypeBean.class, PrototypeClientBean.class
    );
    Set<PrototypeBean> beans = new HashSet<PrototypeBean>();

    // When
    // Then
    beans.add(ac.getBean(PrototypeBean.class));
    assertThat(beans.size(), is(1));

    beans.add(ac.getBean(PrototypeBean.class));
    assertThat(beans.size(), is(2));

    beans.add(ac.getBean(PrototypeBean.class));
    assertThat(beans.size(), is(3));

    beans.add(ac.getBean(PrototypeBean.class));
    assertThat(beans.size(), is(4));
  }

  static class SingletonBean {}
  static class SingletonClientBean {
    @Autowired SingletonBean bean1;
    @Autowired SingletonBean bean2;
  }

  @Scope("prototype")
  static class PrototypeBean {}
  static class PrototypeClientBean {
    @Autowired PrototypeBean bean1;
    @Autowired PrototypeBean bean2;
  }
}
