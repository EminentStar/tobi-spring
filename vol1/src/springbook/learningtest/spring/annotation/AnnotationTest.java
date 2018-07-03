package springbook.learningtest.spring.annotation;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import servicecase.SampleBaseTestRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AnnotationApplicationContext.class) // TODO: 메타 애노테이션에 적용된 Context와 현재 클래스의 애노테이션 설정이 어떻게 적용될까?
public class AnnotationTest extends SampleBaseTestRunner {
  @Autowired
  private FacebookConnector facebookConnector;

  @Test
  public void metaAnnotationTest() {
    assertThat(facebookConnector, is(notNullValue()));
  }
}
