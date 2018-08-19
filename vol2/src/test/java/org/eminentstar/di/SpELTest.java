package org.eminentstar.di;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.eminentstar.ioc.bean.Hello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/di/applicationContextWithSpEL.xml")
public class SpELTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void referenceOtherBeansProperty() {
    // Given
    Hello hello = applicationContext.getBean("hello", Hello.class);
    Hello hello2 = applicationContext.getBean("hello2", Hello.class);

    // When

    // Then
    assertThat(hello2.getName(), is(hello.getName()));
  }

  @Test
  public void beanWithSpELProperty() {
    // Given
    DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);

    // When
    Class driverClass = dataSource.getClass();

    // Then
    System.out.println(dataSource.toString());
  }

  @Test
  public void propertiesBean() throws IOException {
    // Given
    String[] beanNames = applicationContext.getBeanDefinitionNames();

    // When
    Properties properties = applicationContext.getBean("dbprops", Properties.class);

    // Then
    assertThat(properties.getProperty("db.driverClass"), is("com.mysql.jdbc.Driver"));
    assertThat(properties.getProperty("db.url"), is("jdbc:mysql://localhost/testdb"));
    assertThat(properties.getProperty("db.username"), is("spring"));
    assertThat(properties.getProperty("db.password"), is("book"));

  }

}
