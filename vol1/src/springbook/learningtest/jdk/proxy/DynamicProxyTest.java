package springbook.learningtest.jdk.proxy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import springbook.proxy.UppercaseHandler;

public class DynamicProxyTest {
  /**
   * 타깃과 프록시가 구현할 인터페이스
   */
  protected static interface Hello {
    String sayHello(String name);

    String sayHi(String name);

    String sayThankYou(String name);
  }

  /**
   * 타깃 클래스
   */
  static class HelloTarget implements Hello {
    @Override
    public String sayHello(String name) {
      return "Hello " + name;
    }

    @Override
    public String sayHi(String name) {
      return "Hi " + name;
    }

    @Override
    public String sayThankYou(String name) {
      return "Thank You " + name;
    }
  }

  @Test
  public void simpleProxy() {
    //  JDK 다이내믹 프록시 생성
    Hello proxiedHello = (Hello)Proxy.newProxyInstance(
      getClass().getClassLoader(), // 동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더
      new Class[] {Hello.class}, // 구현할 인터페이스
      new UppercaseHandler(new HelloTarget()) // 부가기능과 위임 코드를 담은 InvocationHandler
    );
    assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
    assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
    assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
  }

  @Test
  public void proxyFactoryBean() {
    // Given
    ProxyFactoryBean pfBean = new ProxyFactoryBean();
    pfBean.setTarget(new HelloTarget()); // 타겟 설정
    pfBean.addAdvice(new UppercaseAdvice()); // 부가기능을 담은 어드바이스를 추가함. 여러개를 추가할 수도 있음.

    Hello proxiedHello = (Hello)pfBean.getObject(); // FactoryBean이므로 getObject()로 생성된 프록시를 가져온다.

    // When

    // Then
    assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
    assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
    assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
  }

  static class UppercaseAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      // 리플렉션의 Method와 달리 메소드 실행 시 타깃 오브젝트를 전달할 필요가 없음. MethodInvocation은 메소드 정보와 함께
      // 타깃 오브젝트를 알고 있기 떄문.
      String ret = (String)invocation.proceed();
      return ret.toUpperCase(); // 부가기능 적용
    }
  }

  @Test
  public void pointcutAdvisor() {
    ProxyFactoryBean pfBean = new ProxyFactoryBean();
    pfBean.setTarget(new HelloTarget());

    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut(); // 메소드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트컷 생성
    pointcut.setMappedName("sayH*"); // 이름 비교조건 설정. sayH로 시작하는 모든 메소드를 선택하게 됨.

    // pointcut과 advice를 Advisor로 묶어서 한 번에 추가 (pointcut이 필요없을때는 addAdvice()만 호출해서 어드바이스만 등록하면 됨)
    pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

    Hello proxiedHello = (Hello)pfBean.getObject();

    assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
    assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
    assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby")); // advice 적용되지 않음.
  }

  @Test
  public void classNamePointcutAdvisor() {
    // Prepare Pointcut
    NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
      public ClassFilter getClassFilter() { // 익명 내부 클래스 방식으로 클래스를 정의함.
        return new ClassFilter() {
          public boolean matches(Class<?> clazz) {
            return clazz.getSimpleName().startsWith("HelloT"); // 클래스 이름이 HelloT 로 시작하는 것만 선정.
          }
        };
      }
    };
    classMethodPointcut.setMappedName("sayH*"); // sayH로 시작하는 메소드 이름을 가진 메소드만 선정.

    // Test
    checkAdviced(new HelloTarget(), classMethodPointcut, true);

    class HelloWorld extends HelloTarget {};
    checkAdviced(new HelloWorld(), classMethodPointcut, false);

    class HelloToby extends HelloTarget {};
    checkAdviced(new HelloToby(), classMethodPointcut, true);
//
//    class HelloJunkyu extends HelloTarget {};
//    checkAdviced(new HelloJunkyu(), classMethodPointcut, true); // fail
  }

  /**
   *
   * @param target
   * @param pointcut
   * @param adviced: 적용대상인가?
   */
  private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
    ProxyFactoryBean pfBean = new ProxyFactoryBean();
    pfBean.setTarget(target);
    pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
    Hello proxiedHello = (Hello)pfBean.getObject();

    if (adviced) { // 메소드 선정 방식을 통해 어드바이스 적용
      assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
      assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
      assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby")); // advice 적용되지 않음.
    } else { // 어드바이스 적용 대상 후보에서 아예 탈락
      assertThat(proxiedHello.sayHello("Toby"), is("Hello Toby"));
      assertThat(proxiedHello.sayHi("Toby"), is("Hi Toby"));
      assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby")); // advice 적용되지 않음.
    }


  }
}
