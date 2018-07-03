//package org.springframework.transaction.annotation;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package springbook.user.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

/**
 * @Transactional 의 소스코드
 *
 * @Transactional은 기본적으로 트랜잭션 속성을 정의하는 것이지만, 동시에 포인트컷의 자동등록에도 사용됨.
 *
 * 이 방식으로 포인트컷과 트랜잭션 속성을 애노테이션 하나로 지정할 수 있음.
 *
 * @Transactional은 메소드마다 다르게 설정할 수도 있으므로 매우 유연한 트랜잭션 속성 설정이 가능해진다.
 * 동시에 포인트컷도 @Transactional을 통한 트랜잭션 속성정보를 참조하도록 만든다.
 *
 * # 대체 정책
 * 스프링은 @Transactional을 적용할 때 4단계의 대체(fallback)정책을 이용하게 해줌.
 * 메소드의 속성을 확인할 때, 1. 타깃 메소드, 2. 타깃 클래스, 3. 선언 메소드(인터페이스의) 4. 선언 클래스(인터페이스)의 순서에 따라
 * @Transactional이 적용됐는지 차례로 확인하고 가장 먼저 발견되는 속성정보를 사용하게 하는 방법이다.
 *
 * 애노테이션에 대한 대체 정책의 순서는 타깃 클래스가 인터페이스보다 우선.
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 애노테이션을 사용할 대상을 지정함. 여기에 사용된 '메소드와 타입(클래스, 인터페이스)'처럼 한 개 이상의 대상을 지정할 수 있다.
@Retention(RetentionPolicy.RUNTIME) // 애노테이션 정보가 언제까지 유지되는지를 지정한다. 이렇게 설정하면 런타임 때도 애노테이션 정보를 리플렉션을 통해 얻을 수 있다.
@Inherited // 상속을 통해서도 애노테이션 정보를 얻을 수 있게 한다.
@Documented
public @interface Transactional { // 트랜잭션 속성의 모든 항목을 엘리먼트로 지정할 수 있다. 티폴드 값이 설정되어 있으므로 모두 생략 가능하다.
  String value() default "";

  Propagation propagation() default Propagation.REQUIRED;

  Isolation isolation() default Isolation.DEFAULT;

  int timeout() default -1;

  boolean readOnly() default false;

  Class<? extends Throwable>[] rollbackFor() default {};

  String[] rollbackForClassName() default {};

  Class<? extends Throwable>[] noRollbackFor() default {};

  String[] noRollbackForClassName() default {};
}

