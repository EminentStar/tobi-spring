package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import springbook.user.domain.Level;
import springbook.user.domain.User;

/**
 * User 클래스에 대한 테스트는 굳이 스프링의 테스트 컨텍스트를 사용하지 않아도 됨.
 * (User object는 스프링이 IoC로 관리해주는 오브젝트가 아니기 떄문)
 */
public class UserTest {
  User user;

  @Before
  public void setUp() {
    user = new User();
  }

  @Test()
  public void upgradeLevel() {
    Level[] levels = Level.values();

    for (Level level : levels) {
      if (level.nextLevel() == null) {
        continue;
      }

      user.setLevel(level);
      user.upgradeLevel();
      assertThat(user.getLevel(), is(level.nextLevel()));
    }
  }

  @Test(expected = IllegalStateException.class)
  public void cannotUpgradeLevel() {
    Level[] levels = Level.values();

    for (Level level : levels) {
      if (level.nextLevel() != null) {
        continue;
      }

      user.setLevel(level);
      user.upgradeLevel();
    }
  }

}
