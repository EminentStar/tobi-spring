package springbook.user.service;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import springbook.user.domain.User;

@Deprecated
public class UserServiceTx implements UserService {
  UserService userService;
  PlatformTransactionManager transactionManager;

  public void setUserService(UserService userService) {
    this.userService = userService; // UserService를 구현한 다른 오브젝트를 DI받음.
  }

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  /**
   * DI 받은 UserService 오브젝트에 모든 기능을 위임
   */
  @Override
  public void add(User user) {
    userService.add(user);
  }

  @Override
  public User get(String id) {
    return null;
  }

  @Override
  public List<User> getAll() {
    return null;
  }

  @Override
  public void deleteAll() {

  }

  @Override
  public void update(User user) {

  }

  /**
   * DI 받은 UserService 오브젝트에 모든 기능을 위임
   */
  @Override
  public void upgradeLevels() {
    // [트랜잭션 시작]
    // 트랜잭션을 가져온다는 것은 일단 트랜잭션을 시작한다는 의미로 생각하자.
    // (시작된 트랜잭션은 TransactionStatus 타입의 변수에 저장됨.)
    TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

    try { // 트랜잭션 안에서 진행되는 작업
      userService.upgradeLevels();

      this.transactionManager.commit(status); // 정상적으로 작업을 마치면 transaction commit
    } catch (RuntimeException e) {
      this.transactionManager.rollback(status); // 예외가 발생하면 rollback
      throw e;
    }
  }
}
