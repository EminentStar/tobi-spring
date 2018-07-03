package springbook.user.dao;

import java.util.ArrayList;
import java.util.List;

import springbook.user.domain.User;

public class MockUserDao implements UserDao {
  private List<User> users; // 레벨 업그레이드 후보 User object 목록
  private List<User> updated = new ArrayList<>(); // 업그레이드 대상 오브젝트를 저장해둘 목록

  private MockUserDao(List<User> users) {
    this.users = users;
  }

  public List<User> getUpdated() {
    return this.updated;
  }

  @Override
  public void add(User user) {

  }

  @Override
  public User get(String id) {
    return null;
  }

  @Override
  public List<User> getAll() {
    return null;
  }

  @Override
  public void deleteAll() {

  }

  @Override
  public void delete(String id) {

  }

  @Override
  public int getCount() {
    return 0;
  }

  @Override
  public void update(User user) {

  }
}
