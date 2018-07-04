# Chap 02: 데이터 액세스 기술 

- Declarative Transaction의 `Transaction Propagation`기능 덕분에 여러 메소드를 조합하여 하나의 트랜잭션에서 동작하게 만들 수 있는 것임.
> Declarative Transaction은 EJB의 가장 매력적인 기능이었으나, 스프링에서 POJO방식의 코드로 이 Declarative Transaction을 구현함.

### 2.6.3 트랜잭션 속성
##### Transaction Propagation(트랜잭션 전파): propagation
- `REQUIRED`: (디폴트) 미리 시작된 트랜잭션이 **있으면 참여, 없으면 새로 시작.**
- `SUPPORTS`: 이미 시작된 트랜잭션이 **있으면 참여, 없으면 트랜잭션 없이 진행**
- `MANDATORY`: 이미 시작된 트랜잭션이 **있으면 참여, 트랜잭션이 없으면 Exception 발생.** 
   - 독립적으로 트랜잭션을 진행하면 안되는 경우에 사용.
- `REQUIRES_NEW`: **Always 새로운 독립적인 트랜잭션 시작.**, 이미 트랜잭션이 있으면 그 트랜잭션을 보류시켜놓음.
- `NOT_SUPPORTED`: 트랜잭션을 **사용안함**. 이미 트랜잭션이 있으면 그 트랜잭션을 보류시켜놓음.
- `NEVER`: 트랜잭션을 사용하지 **않도록 강제.** 이미 진행 중인 트랜잭션이 존재하면 안됨.
- `NESTED`: 이미 진행중인 트랜잭션이 있으면 `중첩 트랜잭션(트랜잭션 안의 트랜잭션)`을 시작함.
   - `REQUIRES_NEW`와는 다름.
   - 중첩 트랜잭션은 자신의 부모의 커밋/롤백엔 영향을 받으나 중첩 트랜잭션 자신의 커밋/롤백은 부모 트랜잭션에 영향을 주지 못함.


# Chap 03: 스프링 웹 기술과 스프링 MVC
* 웹 프레젠테이션 계층: 엔터프라이즈 애플리케이션의 가장 앞단에서 사용자 또는 클라이언트 시스템과 연동하는 책임을 맡고 있음.

## 3.1. 스프링의 웹 프레젠테이션 계층 기술
* 스프링은 의도적으로 서블릿 웹 애플리케이션의 컨텍스트를 두가지로 분리하였음.(스프링 웹 서블릿 컷텍스트를 통째로 다른 기술로 대체할 수 있도록 하기 위함.)
    1. 루트 애플리케이션 컨텍스트: 웹 기술에서 완전 독립적인 비즈니스 계층과 데이터 액세스 계층을 담음.
    2. 서블릿 애플리케이션 컨텍스트: 스프링 웹 기술을 기반으로 동작하는 웹 관련 빈을 담음.

### 3.1.1. 스프링에서 사용되는 웹 프레임워크의 종류
* 스프링 웹 프레임워크
    * 스프링 서블릿/스프링 MVC
        -  스프링이 직접 제하는 서블릿 기반의 MVC 프레임워크
        -  front controller 역할을 하는 `DispatcherServlet`을 핵심 엔진으로 사용.
        -  스프링 서블릿은 다양한 종류의 컨트롤러를 동시에 사용할 수 있게 설계되어있음.
        -  스프링 서블릿의 모든 컴포넌트는 스프링의 서블릿 애플리케이션 컨텍스트의 빈으로 등록되어 동작.(따라서 간단히 루트 컨텍스트에 존재하는 서비스 계층의 빈을 사용할 수 있음.)
    * 스프링 포틀릿
* 스프링 포트폴리오 웹 프레임워크(스프링 서블릿을 기반으로 하는 고급 웹 프레임워크)
    * Spring Web Flow
    * Spring JavaScript
    * Spring Faces
    * Spring Web Service
    * Spring BlazeDS Integration
* 스프링을 기반으로 두지 않는 웹 프레임워크 
    * JSP/Servlet
        - 이 JSP나 Servlet을 스프링 애플리케이션의 웹 프레젠테이션 계층으로 사용할 수 있음.
    * Struts1
    * Struts2
    * Tapestry 3, 4
    * JSF/Seam

가장 스프링 답게 스프링의 개발철학과 원칙을 지켜서 효과적으로 개발할 수 있는 방법은 스프링이 직접 제공하는 웹 프레임워크를 사용하는 것.

### 3.1.2. 스프링 MVC와 DispatcherServlet 전략
* 스프링 웹 기술의 핵심이자 기반이 되는 것은 `DispatcherServlet`
    - 스프링의 웹 기술을 구성하는 다양한 전략을 DI로 구성해서 확장하도록 만들어진 스프링 서블릿/MVC의 엔진과 같은 역할을 함.

#### DispatcherServlet과 MVC 아키텍쳐
* 스프링 웹 기술은 MVC 아키텍쳐를 근간에 둠. 
    - M(Model): 정보
    - V(View): 화면 출력 로직
    - C(Controller): 제어 로직
    - 이 3가지 요소가 서로 협력해 하나의 웹 요청을 처리하고 응답을 만들어내는 구조.
* MVC 아키텍쳐는 Front Controller 패턴과 함꼐 사용됨.
    - 프론트 컨트롤러가 프레젠테이션 계층의 가장 앞에서 서버로 들어오는 모든 요청을 받아 처리하게 함.
    - 프론트 컨트롤러는 클라이언트가 보낸 요청을 받아서 공통 작업을 먼저 수행한 후, 적절한 세부 컨트롤러로 작업을 위임해주고 클라이언트에게 보낼 뷰를 선택해서 최종 결과를 생성하는 등의 작업을 수행함.
    - 예외 처리를 일관되게 하는 것도 프론트 컨트롤러의 역할
* 스프링 MVC의 핵심은 DispatcherServlet이라는 Front Controller
![dispatcherservlet](./img/3-1_dispatcherservlet.png)

##### HTTP 요청이 들어왔을 때 과정
1. DispatcherServlet의 HTTP 요청 접수 
    - 자바 서버의 서블릿 컨테이너는 HTTP를 통해 들어오는 요청이 스프링 DispatcherServlet에 할당된 것이라면 HTTP 요청정보를 DispatcherServlet에 전달.
    - `web.xml`에 DispatcherServlet이 전달받을 URL이 정의되어있음.
    - DispatcherServlet은 모든 요청에 대해 공통적으로 진행해야 하는 전처리 작업이 등록된 것이 있으면 이를 먼저 수행(보안, 파라미터 조작, 한글 디코딩)
2. DispatcherServlet에서 Controller로 HTTP 요청 위임
    - DispatcherServlet은 URL이나 파라미터 정보, HTTP 명령등을 참고해서 어떤 컨트롤러에게 작업을 위임할 지 결정.
        - 컨트롤러를 선정하는 것은 DispatcherServlet의 `핸들러 매핑 전략(사용자 요청을 어떤 핸들러에게 작업을 위임할지를 결졍해주는 것)`을 이용.(컨트롤러를 핸들러라고 하기도 함.)
    - 핸들러 매핑 전략은 DispatcherServlet의 수정없이도 DI를 통해 확장가능.
        - DispatcherServlet은 스프링 컨텍스트에 등록되는 빈이 아니므로 DI가 일어나는 것은 아님.
        - DI가 적용되는 것처럼 서블릿 애플리케이션 컨텍스트의 빈을 가져와 사용함.(Auto-wiring 기법 사용)
    - DispatcherServlet은 어떤 종류의 오브젝트라도 컨트롤러로 사용 가능(`어댑터 패턴`를 사용함으로써 가능.)
        - 특정 컨트롤러를 호출해야 할 때 해당 컨트롤러 타입을 지원하는 `어댑터`를 중간에 껴서 호출함.
    - 각 어댑터는 자신이 담당하는 컨트롤러에 맞는 호출 방법을 이용해서 컨트롤러에 작업 요청을 보내고 결과를 돌려받아서 DispatcherServlet에 다시 돌려주는 기능을 가짐.
    - **스프링 서블릿/MVC 확장구조의 기본은 바로 어댑터를 통한 컨트롤러 호출 방식임.**
        - 어떤 어댑터를 사용할지는 DispatcherServlet 전략의 하나인 `핸들러 어댑터 전략`을 통해 결정.
    - DispatcherServlet이 핸들러 어댑터에 웹 요청을 전달할 때는 `모든 웹 요청 정보가 담긴 HttpServletRequest` 타입 오브젝트를 전달해줌. 이를 어댑터가 적절히 변환해 컨트롤러 메소드가 받을 수 있는 파라미터로 변환해서 전달해주는 것임.(HttpServletResponse도 함께 전달해줌. 메소드가 리턴값을 돌려주는 대신 이 오브젝트안에 값을 직접 집어넣을 수도 있음.)
3. Controller의 모델 생성과 정보 등록
    - 컨트롤러의 작업 
        1. 사용자 요청을 해석 
        2. 그에 따라 실제 비즈니스 로직을 수행하도록 서비스 계층 오브젝트에게 작업을 위임 
        3. 결과를 받아서 모델(이름과 오브젝트 값의 쌍)을 생성 
        4. 어떤 뷰를 사용할지 결졍하는 것.
    - 컨트롤러는 어떤식으로든 DispatcherServlet에게 `모델과 뷰`를 돌려줘야함
4. Controller의 결과 리턴: 모델과 뷰
    - (컨트롤러가 뷰 으보젝트를 직접 리턴할 수도 있긴 하지만) 보통 뷰의 논리적인 이름을 리턴해주면 DispatcherServlet의 전략인 `뷰 리졸버`가 이를 이용해 뷰 오브젝트를 생성해줌.(뷰도 하나의 오브젝트임.)
        - 대표적으로 사용되는 뷰는 JSP/JSTL뷰. JSP 파일로 만들어진 뷰 템플릿과 JstlView 클래스로 만들어진 뷰 오브젝트 결합해서 최종적으로 사용자가 보게 될 HTML을 생성함.
    - ModelAndView라는 오브젝트가 DispatcherServlet이 최종적으로 어댑터를 통해 컨트롤러로부터 돌려받는 오브젝트.
5. DispatcherServlet의 뷰 호출과 모델 참조
    - DispatcherServlet은 컨트롤러로부터 모델과 뷰를 받은 후, 뷰 오브젝트에게 모델을 전달해주고 클라이언트에게 돌려줄 최종 결과물을 생성해달라고 요청함.
        - JstlView는 컨트롤러가 돌려준 JSP 뷰 템플릿의 이름을 가져다 HTML을 생성 
    - 뷰 작업을 통한 최종 결과물은 HttpServletResponse 오브젝트 안에 담김.
6. HTTP 응답 돌려주기
    - 뷰까지 생성한 다음에 DispatcherServlet은 등록된 후처리기가 있는지 확인하고, 있다면 후속 작업을 진행한 후에, 뷰가 만들어준 HttpServletResponse에 담긴 최종 결과를 서블릿 컨테이너에 돌려줌.
    - 서블릿 컨테이너는 HttpServletResponse에 담긴 정보를 HTTP 응답으로 만들어 사용자의 브라우저나 클라이언트에게 전송하고 작업을 종료한다.


##### DispatcherServlet의 DI 가능한 전략
스프링 MVC는 자주 사용되는 전략을 디폴트로 설정해주고 있긴함.

* HandlerMapping: URL과 요청 정보를 기준으로 어떤 핸들러 오브젝트, 즉 컨트롤러를 사용할 것인지를 결정하는 로직을 담당.
* HandlerAdapter: 핸들러 매핑으로 선택한 컨트롤러/핸들러를 DispatcherServlet이 호출할 때 사용하는 어댑터
    - 핸들러 매핑과 어댑터는 서로 관련이 있을수도 없을수도 있다.
    - @ReqeusetMapping과 @Controller에 의해서 정의되는 컨트롤러의 경우는 DefaultAnnotationHandlerMapping에 의해 핸들러가 결저오디고, 그에 대응되는 AnnotationMethodHandlerAdapter에 의해 호출이 일어남.
* HandlerExceptionResolver: 예외가 발생했을 때 이를 처리하는 로직을 갖음.
    - DispatcherServlet은 등록된 HandlerExceptionResolver중에서 발생한 예외에 적합한 것을 찾아서 예외처리를 위임함.
* ViewResolver: 컨트롤러가 리턴한 뷰 네임을 참고해서 적절한 뷰 오브젝트를 찾아주는 로직을 가진 전략 오브젝트.
* LocaleResolver: 지역정보를 결졍해주는 전략.
    - AcceptHeaderLocaleResolver는 HTTP 헤더의 정보를 보고 지역정보를 설정.
    - 이 전략을 바꾸면 지역정보를 HTTP 헤더 대신 세션이나 URL 파라미터, 쿠키, XML 설정에 직접 지정한 값등 다양한 방식을 결정할 수 있음.
* ThemeResolver: 테마?정보를 결정해주는 전략
* RequestToViewNameTranslator: 컨트롤러에서 뷰 이름이나 뷰 오브젝트를 제공해주지 않았을 경우 URL과 같은 요청정보를 참고해서 자동으로 뷰 이름을 생성해주는 전략.


> DispatcherServlet은 각 전략의 디폴트 설정을 DispatcherServlet.properties라는 전략 설정파일로부터 가져와서 초기화함. 
> DispatcherServlet은 서블릿 컨테이너가 생성하고 관리하는 으보젝트임. 
> 스프링의 컨텍스트에서 관리하는 빈 오브젝트가 아님.
> 따라서 DispatcherServlet은 내부에 서블릿 웹 애플리케이션 컨텍스트를 갖고있고, 내부 컨텍스트로부터 개발자가 추가하거나 설정을 수정한 전략이 담긴 빈 오브젝트가 있는지 찾아보고, 있다면 이를 가져와서 디폴트 전략을 대신해서 사용함.

## 3.2. 스프링 웹 애플리케이션 환경 구성
- 웹 프레젠테이션 계층은 최소한 서블릿 or 포틀릿 컨테이너가 제공되는 서버환경이 있어야 동작함.

### 3.2.1. 간단한 스프링 웹 프로젝트 생성

#### 루트 웹 애플리케이션 컨텍스트 
서비스 계층과 데이터 액세스 계층을 포함해서 웹 환경과 직접 관련이 없는 모든 빈은 여기에 등록 
- intellij프로젝트의 webapp을 web root 라고 부름.

* 디버깅 
    * 서버 배치중 에러: web.xml 설정에 문제가 있을 수 있음.
    * jsp 실행시 예외 메시지: 다양한 문제가 있을 수도(주로 발생하는 문제는 적절한 스프링 모듈과 라이브러리 파일이 추가되지 않는 경우.)
    * 빈 생성/초기화 작업 시 예외: applicationContext.xml파일에 오류.
    * 그외: 복잡..

#### 서블릿 웹 애플리케이션 컨텍스트 등록
- web.xml에 DispatcherServlet 서블릿 추가
- DispatcherServlet 서블릿 등록시 담당할 HTTP 요청의 URL 패턴을 지정해야 함.
    - 구분 방법 3가지 
        1. 확장자로 구분(e.g. *.do / *.action)
        2. 특정 URL 폴더로 구분 (e.g. /app/*)
        3. 모든 요청 받기(/*)
- DispatcherServlet은 초기화시 서블릿 레벨의 웹 애플리케이션 컨텍스트를 생성해줌. 
    - 디폴트 이름은 `?-servlet.xml`
    - 디폴트로 WEBROOT/WEB-INF 하위에 생성해주자.

#### 스프링 웹 프로젝트 검증 
스프링 MVC를 사용하려면 먼저 DispatcherServlet의 전략을 사용해야함.  
여기선 디폴트 전략들을 사용해보겠음.

#### Handler Adapter
* 컨트롤러는 핸들러 어댑터가 지원하는 타입으로 만들어야함.

* SimpleControllerHandlerAdapter
    - 해당 어댑터가 컨트롤러를 지원하려면 Controller interface를 상속받아야함.

- 컨트롤러는 서블릿 애플리케이션 컨텍스트 안에 빈이 등록되기에 DI 이용가능.
    - 자식 컨텍스트(서블릿 애플리케이션 컨텍스트)는 부모 컨텍스트(루트 컨텍스트)의 빈을 참조할 수 있음.

#### Handler Mapping
URL과 이를 담당하는 핸들러를 매핑하는 일을 담당함.

* BeamNameUrlHandlerMapping
    - 컨트롤러를 빈으로 등록할 때 빈의 이름에 매핑할 URL을 넣어주는 것.

-     컨트롤러가 리턴한 뷰 이름을 참고해서 InternalResourceView를 돌려줌.

#### View Resolver
DispatcherServlet은 뷰 리졸버를 이용해서 컨트롤러가 리턴한 뷰 이름에 해당하는 뷰 오브젝트를 가저옴. 가져온 뷰 오브젝트를 이용해서 최종 HTTP Response를 만들어냄.  

* InternalResourceViewResolver
    - 컨트롤러가 리턴한 뷰 이름을 참고해서 JSP와 서블릿을 템플릿으로 사용하는 InternalResourceView를 돌려줌.

- JSP EL은 컨트롤러에서 생성되어 DispatcherServlet을 거쳐 JSP 뷰로 전달된 모델로부터 특정 이름의 오브젝트를 가져와 표현식 대신 넣어줌.

- JSP파일을 url을 통해 직접 실행시키지 못하게 하기 위해 직접 접근이 불가능한 `WEB_ROOT/WEB-INF/` 하위에 JSP 파일을 두는 게 좋음.
    - InternalResourceView는 내부적으로 WEB-INF아래에 있는 JSP도 실행가능.

>  뷰와 뷰(View), 뷰 이름, JSP 뷰(템플릿)
> * MVC의 한 축으로써의 뷰 컴포넌트 
>   - 모델을 이용해 사용자에게 보여줄 내용을 만들어내는 역할을 통틀어 말함.
> * View 오브젝트.
>   - 스프링 MVC에서는 View 인터페이스를 구현해서 뷰 기능을 담당하는 다양한 클래스를 만들어 사용함. 
>   - 그래서 View는 DispatcherServlet이 직접 사용하는 오브젝트를 가리키는 말이기도 함. 
> * JSP 뷰
>   - JSP는 뷰 오브젝트가 활용하는 템플릿 파일일 뿐이지만, InternalResourceView는 실제 JSP에 모든 뷰 작업을 위임하기 때문에 JSP가 사실상 모델로부터 최종 내용을 생성하는 뷰의 역할을 담당한다고 봄.
> * 사용자가 보는 화면 또는 내용 

### 3.2.2. 스프링 웹 학습 테스트
* MockHttpServletRequest
* MockHttpServletResponse
* MockHttpSession
* MockServletConfig, MockServletContext
    - 스프링의 루트 웹 애플리케이션 컨텍스트는 서블릿 컨텍스트 안에 저장되는데, 모델 1 서블릿에서 루트 컨텍스트를 가져오는 경우를 테스트하려면 MockServletContext를 생성해서 스프링 루트 컨텍스트 넣어주면 됨.
    - 이렇게 생성된 MockServletContext는 MockHttpSession이나 MockHttpServletRequest에 넣어서 서블릿에 전달됨. 
    - 무슨 말이지?????





