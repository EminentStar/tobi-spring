package org.eminentstar.di;

import org.eminentstar.conf.SimpleConfig;
import org.eminentstar.util.BeanDefinitionUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ContainerInfrastructureBeanTest {

  @Test(expected = NullPointerException.class)
  public void occuredNullPointerExceptionWhenCallingABeanObjectInConfigurationClassByRegisteringConfigurationClassDirectly() {
    // Given
    ApplicationContext context = new GenericXmlApplicationContext("/di/applicationContextWithContainerInfrastructureBeanWithoutComponentScan.xml");

    // When
    SimpleConfig sc = context.getBean(SimpleConfig.class);

    // Then
    sc.hello.getName();
  }

  @Test
  public void registeredBeanObjectsInConfigurationClassWithComponentScan() {
    // Given
    ApplicationContext context = new GenericXmlApplicationContext("/di/applicationContextWithContainerInfrastructureBean.xml");

    // When
    SimpleConfig sc = context.getBean(SimpleConfig.class);

    // Then
    sc.hello.getName();
  }

  @Test
  public void showRegisteredBeans() {
    // Given
    ApplicationContext context = new GenericXmlApplicationContext("/di/applicationContextWithContainerInfrastructureBean.xml");

    // When
    // Then
    BeanDefinitionUtils.printBeanDefinitions((GenericApplicationContext)context);
  }

}
