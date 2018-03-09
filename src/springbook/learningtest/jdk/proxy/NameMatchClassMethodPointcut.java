package springbook.learningtest.jdk.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {
  public void setMappedClassName(String mappedClassName) {
    // 기존의 모든 클래스를 다 허용하던 디폴트 클래스 필터를 프로퍼티로 받은 클래스 이름을 이용해서 필터로 만들어 엎어씌운다.
    this.setClassFilter(new SimpleClassFilter(mappedClassName));
  }

  static class SimpleClassFilter implements ClassFilter {
    String mappedName;

    private SimpleClassFilter(String mappedName) {
      this.mappedName = mappedName;
    }

    @Override
    public boolean matches(Class<?> clazz) {
      // 와일드카드(*)가 들어간 문자열 비교를 지원하는 스프링의 유틸리티 메소드. *name, name*, *name* 세가지 방식을 모두 지원.
      return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName());
    }
  }

}
