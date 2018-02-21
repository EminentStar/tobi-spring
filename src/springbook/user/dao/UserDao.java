package springbook.user.dao;

import java.sql.Connection;
import java.util.List;

import springbook.user.domain.User;

/**
 * UserDao의 인터페이스와 구현을 분리함으로써 데이터 액세스의 구체적인 기술과 UserDao의 클라이언트 사이에 DI가 적용됨.
 */
public interface UserDao {
  void add(Connection c, User user);
  User get(Connection c, String id);
  List<User> getAll(Connection c);
  void deleteAll(Connection c);
  void delete(Connection c, String id);
  int getCount(Connection c);
  void update(Connection c, User user);
}
