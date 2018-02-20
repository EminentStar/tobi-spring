package springbook.user.domain;

/**
 * TODO: (과제) UserService에 DI를 통해 UserLevelUpgradePolicy를 적용해보는 것.
 *
 * 해당 인터페이스를 통해 레벨을 업그레이드하는 정책을 유연하게 변경할 수 있도록 개선 가능
 *
 * 레벨을 업그레이드 하는 정책을 UserService에서 분리하는 방법도 고려할 수 있음.
 * 분리된 업그레이드 정책을 담은 오브젝트는 DI를 통해 UserService에 주입함.
 * 스프링 설정을 통해서 평상시 정책을 구현한 클래스를 UserService에서 사용하다가,
 * 이벤트 때 새로운 업그레이드 정책을 담을 클래스를 따로 만들어서 DI 해주면 됨. (이벤트가 끝나면 기존 정책으로 다시 변경)
 *
 */
public interface UserLevelUpgradePolicy {
  boolean canUpgradeLevel(User user);
  void upgradeLevel(User user);
}
