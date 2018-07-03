package springbook.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
  Object target; // 어떤 종류의 인터페이스를 구현한 타깃에도 적용 가능하도록 Object 타입으로 수정

  /**
   * 다이내믹 프록시로부터 전달받은 요청을 다시 타깃 오브젝트에 위임해야 하기 때문에 타깃 오브젝트를 주입받아둔다.
   */
  public UppercaseHandler(Object target) {
    this.target = target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object ret = method.invoke(target, args); // 타겟으로 위임. 인터페이스의 메소드 호출에 모두 적용됨.
    // 호출한 메소드의 리턴 타입이 String인 경우만 대문자 변경 기능을 적용하도록 수정
    if (ret instanceof String && method.getName().startsWith("say")) {
      return ((String)ret).toUpperCase(); // 부가기능 제공
    } else {
      return ret;
    }
  }
}
