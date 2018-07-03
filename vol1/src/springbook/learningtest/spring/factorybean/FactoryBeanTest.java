package springbook.learningtest.spring.factorybean;



import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration // 설정파일 이름을 지정하지 않으면 클래스 이름 + "context.xml"이 디폴트로 사용됨.
public class FactoryBeanTest {
  @Autowired
  ApplicationContext context;

  @Test
  public void getMessageFromFactoryBean() {
    // Given
    Object message = context.getBean("message"); //getBean()은 리턴 타입을 지정해주지 않으면 Object 타입으로 리턴

    // When
    assertEquals(message.getClass(), Message.class);

    // Then
    assertThat(((Message)message).getText(), is("Factory Bean"));
  }

  @Test
  public void getFactoryBean() throws Exception {
    Object factory = context.getBean("&message"); // &가 붙고 안 붙고에 따라 getBean() 메소드가 돌려주는 오브젝트가 달라짐.
    assertEquals(factory.getClass(), MessageFactoryBean.class);
  }
}
