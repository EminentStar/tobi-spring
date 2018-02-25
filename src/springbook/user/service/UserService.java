package springbook.user.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

  /**
   * UserDao 오브젝트의 DI가 가능하도록 setter method 추가
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
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
      List<User> users = userDao.getAll();

      for (User user : users) {
        if (canUpgradeLevel(user)) {
          upgradeLevel(user);
        }
      }

      this.transactionManager.commit(status); // 정상적으로 작업을 마치면 transaction commit
    } catch (Exception e) {
      this.transactionManager.rollback(status); // 예외가 발생하면 rollback
      throw e;
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
    Properties props = new Properties();
    props.put("mail.smtp.host", "mail.ksug.org");
    Session s = Session.getInstance(props, null);

    MimeMessage message = new MimeMessage(s);
    try {
      message.setFrom(new InternetAddress("useradmin@ksug.org"));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
      message.setSubject("Upgrade 안내");
      message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

      Transport.send(message);
    } catch (AddressException e) {
      throw new RuntimeException(e);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

}
