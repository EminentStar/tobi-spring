package org.eminentstar.conf.extend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eminentstar.conf.HelloConfig2;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HelloConfig2.class)
public @interface EnableHelloWithConfigurer {
}
