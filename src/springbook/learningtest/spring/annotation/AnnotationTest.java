package springbook.learningtest.spring.annotation;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnnotationApplicationContext.class)
public class AnnotationTest {
  @Autowired
  private FacebookConnector facebookConnector;

  @Test
  public void metaAnnotationTest() {
    assertThat(facebookConnector, is(notNullValue()));
  }
}
