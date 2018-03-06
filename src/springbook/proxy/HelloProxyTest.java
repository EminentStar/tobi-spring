package springbook.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;

import org.junit.Test;

public class HelloProxyTest {
  @Test
  public void simpleProxy() {
    Hello hello = new HelloTarget(); // Target은 인터페이스를 통해 접근하는 습관을 들이자.
    assertThat(hello.sayHello("Toby"), is("Hello Toby"));
    assertThat(hello.sayHi("Toby"), is("Hi Toby"));
    assertThat(hello.sayThankYou("Toby"), is("Thank you Toby"));
  }

  @Test
  public void helloUppercaseProxy() {
    // 생성된 다이내믹 프록시 오브젝트는 Hello 인터페이스를 구현하고 잇으므로 Hello 타입으로 캐스팅해도 안전함.
    Hello proxiedHello = (Hello)Proxy.newProxyInstance(
      getClass().getClassLoader(), // 동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더
      new Class[] {Hello.class}, // 구현할 인터페이스
      new UppercaseHandler(new HelloTarget()) // 부가기능과 위임 코드를 담은 InvocationHandler
    );
    assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
    assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
    assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
  }
}
