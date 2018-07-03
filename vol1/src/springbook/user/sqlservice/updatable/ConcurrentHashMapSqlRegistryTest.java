package springbook.user.sqlservice.updatable;

import springbook.user.sqlservice.UpdatableSqlRegistry;

/**
 * 슈퍼클래스인 AbstractUpdatableSqlRegistryTest의 @Test 테스트 메소드를 모두 상속받아서 자신의 테스트로 활용하게 됨.
 */
public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
  @Override
  protected UpdatableSqlRegistry createUpdateableSqlRegistry() {
    return new ConcurrentHashMapSqlRegistry();
  }
}
