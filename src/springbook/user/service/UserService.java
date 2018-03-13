package springbook.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import springbook.user.domain.User;

/**
 * 클라이언트가 사용할 로직을 담은 핵심 메소드만 만듬.
 */
@Transactional
public interface UserService {
  void add(User user);
  void deleteAll();
  void update(User user);
  void upgradeLevels();

  @Transactional(readOnly = true)
  User get(String id);
  @Transactional(readOnly = true)
  List<User> getAll();
}
