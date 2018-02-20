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
      Boolean changed = null; // 레벨의 변화가 있는지를 확인하는 플래그
      if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
        user.setLevel(Level.SILVER);
        changed = true;
      } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
        user.setLevel(Level.GOLD);
        changed = true;
      } else if (user.getLevel() == Level.GOLD) {
        changed = false;
      } else {
        changed = false;
      }

      if (changed) {
        userDao.update(user);
      }
    }
  }
}
