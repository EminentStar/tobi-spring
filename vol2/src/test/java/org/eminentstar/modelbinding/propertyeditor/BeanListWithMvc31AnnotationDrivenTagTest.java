package org.eminentstar.modelbinding.propertyeditor;

import org.eminentstar.util.BeanDefinitionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/spring-servlet-default-with-mvc3.1driventag.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BeanListWithMvc31AnnotationDrivenTagTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void test() {
    // Given

    // When
    BeanDefinitionUtils.printBeanDefinitions((GenericApplicationContext)applicationContext);

    // Then

  }
}
