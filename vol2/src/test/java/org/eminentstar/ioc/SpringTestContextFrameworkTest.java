package org.eminentstar.ioc;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNull.notNullValue;

import org.eminentstar.ioc.bean.Hello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/genericApplicationContext.xml")
public class SpringTestContextFrameworkTest {
  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void applicationContextExistanceTest() {
    assertThat(applicationContext, is(notNullValue()));
    assertThat(applicationContext instanceof GenericApplicationContext, is(true));
  }

  @Test
  public void genericApplicationContextTest() {
    // Given

    // When
    Hello hello = applicationContext.getBean("hello", Hello.class);
    hello.print();

    // Then
    assertThat(applicationContext.getBean("printer").toString(), is("Hello Spring"));
  }
}
