package springbook.learningtest.spring.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
  basePackages = {
    "springbook.learningtest.spring.annotation"
  })
public class AnnotationApplicationContext {
}
