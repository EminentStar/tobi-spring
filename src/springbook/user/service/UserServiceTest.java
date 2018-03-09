package springbook.user.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static springbook.user.service.UserServiceImpl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
   *
   *  특정 테스트 클래스에서만 사용되는 클래스는 스태틱 멤버 클래스로 정의하는 것이 편리함.
   */
  static class TestUserServiceImpl extends UserServiceImpl {
    private String id = "madnite1";

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

  /**
   * UserDao.getAll()에 대해서는 스텁으로서,
   * update()에 대해서는(UserService.upgradeLevels()의 핵심 로직을 검증할 수 있는 중요한 부분) 목 오브젝트로 동작하는 test double
   */
  static class MockUserDao implements UserDao {
    private List<User> users; // 레벨 업그레이드 후보 User object 목록
    private List<User> updated = new ArrayList<>(); // 업그레이드 대상 오브젝트를 저장해둘 목록

    private MockUserDao(List<User> users) {
      this.users = users;
    }

    public List<User> getUpdated() {
      return this.updated;
    }

    @Override
    public List<User> getAll() {
      return this.users;
    }

    @Override
    public void update(User user) {
      updated.add(user);
    }

    /**
     * 테스트에 사용되지 않는 메서드
     * */
    @Override
    public void add(User user) {
      throw new UnsupportedOperationException();
    }

    /**
     * 테스트에 사용되지 않는 메서드
     * */
    @Override
    public User get(String id) {
      throw new UnsupportedOperationException();
    }

    /**
     * 테스트에 사용되지 않는 메서드
     * */
    @Override
    public void deleteAll() {
      throw new UnsupportedOperationException();
    }

    /**
     * 테스트에 사용되지 않는 메서드
     * */
    @Override
    public void delete(String id) {
      throw new UnsupportedOperationException();
    }

    /**
     * 테스트에 사용되지 않는 메서드
     * */
    @Override
    public int getCount() {
      throw new UnsupportedOperationException();
    }

  }

  @Autowired
  UserService userService;
  @Autowired
  UserService testUserService;
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

  /**
   * 이 테스트만을 실행한다면 스프링 테스트 컨텍스트를 사용하기 위한 @RunWith등은 제거할 수 있음.
   */
  @Test
  public void upgradeLevels() {
    // Given
    UserServiceImpl userServiceImpl = new UserServiceImpl(); // 고립된 테스트에서는 테스트 대상 오브젝트를 직접 생성하면 됨.

    // 목 오브젝트로 만든 UserDao를 직접 DI해줌.
    // 다이내믹한 목 오브젝트 생성과 메소드의 리턴 값 설정, 그리고 DI까지 세줄이면 충분함.
    UserDao mockUserDao = mock(UserDao.class); // UserDao 인터페이스를 구현한 테스트용 목 오브젝트
    when(mockUserDao.getAll()).thenReturn(this.users); // getAll() 메소드가 불려올 때 사용자 목록을 리턴하도록 스텁 기능 추가
    userServiceImpl.setUserDao(mockUserDao);

    // 메일 발송 결과를 테스트할 수 있도록 목 오브젝틀르 만들어 userService의 의존 오브젝트로 주입.
    MailSender mockMailSender = mock(MailSender.class);
    userServiceImpl.setMailSender(mockMailSender);

    // When
    // 업그레이드 테스트. 메일 발송이 일어나면 MockMailSender 오브젝트의 리스트에 그 결과가 저장됨.
    userServiceImpl.upgradeLevels();

    // Then
    verify(mockUserDao, times(2)).update(any()); // mockUserDao의 update()메소드가 두번 호출됐는지 확인.
    verify(mockUserDao, times(2)).update(any()); // times()는 메소드 호출 횟수를 검증. any()를 상요하면 파라미터의 내용은 무시하고 호출 횟수만 확인 가능
    verify(mockUserDao).update(users.get(1)); // users.get(1)을 파라미터로 update()가 호출된 적이 있는가?
    assertThat(users.get(1).getLevel(), is(Level.SILVER));
    verify(mockUserDao).update(users.get(3));
    assertThat(users.get(3).getLevel(), is(Level.GOLD));

    // 실제 MailSender 목 오브젝트에 전달된 파라미터를 가져와 내용을 검증하는 방법으로 사용. (파라미터를 직접 비교하기보다는 파라미터의 내부 정보를 확인해야 하는 경우에 유용)
    ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
    verify(mockMailSender, times(2)).send(mailMessageArg.capture());
    List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
    assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
    assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
  }

  /**
   * 사용자 레벨 업그레이드를 시도하다가 중간에 예외가 발생했을 경우,
   * 그 전에 업그레이드했던 사용자도 다시 원래 상태로 돌아갔는지 확인
   */
  @Test
  public void upgradeAllOrNothing() throws Exception {
    // Given
    userDao.deleteAll();
    for (User user : users) {
      userDao.add(user);
    }

    // When
    try {
      this.testUserService.upgradeLevels();
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

  private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
    assertThat(updated.getId(), is(expectedId));
    assertThat(updated.getLevel(), is(expectedLevel));
  }

}
