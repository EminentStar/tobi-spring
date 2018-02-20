package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
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
        return (user.getLogin() >= 50);
      case SILVER:
        return (user.getRecommend() >= 30);
      case GOLD:
        return false;
      default:
        throw new IllegalArgumentException("Unknown level: " + currentLevel);
    }
  }

  private void upgradeLevel(User user) {
    user.upgradeLevel();
    userDao.update(user);
  }

}
