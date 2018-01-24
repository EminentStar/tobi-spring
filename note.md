# Chap 01: 오브젝트와 의존관계

### 1.4.3 제어권의 이전을 통한 제어관계 역전
#### 일반적인 프로그램 흐름
- 모든 오브젝트가 능동적으로 자신이 사용할 클래스를 결정하고, 언제 어떻게 그 오브젝트를 만들지를 스스로 관장. 
- 모든 종류의 작업을 사용하는 쪽에서 제어하는 구조.

#### 제어의 역전
- 일반적인 제어 흐름의 개념을 거꾸로 뒤집는 것.
- 오브젝트는 자신이 사용할 오브젝트를 스스로 선택/생성하지 않음.
- 자신이 어떻게 만들어지고 어디서 사용되는지를 알 수 없음.
- 모든 제어 권한을 다른 대상에게 위임하기에 위와 같은 현상 발생.

#### 라이브러리와 프레임워크의 차이(p93)
* 라이브러리: 라이브러리를 사용하는 애플리케이션 코드는 애플리케이션 흐름을 직접 제어함.
* 프레임워크: 거꾸로 애플리케이션 코드가 프레임워크에 의해 사용됨.
  - 애플리케이션은 프레임워크가 짜놓은 틀에서 수동적으로 동작해야 함.
  
IoC에선 프레임워크 또는 컨테이너같이 애플리케이션 컴포넌트의 생성과 관계설정, 사용, 생명주기 관리 등을 관장하는 존재가 필요.

스프링은 IoC를 모든 기능의 기초가 되는 기반 기술로 삼고 있고, IoC를 극한까지 적용하고 있는 프레임워크.


## 1.5 스프링의 IoC

* 라이브러리 추가 필요
  - com.springsource.net.sf.cglib-2.2.0.jar
  - com.springsource.org.apache.commons.logging-1.1.1.jar
  - org.springframework.asm-3.0.7.RELEASE.jar
  - org.springframework.beans-3.0.7.RELEASE.jar
  - org.springframework.context-3.0.7.RELEASE.jar
  - org.springframework.core-3.0.7.RELEASE.jar
  - org.springframework.expression-3.0.7.RELEASE.jar

### 1.5.3 스프링 IoC의 용어 정리
* bean: 스프링이 (IoC 방식으로) 직접 그 생성과 제어를 담당하는 오브젝트
* bean factory: 스프링의 IoC를 담당하는 핵심 컨테이너.
* application context: 빈 팩토리를 확장한 IoC 컨테이너.
* configuration metadata: application context or bean factory가 IoC를 적용하기 위해 사용하는 메타정보
* IoC container: IoC방식으로 빈을 관리한다는 의미에서 application context or bean factory를 이렇게 부름. 애플리케이션 컨텍스트 오브젝트는 하나의 애플리케이션에 보통 여러 개가 만들어져 사용됨(이를 통틀어 스프링 컨테이너라고 부름)
* spring framework: IoC container, application context를 포함한 스프링이 제공하는 모든 기능을 통틀어 말할 때 사용.
  

## 1.6 싱글톤 레지스트리와 오브젝트 스코프

### 1.6.1 싱글톤 레지스트리로서의 애플리케이션 컨텍스트
ApplicationContext는 IoC container이기도 하면서 동시에 Singleton Registry이기도 함.

스프링은 별다른 설정을 하지 않으면 내부에서 생성하는 빈 오브젝트를 싱글톤으로 만듬.

* 하지만 자바에서 구현하는 싱글톤을 그대로 사용하진 않음.
  - private 생성자를 가지고 있어 상속할 수 없음.
  - 테스트하기 힘듬.
  - 서버 배포 환경에서 단일 객체임을 보장 못함.
  - 전역 상태 안좋음.

* 스프링의 싱글톤 레지스트리는 평범한 자바 클래스도 IoC container를 이용해서 생성, 관계설정, 사용에 대한 제어권을 컨테이너에게 넘기기에 싱글톤 방식으로 만들어져 관리할 수 있음.

### 1.6.2 싱글톤과 오브젝트의 상태
싱글톤이 멀티스레드 환경에서 사용되는 경우 stateless 방식으로 만들어져야함.(각 스레드들이 데이터를 공유 못하도록)
- 개별적으로 바뀌는 정보는 데이터의 전역 공유를 막기 위해서 method parameter, local variables를 이용하면 싱글톤 내에서 필요한 데이터를 바르게 저장할 수 있음.


