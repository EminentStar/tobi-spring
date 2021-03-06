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
  - org.springframework.transaction-3.0.0.RELEASE.jar
    - Spring 3에서 DataAccessException, EmptyResultDataAccessException은 위의 jar에 포함되있었음..

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
  

# Chap 03: 템플릿
템플릿:   
바뀌는 성질이 다른 코드중에서 변경이 거의 일어나지 않고,   
일정한 패턴으로 유지되는 특성을 가진 부분을 자유롭게 변경되는 성질을 가진 부분으로부터 독립시켜서 효과적으로 활용할 수 있도록 하는 방법   

#### DB connection pool
일반적으로 서버에서는 제한된 개수의 DB 커넥션을 만들어서 재사용 가능한 풀로 관리  
db풀은 매번 getConnection()으로 가져간 커넥션을 명시적으로 close()해서 돌려줘야지만 다시 풀에 넣었다가 다음 커넥션 요청이 있을 때 재사용 가능.(close()는 사용한 리소스를 풀로 다시 돌려주는 역할)  


## 3.3 JDBC 전략 패턴의 최적화

#### 다른 클래스 내부에 정의되는 클래스를 중첩 클래스(nested class)라고 함.
* static class: 독립적으로 오브젝트로 만들어질 수 있음.
* inner class: 자신이 정의된 클래스의 오브젝트 안에서만 만들어질 수 있음.
  - score에 따라 나뉨
  1. member inner class: 멤버 필드처럼 오브젝트 레벨에서 정의되는
  2. local class: 메소드 레벨에서 정의되는
  3. anonymous inner class: 이름을 갖지 않는


#### 익명 내부 클래스
클래스 선언과 오브젝트 생성이 결합된 형태로 만들어지며, 상속할 클래스나 구현할 인터페이스를 생성자 대신 사용해서 다음과 같은 형태로 만들어 사용  
클래스를 재사용할 필요가 없고, 구현한 인터페이스 타입으로만 사용할 경우에 유용.  
```java
new INTERFACE_NAME() { // codes };
```


# Chap 04: 예외
### 4.1.2 예외의 종류와 특징
자바에서 throw를 통해 발생시킬 수 있는 예외는 크게 3가지
1. Error
  - java.lang.Error클래스의 서브클래스들.
  - 시스템에 뭔가 비정상적인 상황이 발생했을 경우에 사용됨.
  - e.g. OutOfMemoryError, ThreadDeath
2. Exception과 체크 예외
  - java.lang.Exception 클래스와 그 서브클래스로 정의되는 예외.
  - 에러와 달리 개발자들이 만든 애플리케이션 코드의 작업 중에 예외상황이 발생했을 경우에 사용됨.
  - Exception은 체크 예외와 언체크 예외로 구분됨.
    - 체크 예외: RuntimeException을 상속하지 않은 것.
      - 체크 예외가 발생할 수 있는 메소드를 사용할 경우 반드시 예외를 처리하는 코드를 함께 작성해야 함.(catch 혹은 throws 정의)
        - 안하면 컴파일 에러.
        - e.g IOException, SQLException
    - 언체크 예외/런타임 예외: RuntimeException을 상속한 것.
      - 명시적인 예외처리를 강제하지 않기 때문에 unchecked 예외라 불림.
      - 주로 프로그램의 오류가 있을 때 발생하도록 의도된 것들.
      - 피할 수 있지만 개발자가 부주의해서 발생할 수 있는 경우에 발생하도록 만든 것.
        - e.g. NullPointerException, IllegalArgumentException

### 4.1.3 예외처리 방법
#### 1. 예외 복구: 문제를 해결해서 정상 상태로 돌려놓는 것.
```java
int maxretry = MAX_RETRY;
while (maxretry --> 0) {
  try {
    // ... 
    return;
  } 
  catch (SomeException e) {
    // 로그 출력. 정해진 시간만큼 대기
  }
  finally {
    // 리소스 반납. 정리 작업
  }
}

throw new RetryFailedException(); // 최대 재시도 횟수를 넘기면 직접 예외 발생

```
#### 2. 예외처리 회피
- 콜백-템플릿처럼 긴밀하게 역할을 분담하고 있는 관계가 아니라면 자신의 코드에서 발생하는 예외를 그냥 던져버리는 건 무책임한 책임회피 일수도 잇음.
- 예외를 회피하는 것은 예외를 복구하는 것처럼 의도가 분명해야 함.
#### 3. 예외 전환
- (발생한 예외를 그대로 넘기는 게 아니라) 적절한 예외로 전환해서 던짐.
- 목적
  - 기존에 내부에서 발생하는 예외를 그대로 던지는 것이 그 예외상황에 대한 적절한 의미를 부여해주지 못하는 경우에 의미를 분명하게 해줄 수 있는 예외로 바꿔주기 위해.
    - 의미가 분명한 예외가 던져지면 서비스 계층 오브젝트에는 적절한 복구 작업을 시도할 수 있음.
##### 예외 포장: 
체크 예외에서 비즈니스 로직으로 봤을 때 의미있는 예외가 아니거나, 복구 불가능한 예외이면 런타임 예외로 포장해서 던지는 편이 나음.
(e.g StorePlatformException)
```java
try {
  OrderHome orderHome = EJBHomeFactory.getInstance().getOrderHome();
  Order order = orderHome.findByPrimaryKey(Integer id);
} catch (SQLException se) {
  throw new EJBException(se);  
} catch (RemoteException re) {
  throw new EJBException(re);  
}
```
일반적으로 체크예외를 계속 throws 로 던지는 것은 무의미함.(메소드만 지저분해지고 아무런 장점이 없음.)  
어차피 복구가 불가능한 예외라면 가능한 한 빨리 런타임 예외로 포장해 던지게 해서 다른 계층의 메소드를 작성할 때 불필요한 throws 선언이 들어가지 않도록 해줘야 함.

* 라이브러리 추가 필요
  - com.mysql.jdbc_5.1.5.jar


### 애플리케이션 예외
애플리케이션 자체의 로직에 의해 의도적으로 발생시키고, 반드시 catch해서 무엇인가 조치를 취하도록 요구하는 예외.
* 이런 기능을 담은 메소드를 설계하는 두가지 방법
  1. 정상과 예외상황에 대해 각각 다른 종류의 러턴 값을 돌려주는 것.
    - (단점)
    - 예외상황에 대한 리턴 값을 명확하게 코드화하고 관리하지 않으면 혼란이 생길 수 있음.
    - 결과값을 확인하는 조건문이 자주 등장한다는 점.
  2. 정상 흐름의 코드는 놔두고, 잔고 부족과 같은 예외상황에서는 비즈니스적인 의미를 띤 예외를 던지도록 만드는 것.
    - 이 예외는 의도적으로 체크 예외로 만들어, 개발자가 잊지않고 예외상황에 대한 로직을 구현하도록 강제해주는 것이 좋음.

## 4.3 정리
* 예외를 잡아 아무 조치를 하지 않거나 의미없는 throws 선언을 남발하는 것은 위험함.
* 예외는 복구 or 예외처리 오브젝트로 의도적으로 전달 or 적절한 예외로 전환
* 좀 더 의미있는 예외로 변경 or 불필요한 catch/throws를 피하기 위해 RuntimeException으로 포장하는 두가지 방법의 예외 전환이 있음.
* 복구할 수 없는 예외는 가능한 한 빨리 RuntimeException으로 전환하는 것이 바람직.
* 애플리케이션의 로직을 담기 위한 예외는 체크 예외로 만듬.
* JDBC의 SQLException은 대부분 복구할 수 없는 예외이므로 런타임 예외로 포장해야 함.
* SQLException의 에러 코드는 DB에 종속되기 때문에 DB에 독립적인 예외로 전환될 필요가 있음.
* 스프링은 DataAccessException을 통해 DB에 독립적으로 적용가능한 추상화된 RuntimeException 게층을 제공.
* DAO를 데이터 액세스 기술에서 독립시키려면 인터페이스 도입과 런타임 예외 전환, 기술에 독립적인 추상화된 예외로 전환이 필요함.


# Chap 05: 서비스 추상화

* USER 테이블에 필드 추가
  - Level: int not null
  - Login: int not null
  - Recommend: int not null

* DAO는 데이터를 어떻게 가져오고 조작할지를 다루는 곳. 비즈니스 로직을 두는 곳은 아님.
  - Service 클래스를 둬서 DAO를 DI 적용하자.(DI를 적용하려면 받는 쪽(UserService), 주입 당하는 쪽(UserDao) 모두 스프링의 빈으로 등록이 되어야함.)

* 객체지향적인 코드는 다른 object의 데이터를 가져와서 작업하는 대신 데이터를 갖고 있는 다른 object에게 작업을 해달라고 request함.
  - object에게 데이터를 요구하지 말고 작업을 요청하라는 것이 객체지향 프로그래밍의 가장 기본이 되는 원리이기도 함.

## 5.2 트랜잭션 서비스 추상화

#### 트랜잭션
* 더 이상 나눌 수 없는 단위 작업.
  - 작업을 쪼개서 작은 단위로 만들 수 없다는 것은 트랜잭션의 핵심 속성인 원자성을 의미
  - 트랜잭션의 성질: 중간에 예외가 발생해서 작업을 완료할 수 없다면 아예 작업이 시작되지 않은 것처럼 초기 상태로 돌려놔야 함.
* transaction rollback: 트랜잭션내에서 문제가 발생할 경우 앞에서 처리한 SQL 작업도 취소시키는 것.
* transaction commit: 여러개의 SQL을 하나의 트랜잭션으로 처리하는 경우 모든 SQL 작업이 다 성공적으로 마무리됐다고 DB에 알려줘서 작업을 확정시키는 것.
* 트랜잭션 경계: 애플리케이션 내에서 트랜잭션이 시작되고 끝나는 위치

##### JDBC 트랜잭션의 트랜잭션 경계설정
```java
Connection c = dataSource.getConnection();

c.setAutoCommit(false); // 트랜잭션 시작; 트랜잭션의 시작과 종료는 Connection object를 통해 이뤄짐.

try {
  PreparedStatement st1 = 
    c.preparedStatement("update users ...");
  st1.eecuteUpdate();
  
  PreparedStatement st2 = 
    c.preparedStatement("delete users ...");
  st2.eecuteUpdate();
  
  c.commit(); // 트랜잭션 커밋
} catch (Exception e) {
  c.rollback(); // 트랜잭션 롤백
}

c.close();

```
- 일반적으로 작업 중에 예외가 발생하면 트랜잭션을 롤백한다.
    - (예외가 발생했다는 건, 트랜잭션을 구성하는 데이터 액세스 작업을 마무리할 수 없는 ㅏㅇ황이거나 DB에 결과를 반영하면 안 되는 이유가 생겼기 때문.)
- 위와 같이 setAutoCommit(false)로 트랜잭션의 시작을 선언하고 commit() or rollback()으로 트랜잭션을 종료하는 작업을 transaction demarcation(트랜잭션의 경계설정)이라고 함.
- 하나의 DB 커넥션 안에서 만들어지는 트랜잭션을 local transaction이라고도 함.
- 트랜잭션 작업은 내구성을 보장받기 때문에 일단 커밋되고 나면 DB 서버가 다운되더라도 그 결과는 DB에 그대로 남음.
- 트랜잭션은 Connection 오브젝트 안에서 만들어짐.
  - 여러 작업이 하나의 트랜잭션으로 묶이려면 그 작업이 진행되는 동안 DB 커넥션도 하나만 사용돼야 함.
  
#### 5.2.2 트랜잭션 경계설정: 비즈니스 로직 내의 트랜잭션 경계설정의 문제점
1. DB 커넥션을 비롯한 리소스의 깔끔한 처리를 가능하게 했던 JdbcTemplate을 더이상 활용할 수 없게 됨.
2. DAO의 메소드와 비즈니스 로직을 담고 있는 UserService()에 Connection 파라미터가 추가돼야 한다는 점
3. Connection 파라미터가 UserDao 인터페이스 메소드에 추가되면 UserDao는 더 이상 데이터 액세스 기술에 독립적일 수가 없다는 점
  - JPA나 Hibernate로 UserDao의 구현 방식을 변경하려고 하면 Connection 대신 EntityManager나 Session 오브젝트를 UserDao 메소드가 전달받도록 변경해야함.
4. DAO 메소드에 Connection 파라미터를 받게 하면 테스트 코드에도 영향을 미침.
  - 테스트 코드에서 직접 Connection 오브젝트를 만들어서 DAO 메소드를 호출하도록 모두 변경해야함.
  
#### 5.2.3 트랜잭션 동기화 (위의 문제점 해결)
##### 문제점: Connection 파라미터 제거
upgradeLevels() 메소드가 트랜잭션 경계설정을 해야 한다는 점은 그대로 가져가야 함.(그 안에서 Connection을 생성하고 트랜잭션 시작과 종료를 관리.)  
대신 여기서 생성된 Connection 오브젝트를 계속 메소드의 파라미터로 전달하다가 DAO를 호출할 때 사용하게 하는 건 피하도록 하자.
###### 해결: Connection 파라미터 제거 -> transaction synchronization 방식으로 해결
UserService에서 트랜잭션을 시작하기 위해 만든 Connection 오브젝트를 특별한 저장소에 보관해두고,   
이후에 호출되는 DAO의 메소드에서는 저장된 Connection을 가져다가 사용하게 하는 것.(트랜잭션이 모두 종료되면 그때 동기화를 마치면 됨.)   
- 트랜잭션 동기화 저장소는 작업 스레드마다 독립적으로 Connection 오브젝트를 저장하고 관리하기 때문에 다중 사용자를 처리하는 서버의 멀티스레드 환경에서도 충돌이 날 염려는 없음.
- 트랜잭션 동기화 기법을 사용하면 파라미터를 통해 일일이 Connection 오브젝트를 전달할 필요가 없어짐.

##### JdbcTemplate과 트랜잭션 동기화
- 만약 미리 생성돼서 트랜잭션 동기화 저장소에 등록된 DB 커넥션이나 트랜잭션이 없는 경우에는 JdbcTemplate이 직접 DB 커넥션을 만들고 트랜잭션을 시작해서 JDBC 작업을 진행
- 트랜잭션 동기화를 시작해놓았다면 그때부터 실행되는 JdbcTemplate의 메소드에서는 (직접 DB 커넥션을 만드는 대신) 트랜잭션 동기화 저장소에 들어 있는 DB 커넥션을 가져와서 사용함.
* JDBC 코드의 `try/catch/finally 작업 흐름 지원`, `SQLException 예외 변환`과 함께 JdbcTemplate이 제공하는 3 가지 유용한 기능중 하나임.

#### 5.2.4 트랜잭션 서비스 추상화
- 한개 이상의 DB로의 작업을 하나의 트랜잭션으로 만드는 건 JDBC의 Connection을 이용한 트랜잭션 방식인 로컬 트랜잭션으로는 불가능.(로컬 트랜잭션은 하나의 DB 커넥션에 종속됨.)
- 별도의 트랜잭션 관리자를 통해 트랜잭션을 관리하는 글로벌 트랜잭션 방식을 사용해야함. (e.g. JTA(Java Transaction API))
- 근데 DB 종류가 바뀌면 트랜잭션 관리자도 변경되어야 함.
- UserService에서 트랜잭션의 경계설정을 해야 할 필요가 생기면서 다시 특정 데이터 액세스 기술에 종속되는 구조가 되고 말았음.
```java
/**
* JTA르 이용한 트랜잭션 코드 구조
* */
InitialContext ctx = new InitialContext();
UserTransaction tx = (UserTransaction)ctx.lookup(USER_TX_JNDI_NAME);

tx.begin();
Connection c = dataSource.getConnection();

try {
  // 데이터 액세스 코드
  
  tx.commit();
} catch (Exception e) {
  tx.rollback();
  throw e;
} finally {
  c.close();
}
```
* 그런데 트랜잭션 경계설정을 담당하는 코드는 일정한 패턴을 갖는 유사한 구조임.
  - 이렇게 공통점이 있다면 추상화를 생각해볼 수 있음.
  - `추상화`란 하위 시스템의 공통점을 뽑아내서 분리시키는 것을 말함. 이렇게 하면 하위 시스템이 어떤 것인지 알지 못해도, 또는 하위 시스템이 바뀌더라도 일관된 방법으로 접근할 수 있음.
* **JDBC, JTA, Hibernate, JPA, JDO, JMS등의 트랜잭션 경계설정 방법에서 공통점을 뽑아 낼 수 있음.**
  - 스프링은 트랜잭션 기술을 공통점을 담은 트랜잭션 추상화 기술을 제공하고 있음.
  - 이렇게 하면 애플리케이션에서 직접 각 기술의 트랜잭션 API를 이용하지 않고도, 일관된 방식으로 트랜잭션을 제어하는 트랜잭션 경계설정 작업이 가능해짐.
    - 스프링이 제공하는 트랜잭션 경계설정을 위한 추상 인터페이스는 PlatformTransactionManager.
    - JDBC의 로컬 트랜잭션을 이용한다면 PlatformTransactionManager를 구현한 DataSourceTransactionManager를 사용하면 됨.
    - 스프링의 트랜잭션 추상화 기술은 앞에서 적용했던 트랜잭션 동기화를 사용함.
    - PlatformTransactionManager로 시작한 트랜잭션은 트랜잭션 동기화 저장소에 저장됨.
    - PlatformTransactionManager를 구현한 DataSourceTransactionManager 오브젝트는 JdbcTemplate에서 사용될 수 있는 방식으로 트랜잭션을 관리함.


* 어떤 클래스든 스프링의 빈으로 등록할 때 먼저 검토해야 할 것은 싱글톤으로 만들어져 여러 스레드에서 동시에 사용해도 괜찮은 가 하는 점.
  - 상태를 갖고 있고, 멀티스레드 환경에서 안전하지 않은 클래스를 빈으로 무작정 등록하면 심각한 문제가 발생하기 때문.
  
### 5.4 메일 서비스 추상화
* 라이브러리 추가 필요
  - com.springsource.javax.activation-1.1.0.jar
  - com.springsource.javax.mail-1.4.0.jar
  - org.springframework.context.support-3.0.7.RELEASE.jar

#### 5.4.4 테스트 대역
실전에서 사용할 오브젝트를 교체하지 않더라도, 단지 테스트만을 위해서도 DI는 유용함.  
##### 테스트 대역의 종류와 특징
* 테스트 대역(test double): 테스트 환경을 만들어주기 위해, 테스트 대상이 되는 오브젝트의 기능에만 충실하게 수행하면서 빠르게, 자주 테스트를 실행할 수 있도록 사용하는 오브젝트.
  - 테스트 스텁(test stub): 테스트 대상 오브젝트의 의존객체로서 존재하면서 테스트 동안에 코드가 정상적으로 수행할 수 있도록 돕는 것.
    - 스텁을 이용하면 간접적인 입력 값을 지정해 줄 수도 있음.
    - 스텁을 이용하면 간접적인 출력 값을 받게 할 수도 있음.
  - 목 오브젝트(mock object): 테스트 대상의 간접적인 출력 결과를 검증하고, 테스트 대상 오브젝트와 의존 오브젝트 사이에서 일어나는 일을 검증할 수 있도록 설계된 오브젝트.
    - 간접적인 출력 값까지 확인이 가능함.

### 5.5 정리
- 비즈니스 로직을 담은 코드는 데이터 액세스 로직을 담은 코드와 깔끔하게 분리되는 것이 바람직. 비즈니스 로직 코드 또한 내부적으로 책임과 역할에 따라서 깔끔하게 메소드로 정리돼야 함.
- 이를 위해 DAO의 기술 변화에 서비스 계층의 코드가 영향을 받지 않도록 인터페이스와 DI를 잘 활용해서 결합도록 낮춰줘야 함.
- DAO를 사용하는 비즈니스 로직에는 단위 작업을 보장해주는 트랜잭션이 필요
- 트랜잭션의 시작과 종료를 지정하는 일을 트랜잭션 경계설정이라고 함. 트랜잭션 경계썰정은 주로 비즈니스 로직 안에서 일어나는 경우가 많음.
- 시작된 트랜잭션 정보를 담은 오브젝트를 파라미터로 DAO에 전달하는 방법은 매우 비효율적이기에 스프링이 제공하는 트랜잭션 동기화 기법을 활용하는 것이 편리.
- 자바에서 사용되는 트랜잭션 API의 종류와 방법은 다양함. 환경과 서버에 따라서 트랜잭션 방법이 변경되면 경계설정 코드도 함께 변경돼야 함.
- 트랜잭션 방법에 따라 비즈니스 로직을 담은 코드가 함께 변경되면 단일 책임 원칙에 위배되며, DAO가 사용하는 특정 기술에 대해 강한 결합을 만들어냄.
- 트랜잭션 경계설정 코드가 비즈니스 로직 코드에 영향을 주지 않게 하려면 스프링이 제공하는 트랜잭션 서비스 추상화를 이용하면 됨.
- 서비스 추상화는 로우레벨의 트랜잭션 기술과 API의 변화에 상관없이 일관된 API를 가진 추상화 계층을 도입함.
- 서비스 추상화는 테스트하기 어려운 JavaMail 같은 기술에도 적용할 수 있음. 테스트를 편리하게 작성하도록 도와주는 것만으로도 서비스 추상화는 가치가 있음.
- 테스트 대상이 사용하는 의존 오브젝트를 대체할 수 있도록 마든 오브젝트를 테스트 대역이라고 함.
- 테스트 대역은 테스트 대상 오브젝트가 원활하게 동작할 수 있도록 도우면서 테스트를 위해 간접적인 정보를 제공해주기도 함.
- 테스트 대역 중에서 테스트 대상으로 부터 전달받은 정보를 검증할 수 있도록 설계된 것을 목 오브젝트라 함.


# Chap 06: AOP
AOP는 IoC/DI, 서비스 추상화와 더불어 스프링의 3대 기반기술 중 하나

### 여기까지의 문제
* 5장에서 트랜잭션 경계설정을 위한 코드때문에 비즈니스 로직이 주인이어야할 메소드에 트랜잭션 코드가 더 많아짐    

### @Autowired
- 기본적으로 타입이 일치하는 빈을 찾아줌.
- 타입으로 하나의 빈을 결정할 수 없는 경우 필드 이름을 이용해 빈을 찾음.


### 6.2.4 목 프레임워크
* 라이브러리 추가 필요(File/Project Structure/Libraries/New Project Library/Java)
  - mockito-all-1.9.0-rc1.jar


* Mockito 목 오브젝트를 사용하는 4단계(2, 4번째는 필요할 경우에만 사용 가능)
  1. 인터페이스를 이용해 목 오브젝트를 만듬
  2. 목 오브젝트가 리턴할 값이 있으면 이를 지정해줌. 메소드가 호출되면 예외를 강제로 던지게 만들 수도 있음.
  3. 테스트 대상 오브젝트에 DI해서 목오브젝트가 테스트중에 사용되도록 함.
  4. 테스트 대상 오브젝트를 사용한 후에 목 오브젝트의 특정 메소드가 호출됐는지, 어떤 값을 가지고 몇 번 호출됐는지를 검증

### 6.3.2 다이내믹 프록시

* 자바의 모든 클래스는 그 클래스 자체의 구성정보를 담은 Class 타입의 오브젝트를 하나씩 갖고 있음.
  - `클래스이름.class` or `오브젝트의 getClass() 호출`
* Method 인터페이스에 정의된 invoke() 메소드를 이용해 메소드를 실행할 수 있음.
 
 
* 일반적인 프록시의 기능
  1. 타깃과 같은 메소드를 구현하고 있다가 메소드가 호출되면 타깃 오브젝트로 위임.
  2. 지정된 요청에 대해서는 부가기능을 수행함.
  
* 일반적인 프록시의 문제점
  1. 인터페이스의 모든 메소드를 구현해 위임하도록 코드륵 만들어야함.(번거로움)
  2. 부가기능 코드가 중복될 가능성이 많음.
  
  
* 다이내믹 프록시: 프록시 팩토리에 의해 런타임 시 다이내믹하게 만들어지는 오브젝트
  - 프록시로 필요한 부가기능 제공 코드는 직접 작성해야 함.(부가기능은 프록시 오브젝트와 독립적으로 InvocationHandler를 구현한 오브젝트에 담음.)
  - 다이내믹 프록시 오브젝트는 클라이언트의 모든 요청을 리플렉션 정보로 변환해서 InvocationHandler 구현 오브젝트의 invoke() 메소드로 넘김.
    - InvocationHandler 구현 오브젝트가 타깃 오브젝트 레퍼런스를 갖고 있다면 리플렉션을 이용해 간단히 위임 코드를 만들어 낼 수 있음.

* 다이내믹 프록시로부터 요청을 전달받으려면 InvocationHandler를 구현해야 함.(메소드는 invoke() 하나.)
  - 다이내믹 프록시가 클라이언트로부터 받는 모든 요청은 invoke() 메소드로 전달됨.


- **다이내믹 프록시를 사용하면 Hello 인터페이스의 메소드가 더 늘어나더라도 전혀 손 댈 것이 없음.**
  - 클래스로 직접 구현한 프록시의 경우 인터페이스가 변경될 때마다 추가된 메소드를 매번 추가해야 한다.!
 

### 6.3.3 다이내믹 프록시를 이용한 트랜잭션 부가기능

- 리플렉션 메소드인 Method.invoke()를 이용해 타깃 오브젝트의 메소드를 호출할 때는 타깃 오브젝트에서 발생하는 예외가 InvocationTargetException으로 한 번 포장되서 전달된다.
  - 그래서 일단 InvocationTargetException으로 받은 후 getTargetException() 메소드로 중첩되어 있어는 예외를 가져와야 한다.
  
### 6.3.4. 다이내믹 프록시를 위한 팩토리 빈
* 문제: 다이내믹 프록시 오브젝트는 클래스 자체도 내부적으로 다이내믹하게 새로 정의해서 사용하기에 클래스가 어떤 것인지 알 수 없음.
  - 일반적인 스프링의 빈으로 등록할 방법이 없음.

* Factory Bean: 빈을 만들 수 있는 방법 중 하나. 스프링을 대신해서 오브젝트의 생성로직을 담당하도록 만들어진 특별한 빈.

- 스프링은 private 생성자를 가진 클래스도 빈으로 등록해주면 리플렉션을 이용해 오브젝트를 만들어 주긴 함(리플렉션은 private으로 선언된 접근 규약을 위반할 수 있는 기능이 잇기 때문.)
  - 권장되지 않음. 잘 동작안할 수도 있음.

## 6.4. 스프링의 프록시 팩토리 빈

### 6.4.1. ProxyFactoryBean
* 라이브러리 추가 필요(File/Project Structure/Libraries/New Project Library/Java)
  - com.springsource.org.aopalliance-1.0.0.jar
  - org.springframework.aop-3.0.7.RELEASE.jar
  
#### Advice: 타깃이 필요 없는 순수한 부가기능

* MethodInterceptor로는 메소드 정보와 함께 타깃 오브젝트가 담긴 MethodInvocation 오브젝트가 전달됨.
  - MethodInvocation은 타깃 오브젝트의 메소드를 실행할 수 있는 기능이 있기 때문에 MethodInterceptor는 부가기능을 제공하는 데만 집중할 수 있음.
  - MethodInvocation은 일종의 콜백 오브젝트로, prceed() 메소드를 실행하면 타깃 오브젝트의 메소드를 내부적으로 실행해주는 기능이 있음. 
  - 이 MethodInvocation 구현 클래스는 일종의공유 가능한 템플릿처럼 동작하는 것
  
* ProxyFactoryBean은 기본적으로 JDK가 제공하는 다이내믹 프록시를 만들어주는데, 경우에 따라서는 CGLib라고 하는 오픈소스 바이트코드 생성 프레임워크를 이용해 프록시를 만들기도 함. 
  
* advice: 타깃 오브젝트에 적용하는 부가기능을 담은 오브젝트를 스프링에선 advice라고 부름

#### Pointcut: 부가기능 적용 대상 메소드 선정 방법
* pointcut: 메소드 선정 알고리즘을 담은 오브젝트

- advice와 pointcut 모두 프록시에 DI로 주입돼서 사용됨.
- 두가지 모두 여러 프록시에서 공유가 가능하도록 만들어지기 때문에 스프링의 싱글톤 빈으로 동륵이 가능함.

1. proxy는 client로부터 요청을 받으면 먼저 pointcut에게 부가기능을 부여할 메소드인지를 확인해달라고 요청함.
2. proxy는 pointcut으로 부터 부가기능을 부여할 메소드인지 확인받으면 MethodInterceptor 타입의 advice를 호출함.
  - advice는 직접 타깃을 호출하지 않음.(자신이 공유돼야 하기에 타깃 정보를 가질 수 없음.)
  - 타깃에 직접 의존하지 않도록 일종의 템플릿 구조로 설계되어 있음.
  - 타깃 메소드의 호출이 필요하면 프록시로부터 전달받은 MethodInvocation 타입 콜백 오브젝트의 proceed() 메소드를 호출해주기만 하면 됨.
3. 실제 위임 대상인 타깃 오브젝트의 레퍼런스를 갖고 있고, 이를 이용해 타깃 메소드를 직접 호출하는 것은 프록시가 메소드 호출에 따라 만드는 Invocation 콜백의 역할.

* advisor = pointcut과 advice의 조합

## 6.5. 스프링 AOP
* 프록시 팩토리 빈 방식의 접근 방법의 한계라고 생각했던 두 가지 문제중에 부가기능이 타깃 오브젝트마다 새로 만들어지는 문제는 스프링 ProxyFactoryBean 어드바이스를 통해 해결
* 문제: 부가기능의 적용이 필요한 타깃 오브젝트마다 거의 비슷한 내용의 ProxyFactoryBean 빈 설정정보를 추가해주는 부분이 남음.


* BeanPostProcessor(빈 후처리기): 스프링 빈 오브젝트로 만들어지고 난 후에, 빈 오브젝트를 다시 가공할 수 있게 해줌.
  - 스프링은 BeanPostProcessor가 빈으로 등록되어 있으면 빈 오브젝트가 생성될 때마다 BeanPostProcessor에 보내서 후처리 작업을 요청한다.
  - BeanPostProcessor는 빈 오브젝트의 프로퍼티를 강제로 수정할 수도 있고 별도의 초기화 작업을 수행할 수도 있다. 
  - 심지어 만들어진 빈 오브젝트 자체를 바꿔치기 할 수도 있다. 따라서 스프링이 설정을 참고해서 만든 오브젝트가 아닌 다른 오브젝트를 빈으로 등록시키는 것이 가능함.
  - 이를 잘 이용하면 스프링이 생성하는 빈 오브젝트의 일부를 프록시로 포장하고, 프록시를 빈으로 대신 등록할 수도 있음.(이것이 자동 프록시 생성 빈 후처리기;DefaultAdvisorAutoProxyCreator)
  

* DefaultAdvisorAutoProxyCreator
  1. 등록된 빈 중에서 Advisor 인터페이스를 구현한 것을 모두 찾는다.
  2. 생성되는 모든빈에 대해 어드바이저의 포인트컷을 적용해보면서 프록시 적용 대상을 선정한다.
  3. 빈 클래스가 프록시 선정 대상이라면 프록시를 만들어 원래 빈 오브젝트와 바꿔치기한다.
    - 원래 빈 오브젝트는 프록시 뒤에 연결돼서 프록시를 통해서만 접근 가능하게 바뀌는 것.
    - 타깃 빈에 의존한다고 정의한 다른 빈들은 프록시 오브젝트를 대신 DI 받게 됨.
    
    
### 6.5.3. 포인트컷 표현식을 이용한 포인트컷 
- 포인트컷 표현식을 지원하는 포인트컷을 적용하려면 AspectJExpressionPointcut 클래스를 사용하면 됨.
- AspectJ 포인트컷 표현식은 포인트컷 지시자를 이용해 작성함.
  - 대표적으로 사용되는 것이 execution()
  - bean(): bean(*Service)라고 쓰면 아이디가 Service로 끝나는 모든 빈을 선택함.
  - 또한 특정 애노테이션이 타입, 메소드, 파라미터에 적용되어 있는 것을 보고 메소드를 선정하게 하는 포인트컷도 만들 수 있음.
    - e.g. `@annotation(org.springframework.transaction.annotation.Transactional)`은 @Transactional이라는 애노테이션이 적용된 메소드를 선정하게 해줌.



* 라이브러리 추가 필요(File/Project Structure/Libraries/New Project Library/Java)
  - com.springsource.org.aspectj.tools-1.6.6.RELEASE.jar
  

* 포인트컷 표현식의 클래스에 적용되는 패턴은 타입 패턴임.(클래스 이름 패턴이 아님.)


## 6.6. 트랜잭션 속성

### 6.6.1. 트랜잭션 정의
#### 1. Transaction Propagation
TransactionDefinition은 트랜잭션의 동작방식에 영향을 줄 수 있는 네가지 속성을 정의
1. transaction propagation: 트랜잭션의 경계에서 이미 진행 중인 트랜잭션이 있을 때 또는 없을 때 어떻게 동작할 것인가를 결정하는 방식.(독자적인 트랜잭션 경계를 가진 코드에 대해 이미 진행 중인 트랜잭션이 어떻게 영향을 미칠 수 있는가를 정의하는 것.)
  * `PROPAGATION_REQUIRED`: 가장많이 사용되는 transaction propagation 속성.
    - 진행 중인 트랜잭션이 없으면 새로 시작하고, 이미 시작된 트랜잭션이 있으면 이에 참여함.
    - A, B 메소드가 둘다 propagation required면 A, B, A-B, B-A 네가지의 조합된 트랜잭션이 가능함.
    - DefaultTransactionDefinition의 트랜잭션 전파 속성이 `PROPAGATION_REQUIRED`
  * `PROPAGATION_REQUIRES_NEW`: 항상 새로운 트랜잭션을 시작.
    - 독립된 트랜잭션이 보장돼야 하는 코드에 적용할 수 있음.
  * `PROPAGATION_NOT_SUPPORTED`: 트랜잭션 없이 동작하도록 만들 수 있음.
    - 보통 트랜잭션 경계설정은 AOP를 이용해 한 번에 많은 메소드에 동시에 적용하는 방법을 사용함.
    - 그런데 그중에 특별한 메소드만 트랜잭션 적용에서 제외하려면 포인트컷이 상당히 복잡해짐.
    - 그래서 특정 메소드의 트랜잭션 전파 속성만 `PROPAGATION_NOT_SUPPORTED`로 설정해서 트랜잭션 없이 동작하게 만드는 편이 나음.

#### 2. Isolation Level
모든 DB transaction은 isolation level을 갖고 있어야 함.
- 서버 환경에선 여러개의 트랜잭션이 동시에 진행될 수 있는데, 적절하게 격리수준을 조정해서 가능한 한 많은 트랜잭션을 동시에 진행시키면서도 문제가 발생하지 않게 하는 제어가 필요함.
- isolation level은 기본적으로 db에 설정되어 있지만, jdbc driver나 DataSource등에서 재설정 할 수 있고, 필요하다면 트랜잭션 단위로 격리수준을 조정할 수 도 있음.
- DefuaultTransactionDefinition에 설정된 격리수준은 `ISOLATION_DEFAULT`.(DataSource의 격리수준을 그대로 따른다는 뜻.)
- 특별한 작업을 수행하는 메소드의 경우는 독자적인 격리수준을 지정할 필요가 있음.

#### 3. Timeout
트랜잭션을 수행하는 제한시간(timeout)을 설정할 수 있음.
- DefaultTransactionDefinition의 기본 설정은 제한시간이 없음.
- 제한시간은 트랜잭션을 직접 시작할 수 있는 `PROPAGATION_REQUIRED`, `PROPAGATION_REQUIRES_NEW`와 함께 사용해야만 의미가 있음.

#### 4. Readonly
- readonly로 설정해두면 트랜잭션 내에서 데이터를 조작하는 시도를 막아줄 수 있음.
- 또한 데이터 액세스 기술에 따라 성능이 향상될 수도 있음.

#### 5. Transaction Rollback Exception

#### 6. transaction Commit Exception

* **트랜잭션 정의를 바꾸고 싶다면 DefaultTransactionDefinition을 사용하는 대신 외부에서 정의된 TransactionDefinition 오브젝트를 DI받아서 사용하도록 하면 됨.**


### 6.6.2. 트랜잭션 인터셉터와 트랜잭션 속성
스프링엔 편리하게 트랜잭션 경계설정 어드바이스로 사용할 수 있도록 만들어진 TransactionInterceptor가 존재함.

* rollbackOn(): 어떤 예외가 발생하면 롤백을 할 것인가를 결정하는 메소드.



#### tx 네임스페이스를 이용한 설정 방법
- TransactionInterceptor 타입의 어드바이스 빈과 TransactionAttribute 타입의 속성 정보도 tx 스키마의 전용 태그를 이용해 정의할 수 있음.
  - 트랜잭션 어드바이스도 포인트컷이나 어드바이저만큼 자주 사용되고, 애플리케이션의 컴포넌트가 아닌 컨테이너가 사용하는 기반기술 설정의 한가지이기 때문.


### 6.6.3. 포인트컷과 트랜잭션 속셩의 적용전략
1. 트랜잭션 포인트컷 표현식은 타입 패턴이나 빈 이름으 이용
2. 공통된 메소드 이름 규칙을 통해 최소한의 트랜잭셕 어드바이스와 속성을 정의함 
3. (주의사항) 프록시 방식 AOP는 같은 타깃 오브젝트 내의 메소드를 호출할 때는 적용되지 않음.
  - 타깃 오브젝트가 자기 자신의 메소드를 호출할 때는 프록시를 통한 부가기능의 적용이 일어나지 않음.
    * 해결 방법
      1. 스프링 API를 이용해 프록시 오브젝트에 대한 레퍼런스를 가져온 뒤에 같은 오브젝트의 메소드 호출도 프록시를 이용하도록 강제하는 방법(스프링 api와 프록시 호출코드로 복잡성 증가;별로 추천X)
      2. AspectJ와 같은 타깃의 바이트코드를 직접 조작하는 방식의 AOP 기술을 적용하는 것.
        - 스프링은 프록시 기반의 AOP를 기본적으로 사용하고 있지만 필요하다면 AspectJ 방식으로 변경가능.


## 6.7 애노테이션 트랜잭션 속성과 포인트컷
##### @Transactional
* 내가 이해한 것.
  - 문제점: 하나의 트랜잭션으로 묶을 필요가 있는 로직들을 코드로 경계를 묶기 위해서는 **DB와 관련된 선후작업(Connection 열고, DB 액세스, Connection 닫기와 같은)이 똑같이 반복**되고, **DB 관련 파라미터(Connection와 같은)들을 메소드를 트래킹시 중복해서 달고다녀야**하는 복잡함이 있음.
<br>
- 위의 문제점을 해결하기 위해 AOP를 활용하여 일련의 코드를 하나의 트랜잭션으로 묶을 수 있게 하는데(Declarative Transaction이라고 부른다고함.) 사용되는 애노테이션이 @Transactional

###### @Transactional 기능: 트랜잭션 속성 정의 + 포인트컷의 자동등록에 사용
- 스프링은 @Transactional이 부여된 모든 오브젝트를 자동으로 타깃 오브젝트로 인식.(TransactionAttributeSourcePointcut이 사용됨.)
- TransactionInterceptor(트랜잭션 경계설정 advice로 사용)는 AnnotationTransactionAttributeSource(@Transactional의 엘러먼트에서 트랜잭션 속성을 가져오는 역할) 사용.

![...](https://dhsim86.github.io/static/assets/img/blog/web/2017-09-14-toby_spring_06_aop_3/02.png)

###### @Transactional fallback 정책
* 우선순위에 따라 가장 먼저 나오는 @Transactional의 속성정보를 이용함.
###### 우선 순위
타깃 메소드 > 타깃 클래스 > 선언(타깃 클래스가 구현한 인터페이스) 메소드 > 선언 타입

```java
[1]
public interface Service {
    [2]
    void method1();
    [3]
    void method2();
}
[4]
public class ServiceImpl implements Service {
    [5]
    public void method1() {}
    [6]
    public void method2() {}
} // 우선순위: {[5], [6]} > [4] > {[2], [3]} > [1]
```



### 6.8.3 테스트를 위한 트랜잭션 애노테이션
#### @Transactional
- 테스트 클래스 또는 메소드에 @Transactional 애노테이션을 부여해주면 마치 타깃 클래스나 인터페이스에 적용된 것 처럼 테스트 메소드에 트랜잭션 경계가 자동으로 설정됨.(목적은 AOP를 위한 것은 아니지만, 트랜잭션을 부여해주는 용도로 쓰임.)
- 클래스 레벨에 부여할 경우 -> 이경우 클래스 내의 모든 메소드에 transaction이 적용됨.
- 각 메소드에 @Transactional을 지정 -> 클래스의 공통 트랜잭션과는 다른 속성 지정가능.
  - 메소드의 트랜잭션 속성이 클래스의 속성보다 우선.
- @Transactional을 이용한 테스트용 트랜잭션은 **테스트가 끝나면 자동으로 롤백**됨.(그외에는 일반 클래스와의 디폴트 속성은 동일.)


#### @Rollback
> @Transactional을 테스트에 사용했을 때의 문제점
> @Transactional은 기본적으로 테스트에서 사용할 용도로 만든 게 아니라서 롤백 테스트에 대한 설정을 담을 수 없음.(무조건 자동 롤백)
> 이런 경우 테스트에서 작업들을 한 트랜잭션을 묶고는 싶지만 롤백을 원하지 않을 때 @Transactional로만은 부족함.

- 롤백 여부를 지정하는 값을 갖고 있음.(default는 true)
- 메소드 레벨에만 적용가능  -> spring 2.5 이후로 클래스에도 적용가능해진걸로 확인.
```java
// 아래와 같이 설정해주면 테스트메소드 전체에 걸쳐 하나의 트랜잭션이 만들어지고 예외가 발생하지 않는 한 트랜잭션은 커밋됨.
@Test
@Transactional
@Rollback(false)
public void transactionSync() {
}
```

#### @TransactionConfiguration
> @Rollback의 문제점 
> @Transactional과 달리 @Rollback은 메소드 레벨에만 적용가능.

- 클래스 레벨에 롤백에 대한 공통 속성을 지정할 수 있음.

```java
/**
* 클래스 레벨의 공통 롤백 속성은 false로 두면서 일부 테스트에만 롤백을 적용하고 싶은 경우 아래와 같이 사용가능
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class UserServiceTest {
  @Test
  @Rollback
  public void add() throws SQLException {...}

  @Test
  public void subtract() throws SQLException {...}
}
```

#### Propagation.NEVER
- @Transactional(propagation=Propagation.NEVER)
  - 메소드의 트랜잭션을 시작하지 않음.


#### DB Test(DB 테스트)
- d일반적으로 의존, 협력 오브젝트를 사용하지 않고 고립된 상태에서 테스트를 진행하는 단위 테스트와, DB 같은 외부의 리소스나 여러 계층의 클래스가 참여하는 통합 테스트는 아예 클래스를 구분해서 따로 만드는게 좋음.
- DB 테스트는 가능한 롤백테스트로 만드는게 좋음.
- 롤백 테스트로 되어 있다면 테스트 사이에 서로 영향을 주지 않으므로 독립적이고 자동화된 테스트로 만들기가 매우 편함 
- 테스트는 어떤 경우에도 서로 의존하면 안됨.(현재 spl suitetest의 문제점?)
- 테스트가 진행되는 순서나 앞의 테스트의 성공여부에 따라서 다음 테스트의 결과가 달라지는 테스트를 만들 면 안됨.
- 코드가 바뀌지 않는 한 어떤 순서로 진행되더라도 테스트는 일정한 결과를 내야함.


# Chap 07: 스프링 핵심 기술의 응용
## 7.1. SQL과 DAO의 분리
* 라이브러리 추가 필요
  - org.apache.commons.lang3.jar

## 7.2 인터페이스의 분리와 자기참조 빈
### 7.2.1 XML 파일 매핑
* unmarshalling: XML 문서를 읽어서 자바의 오브젝트로 변환하는 것을 JAXB에서는 unmarshalling이라 부름
* marshalling: 바인딩오브젝트를 XML 문서로 변환하는 것.
(Java object를 byte stream으로 바꾸는 걸 Serialization이라고 부르는 것과 비슷함.)


### 7.2.3. 빈의 초기화 작업 
#### 스프링 컨테이너인 애플리케이션 컨텍스트가 xml 설정파일을 읽고 진행하는 작업의 순서
1. XML 빈 설정을 읽는다. (applicationContext.xml)
2. 빈의 오브젝트를 생성한다. (`<bean id=".." class="ClassName">`)
3. 프로퍼티에 의존 오브젝트 또는 값을 주입한다. (`<property name=".." value="xyz"/> <property name=".." ref="beanId"/>`)
4. 빈이나 태그로 등록된 후처리기를 동작시킴. [코드에 달린 애노테이션에 대한 부가작업 진행] (`@PostContruct public void init() {...}`)

### 7.2.6. 디폴트 의존관계
* 디폴트 의존관계: 외부에서 DI 받지 않는 경우 기본적으로 자동 적용되는 의존관계를 말함.


## 7.3. 서비스 추상화 적용
### 7.3.1. OXM 서비스 추상화
- OXM(Object-XML Mapping): XML과 Java object를 매핑해서 상호 변환해주는 기술

* 라이브러리 추가 필요
  - org.springframework.oxm-3.0.7.RELEASE.jar
  - com.springsource.org.castor-1.2.0.jar



## 7.5. DI를 이용해 다양한 구현 방법 적용하기
* 라이브러리 추가 필요
  - com.springsource.org.hsqldb-1.8.0.9.jar

## 7.6. 스프링 3.1의 DI

### Annotation
- 애노테이션은 옵션에 따라 컴파일된 클래스에 존재하거나 애플리케이션이 동작할 때 메모리에 로딩되기도 하지만 자바 코드가 실행되는 데 직접 참여하지 못함.
- 오브젝트에 타입을 부여하지도, 그자체로 상속이나 오버라이딩이 가능하지도 않음. 동작하는 코드를 넣을 수도 없고, 코드에서 간단히 참조하거나 활용할 수 없음.
- 단지 복잡한 리플렉션 API를 이용해 애노테이션의 메타정보를 조회하고, 애노테이션 내에 설정된 값을 가져와 참고하는 방법이 전부임.

* annotation은 프레임워크가 참조하는 메타정보로 사용되기에 여러 가지 유리한 점이 많음.

- annotation은 정의하기에 따라서 타입, 필드, 메소드, 파라미터, 생성자, 로컬 변수의 한군데 이상 적용 가능.
- annotation이 부여된 클래스의 패키지, 클래스 이름, 접근 제한자, 상속한 클래스나 구현 인터페이스가 무엇인지 알 수 있음.
```java
package com.mycompany.myproject;

@Special
public class MyClass {
  ...
}
```

- 동일한 정보를 XML로 표현하려면 모든 내용을 명시적으로 나타내야함.
```xml
<x:special target="type" class="com.mycompany.myproject.MyClass"/>
```

* 메타정보 활용 방식
  1. XML
    - 장점: 어느 환경에서나 손쉽게 편집이 가능하고, 내용을 변경하더라도 다시 빌드를 거칠 필요가 없음.
    - 단점: 리팩토링 시 패키지나 클래스 정보등이 단순 텍스트로 작성되어 있어서 번거롭고 안전하지 못함.
  2. Annotation  
    - 장점: 리팩토링 시 IDE를 활용하면 이름이나 위치를 바꾸는 것이 간단함.
    - 단점: 자바 코드에 존재하므로 변경할 때마다 매번 클래스를 새로 컴파일해줘야 함.

- 스프링은 점차 애노테이션으로 메타정보를 작성하고, 미리 정해진 정책과 관례를 활용해서 간결한 코드에 많은 내용을 담을 수 있는 방식을 적극 도입.
  - 스프링 3.1은 그런 면에서 완성도가 높음.
  - XML을 전혀 사용하지 않고도 스프링 애플리케이션을 만들 수 있다는 점이 스프링이 공개된 이후로 가장 큰 변화.



## 7.6.1. 자바 코드를 이용한 빈 설정

#### <context:annotation-config />
- @PostConstruct를 붙인 메소드가빈이 초기화 된 후에 자동으로 실행되도록 사용했음.
- annotation-config에 의해 등록되는 빈후처리기가 @PostConstruct같은 표준 애노테이션을 인식해서 자동으로 메소드를 실행해줌.
- XML에 담긴 DI 정보를 이용하는 스프링 컨테이너를 사용하는 경우에는 
  @PostConstruct와 같은 애노테이션의 기능이 필요하면 반드시 `<context:annotation-config/>`를 포함시켜서 필요한 빈 후처리기가 등록되게 만들어야 함.
- @Configuration이 붙은 설정 클래스를 사용하는 컨테이너가 사용되면 더이상 `<context:annotation-config />`를 넣을 필요가 없음.
  - 컨테이너가 직접 @PostConstruct 애노테이션을 처리하는 빈후처리기를 등록해주기 때문.
  

#### <bean>
- `<bean>`으로 정의된 DI 정보는 자바코드, 특별히 `@Bean`이 붙은 메소드와 거의 1:1로 매핑됨.
  - `@Bean`은 @Configuration이 붙은 DI 설정용 클래스에서 주로 사용되는 것으로, 
    메소드를 이용해서 빈 오브젝트의 생성과 의존관계 주입을 직접 자바 코드로 작성할 수 있게 해줌
- `<bean>`은 `@Bean`이 붙은 public 메소드로 만들어주면 됨.
- method name은 `<bean>`의 id값으로 함.
- method's return type은 신중히 결정해야 함.
  - class attribute의 클래스를 그대로 사용해도 상관없지만 정확히 하려면 빈을 주입받아서 사용하는 다른 빈이 어떤 타입으로 이 빈의 존재를 알고 있는지 확인할 필요가 있음.

- @Configuration 자바 클래스에서 정의한 빈과 XML에서 정의한 빈은 얼마든지 서로 참조가 가능.

- @Bean 메소드로 정의된 빈을 프로퍼티에 주입해주는 방법: 주입해줄 빈의 메소드를 직접 호출해서 그 리턴 값을 수정자 메소드에 넣어주면 됨.

##### @Autowired
@Autowired가 붙은 필드의 타입과 같은 빈이 있으면 해당 빈을 필드에 자동으로 넣어줌.
- bean 클래스 내부에서도 수정자 주입을 대신해서 필드 주입으로 사용할 수도 있고
- @Configuration 클래스에서 XML 등으로 정의된 빈을 가져올 때도 사용가능 
- @Configuration 클래스를 하나 이상 상요하는 경우 다른 클래스의 @Bean 메소드로 정의된 빈을 참조할 때도 사용할 수 있음.

##### @Resource
- @Autowired와 유사하게 필드에 빈을 주입받을 때 사용.
  - 차이점
    * @Autowired: 필드의 타입을 기준으로 빈을 찾음.
    * @Resource: 필드 이름을 기준으로 찾음.
    

##### 전용 태그
* `<tx:annotation-driven />`은 옵션을 주지 않는다면 기본적으로 다음 네 가지 클래스를 빈으로 등록해줌.
  1. org.springframework.aop.framework.InfrastructureAdvisorAutoProxyCreator
  2. org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
  3. org.springframework.transaction.interceptor.TransactionInterceptor
  4. org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor
- 이 4개의 빈을 등록하고 적절히 프로퍼티 값을 넣어주면 `<tx:annotation-driven />`을 대체할 수 있음.
- 하지만 너무 귀찮음.
* 스프링 3.1에서는 XML에서 자주 사용되는 전용 태그를 `@Enable`로 시작하는 애노테이션으로 대체할 수 있게 다양한 애노테이션을 제공함.
  - 대표적으로 `@EnableTransactionManagement`
  
  
## 7.6.2. 빈 스캐닝과 자동와이어링
### @Autowired를 이용한 자동와이어링
> 기존에 빈의 프로퍼티에 다른 빈을 넣어서 런타임 관계를 맺어주려면 `<bean>`의 `<property>`를 사용해 빈을 정의하거나, 자바 코드로 직접 수정자 메소드를 호출해줘야 했음.

* @Autowired는 자동와이어링 기법을 이용해서 조건에 맞는 빈을 찾아 자동으로 수정자 메소드나 필드에 넣어줌.

- 스프링은 @Autowired가 붙은 수정자 메소드가 있으면 파라미터 타입을 보고 주입 가능한 타입의 빈을 모두 찾음.
  - 주입 가능한 타입의 빈이 하나라면 스프링이 수정자 메소드를 호출해서 넣어줌.
  - 주입 가능한 타입이 두 개 이상이 나온다면 그중에서 프로퍼티와 동일한 이름의 빈이 있는지 찾음.
  - 타입과 이름을 모두 비교해도 최종 후보를 찾아내지 못하면 주입할 빈을 찾을 수 없다는 에러가 날 것임.
  
- @Autowired는 타입을 기준으로 하나의 빈을 찾게 되어 있음.
- @Autowired는 일단 타입을 기준으로 적용할 빈을 찾아보고, 같은 타입의 빈이 두 개 이상 발견되면 이름을 기준으로 다시 최종 후보를 찾는 방식으로 독작

### @Component를 이용한 자동 빈 등록
- 클래스에 부여됨.
- @Component가 붙은 클래스는 빈 스캐너를 통해 자동으로 빈으로 등록됨.
  - 정확히는 @Component 또는 @Component를 메타 애노테이션(??)으로 갖고 있는 애노테이션이 붙은 클래스가 자동 빈 등록 대상이 됨.

* 자동등록이든 XML을 통한 등록이든, 아무튼 스프링 컨테이너에등록된 빈을 가져와 사용할 때는 @Autowired로 가져오면 됨.
  - 같은클래스 안에 @Bean 메서드가 존재하는 경우에도 메소드 호출대신 @Autowired를 통해 빈을 참조할 수 있음.  
  

* @Component: 빈으로 등록될 후보 클래스에 붙여주는 일종의 marker라 보면 됨.

* @ComponentScan
  - @Component 가 달린 클래스를자동으로 찾아서 빈을 등록해주게 하려면 빈 스캔 기능을 사용하겠다는 애노테이션 정의가 필요함.(빈 자동등록이 컨테이너가 디폴트로 제공하는 기능은 아니기 때문.)
  - 프로젝트 내의 모든 클래스패스를 다 뒤져서 @Component 가 달린 클래스를 찾는 것은 부담이 많이 가는 작업.
  - 그래서 특정 패키지 아래서만 찾도록 기준이 되는 패키지를 지정해줄 필요가 있음.
  - 이때 사용되는 애노테이션이 @ComponentScan
  - @Component가 붙은 클래스가 발견되면 새로운 빈을 자동으로 추가함.
    - 빈의 클래스는 @Component가 붙은 클래스이고, 빈의 아이디는 따로 지정하지 않았으면 클래스 이름의 첫 글자를 소문자로 바꿔서 사용함.


#### 메타 애노테이션(애노테이션의 정의에 부여된 애노테이션)
- 여러 개의 애노테이션에 공통적인 속성을 부여하려면 메타 애노테이션을 이용.
- 애노테이션이 빈 스캔을 통해 자동등록 대상으로 인식되게 하려면 애노테이션 정의에 @Component를 메타 애노테이션으로 붙여주면 됨.

* **@Repository**: 스프링이 DAO 빈을 자동등록 대상으로 만들 때 사용할 수 있게해주는 애노테이션
  - DAO 기능을 제공하는 클래스에는 @Repository를 사용하도록 스프링은 권장.
  - @Repository는 @Component를 메타 애노테이션으로 갖고 있음.


* **@Service**: 스프링이 제공하는 빈 자동등록용 애노테이션. 
  - 비즈니스 로직을 담고 있는 서비스 계층의 빈을 구분하기 위해 사용.(서비스 계층은 트랜잭션 경계가 되는 곳이라 @Transactional이 함께 사용되는 경우가 많음.)

### @Import
- 자바 클래스로 된 설정정보를 가져올 때 사용


### @Profile
* 실행환경에 따라 빈 구성이 달라지는 내용을 프로파일로 정의해서 만들어두고, 실행 시점에 어떤 프로파일의 빈 설정을 사용할 지 지정.
- 프로파일은 설정 클래스 단위로 지정.
- 프로파일이 지정되어 있지 않은 빈 설정은 default 프로파일로 취급.


### @ActiveProfiles
- 활성 프로파일이란 스프링 컨테이너를 실행할 때 추가로 지정해주는 속성. 


### DefaultListableBeanFactory
- 스프링 컨테이너는 모두 BeanFactory라는 인터페이스를 구현하고 있음.  
- 대부분의 스프링 컨테이너는 DefaultListableBeanFactory(BeanFactory 구현 클래스)를 이용해 빈을 등록하고 관리함.

## 7.6.5. 프로퍼티 소스
스프링 3.1은 빈 설정 작업에 필요한 프로퍼티 정보를 컨테이너가 관리하고 제공해줌.  
스프링 컨테이너가 지정된 정보 소스로부터 프로퍼티 값을 수집하고, 이를 빈 설정 작업 중에 사용할 수 있게 해줌.   

### @PropertySource: 
- 프로퍼티 파일이나 리소스의 위치를 지정해서 사용되는 프로퍼티 소스의 등록에 사용하는 애노테이션
- @PropertySource로 등록한 리소스로부터 가져오는 프로퍼티 값은 컨테이너가 관리하는 Environment 타입의 환경 오브젝트에 저장됨.(Environment 오브젝트의 getProperty() 메소드를 통해 프로퍼티 값을 가져올 수 있음.)


### PropertySourcesPlaceholderConfigurer
- Environment 오브젝트 대신 프로퍼티 값을 직접 DI 받는 방법.
#### @Value
- 값을 주입받을 때 사용.
- @Value의 사용방법은 여러 방법이 있지만 여기서는 프로퍼티 소스로부터 값을 주입받을 수 있게 치환자(placeholder)를 이용.
  - 프로퍼티 값을 주입받을 필드를 선언하고 앞에  @Value 애노테이션을 붙여줌.
  - @Value에 프로퍼티 네임을 `${}`안에 넣은 문자열을 디폴트 엘리먼트 값으로 지정해줌.
- @Value에 넣은 ${db.driverClass}같은 것을 치환자라고 부르는 이유는 XML에서 property 태그의 value에 사용하는 값 치환 방식과 유사하기 때문 
```xml
<property name="driverClass" value="${db.driverClass}" />
```
- XML에서는 치환자 자리의 값을 바꿔주는데, @Value에서는 @Value가 붙은 필드의 값을 주입해주는 방식으로 동작함.

* @Value와 치환자를 이용해 프로퍼티 값을 필드에 주입하려면 특별한 빈을 하나 선언해야함.
  - 프로퍼티 소스로부터 가져온 값을 @Value 필드에 주입하는 기능을 제공해주는 **PropertySourcesPlaceholderConfigurer**를 빈으로 정의해야 함.

- @Value를 이용하면 driverClass처럼 문자열을 그대로 사용하지 않고 타입 변환이 필요한 프로퍼티를 스프링이 알아서 처리해준다는 장점이 있음.


## 7.6.6. 빈 설정의 재사용과 @Enable*
- @Configuration 애노테이션이 달린, 빈 설정으로 사용되는 AppContext 같은 클래스도 스프링에선 하나의 빈으로 취급됨.
  - @Configuration은 @Component를 메타 애노테이션으로 갖고 있는 자동 빈 등록용 애노테이션이기도 함.
  - 원한다면 @Configuration 클래스도 빈 스캐너를 통해 자동등록되게 만들 수 있음.


### @Enable* 애노테이션


* @Component는 빈 자동등록 대상을 지정할 때 사용하는 애노테이션인데, 많은 경우 @Component를 직접 사용하기보단 @Repository나 @Service처럼 좀 더 의미있는 이름의애노테이션을 만들어 사용함.

- @Component를 메타 애노테이션으로 넣어서 애노테이션을 정의해주면 @Component와 동일한 빈 등록기능이 적용되면서 자동등록되는 빈의 종류나 계층이 무엇인지 나타낼 수 있고,
- AOP를 이용해 특정 애노테이션이 달린 빈만 선정해 부가 기능을 제공하게 만들 수도 있음.


# Chap 09: 스프링 프로젝트 시작하기

### 9.2.4. 라이브러리 관리와 빌드 툴

#### 빌드 툴과 라이브러리 관리

##### Maven
- Maven은 단순 빌드 툴을 넘어서 개발 과정에서 필요한 빌드, 테스트, 배치, 문서화, 리포팅 등의 다양한 작업을 지원하는 종합 프로젝트 관리 툴의 성격을 띠고 있음.
- Maven의 특징은 `POM`이라는 프로젝트 모델 정보를 이용한다는 점.
  - 절차적인 스크립트와 구조가 비슷한 ANT와 달리 선언적임.
* 프로젝트의 주요한 구조와 특징, 필요한 정보를 POM의 프로젝트 모델 정보로 만들어두면, 이를 참조해서 Maven에 미리 정해진 절차에 따라 빌드 또는 프로젝트 관리 작업을 진행할 수 있음.
* Maven POM이 가진 독특한 특징 중의 하나는 애플리케이션이 필요로 하는 의존 라이브러리를 선언해두기만 하면 원격 서버에서 이를 자동으로 다운로드 받아서 사용할 수 있게 해주는 것.
  - Maven으로 빌드할 때 필요한 라이브러리가 개발 PC의 공통 저장소에 있는지 확인하고 없으면 Maven의 원격 서버에서 자동으로 다운로드 받아서 설치해줌.
* 또한 Maven의 의존 라이브러리 관리 기능은 `전이적인 의존 라이브러리 추적 기능`을 제공함. 
  - POM의 의존정보에 하나의 라이브러리를 지정하면, 지정된 라이브러리가 동작하는데 필요한 여타 라이브러리까지 함께 다운로드해줌
    - 이런 과정은 모든 라이브러리에 재귀적으로 적용됨. 
    
- 스프링의 모든 모듈은 POM 정보를 갖고 있음.
- 스프링의 각 모듈이 필요로 하는 라이브러리중에 필수는 몇개 되지 않음.
  - 선택 라이브러리는 Maven의 전이적 의존 라이브러리 추적 기능의 적용을 받지 못함.(참고는 할 수 있으되 사용하려면 명시적으로 POM에 선언해줘야 한다는 뜻.)


#### 스프링 모듈의 두 가지 이름과 리포지토리
스프링 모듈의 jar파일의 이름을 살펴보면 두 가지 종류가 있음.
> spring-core-3.0.7.RELEASE.jar
> org.springframework.core-3.0.7.RELEASE.jar
위의 두 파일은 동일한 파일임. 단지 배포되는 기술에 따라서 관례적으로 다른 이름을 사용할 뿐임.   

- **spring-core-3.0.7.RELEASE.jar**
  - Maven에서 사용하는 명명 규칙을 따른 것.
  - Maven은 `groupId, artifactId, version` 이 세 가지로 라이브러리를 정의하는데 
  - 그중에서 artifactId와 version을 조합해서 파일 이름으로 사용함.
  - 예시
```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-core</artifactId>
  <version>3.0.7.RELEASE</version>
</dependency>
```

- **org.springframework.core-3.0.7.RELEASE.jar**
  - OSGi의 모듈 명명 규칙을 따른 것
  - 스프링의 모든 모듈은 OSGi 호환 모듈로 만들어져 있음.
  - 그리고 OSGi 플랫폼에서 사용되지 않는다고 할지라도 OSGi 스타일의 모듈 이름을 사용하도록 권장.
  - OSGi 호환 이름을 갖는 스프링 모듈을 사용할 경우에는 Maven의 표준 리포지토리 대신
    스프링소스가 제공하는 엔터프라이즈 번들 리포지토리를 사용해야 함.
```xml
<repository>
  <id>com.springsource.bundles.release</id>
  <name>SpringSource Enterprise Bundle Repository - SpringSource Bundle Releases</name>
  <url>http://repository.springsource.com/maven/bundles/release</url>
</repository>

<repository>
  <id>com.springsource.bundles.external</id>
  <name>SpringSource Enterprise Bundle Repository - External Bundle Releases</name>
  <url>http://repository.springsource.com/maven/bundles/external</url>
</repository>
```
- 리포지토리를 지정했다면 스프링의 표준 모듈 이름을 따라서 다음과 같이 의존 라이브러리를 선언할 수 있음.

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>org.springframework.core</artifactId>
  <version>3.0.7.RELEASE</version>
</dependency>
```