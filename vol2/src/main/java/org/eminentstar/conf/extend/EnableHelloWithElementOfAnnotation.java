package org.eminentstar.conf.extend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eminentstar.conf.HelloConfig;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HelloConfig.class)
public @interface EnableHelloWithElementOfAnnotation {
  String name() default "";
}
