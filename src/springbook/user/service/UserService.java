package springbook.user.service;

import java.util.List;

import springbook.user.domain.User;

/**
 * 클라이언트가 사용할 로직을 담은 핵심 메소드만 만듬.
 */
public interface UserService {
  void add(User user);
  User get(String id);
  List<User> getAll();
  void deleteAll();
  void update(User user);

  void upgradeLevels();
}
