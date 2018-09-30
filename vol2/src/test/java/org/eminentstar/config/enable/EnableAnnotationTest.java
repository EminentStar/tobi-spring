package org.eminentstar.config.enable;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.eminentstar.conf.Simple2Config;
import org.eminentstar.conf.Simple3Config;
import org.eminentstar.conf.SimpleConfig;
import org.eminentstar.ioc.bean.Hello;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EnableAnnotationTest {

  private ApplicationContext ctx1;
  private ApplicationContext ctx2;
  private ApplicationContext ctx3;

  @Before
  public void setUp() {
    ctx1 = new AnnotationConfigApplicationContext(SimpleConfig.class);
    ctx2 = new AnnotationConfigApplicationContext(Simple2Config.class);
    ctx3 = new AnnotationConfigApplicationContext(Simple3Config.class);
  }

  @Test
  public void enableAnnotationWithImportAware() {
    // given

    // when
    Hello hello = ctx1.getBean("hello", Hello.class);

    // then
    assertThat(hello.getName(), is("eminent.star.importaware"));
  }

  @Test
  public void enableAnnotationWithConfigurer() {
    // given

    // when
    Hello hello = ctx2.getBean("hello", Hello.class);

    // then
    assertThat(hello.getName(), is("eminent.star.configurer"));
  }

  @Test
  public void enableAnnotationWithImportSelector() {
    // given

    // when
    Hello hello = ctx3.getBean("hello", Hello.class);

    // then
    assertThat(hello.getName(), is("mode1"));
  }

}
