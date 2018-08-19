package org.eminentstar.di;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/di/applicationContextWithPropertiesFile.xml")
public class PropertiesTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Value("#{systemProperties['os.name']}")
  String osName;

  @Value("#{systemEnvironment['Path']}")
  String path;

  @Test
  public void test() {
    // Given
    DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);

    // When
    Class driverClass = dataSource.getClass();

    // Then
    System.out.println(dataSource.toString());
  }

  @Test
  public void systemPropertiesAndEnvironment() {
    System.out.println(osName);
    System.out.println(path);
  }

}
