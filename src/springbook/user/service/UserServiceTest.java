package springbook.user.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static springbook.user.service.UserService.*;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
  static class TestUserServiceException extends RuntimeException {
  }

  /**
   *  테스트에서만 사용할 클래스이기에 파일을 따로 만들지 말고 테스트 클래스 내부에 스태틱 클래스로 만들어 사용.
   */
  static class TestUserService extends UserService {
    private String id;

    private TestUserService(String id) {
      this.id = id;
    }

    protected void upgradeLevel(User user) {
      if (user.getId().equals(this.id)) { // 지정된 id의 User 오브젝트가 발견되면 예외를 던져서 작업을 강제로 중단.
        throw new TestUserServiceException();
      }

      super.upgradeLevel(user);
    }
  }

  @Autowired
  PlatformTransactionManager transactionManager;
  @Autowired
  UserService userService;
  @Autowired
  UserDao userDao;
  @Autowired
  DataSource dataSource;

  List<User> users; // test fixture

  @Before
  public void setUp() {
    users = Arrays.asList(
      new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0),
      new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0), // 이런게 경계값 테스트구나.. 49 -> 50
      new User("erwins", "신승한", "p3", Level.SILVER, MIN_RECOMMEND_FOR_GOLD - 1, 29),
      new User("madnite1", "이상호", "p4", Level.SILVER, MIN_RECOMMEND_FOR_GOLD, 30),
      new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
    );
  }

  @Ignore
  @Test
  public void bean() {
    assertThat(this.userService, is(notNullValue()));
  }

  @Test
  public void upgradeLevels() throws Exception {
    // Given
    userDao.deleteAll();
    for (User user : users) {
      userDao.add(user);
    }

    // When
    userService.upgradeLevels();

    // Then
    checkLevelUpgraded(users.get(0), false);
    checkLevelUpgraded(users.get(1), true);
    checkLevelUpgraded(users.get(2), false);
    checkLevelUpgraded(users.get(3), true);
    checkLevelUpgraded(users.get(4), false);
  }

  /**
   * 사용자 레벨 업그레이드를 시도하다가 중간에 예외가 발생했을 경우,
   * 그 전에 업그레이드했던 사용자도 다시 원래 상태로 돌아갔는지 확인
   */
  @Test
  public void upgradeAllOrNothing() throws Exception {
    // Given
    UserService testUserService = new TestUserService(users.get(3).getId());
    testUserService.setUserDao(this.userDao); // userDao manual DI
    testUserService.setTransactionManager(this.transactionManager);

    userDao.deleteAll();

    // When
    for (User user : users) {
      userDao.add(user);
    }

    try {
      testUserService.upgradeLevels();
      fail("TestUserServiceException expected");
    } catch (TestUserServiceException e) {
      // TestUserService가 던지는 예외를 잡아서 계속 진행하도록 함.
      // 그외의 예외이면 테스트 실패
    }

    // Then
    // 예외가 발생하기 전에 레벨 변경이 있었던 사용자의 레벨이 처음 상태로 바뀌었나 확인
    checkLevelUpgraded(users.get(1), false);
  }

  private void checkLevelUpgraded(User user, boolean upgraded) {
    User userUpdate = userDao.get(user.getId());
    if (upgraded) {
      assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
    } else {
      assertThat(userUpdate.getLevel(), is(user.getLevel()));
    }
  }

}
