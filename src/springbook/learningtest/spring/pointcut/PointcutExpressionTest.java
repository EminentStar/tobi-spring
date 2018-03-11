package springbook.learningtest.spring.pointcut;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class PointcutExpressionTest {

  @Test
  public void showFullSignatureOfMethod() throws NoSuchMethodException {
    System.out.println(Target.class.getMethod("minus", int.class, int.class));
    /**
     public int springbook.learningtest.spring.pointcut.Target.minus(int,int) throws java.lang.RuntimeException

     []: 생략가능
     | : OR

     [Access Modifier] ReturnTypePattern [ClassTypePattern.]MethodNamePattern (ParameterTypePattern | "..", ...) [throws ExceptionTypePattern]


     ###
     * ReturnType: *를 써서 모든 타입을 다 선택하겠다고 해도 됨.
     * ClassTypePattern: 이름에 *를 사용할 수 있고, '..'를 사용하면 한번에 여러 개의패키지를 선택할 수 있음.
     * MethodNamePattern: *를 써서 모든 메서드를 선택할 수 있음.
     * ParatmerTypePattern: ',' 를 써서 순서대로 나열 가능. 파라미터 타입과 갯수에 상관없이 모두 다 허용하겠다면 '..'를 사용하면 됨. '...'를 이용해서 뒷부분의 파라미터 조건만 생략할 수도 있음.
     */
  }

  @Test
  public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
    // Given
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    String expressionStr = "execution(public int " +
      "springbook.learningtest.spring.pointcut.Target.minus(int,int) " +
      "throws java.lang.RuntimeException)";

    pointcut.setExpression(expressionStr);

    // When
    // Then
    assertThat(pointcut.getClassFilter().matches(Target.class), is(true));
    assertThat(pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null), is(true));

    assertThat(pointcut.getClassFilter().matches(Target.class), is(true));
    assertThat(pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null), is(false));

    //    assertThat(pointcut.getClassFilter().matches(Bean.class), is(false)); // TODO: 이건 왜 Class 필터링에 잡히지?
    assertThat(pointcut.getMethodMatcher().matches(Bean.class.getMethod("method"), null), is(false));
  }

  @Test
  public void pointcut() throws Exception {
    targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
    targetClassPointcutMatches("execution(* hello(..))", true, true, false, false, false, false);
    targetClassPointcutMatches("execution(* hello(String))", false, true, false, false, false, false);
    targetClassPointcutMatches("execution(* meth*(..))", false, false, false, false, true, true);
    targetClassPointcutMatches("execution(* *(int, int))", false, false, true, true, false, false);
    targetClassPointcutMatches("execution(* *())", true, false, false, false, true, true);
    targetClassPointcutMatches("execution(* springbook.learningtest.spring.pointcut.Target.*(..))", true, true, true, true, true, false);
    targetClassPointcutMatches("execution(* springbook.learningtest.spring.pointcut..*.*(..))", true, true, true, true, true, true);
    targetClassPointcutMatches("execution(* springbook..*.*(..))", true, true, true, true, true, true);
    targetClassPointcutMatches("execution(* com..*.*(..))", false, false, false, false, false, false);
    targetClassPointcutMatches("execution(* *..Target.*(..))", true, true, true, true, true, false);
    targetClassPointcutMatches("execution(* *..Tar*.*(..))", true, true, true, true, true, false);
    targetClassPointcutMatches("execution(* *..*get.*(..))", true, true, true, true, true, false);
    targetClassPointcutMatches("execution(* *..B*.*(..))", false, false, false, false, false, true);
    targetClassPointcutMatches("execution(* *..TargetInterface.*(..))", true, true, true, true, false, false);
    targetClassPointcutMatches("execution(* *(..) throws Runtime*)", false, false, false, true, false, true);
    targetClassPointcutMatches("execution(int *(..))", false, false, true, true, false, false);
    targetClassPointcutMatches("execution(void *(..))", true, true, false, false, true, true);
  }

  public void targetClassPointcutMatches(String expression, boolean... expected) throws Exception {
    pointcutMatches(expression, expected[0], Target.class, "hello");
    pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
    pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
    pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
    pointcutMatches(expression, expected[4], Target.class, "method");
    pointcutMatches(expression, expected[5], Bean.class, "method");
  }

  public void pointcutMatches(String expression, Boolean expected, Class<?> clazz,
    String methodName, Class<?>... args) throws Exception {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(expression);

    assertThat(pointcut.getClassFilter().matches(clazz)
      && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null), is(expected));
  }

}
