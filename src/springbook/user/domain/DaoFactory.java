package springbook.user.domain;

public class DaoFactory {
  /**
   * 팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지를 결정함.
   * @return UserDao
   */
  public UserDao userDao() {
    ConnectionMaker connectionMaker = new DConnectionMaker(); // UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만든다.
    //    ConnectionMaker connectionMaker = new NConnectionMaker(); // D 사의 ConnectionMaker로도 자유롭게 변경가능
    UserDao userDao = new UserDao(connectionMaker);
    return userDao;
  }
}
