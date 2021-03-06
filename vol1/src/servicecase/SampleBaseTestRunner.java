package servicecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.learningtest.spring.annotation.AnnotationApplicationContext;
import springbook.user.service.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnnotationApplicationContext.class)
@Transactional
public class SampleBaseTestRunner {
  @Before
  public void setUp() {
    // Setup codes
    System.out.println("---------------------BaseTestRunner setUp method---------------------");
  }

  @Test
  public void dummyTest() {
  }
}
