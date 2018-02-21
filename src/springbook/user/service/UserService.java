package springbook.user.service;

import java.sql.Connection;
import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
  public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
  public static final int MIN_RECOMMEND_FOR_GOLD = 30;

  UserDao userDao;

  /**
   * UserDao 오브젝트의 DI가 가능하도록 setter method 추가
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void add(User user) {
    if (user.getLevel() == null) {
      user.setLevel(Level.BASIC);
    }

    userDao.add(user);
  }

  public void upgradeLevels() {
    Connection c = null;
    List<User> users = userDao.getAll(c);

    for (User user : users) {
      if (canUpgradeLevel(user)) {
        upgradeLevel(c, user);
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
  protected void upgradeLevel(Connection c, User user) {
    user.upgradeLevel();
    userDao.update(c, user);
  }

}
