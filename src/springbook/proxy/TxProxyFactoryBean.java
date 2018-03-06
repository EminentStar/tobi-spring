package springbook.proxy;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * # FactoryBean<Object>에서 <Object>로 지정한 이유
 * 생성할 오브젝트 타입을 지정할 수도 있지만 범용적으로 사용하기 위해 Object로 함.
 */
public class TxProxyFactoryBean implements FactoryBean<Object> {
  Object target;
  PlatformTransactionManager transactionManager;
  String pattern;
  // 위의 멤버들은 TransactionHandler를 생성할 때 필요.

  Class<?> serviceInterface; // 다이내믹 프록시를 생성할 때 필요함. UserService외의 인터페이스를 가진 타깃에도 적용할 수 있음.k

  public void setTarget(Object target) {
    this.target = target;
  }

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setServiceInterface(Class<?> serviceInterface) {
    this.serviceInterface = serviceInterface;
  }

  // FactoryBean 인터페이스 구현 메소드

  /**
   * DI 받은 정보를 이용해서 TransactionHandler를 사용하는 다이내믹 프록시를 생성.
   */
  @Override
  public Object getObject() throws Exception {
    TransactionHandler txHandler = new TransactionHandler();
    txHandler.setTarget(target);
    txHandler.setTransactionManager(transactionManager);
    txHandler.setPattern(pattern);

    return Proxy.newProxyInstance(
      getClass().getClassLoader(),
      new Class[] {serviceInterface},
      txHandler
    );
  }

  /**
   * 팩토리 빈이 생성하는 오브젝트의 타입은 DI 받은 인터페이스 타입에 따라 달라짐.
   * 따라서 다양한 타입의 오브젝트 생성에 재사용할 수 있음.
   */
  @Override
  public Class<?> getObjectType() {
    return serviceInterface;
  }

  /**
   * 싱글톤 빈이 아니라는 뜻이 아니라 getObject()가 매번 같은 오브젝트를 리턴하지 않는다는 의미
   */
  @Override
  public boolean isSingleton() {
    return false;
  }
}
