package springbook.learningtest.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * FactoryBean 인터페이스를 구현한 클래스를 스프링 빈으로 만들어두면
 * getObject()라는 메소드가 생성해주는 오브젝트가 실제 빈의 오브젝트로 대치됨.
 */
public class MessageFactoryBean implements FactoryBean<Message> {
  String text;

  /**
   * 오브젝트를 생성할 때 필요한 정보를 팩토리 빈의 프로퍼티로 설정해서 대신 DI 받을 수 있게 함.
   * 주입된 정보는 오브젝트 생성 중에 사용됨.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * 실제 빈으로 사용될 오브젝트를 직접 생성.
   * 코드를 이용하기 때문에 복잡한 방식의 오브젝트 생성과 초기화 작업도 가능.
   *
   * getOBject() 메소드가 생성해주는 오브젝트가 빈의 오브젝트가 됨.
   */
  @Override
  public Message getObject() throws Exception {
    return Message.newMessage(this.text);
  }

  @Override
  public Class<?> getObjectType() {
    return Message.class;
  }

  /**
   * getOBject() 메소드가 돌려주는 오브젝트가 싱글톤인지를 알려줌.
   * 이 팩토리 빈은 매번 요청할 때마다 새로운 오브젝트를 만들므로 false로 설정.
   * 이것은 팩토리 빈의 동작방식에 관한 설정이고 만들어진 빈 오브젝트는 싱글톤으로 스프링이 관리해줄 수도 있음.
   */
  @Override
  public boolean isSingleton() {
    return false;
  }
}
