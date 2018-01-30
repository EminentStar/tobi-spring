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

* 라이브러리 추가 필요(File/Project Structure/Libraries/New Project Library/Java)
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


### 1.6.3 스프링 빈의 스코프
* scope: 빈이 생성되고, 존재하고, 적용되는 범위

- 기본 스코프: singleton
- prototype: 컨테이너가 빈을 요청할 때마다 새로운 오브젝트 생성
- request: http 요청이 생길때마다 생성되는
- session: 웹의 세션과 유사함.


## 1.7 Dependency Injection (DI; 의존관계 주입)
### 1.7.1 IoC와 DI
- 스프링 IoC 기능의 대표적인 동작원리는 주로 DI로 불림.  
- DI의 핵심은 오브젝트 레퍼런스를 외부로부터 제공(주입)받고 이를 통해 여타 오브젝트와 dynamic하게 의존관계가 만들어지는 것.


### 1.7.2 런타임 의존관계 설정
자바에서 오브젝트에 무언가를 넣어준다는 개념은 메소드를 실행하면서 파라미터로 오브젝트의 레퍼런스를 전달해주눈 방법뿐임.

### 1.7.3 Dependency Lookup
- 자신이 필요로 하는 의존 오브젝트를 능동적으로 찾음.
- 물론 런타임 시 의존관계를 맺을 오브젝트를 결정하는 것과 오브젝트 생성작업은 IoC Container에게 맡김.
- 다만 이를 가져올 때 메소드나 생성자를 통한 주입 대신 스스로 컨테이너에게 요청하는 방법
- static method에선 DI를 이용해 오브젝트를 주입받을 방법이 없음.
  - 그래서 애플리케이션 의 기둥 시점에선 적어도 한번은 DL 방식을 사용해 오브젝트를 가져와야 함.

* 하지만 DI가 단순 하고 깔끔함.
  - DL은 코드 안에 오브젝트 팩토리 클래스나 스프링 API가 나타남.

* DL 방식에선 검색하는 오브젝트는 자신이 스프링의 bean일 필요가 없음.

* **DI를 원하는 오브젝트는 먼저 자기 자신이 Container가 관리하는 bean이 돼야 함.**
* DI받는 메소드 파라미터가 특정 클래스 타입으로 고정되어 있다면 DI는 일어날 수 없음. 
  - DI의 주입은 다이나믹하게 구현 클래스를 결정해서 제공받을 수 있도록 인터페이스 타입의 파라미터를 통해 이뤄져야 함.
  

## 1.8 XML을 이용한 설정 
* ApplicationContext와 같은 범용 ㅇI컨테이너를 사용하면 오브젝트 사이 의존정보를 자바코드로 관리하기 힘듬.
  - DI 구성이 바뀔 때마다 자바 코드 수정 후 클래스를 다시 컴파일하는 것도 귀찮음.
 
* 그외 대표적인 방법중 하나가 XML을 통한 DI 의존관계 설정정보 생성
  - 쉽게 이해
  - 컴파일 같은 별도의 빌드 작업이 없음.
  - 변경사항도 빠르게 반영
  - 스키마나 DTD를 이용해서 정해진 포맷을 따라 작성됐는지 손쉽게 확인가능

### 1.8.1 XML 설정
DI 정보가 담긴 XML 파일은 \<beans\>를 루트 엘러먼트로 사용  
\<beans\>안에는 여러개의 \<bean\>를 정의 가능   

- @Configuration이 \<beans\>에
- @Bean이 \<bean\>에 해당한다고 보면됨

* @Bean 메서드를 통해 얻을 수 있는 Bean의 DI 정보
  1. name: @Bean 메서드 이름이 빈의 이름. getBean()에서 사용함.
    - xml에선 `id` attr
  2. class: 빈 오브젝트를 어떤 클래스를 이용해서 만들지 정의
    - xml에선 `class` attr
      - 메서드에서의 리턴 타입(Interface)가 아니라, return할때 오브젝트를 만들 때 사용하는 클래스를 설정해야함.
  3. dependent object: 빈의 생성자나 setter method를 통해서 의존 오브젝트를 넣어줌.

**단, XML은 자바 코드처럼 유연하게 정의될 수 있는 것이 아니므로, 핵심 요소를 잘 짚어서 그에 해당하는 태그와 애트리뷰트가 무엇인지 알아야함.**

|          |java code config       |xml config                      |
|----------|-----------------------|--------------------------------|
|빈 설정파일  |@Configuration         |\<beans\>                       |
|빈 이름     |@Bean methodName()     | \<bean id="methodName"\>       |
|빈의 클래스  |return new BeanClass();| class="a.b.c... BeawnClass"\>  |

- 자바빈의 관례를 따라서 수정자 메소드는 프로퍼티가 됨.
- 프로퍼티 이름은 메서드 이름에서 set을 제외한 나머지 부분을 사용한다. 
- xml에선  \<property\> 태그를 사용해 의존 오브젝트와의 관계를 정의(setter method DI에서)
  - name attr: 프로퍼티 이름
  - ref attr: setter method 를 통해 주입해줄 오브젝트의 빈의 이름(DI할 오브젝트도 빈이기에)
  
  
#### DTD와 schema
XML 문서는 미리 정해진 구조를 따라서 작성됐는지 검사할 수 있음.  
XML 문서의 구조를 정의하는 방법엔 DTD, schema가 있음.(스프링 xml 은 이걸 모두 지원함.)   

* DTD를 사용할 경우 beans 엘러먼트 앞에 DTD 선언해야함.
```$xslt
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN 2.0//EN"
  "http://www.springframework.org/dtd/spring-beans-2.0.dtd">
```

* schema
  - DI를 위한 기본 태그인 beans, bean 이외에 특별한 목적을 위해 별도의 태그를 사용할 수 있는 방법을 제공.
  - 각 태그들은 별개의 스키마 파일에 정의되어 있고, 독립저인 네임스페이스를 사용해야만 함.
  - 이 태그를 사용하려면 DTD 대신 네임스페이스가 지원되는 스키마를 사용해야 함.
```$xslt
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
```



### 1.8.2 XML을 이용하는 애플리케이션 컨텍스트
* XML에서 빈의 의존관계 정보를 이용하는 IoC/DI 작업에는 GenericXmlApplicationContext를 사용.  
* GenericXmlApplicationContext의 생성자 파라미터로 XML 파일의 클래스패스를 지정해주면 됨. 
  - GenericXmlApplicationContext는 classpath뿐만 아니라 다양한 소스로부터 설정파일을 읽어올 수 있음
* XML 설정파일은 클랲스패스 최상단에 두면 편함.



### 1.8.3 DataSource Interface로 변환
* 라이브러리 추가 필요
  - spring-jdbc-3.0.7.release.jar

* DataSource Interface: Java에서 DB connection을 가져오는 오브젝트의 기능을 추상화해서 비슷한 용도로 사용할 수 있게 만든 interface



### 1.8.4 프로퍼티 값의 주입
setter method엔 다른 빈이나 오브젝트 뿐만 아니라, String 같은 단순 값을 넣어줄 수도 있음.   
DI에서처럼 오브젝트의 구현 클래스를 다이내믹하게 바꿀 수 있게 하는 것이 목적은 아니지만,   
**클래스 외부에서** DB 연결정보같이 변경 가능한 정보를 설정해줄 수 있도록 프로퍼티 값을 주입할 수 있다.   
(e.g. DB 접속 아이디가 바뀌었더라도 클래스 코드는 수정해줄 필요가 없게 해주는 것.)  

이걸 `값을 주입한다`라고 말함. 성격은 다르지만 일종의 DI라고 볼 수 있음.(사용할 오브젝트 자체를 바꾸지는 않지만, 오브젝트의 특성은 외부에서 변경할 수 있기 때문)  

- 빈으로 등록될 클래스에 setter method가 정의되어 있으면 \<property\>를 사용해 주입할 정보를 지정할 수 있다는 점에서 \<property ref=""\>와 동일.  
- 하지만 다른 빈 오브젝트의 reference(ref)가 아니라 단순 값(value)를 주입하는 것이기에 ref attr 대신 value attr을 사용함.

#### value 값의 자동 변환
dataSource 빈에 property를 세팅할 때 url, username, password는 String이라 value attr을 사용하는게 상관은 없음.   
근데 `driverClass`의 경우 type은 java.lang.Class 타입이다.   
```java
Class driverClass = "com.mysql.jdbc.Driver"; // 말이 안됨.
```
이런게 가능 한 이유는 스프링이 property's value를 setter method's parameter type을 참고해서 적절한 형태로 변환해주기 때문임.  

내부적으론 아래와 같은 변환작업이 일어난다고 생각하면 됨.
```java
Class driverClass = Class.forName("com.mysql.jdbc.Driver");
dataSource.setDriverClass(driverClass);
```

스프링은 value에 지정한 텍스트 값을 적절한 자바 타입으로 변환해줌.



# Chap 02: 테스트

### 2.2.2 테스트의 효율적인 수행과 결과 관리
* 라이브러리 추가 필요
  - com.springsource.org.junit-4.7.0.jar


* JUnit
  - 테스트 메소드는 public으로 선언돼야 하고, 메소드에 @Test 애노테이션을 붙여줘야함.

### 2.3.2 테스트 결과의 일관성
- 코드에 변경사항이 없다면 테스트는 항상 동일한 결과를 내야 한다.
  - 테스트하기 전에 테스트 실행에 문제가 되지 않는 상태를 만들어주는 편이 나음.
- 단위 테스트는 항상 일관성 있는 결과가 보장돼야 한다는 점을 잊지 말자.


### 2.3.3 포괄적인 테스트
- 한가지 결과만 검증하고 마는 테스트는 위험함.
- 테스트 메소드는 ㅎ나 번에 한 가지 검증 목적에만 충실한 것이 좋음.

- JUnit은 테스트 메소드의 실행 순서를 보장해주지 않음.
  - 테스트의 결과가 테스트 실행 순서에 영향을 받는다면 테스트를 잘못 만든 것임.

- 개발자가 테스트 작성시 많이 하는 실수: 성공하는 테스트만 만드는 것.
  - 테스트 작성 시 부정적인 케이스를 먼저 작성하는 습관을 들이면 좋음.
  - 예외적인 상황을 빠뜨리지 말자.



### 2.3.5 테스트 코드 개선
#### JUnit 테스트 수행 방식
1. 테스트 클래스에서 @Test가 붙은 public이고 void형에 파리미터가 없는 테스트 메서드를 모두 찾는다.
2. 메소드 테스트 하나에 대해서 테스트 클래스의 오브젝트를 하나 만든다. 
3. @Before가 붙은 메소드가 있으면 실행한다.
4. @Test가 붙은 메소드를 하나 호출하고 테스트 결과를 저장해둔다.
5. @After가 붙은 메소드가 있으면 실행한다.
6. 나머지 테스트 메소드에 대해 2~5번을 반복한다.
7. 모든 테스트의 결과를 종합해서 돌려준다.


#### JUnit 기억사항
* 공통적인 준비/정리 작업은 @Before/@After 가 붙은 메서드에서 실행
* 서로 주고받을 정보나 오브젝트가 있다면 인스턴스 변수를 이용
* **각 테스트 메소드를 실행할 때마다 테스트 클래스의 오브젝트를 새로 만듬.**
  - 한번 만들어진 테스트 클래스 오브젝트는 테스트 메소드 하나를 사용하고 나면 버려짐.
  - 이렇게 함으로써 각 테스트가 서로 영향을 주지 않고 독립적으로 실행됨을 확실해 보장됨.

* 테스트 메소드중 일부에서만 공통적으로 사용되는 공통코드가 있다면 @Before보단, 
  - 메소드 분리후 테스트 메소드에서 직접 호출하거나
  - 공통적인 특징을 지닌 테스트 메소드를 모아서 별도의 테스트 클래스로 만드는 방법

* Fixture: 테스트를 수행하는 데 필요한 정보나 오브젝트
  - 중복제거를 위해 픽스쳐를 로컬변수에서 인스턴스 변수화시키고, @Before 메소드에서 초기화해주는 쪽으로
  
## 2.4 스프링 테스트 적용
- @Before 메소드는 테스트 개수만큼 반복되는데, 이 안에 ApplicationContext 생성 코드를 넣으면 ApplicationContext를 여러번 생성하게됨.
- ApplicationContext 생성시 모든 싱글톤 빈 오브젝트를 초기화하는데, 빈 오브젝트에 따라 오랜 시간이나 많은 리소스를 할당해야하는 것도 있음.
- ApplicationContext는 초기화되고 나면 내부의 상태가 바뀌는 일은 거의 없음.(stateless)
- ApplicationContext는 스프링에서 제공하는 애플리케이션 컨텍스트 테스트 지원 기능을 사용하는게 편리


### 2.4.1 테스트를 위한 애플리케이션 컨텍스트 관리
* 라이브러리 추가 필요
  - org.springframework.test-3.0.7.RELEASE.jar
  
#### @RunWith: JUnit 프레임워크의 테스트 실행 방법을 확장할 때 사용하는 애노테이션
- SpringJUnit4ClassRunner 라는 JUnit 테스트 컨텍스트 프레임워크 확장 클래스를 지정하면 JUnit이 테스트를 진행하는 동안 테스트가 사용할 애플리케이션 컨텍스트를 만들고 관리하는 작업 진행
  
#### @ContextConfiguration: 자동으로 만들어줄 애플리케이션 컨텍스트의 설정파일 위치 지정
- 스프링은 설정파일의 종류만큼 애플리케이션 컨텍스트를 만들고, 같은 설정파일을 지정한 테스트 클래스에서는 이를 공유하게 함.
  - (100개의 테스트 클래스에서도 1개의 설정파일을 호출하면 applicationContext는 1개)
  
#### Autowired (스프링 DI를 위한 애노테이션) 
@Autowired가 붙은 인스턴스 변수가 있으면, 테스트 컨텍스트 프레임워크는 변수 타입과 일치하는 컨텍스트 내의 빈을 찾음.  
일치하는 빈이 있으면 인스턴스 변수에 주입해줌.     
일반적으로는 주입을 위해서 생성자나 수정자 메서드 같은 메서드가 필요하지만, 이 경우엔 메소드가 없어도 주입이 가능   
  
- 애플리케이션 컨텍스트는 초기화할 때 자기 자신도 빈으로 등록하기 때문에 ApplicationContext에 @Autowired를 붙여도 DI가 가능했음.
- @Autowired는 같은 타입의 빈이 두 개 이상 있는 경우에는 타입만으로는 어떤 빈을 가져올지 결정할 수 없음.
  - @Autowired는 타입으로 가져올 빈 하나를 선택할 수 없는 경우에는 변수의 이름과 같은 이름의 빈이 있는지 확인.
    - 그마저도 없으면 예외 발생.



### 2.4.2 DI와 테스트
만약 테스트내에서 공유되는 ApplicationContext의 상태를 변경할 필요가 있다면, 테스트 클래스에 @DirtiesContext 애노테이션을 붙이면 됨.   
테스트 컨텍스트는 이 애노테이션이 붙은 클래스에는 애플리케이션컨텍스트의 공유를 허용X  
@DirtiesContext를 메소드 레벨에 붙일 수도 있음.



#### 테스트 기법
* 동등 분할(Equivalence Partitioning)
  - 같은 결과를 내는 값의 범위를 구분해서 각 대표 값으로 테스트하는 방법.
  - 어떤 작업의 결과의 종류가 true, false, exception 3가지라면 각 결과를 내는 입력 값이나 상황의 조합을 만들어서 모든 경우에 대한 테스트를 해보는 것이 좋음.
* 경계값 분석(Boundary Value Analysis)
  - 에러는 동등분할 범위의 경계에서 주로 많이 발생한다는 특징을 이용해서 경계의 근처에 있는 값을 이용해 테스트하는 방법
  - 보통 숫자의 입력 값인 경우 0이나 그 주변 값 또는 정수의 최대값, 최소값 등으로 테스트해보면 도움이 될 때가 많음.
