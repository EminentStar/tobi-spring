package springbook.user.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static springbook.user.service.UserServiceImpl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
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
  static class TestUserServiceImpl extends UserServiceImpl {
    private String id;

    private TestUserServiceImpl(String id) {
      this.id = id;
    }

    protected void upgradeLevel(User user) {
      if (user.getId().equals(this.id)) { // 지정된 id의 User 오브젝트가 발견되면 예외를 던져서 작업을 강제로 중단.
        throw new TestUserServiceException();
      }

      super.upgradeLevel(user);
    }
  }

  static class MockMailSender implements MailSender {
    private List<String> requests = new ArrayList<>();

    public List<String> getRequests() {
      return requests;
    }

    @Override
    public void send(SimpleMailMessage mailMessage) throws MailException {
      requests.add(mailMessage.getTo()[0]); // 전송 요청을 받은 이메일 주소를 저장.
    }

    @Override
    public void send(SimpleMailMessage[] mailMessages) throws MailException {

    }
  }

  @Autowired
  PlatformTransactionManager transactionManager;
  @Autowired
  UserServiceImpl userService;
  @Autowired
  UserDao userDao;
  @Autowired
  DataSource dataSource;
  @Autowired
  MailSender mailSender;

  List<User> users; // test fixture

  @Before
  public void setUp() {
    users = Arrays.asList(
      new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "junk3843@naver.com"),
      new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "junk3843@naver.com"), // 이런게 경계값 테스트구나.. 49 -> 50
      new User("erwins", "신승한", "p3", Level.SILVER, MIN_RECOMMEND_FOR_GOLD - 1, 29, "junk3843@naver.com"),
      new User("madnite1", "이상호", "p4", Level.SILVER, MIN_RECOMMEND_FOR_GOLD, 30, "junk3843@naver.com"),
      new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "junk3843@naver.com")
    );
  }

  @Ignore
  @Test
  public void bean() {
    assertThat(this.userService, is(notNullValue()));
  }

  @Test
  @DirtiesContext // 컨텍스트의 DI 설정을 변경하는 테스트라는 것을 알려줌.
  public void upgradeLevels() throws Exception {
    // Given
    userDao.deleteAll();
    for (User user : users) {
      userDao.add(user);
    }

    // 메일 발송 결과를 테스트할 수 있도록 목 오브젝틀르 만들어 userService의 의존 오브젝트로 주입.
    MockMailSender mockMailSender = new MockMailSender();
    userService.setMailSender(mockMailSender);

    // When
    // 업그레이드 테스트. 메일 발송이 일어나면 MockMailSender 오브젝트의 리스트에 그 결과가 저장됨.
    userService.upgradeLevels();

    checkLevelUpgraded(users.get(0), false);
    checkLevelUpgraded(users.get(1), true);
    checkLevelUpgraded(users.get(2), false);
    checkLevelUpgraded(users.get(3), true);
    checkLevelUpgraded(users.get(4), false);

    // Then
    // 목 오브젝트에 저장된 메일 수신자 목록을 가져와 업그레이드 대상과 일치하는지 확인.
    List<String> requests = mockMailSender.getRequests();
    assertThat(requests.size(), is(2));
    assertThat(requests.get(0), is(users.get(1).getEmail()));
    assertThat(requests.get(1), is(users.get(3).getEmail()));
  }

  /**
   * 사용자 레벨 업그레이드를 시도하다가 중간에 예외가 발생했을 경우,
   * 그 전에 업그레이드했던 사용자도 다시 원래 상태로 돌아갔는지 확인
   */
  @Test
  public void upgradeAllOrNothing() throws Exception {
    // Given
    UserServiceImpl testUserService = new TestUserServiceImpl(users.get(3).getId());
    testUserService.setUserDao(this.userDao); // userDao manual DI
    testUserService.setTransactionManager(this.transactionManager);
    testUserService.setMailSender(this.mailSender);

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
