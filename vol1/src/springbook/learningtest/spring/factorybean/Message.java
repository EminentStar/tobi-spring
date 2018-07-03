package springbook.learningtest.spring.factorybean;

/**
 * Message 클래스의 오브젝트를 만들려면 newMessage() 라는 스태틱 메소드를 사용해야 함.
 * 따라서 이 클래스를 직접 스프링 빈으로 등록해서 사용할 수 없음.
 * 아래와 같이 사용하면 안됨.
 * <bean id="m" class="springbook.learningtest.spring.factorybean.Message"> // private 생성자를 가진 클래스의 직접 사용 금지
 * </bean>
 */
public class Message {
  String text;

  /**
   * 생성자가 private으로 선언되어 있어서 외부에서 생성자를 통해 오브젝트를 만들 수 없다.
   */
  private Message(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  /**
   * 생성자 대신 사용할 수 있는 스태틱 팩토리 메소드를 제공.
   */
  public static Message newMessage(String text) {
    return new Message(text);
  }
}
