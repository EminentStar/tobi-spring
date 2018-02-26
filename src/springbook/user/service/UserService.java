package springbook.user.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
  public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
  public static final int MIN_RECOMMEND_FOR_GOLD = 30;

  private UserDao userDao;

  private PlatformTransactionManager transactionManager;

  private MailSender mailSender;

  /**
   * UserDao 오브젝트의 DI가 가능하도록 setter method 추가
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  public void setMailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void add(User user) {
    if (user.getLevel() == null) {
      user.setLevel(Level.BASIC);
    }

    userDao.add(user);
  }

  public void upgradeLevels() throws Exception {
    // [트랜잭션 시작]
    // 트랜잭션을 가져온다는 것은 일단 트랜잭션을 시작한다는 의미로 생각하자.
    // (시작된 트랜잭션은 TransactionStatus 타입의 변수에 저장됨.)
    TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

    try { // 트랜잭션 안에서 진행되는 작업
      upgradeLevelsInternal();
      this.transactionManager.commit(status); // 정상적으로 작업을 마치면 transaction commit
    } catch (Exception e) {
      this.transactionManager.rollback(status); // 예외가 발생하면 rollback
      throw e;
    }

  }

  private void upgradeLevelsInternal() {
    List<User> users = userDao.getAll();

    for (User user : users) {
      if (canUpgradeLevel(user)) {
        upgradeLevel(user);
      }
    }
  }

  private boolean canUpgradeLevel(User user) {
    Level currentLevel = user.getLevel();
    switch (currentLevel) {
      case BASIC:
        return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
      case SILVER:
        return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
      case GOLD:
        return false;
      default:
        throw new IllegalArgumentException("Unknown level: " + currentLevel);
    }
  }

  /**
   * 테스트를 위한 상속을 할 때 private 접근제한이 걸려있어 오버라이딩이 불가.
   * 이번은 예제 상의 예외상황으로 상속을 위해 `protected` Access Specifier를 사용하자.
   */
  protected void upgradeLevel(User user) {
    user.upgradeLevel();
    userDao.update(user);
    sendUpgradeEMail(user);
  }

  private void sendUpgradeEMail(User user) {
    /*
     * ## JavaMail을 이용한 테스트의 문제점.
     * JavaMail에서는 Session 오브젝트를 만들어야만 메일 메시지를 생성할 수 있고, 메일을 전송가능.
     * 근데 이 Session은 인터페이스가 아니라 클래스임. 또 생성자가 private이라 직접 생성도 불가능.(스태틱 팩토리 메소드를 이용해 오브젝트를 만드는 방법밖에 없음.)
     * 게다가 Session 클래스는 더이상 상속이 불가능한 final 클래스.
     * MailMessage, Transport 도 마찬가지임.
     * 결국 JavaMail의 구현을 테스트용으로 바꿔치기하는 건 불가능하다고 볼 수 밖에 없음.
     *
     * ## JavaMail 처럼 테스트하기 힘든 구조인 API를 테스트 하기 좋은 방법으로 서비스 추상화.
     * 스프링은 JavaMail을 사용해 만든 코드는 손쉽게 테스트하기 힘들다는 문제를 해결하기 위해 JavaMail에 대한 추상화 기능을 제공함.
     */
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(user.getEmail());
    mailMessage.setFrom("useradmin@jsug.org");
    mailMessage.setSubject("Upgrade 안내");
    mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

    this.mailSender.send(mailMessage);

  }

}
