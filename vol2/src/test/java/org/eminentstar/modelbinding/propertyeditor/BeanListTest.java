package org.eminentstar.modelbinding.propertyeditor;

import org.eminentstar.util.BeanDefinitionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/spring-servlet-default.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BeanListTest {

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * TODO: AjaxController로 했을 떄 @Autowired가 안된 것과 뭐 그런 이슈들 한번 파보자.
   */
  @Test
  public void test() {
    // Given

    // When
    BeanDefinitionUtils.printBeanDefinitions((GenericApplicationContext)applicationContext);

    // Then

  }
}
