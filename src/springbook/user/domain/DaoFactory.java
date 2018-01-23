package springbook.user.domain;

public class DaoFactory {
  /**
   * 팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지를 결정함.
   * @return UserDao
   */
  public UserDao userDao() {
    //      return new UserDao(new DConnectionMaker());  // ConnectionMaker 생성코드 중복
    return new UserDao(connectionMaker());
  }

  public AccountDao accountDao() {
    //    return new AccountDao(new DConnectionMaker()); // ConnectionMaker 생성코드 중복
    return new AccountDao(connectionMaker());
  }

  public MessageDao messageDao() {
    //    return new MessageDao(new DConnectionMaker()); // ConnectionMaker 생성코드 중복
    return new MessageDao(connectionMaker());
  }

  public ConnectionMaker connectionMaker() {
    // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
    return new DConnectionMaker();
  }
}
