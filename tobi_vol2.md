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

<hr>

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
    - 디폴트 이름은 `서블릿이름-servlet.xml`
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

## 3.3. 컨트롤러 
- 서블릿이 넘겨주는 HTTP 요청은 HttpServletRequest 오브젝트에 담겨 있음.
- 컨트롤러는 이 HttpServletRequest의 정보를 추출함.
- 사용자가 바르게 요청을 보냈는지도 검증을 해야함.
- 그후 컨트롤러는 서비스 계층의 메소드를 선정하는 것과 메소드가 필요로 하는 파라미터 타입에 맞게 요청 정보를 변환해야함.
- 컨트롤러는 서비스 계층 메서드가 돌려준 결과를 보고 어떤 뷰를 보여줘야 하는지도 결정해야 함.
- 뷰 선택이 끝난 후 뷰에 출력할 내용을 모델에 넣어줘야함.

### 3.3.1. 컨트롤러의 종류와 핸들러 어댑터 
- 스프링 MVC가 지원하는 컨트롤러의 종류는 4가지.(각 컨트롤러를 DispatcherServlet에 연결해주는 핸들러 어댑터도 하나씩 있어야 하므로 핸들러 어댑터도 총 4가지)   
- SimpleServletHandlerAdapter를 제외한 세 개의 핸들러 어댑터는 DispatcherServlet에 디폴트 전략으로 설정되어 있음.

#### 4가지 컨트롤러 / 핸들러 어댑터
##### 1. `Servlet / SimpleServletHandlerAdapter`
- `표준 서블릿`을 MVC의 컨트롤러로 사용가능함.
- (서블릿이라면 web.xml에 등록하고 사용하면 되긴하지만,) 기존에 서블릿으로 개발된 코드를 스프링 애플리케이션에 가져와 사용하려면 일단 서블릿을 web.xml에 별도로 등록하지 말고 스프링 MVC 컨트롤러로 등록해 사용
- 서블릿을 컨트롤러로 사용했을 때의 장점은 서블릿 코드를 그대로 유지하면서 서블릿이 스프링의 빈으로 등록된다는 점(서블릿 코드 스프링 포팅시 유용)
- 서블릿이 컨트롤러 빈으로 등록된 경우 init(), destroy()같은 생명주기 메서드는 호출되지 않음.
- 서블릿에서 초기화 작업을 하는 코드가 있으면 bean tag로 등록시 `init-method` attribute나 `@PostConstruct` 애노테이션을 이용해 빈 생성 후에 초기화 메소드가 실행되게 해야함 
- SimpleServletHandlerAdapter는 XML 설정에 핸들러 어댑터를 등록해줘야함.
- 빈이 등록되어 있으면 DispatcherServlet은 이를 자동으로 감지해 디폴트 핸들러 어댑터를 대신해 사용함.
    - 동시에 두가지 이상의 컨트롤러를 사용할 수 있기 때문에 DispatcherServlet은 여러 개의 핸들러 어댑터를 사용하기도 함. 
    - **핸들러 매핑에 의해 사용할 컨트롤러 빈을 찾아주면 그에 맞는 핸들러 어댑터를 이용해 컨트롤러를 호출해주는 것임.**
- Servlet 타입 컨트롤러는 모델과 뷰를 리턴하지 않음.

DispatcherServlet은 컨트롤러가 ModelAndView 타입의 오브젝트 대신 null을 리턴하면 뷰를 호출하는 과정을 생략하고 작업을 마치게 되어있음.(??)

#### 2. HttpRequestHandler / HttpRequestHandlerAdapter
- 서블릿 인터페이스와 비슷. 서블릿처럼 동작하는 컨트롤러를 만들기 위해 사용.
- 서블릿 스펙을 준수할 필요 없이 HTTP 프로토콜 기반으로 한 전용 서비스를 만들려고 할 때 사용. 
- 스프링은 HttpRequestHandler를 이용해서 자바의 RMI(Remote Method Invoker)를 대체할 수 있는 HTTP 기반 원격 호출 서비스인 HTTP Invoker를 제공.
- 모델과 뷰 개념이 없는 HTTP 기반의 RMI 같은 로우레벨 서비스를 개발할 때 이용 할 수 있다는 사실만 기억하자.

##### 3. Controller / SimpleControllerHandlerAdapter
- Controller 인터페이스를 구현한 컨트롤러.
- DispatcherServlet이 컨트롤러와 주고받는정보를 그대로 메소드의 파라미터와 리턴값으로 갖고 있음.
- 스프링 3.0의 애노테이션과 관려를 이용한 컨트롤러가 나오기전까지 많이 사용되던 컨트롤러
* Controller 타입 컨트롤러는 스프링 MVC를 확장해서 애플리케이션에 최적화된 전용 컨트롤러를 설계할 때 가장 유용 
    - Controller 처럼 HttpServletRequest를 직접 받지않고, 컨트롤러 메소드 파라미터에서 생략시킴. 
    - 프로퍼티에 지정해둔 파라미터만 request에서 추출해 이를 별도의 맵에 저장해 전달.
    - 기반 컨트롤러에서 미리 모델을 만들어 전달해주면 그 모델안에 값만 저장되도록 구현

##### 4. AnnotationMethodHandlerAdapter
- 지원하는 컨트롤러의 타입이 정해져 있지 않음.
- 클래스와 메소드에 붙은 몇 가지 애노테이션의 정보와 메소드 이름, 파라미터, 리턴 타입에 대한 규칙등을 종합적으로 분석해서 컨트롤러를 선별하고 호출 방식을 결정함. 
- 컨트롤러 하나가 하나 이상의 URL에 매핑될 수 있음. 
    - AnnotationMethodHandlerAdapter를 지원하면서 URL의 매핑을 컨트롤러 단위가 아니라 메소드 단위로 가능하게 됨.(스프링 2.5부터)
    - 메소드 단위로 컨트롤러 로직을 넣기위해 유연한 방식으로 매핑정보등을 지정해줘야 하기 때문에 애노테이션을 필요. 
- DefaultAnnotationHandlerMapping과 함께 사용해야 함.(동일한 애노테이션 사용)

### 3.3.2. 핸들러 매핑
* 핸들러 매핑: **HTTP 요청정보를 이용해서 이를 처리할 핸들러 오브젝트, 즉 컨트롤러를 찾아주는 기능을 가진 DispatcherServlet의 전략.**
- 핸들러 매핑은 컨트롤러의 타입과는 상관 X(한 핸들러 매핑 전략이 여러 타입의 컨트롤러를 선택 가능)
- 스프링은 기본적으로 5가지 핸들러 매핑을 제공.
    - 디폴트는 BeanNameUrlHandlerMapping, DefaultAnnotationHandlerMapping

#### 5가지 핸들러 매핑 
##### 1. BeanNameUrlHandlerMapping
- 빈의 이름에 들어 있는 URL을 HTTP 요청의 URL과 비교해서 일치하는 빈을 찾아줌.

##### 2. ControllerBeanNameHandlerMapping
- 빈의 아이디나 빈 이름을 이용해 매핑해주는 핸들러 매핑 전략
    - 빈의 id에 /를 사용할 수 없지만 ControllerBeanNameHandlerMapping이 자동으로 빈 아이디에 /를 붙여줌.
- 디폴트 전략이 아니기에 사용하려면 전략 빈으로 등록해줘야 함.
    - `특정 전략 클래스를 빈으로 등록한 경우엔 디폴트 전략은 무시됨`

##### 3. ControllerClassNameHandlerMapping
- 클래스 이름을 URL에 매핑해줌.
- 기본적으로 클래스 이름을 모두 url에 매핑하지만, 컨트롤러가 Controller로 끝날 때는 Controller를 제외한 이름이 url에 매핑됨.

##### 4. SimpleUrlHandlerMapping
- {URL-컨트롤러} 매핑 정보를 한곳에 모아놓을 수 있는 핸들러 매핑 전략. 
- 매핑정보는 SimpleUrlHandlerMapping의 빈의 프로퍼티에 넣어줌.
- 매핑정보가 한곳에 모여 있기 때문에 url 관리가 편함.
- 단점: 매핑할 컨트롤러 빈의 이름을 직접 적어줘야 하기 때문에 오타 등의 오류가 발생할 가능성이 있음.

##### 5. DefaultAnnotationHandlerMapping
- `@RequestMapping`이라는 애노테이션을 컨트롤러 클래스나 메소드에 직접부여하고 이를 이용해 매핑하는 전략 
- 메소드 단위로 url를 매핑할 수 있어서 컨트롤러 개수를 많이 줄일 수 있음.
- URL 뿐만 아니라 HTTP 메소드, 파라미터와 HTTP 헤더 정보까지 매핑에 활용할 수 있음.
- 다른 핸들러 매핑방식에선 컨트롤러의 코드에서 처리해야하는 것을 애노테이션으로 대체할 수 있음.
- 단점: 매핑 애노테이션의 사용 정책과 작성 기준을 잘 만들어두지 않으면 개발자마다 제멋대로 매핑 방식을 적용해서 매핑정보가 지저분해지고 관리하기 힘들어질 수
- 도 있음.

##### 기타 공통 설정 정보 
* order
    - 두개 이상의 핸들러 매핑을 적용했을 때 URL 매핑 정보가 중복되는 경우를 주의 해야함.
    - 이런 경우를 위해 스프링에서 핸들러 매핑의 우선순위를 지정할 수 있음. 
* defaultHandler
    - URL 매핑을 찾기 못했을 경우 자동으로 디폴트 핸들러를 서택.
    - 핸들러 매핑에서 URL에 매핑할 컨트롤러를 찾지 못하면 http 404 에러가 나는데, 대신에 디폴트 핸들러를 넘겨서 유연하게 대처 가능.
* alwaysUseFullPath 
    - URL 매핑은 기본적으로 웹 애플리케이션의 컨텍스트 패스와 서블릿 패스 두가지를 제외한 나머지만 가지고 비교.(웹 애플리케이션 배치 경로와 서블릿 매핑을 변경하더라도 URL 매핑정보가 영향받지 않게 하기 위해.)
    - 특이 케이스로 URL 전체를 사용해서 컨트롤러를 매핑하길 원할때 true 로 지정.
* detectHandlersInAncestorContexts
    - 보통 서블릿 컨텍스트의 부모 컨텍스트는 루트 컨텍스트인데, 자식 컨텍스트에선 부모 컨텍스트의 빈을 참조가능.(역은 불가)
    - 근데 핸들러 매핑의 경우 서블릿 컨텍스트내에서만 매핑할 컨트롤러를 찾음.(detectHandlersInAncestorContexts가 false)
    - true로 설정하면 루트 컨텍스트의 빈도 참조 대상이 되긴한데, 웹에 종속된 컨트롤러 빈은 서블릿 컨텍스트에만 두는게 바람직. 


### 3.3.3. 핸들러 인터셉터 
* 핸들러 매핑의 역할: URL에 매핑된 핸들러를 찾아주는 것 + `핸들러 인터셉터 적용`
* 핸들러 인터셉터: DispatcherServlet이 컨트롤러를 호출하기 전/후에 요청과 응답을 참조하거나 가공할 수 있는 일종의 필터(서블릿 필터같은 느낌. 서블릿 필터는 뭐지?)

핸들러 매핑은 DispatcherServlet으로 부터 매핑 작업을 요청받으면 그 결과로 HandlerExecutionChain을 돌려줌. 이 HandlerExecutionChain은 하나 이상의 HandlerInterceptor를 거쳐서 컨트롤러가 실행되게끔 구성되어 있음. 인터셉터가 전혀 등록되어 있지 않으면 바로 컨트롤러가 실행됨. 하나 이상의 인터셉터가 지정되었으면 순서에 따라 인터셉터를 거친 후 컨트롤러 호출됨.

* 핸들러 인터셉터는 서블릿 필터와 쓰임새가 유사.
    - HttpServletRequest/HttpServletResponse뿐만 아니라, 실행될 컨트롤러 빈 오브젝트, 컨트롤러가 돌려주는 ModelAndView, 발생한 예외등을 제공받을 수 있어 서블릿 필터보다 더 정교하게 인터셉터를 만들 수 있음.
* 핸들러 인터셉터는 스프링 빈이기때문에 DI를 통해서 다른 빈을 활용가능함.

#### HandlerInterceptor
* HandlerInteceptor 인터페이스를 구현 
* 3개의 메소드를 구현
    1. boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
        - 컨트롤러 호출 전 실행. 
        - handler 파라미터는 핸들러 매핑이 찾아준 컨트롤러 빈 오브젝트 
        - 컨트롤러 실행이전에 처리해야할 작업이 있다거나 요청정보를 가공하거나 추가하는 경우에 사용. 
        - 리턴값이 true이면 HandlerExectionChain의 다음단계로 진행. false면 작업을 중단하고 리턴해서 컨트롤러와 남은 인터셉터는 실행되지 않음.  
    2. void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mav)
        - 컨트롤러가 실행되고 난 다음 호출. 
        - 일종의 후처리 작업을 진행.
        - 컨트롤러가 돌려준 ModelAndView 타입 정보가 제공되기에 작업 결과를 참조하거나 가공가능.
        - preHandle()에서 false를 리턴했을 경우 postHandle()은 실행되지 않음.
    3. void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
        - 뷰에서 최종결과를 생성하는 등의 모든 작업이 완료된 후에 실행.
        - 요청 처리중 사용한 리소스를 반환해주기에 적당한 메소드.
* 핸들러 인터셉터는 하나이상 등록 가능. 
    - preHandle()은 인터셉터가 등록된 순서대로 호출 
    - postHandle(), afterCompletion()은 preHandle() 순서와 반대. 

* 핸들러 인터셉터는 핸들러 매핑 단위로 등록. 
* 인터셉터 자체도 하나의 독립적인 빈임.(한번만 등록해주고 각 매핑에 레퍼런스를 여러번 설정해주긴 해야함.)

- 서블릿 필터는 web.xml에 별도 등록해줘야하고, 필터 자체는 스프링 빈이 아님. 하지만 웹 애플리케이션으로 들어오는 모든 요청에 적용된다는 장점. 

* 컨트롤러에 공통적으로 적용할 부가 기능은 핸들러 인터셉터를 사용하는게 좋음.
    - AOP를 통해 인터셉터의 기능처럼 컨트롤러에 공통적인 부가 기능을 부여할 수도 있긴하지만, 컨트롤러의 타입이 일정하지 않고, 호출 패턴도 정해져 있지 않기 때문에 AOP 적용은 복잡함. 
    - 스프링 MVC는 모든 종류의 컨트롤러에 동일한 인터셉터를 적용할 수 있게 해줌


## 3.4. 뷰 
* View는 MVC아키텍처에서 모델이 가진 정보를 어떻게 표현해야하는가에 대한 로직을 담고있는 컴포넌트.
* 컨트롤러가 작업을 마치고 뷰 정보를 ModelAndView 오브젝트로 담아서 DispatcherServlet에 돌려주는 방법은 두가지가 있음.
    1. View 타입의 오브젝트를 돌려주는 방법
    2. View 이름을 돌려주는 방법
        - 뷰 이름으로 부터 실제 사용할 뷰를 결정할 `ViewResolver`가 필요함.

### 3.4.1. 뷰
* View 인터페이스
    - getContentType(): 뷰 오브젝트가 생성하는 컨텐트 타입 정보 제공.
    - render(): 모델을 전달받아 클라이언트에 돌려줄 결과물을 만들어내는 작업.
* 뷰를 사용하는 방법
    1. 스프링이 제공하는 기반 뷰 클래스를 확장해서 코드로 뷰를 만드는 방법(엑셀, PDF, RSS피드 같은 뷰는 컨텐트 생성하는 API를 사용해 뷰 로직 작성)
    2. 스프링이 제공하는 뷰를 활용하되, (뷰 클래스를 상속하거나 코드를 작성하지는 않고) JSP나 프리마커 같은 템플릿 파일을 사용하거나 모델을 자동으로 뷰로 전환하는 로직을 적용.

#### InternalResourceView, JstlView
##### InternalResourceView
- RequestDispatcher의 forward()나 include()를 이용하는 뷰.
- forward()나 include()는 다른 서블릿을 실행해서 그 결과를 현재 서블릿의 결과로 사용하거나 추가하는 방식.
- 서블릿을 forward() 용도로 사용하는 일은 드뭄(?).
- 주로 JSP 서블릿을 통해 JSP 뷰를 적용할 때 사용
- 서블릿을 웹 계층에서 만들던 때라면 서블릿안에서 RequestDispatcher를 만들어 JSP로 포워딩해주는 방식을 사용했을 것임.(뷰 역할을 하는 .jsp가 HTML페이지를 완성하는데 사용할 다이내믹한 정보는 서블릿 요청의 애트리뷰트에 담아서 전달.)
    - 컨트롤러가 돌려준 뷰 이름을 포워딩할 JSP의 이름을 사용하고 모델 정보를 요청 애트리뷰트에 넣어주는 작업을 InternalResourceView와 DispatcherServlet이 대신 해주는 것임.

##### JstlView
- JstlView는 InternalResourceView의 서브클래스
- 지역정보에 따라 달라지는 지역화된 메시지를 JSP 뷰에 사용할 수 있게 해줌.

<br>

* InternalResourceView/JstlView를 사용하려면 컨트롤러안에서 뷰 오브젝트를 직접생성하는 것보다 `뷰 리졸버를 이용하는 게 편함`.(JSP 파일의 위치를 나타내는 논리적인 뷰 이름만 넘겨주면 됨.)
* 뷰 오브젝트 대신 뷰 이름이 ModelAndView에 전달되오면 DispatcherServlet은 디폴트 뷰 리졸버로 등록된 InternalResourceViewResolver를 통해 InternalResourceView를 가져와 사용할 것임. 
* 뷰 리졸버는 보통 뷰 오브젝트를 캐싱하기 때문에 같은 URL의 뷰가 반복적으로 만들어지지 않게 해서 성능 면에서도 유리함.

#### RedirectView
* HttpServletResponse의 sendRedirect()를 호출해주는 기능을 가진 뷰.
    - 뷰가 생성되는게 아니라, URL만 만들어져 다른 페이지로 리다이렉트 됨.
    - 모델정보가 있으면 URL뒤에 파라미터로 추가됨.
* 컨트롤러가 RedirectView 오브젝트를 직접 생성해서 리턴해도되지만, 뷰리졸버가 인식할 수 있게 `redirect:`로 시작하는 뷰이름을 사용하면 편리함.
```java
return new ModelAndView(new RedirectView("/main"));

return new ModelAndView("redirect:/main");
```
* 리다이렉트에서 쓰는 url은 'http://' 혹은 '/'으로 시작할 수 있음.
    - '/'로 시작하는 경우 서버의 루트 URL로부터 시작되야함.
        - 웹 애플리케이션의 루트 패스가 '/'가 아니라면 생성자나 프로퍼티를 통해 contextRelative를 true로 바꿔줘야함.
        - contextRelative가 false이면 Redirect url에 웹 컨텍스트 루트까지 써줘야함.
    - 서블릿 패스또한 자동으로 추가되지 않음.
#### 그외
- VelocityView, FreeMarkerView
- MarshallingView
- AbstractExcelView, AbstractJExcelView, AbstractPdfView
- AbstractAtomFeedView, AbstractRssFeedView
- XsltView, TilesView, AbstractJasperReportsView
- MappingJacksonJsonView

### 3.4.2. 뷰 리졸버 
* 뷰 리졸버는 뷰 이름으로 부터 사용할 뷰 오브젝트를 만들어줌.
* 뷰 리졸버를 빈으로 등록하지 않으면 DispatcherServlet의 디폴트 뷰 리졸버인 InternalResourceViewResolver 가 사용됨.
* 하나 이상의 뷰 리졸버를 빈으로 등록 가능.


#### InternalResourceViewResolver
* 디폴트 뷰 리졸버 
* 단순한 예제를 만드는게 아니면 디폴트로 등록된 기본상태의  InternalResourceViewResolver를 그대로 사용하는 일은 피해야함.
* 디폴트 상태로 사용하게 되면 뷰로 사용할 템플릿의 full path; e.g. `/WEB-INF/view/hello.jsp`를 다 적어줘야함. 
* suffix, prefix 프로퍼티를 통해 생략가능함.(뷰리졸버를 빈으로 등록하는 설정을 해줘야함.)
    - 이렇게 되면 뷰 리졸버가 뷰 이름을 이용해 포워딩할 때 앞뒤에 붙여주는 내용을 지정함.
* 뷰와 관련된 DispatcherServlet 전략중에 `RequestToViewNameTranslator`라는 것이 있음.
    - 컨트롤러가 뷰 이름을 넘겨주지 않았을 경우 URL을 이용해서 자동으로 뷰 이름을 만들어줌.
* InternalResourceViewResolver는 JSTL 라이브러리가 클래스패스상에 존재하면 JstlView(JSTL 부가기능을 지원하는)를 사용하고, 존재하지 않으면 InternalResourceView를 사용함.

#### VelocityViewResolver, FreeMarkerViewResolver
- JSP와는 다르게 템플릿의 경로를 만들때 사용할 루트 패스를 VelocityConfigurer, FreeMarkerConfigurer로 지정해줘야 함.

#### ResourceBundleViewResolver, XmlViewResolver, BeanNameViewResolver
* ResourceBundleViewResolver, XmlViewResolver는 외부 리소스 파일에 각 뷰 이름에 해당하는 뷰 클래스와 설정을 저장하고 이를 참조함.

##### ResourceBundleViewResolver
* 기본적으로 views.properties 파일에서 뷰 이름으로 시작하는 키르 ㄹ찾아 뷰 클래스와 url등의 정보를 가져와 뷰를 생성함.
* 장점: 독립적인 파일을 이용해 뷰를 자유롭게 매핑할 수 있다.
* 단점: 모든 뷰를 일일이 파일에 정의해야함/뷰에서 다른 빈을 참조하는 경우 프로퍼티 파일 사용이 어려움.

##### XmlViewResolver
* xml의 빈 설정파일을 이용해 뷰를 등록(뷰 이름과 일치하는 아이디를 가진 빈을 뷰로 사용.)
* 서블릿 컨텍스트를 부모 컨텍스트로 갖는 애플리케이션 컨텍스트로 만들어짐. 
* 빈 설정을 사용하기 때문에 DI를 자유롭게 이용할 수 있음.

##### BeanNameViewResolver
* 뷰 이름과 동일한 빈 이름을 가진 빈을 찾아서 뷰로 사용하게 해줌.
* 서블릿 컨텍스트의 빈을 사용함.


> 템플릿 파일을 사용하는 뷰가 아닌 경우 컨트롤러에서 뷰 오브젝트를 직접 리턴하는 방법과 설정 파일을 이용해서 뷰를 매핑해주는 방법 중에 한가지를 선택해 사용하면 됨.

#### ContentNegotiatingViewResolver
* 뷰 이름으로부터 직접 뷰 오브젝트를 찾아주지 않음.
* 미디어 타입을 이용해서 다른 뷰 리졸버에게 뷰 찾는 일을 위임한후에 적절한 뷰를 선정해서 돌려줌.
* ContentNegotiatingViewResolver의 뷰 결정 과정 
    1. 미디어 타입 결정
        - 요청정보로부터 사용자가 요청할 미디어 타입정보 추출
        - 4가지 방법(URL 확장자 이용/파라미터 이용/Accept 헤더 이용/디폴트 이용)
    2. 뷰 리졸버 위임을 통한 후보 뷰 선정
        - 컨트롤러가 돌려준 뷰 이름을 모든 후보 뷰 리졸버에 보내 사용 가능한 뷰를 확인
        - ContentNegotiatingViewResolver를 사용하는 경우에는 다른 뷰 리졸버를 독립적으로 사용하지 않음. 
        - defaultVeiws 프로퍼티를 이용해서 디폴트 뷰를 등록해주면 디폴트 뷰는 뷰 리졸버의 조회 결과에 상관없이 무조건 후보 뷰로 추가됨.
    3. 미디어 타입 비교를 통한 최종 뷰 선정
        - 요청정보의 미디어 타입과 뷰 리졸버에서 찾은 후보 뷰 리스트를 비교해 사용할 뷰 선정.


> InternalResourceViewResolver는 `new JstlView("/WEB-INF/jsp/content.jsp")`와 같이 생성된 뷰를 돌려줄 것임.
> 그렇다면 모든 url에 대해 요청이 한번씩은 들어왔다고 가정한다면 ViewResolver는 각 JSP템플릿을 이용한 JstlView 오브젝트를 하나씩 생성해서 캐싱하고 있다고 보면 될라나?


## 3.5. 기타 전략
### 3.5.1. 핸들러 예외 리졸버
* 컨트롤러의 작업중에 발생한 예외를 어떻게 처리할지 결정하는 전략.
* 보통 컨트롤러/그 밑 레이어에서 예외가 던져지면 DispatcherServlet이 전달받아 서블릿 밖으로 던져서 서블릿 컨테이너가 처리하게 됨.
    - 좀 더 친절하게 web.xml에 `<error-page>`를 지정해서 예외가 발생했을 때 JSP 안내 페이지를 보여줄 수도 있음.
* HandlerExceptionResolver가 등록되있으면 DispatcherServlet은 예외 발생시 먼저 HandlerExceptionResolver에게 처리할 수 있는지 확인.
    - 처리할 수 있다면 예외를 DispatcherServlet 밖으로 던지지 않고 해당 HandlerExceptionResolver가 처리함.

#### AnnotationMethodExceptionHandler
* 예외가 발생한 `컨트롤러 내의 메소드 중`에서 `@ExceptionHandler` 애노테이션이 붙은 메소드를 찾아 예외처리를 맡겨주는 핸들러 예외 리졸버
* 특정 예외 클래스를 애노테이션에 지정할 수 있음.
* ExceptionHandler에서 ModelAndView를 리턴하게 되면 DispatcherServlet에서 컨트롤러가 ModelAndView를 리턴한 것 처럼 뷰를 통해 결과를 만들어줌.

#### ResponseStatusExceptionHandler
* 예외를 특정 HTTP 응답 상태코드로 전환해주는 것.(좀 더 의미있는 HTTP 상태로)
* ResponseStatusExceptionHandler는 발생한 예외의 클래스에 @ResponseStatus가 있는지 확인하고, 만약 있다면 애노테이션에 지정해둔 HTTP 응답 상태코드를 클라이언트에 전달함. 
* 단점: @ResponseStatus를 붙일 수 있는 예외 클래스를 만들어 사용해야함. 

#### DefaultHandlerExceptionResolver
* 위 두가지 ExceptionResolver에서 처리하지 못한 예외를 다루는 마지막 ExceptionHandler
* 스프링 내부적으로 발생하는 주요 예외를 처리해주는 표준 예외처리 로직을 담고 있음.
* 스프링 MVC 내부에서 발생하는 예외를 다루는 것이므로 신경쓰지 않아도 됨.

#### SimpleMappingExceptionHandler
* web.xml의 `<error-page>`와 유사하게 예외를 처리할 뷰를 지정할 수 있음.
* 디포트 빈이 아니기에 직접 빈등록 해야함.
* 모든 컨트롤러에서 발생하는 예외에 일괄적용 가능
> 예외 발생시 로그 기록/알람 같은 경우는 HandlerInterceptor의 afterCompletion()에서 처리하는 게 좋음.

### 3.5.2. 지역정보 리졸버
* LocaleResolver는 애플리케이션에서 사용하는 지역정보를 결정하는 전략. 
* AcceptHeaderLocaleResolver: HTTP 헤더의 지역정보를 그대로 활용. 
    - 브라우저 설정을 따름.
* 브라우저 설정을 따르지 않고 사용자가 직접 변경하도록 만드려면 SessionLocaleResolver/CookieLocaleResovler를 사용하는 것이 편리
    - HTTP 세션이나 쿠키에 들어 있는 값을 확인해 애플리케이션의 지역정보를 결정.

### 3.5.3. 멀티파트 리졸버 
* 멀티파트 포맷의 요청정보를 처리하는 전략 설정. 
* DispatcherServlet은 멀티파트 요청을 받으면 MultipartResolver에게 요청해서 HttpServletRequest를 MultipartHttpServletRequest로 전환함.

### RequestToViewNameTranslator
* 컨트롤러가 Dispatcher에게 뷰 이름이나 뷰 오브젝트를 전달하지 않았을 경우 HTTP 요청정보를 참고해서 뷰 이름을 생성해주는 로직를 담음.
* 디폴트로 DefaultRequestToViewNameTranslator 전략 등록. 
    - URL을 기준으로 뷰 이름 결정 
    - 기본 URL에서 확장자를 제거한 것을 뷰 이름으로 사용함.


## 3.6. 스프링 3.1의 MVC
### 3.6.1. 플래시 맵 매니저 전략 
- 스프링 3.1에서 플래시 맵을 관리하는 `FlashMapManager` 전략이 추가됨.

#### 플래시 맵
* 플래시 애트리뷰트를 저장하는 맵
* 플래시 애트리뷰트
    - 하나의 요청에서 생성되어 다음 요청으로 전달되는 정보
    - 다음 요청에서 한번 사용되고 바로 제거됨
    - 주로 Post/Redirect/Get 패턴을 사용하는 경우 Post 요청에서의 작업 결과 메시지를 redirect되는 페이지로 전달할 때 주로 사용함.
* **Post/Redirect/Get 패턴**
    -  POST 요청의 결과로 뷰를 보여주게 되면, 이때 나오는 웹페이지는 POST 요청의 결과이기 때문에 브라우저에서 새로고침을 하게 되면 동일한 POST 요청으로 폼 결과가 재전'/송되기에 사용자 정보가 중복되어 저장될 위험이 있음.
    - 그래서 POST의 작업을 마친 후에 `RedirectView`를 이용해서 아예 다른 URL을 가진 페이지로 이동시키는 것이 권장됨.
* 새로운 페이지로 리다이렉트하는 경우 HTTP 요청이 바뀌기에 컨트롤러가 모델을 이용해서 다음 요청의 뷰로 정보를 전달할 수 없음.
    - 리다이렉트 하는 url에 파라미터로 담거나, 세션을 사용해야함.
* 리다이렉트 될 때 한번만 메시지를 보여주고 같은 페이지에서 검색 조건을 바꾸거나 직접 페이지를 갱신해서 동일한 웹 페이지가 다시 로딩되는 경우 작업 직후에 보여줬던 메시지가 사라져야함.
    - 이에 적합한 게 `플래시 애트리뷰트`
* 플래시 애트리뷰트는 보통 다음 웹 요청에서 사용하고 제거함.
* ajax와 같이 액션없이 서버에 수시로 요청을 보내는 기능을 가진 웹 페이지에선 POST/Redirect 사이에 다른 요청이 끼어들 위험도 있음
    - 특정 페이지의 요청에서만 처리하도록 지정할 수 있음. 

#### 플래시 맵 매니저 
* 플래시 맵을 저장/유지/조회/제거 하는 등의 작업을 담당하는 오브젝트.

#### 플래시 맵 매니저 전략 
* 플래시맵을 저장하는 방법은 세션이나, 디비등에 가능.

### 3.6.2. WebApplicationInitializer를 통한 컨텍스트 등록
* 서블릿 3.0은 이전까지 웹 애플리케이션 구성에 web.xml 파일만 단독으로 사용하던 것을 벗어나, 설정 방식을 모듈화해서 관리하는 방법을 도입.
* 프레임워크 모듈에서 직접 서블릿 컨텍스트를 초기화할 수 있게 도와주는 ServletContainerInitializer 같은 API가 제공됨.
    - `서블릿 컨테이너를 초기화한다는 것은 web.xml에서 했던 주요한 설정 작업들, 대표적으로 서블릿 등록과 매핑, 리스너 등록, 필터 등록 같은 작업을 말함`
* 스프링 3.1에서는 ServletContainerInitializer를 이용하면 스프링 컨텍스트 설정과 등록 작업에 자바 코드를 이용할 수 있다. 
    - 스프링의 웹 모듈 내에 ServletContainerInitializer를 구현한 클래스가 포함되어 있음.
    - WebApplicationInitializer 인터페이스를 구현한 클래스를 찾아 컨텍스트의 초기화 작업을 위임.

* **WebApplicationInitializer를 구현한 클래스를 만들어두면 웹 애플리케이션이 시작될 때 onStartup() 메소드가 자동으로 실행됨.**
    - 이때 메소드 파라미터로 ServletContext 오브젝트가 전달되는데, 이를 이용해 필요한 컨텍스트 등록 작업을 수행하면 됨.
    - `이 방법을 이용하면 기존에 web.xml내에 <listener>나 <servlet>을 이용해 등록했던 루트 컨텍스트나 서블릿 컨텍스트를 자바 코드에서 직접 등록하고 생성할 수 있음.`

#### 루트 웹 컨텍스트 등록 
* 기존에 루트 컨텍스트는 web.xml에서 서블릿 컨텍스트 리스너 형태로 등록.
* 리스너로 등록되는 **ContextLoaderListener**는 기본적으로 `/WEB-INF/applicationContext.xml`를 설정파일로 사용하는 XmlWebApplicationContext 타입의 애플리케이션 컨텍스트를 생성하고, 생성된 컨텍스트를 서블릿 컨텍스트의 애트리뷰트로 등록해서 다른 곳에서 손쉽게 가져다 사용할 수 있게 해줌.
    - (`서블릿의 리스너에 대해서 한번 공부를 또 해봐야할 듯.`)
```xml
  <!-- (리스너를 이용한) 루트 웹 애플리케이션 컨텍스트 생성. -->
  <listener>
    <display-name>ContextLoader</display-name>
    <listener-class>
      org.springframework.web.context.ContextLoaderListener
    </listener-class>
  </listener>
```

* 리스너란 서블릿 컨텍스트가 생성하는 이벤트를 전달받도록 만들어진 것.(이벤트 리스너)
    - 서블릿 컨텍스트가 만드는 이벤트는 `컨텍스트 초기화/종료 이벤트`. 웹 애플리케이션이 시작되고 종료되는 시점에 이벤트가 발생하고, 리스너를 만들어두면 두가지 이벤트를 전달받게 됨.
    - **스프링이 리스너를 이용해 루트 애플리케이션 컨텍스트를 생성하는 이유는 루트 컨텍스트의 생명주기가 서블릿 컨텍스트와 일치하기 때문.**(서블릿 컨텍스트가 초기화/종료될 때 루트 컨텍스트도 초기화/종료 될 필요가 있음.)
    - 이렇게 웹 애플리케이션과 같은 범위 동안 유지되는 서비스를 관리하기 위해 리스너가 사용됨.
* WebApplicationInitializer를 구현한 코드는 서블릿 컨텍스트가 초기화 될 때 자동으로 실행되니 초기화 시점을 고려할 필요는 없음.
    - 하지만 서블릿 컨텍스트 종료 시점은 파악할 수 없기때문에, WebApplicationInitializer를 사용하더라도 루트 컨텍스트는 리스너를 이용해 관리하는게 좋음.

* 기존 루트 컨텍스트를 web.xml에서 리스너를 등록해서 생성하는 방법에서, 디폴트 루트 컨텍스트인 XmlWebApplicationContext 대신 AnnotationConfigWebApplicationContext가 사용되게 한다거나, 디폴트 XML 설정파일인 /WEB-INF/applicationContext.xml 외의 파일을 추가하고 싶다면 ContextLoaderListener가 참고 할 수 있게 컨텍스트 클래스와 설정파일 위치 정보를 별도로 제공해야함.
    - 컨텍스트 초기화 파라미터를 이용해서 전달 가능.

* XML에서 컨텍스트 파라미터를 이용해 루트 컨텍스트의 클래스나 설정파일, 클래스의 위치를 지정하게 한 것은 XML에선 이런 정보를 지정하는 방식에 제약이 있기 때문.
    - 자바 코드에서는 직접 클래스를 이용해 컨텍스트 오브젝트를 생성하고필요한 설정을 할 수 있음.

* ContextLoaderListener 생성
    - 디폴트 생성자를 사용하면, 우선 컨텍스트 파라미터가 등록된게 있는지 먼저 확인하고 없으면 디폴트 설정으로 루트 컨텍스트 오브젝트를 생성.
    - 만들어진 컨텍스트 오브젝트를 생성자에 넣으면 해당 컨텍스트 오브젝트를 루트 컨텍스트로 사용함. 
* 루트 컨텍스트 오브젝트를 코드에서 생성했기 때문에 마음대로 초기화 작업을 진행가능.
    - scan()을 통해 패키지를 통째로 스캔할 수도, 
    - register()를 통해서 @Configuration 클래스 하나만 등록할 수도 있음.
* 생성된 루트 컨텍스트에서 런타임 환경 오브젝트를 가져와서 활성 프로파일을 지정하거나 프로퍼티 소스를 추가할 수도 있음.

> **여기까지 했다면 초기화 작업까지 마친 루트 컨텍스트 오브젝트를 리스너에 담아서 서블릿 컨텍스트에 추가해주면 루트 컨텍스트 등록 부분은 마무리가 됨.**

#### 서블릿 컨텍스트 등록
* 서블릿 컨텍스트는 서블릿 안에서 초기화되고 서블릿이 종료될 때 같이 종료됨.
    - 이때 사용되는 서블릿이 DispatcherServlet
* 서블릿 컨텍스트를 만드는 XML에서 서블릿 등록시 `서블릿 이름/클래스/서블릿 로딩 조건/매핑 URL 패턴`이 담겨져 있음.
* 서블릿 컨텍스트를 DispatcherServlet의 디폴트 설정과 다르게 만드려면 `<init-param>`을 사용.
    - ServletRegistration.Dynamic 오브젝트의 setInitParameter()를 통해 등록가능.

* 서블릿 컨텍스트를 직접만들고 초기화해서 DispatcherServlet의 생성자로 전달해도 됨.

> WebApplicationInitializer 인터페이스를 구현한 클래스를 넣어두면 스프링과 관려된 모든 설정을 web.xml에서 제거할 수 있음.
> web.xml과 WebApplicationInitializer를 같이 사용하는 경우 web.xml의 `<web-app>` 루트 엘리먼트 버전이 반드시 3.0이어야함.

* **web.xml 및 xml 설정정보를 WebApplicationInitializer를 이용하도록 했을 때 처음에 안됬던 이유(디버깅)**
    - 우선 web.xml을 deployment descriptor로 사용하던 것에서 web.xml을 제거하는 방식으로 변경했을 때 인텔리제이 프로젝트의 proejct structure에서 module의 Web의 Deployment Descriptor가 web.xml.bak에 레퍼런스로 박혀잇던걸 제거하니 우선 해당 이슈는 넘어가고 웹앱이 잘 떴음.
    - 근데 또 CGLIB가 없어서 @Configuration 클래스를 읽을 수 없었음. pom에 cglib추가하니 되긴 했는데, 이게 왜 이런건지 파악이 필요함.
        - (이런 이슈가 뜨는데, 이게 일어나는 이유와 CGLIB가 하는일들, 그리고 @Configuration을 진행하는데 왜 CGLIB가 필요한지를 파악해보자.)

## 3.7. 정리
* 스프링 MVC의 핵심엔진인 `DispatcherServlet`은 7가지 종류의 전략을 제공함. 
    - 각 전략은 빈으로 등록하고 설정할 수 있음. 직접하지 않으면 디폴트 전략 구성을 활용.
* DispatcherServlet은 컨트롤러를 직접 호출하지 않고 핸들러 어댑터를 통해 호출함. 
* 핸들러 매핑은 다양한 전략을 통해 요청정보와 이를 처리하는 컨트롤러를 연결 
* 핸들러 인터셉터는 컨트롤러 수행 전/후에 적용할 부가기능을 만들때 사용 
* 뷰 리졸버는 컨트롤러가 리터하는 논리적인 뷰 이름을 이용해 뷰 오브젝트를 찾아줌.
* 핸들러 예외 리졸버를 통해 애플리케이션에서 발생한 예외를 처리하는 방법 지정
* 스프링 3.1에서는 플래시 맵 매니저 전략이 추가됐고, WebApplicationInitializer를 이용해 컨텍스트 생성과 등록을 위한 초기화 코드를 작성할 수 있음.

<hr>

# Chap 04: 스프링 @MVC
애노테이션 중심의 새로운 MVC 확장기능은 @MVC라는 별칭으로 부르기도 함.(애노테이션 기반 MVC)

## 4.1. @RequestMapping 핸들러 매핑
* @MVC의 가장 큰특징은 핸들러 매핑과 핸들러 어댑터의 대상이 오브젝트가 아니라 `메소드`
* @MVC에선 모든 것이 메소드 레벨로 세분화 
    - 애노테이션은 타입(클래스, 인터페이스) 레벨 뿐만 아니라 메소드 레벨에도 적용이 가능 
* 애노테이션은 부여되는 대상의 타입이나 코드에는 영향을 주지 않는 메타 정보이기에 유연하게 컨트롤러를 구성할 수 있음. 
* @MVC 핸들러 매핑을 위해선 `DefaultAnnotationHandlerMapping`이 필요. 

### 4.1.1. 클래스/메소드 결합 매핑정보 
* DefaultAnnotationHandlerMapping의 핵심은 `매핑정보로 @RequestMapping 애노테이션`을 활용한다는 점.
* @RequestMapping은 타입레벨 뿐만 아니라 메소드 레벨에도 붙일 수 있음.
    - 스프링은 이 두 위치에 붙은 @RequestMapping 정보를 결합해서 최종 매핑정보를 생성.
* 기본적인 결합방법은 타입 레벨의 @RequestMapping정보를 기준으로 삼고, 메소드 레벨의 @RequestMapping 정보는 타입 레벨의 매핑을 좀 더 세분화하는데 사용.

#### @RequestMapping 애노테이션
##### String[] values(): URL 패턴 
* URL 패턴을 지정.
* 와일드카드를 사용할 수도 있음. 
```java
@RequestMapping("/hello")
@RequestMapping("/main*")
@RequestMapping("/view.*")
@RequestMapping("/admin/**/user")
```
* {}를 사용하는 URL템플릿을 사용할 수도 있음.
    - {}위치에 해당하는 내용을 컨트롤러 메소드에서 파라미터로 전달 받을 수 있음.
    - {}에 들어가는 이름은 `path variable`이라 불리고, 하나이상 등록 가능.
```java
@RequestMapping("/user/{userid}")
```
* 하나 이상의 URL 패턴 정의 가능.
```java
@RequestMapping({"/hello", "/hi"})
```

* **default suffix pattern**이 적용됨.
```java
@RequestMapping("/hello") // 이 URL을 지정했다고 하면
// 아래와 같은 3개의 URL 패턴을 적용했을 때와 동일한 결과가 나옴.
@RequestMapping({"/hello", "/hello/", "/hello.*"})
// "/hello"라고 정의하면 "/hello.do", "/hello.html"과 같이 확장자가 붙은 URL이나, "/hello/"처럼 끝에 /가 붙은 URL도 자동 매핑됨.
```
##### RequestMethod[] method(): HTTP 요청 메소드 
* 같은 URL이더라도 요청 http 메소드에 따라 다른 컨트롤러 메소드에 매핑해줄 수 있음.

> HTML의 폼에서는 GET과 POST만 지원하기 때문에 PUT, DELETE 같은 요청 메소드를 사용하기가 쉽지 않음. 
> 이런 요청 메소드를 사용하려면 js나, 스프링이 지원하는 커스텀 태그인 `<form:form>`을 이용해서 히든 필드를 통해 HTTP 메소드를 전달하는 방법이 있음. 

##### String[] params(): 요청 파라미터 
* 요청 파라미터와 그 값을 비교해서 매핑해주는 것.
* 같은 URL을 사용하더라도 http 요청 파라미터에 따라 별도 작ㅇ버을 해주고 싶을 때가 있음.
    - 코드에서 파라미터 체크 후 기능 분리하는 대신 @RequestMapping에 매핑을 위한 요청 파라미터를 지정해줄 수 있음. 
```java
@RequestMapping(value="/user/edit", params="type=admin")
@RequestMapping(value="/user/edit", params="type=member")
@RequestMapping(value="/user/edit")
```
* URL구분이 좀 더 상세한 쪽이 선택됨.
* 폼에서 POST로 전송한 폼 파라미터도 비교 대상임.
* 특정파라미터가 존재하지 않아야 한다는 조건을 지정할 수도 있음.
```java
@RequestMapping(value="/user/edit", params="!type")
```

##### String[] headers(): HTTP 헤더
```java
@RequestMapping(value="/view", headers="content-type=text/*")
```

#### 타입 레벨 매핑과 메소드 레벨 매핑의 결합 
* 타입(클래스/인터페이스) 레벨에 붙은 @RequestMapping은 타입 내의 모든 매핑용 메소드의 공통 조건을 지정할 때 사용.
    - 그후 메소드 레벨에서 조건을 세분화해줌.
```java
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/add")
    public String add(...) {}
    @RequestMapping("/edit")
    public String edit(...) {}
    @RequestMapping("/delete")
    public String delete(...) {}
}
```
* 타입 레벨 URL 패턴에 `*나 **`를 사용했을 때도 URL을 결합가능.
    - 타입레벨에서 /user/* 를 사용했을 경우 메소드 레벨에 /add가 선언되있으면 /user/add 로 결함.
        - 타입 레벨이 /user, /user/, /user/* 중 하나로 되어있으면 메소드레벨에 /add라 선언되있으면 /user/add로 결합가능
    - 타입레벨에서 /user/** 를 사용했을 경우 메소드 레벨에 /add는 /user/**/add로 결합.

* **타입 레벨에서 공통 매핑조건을 정의하고 각 메소드에서 세분화된 매핑조건을 추가한다는 개념만 있으면 어떤식이든 결합가능**

#### 메소드 레벨 단독 매핑
* 타입 레벨에 조건을 주지 않고 메소드 레벨에만 매핑정보를 지정할 수도 있음.
    - 타입 레벨에는 조건이 없는 @RequestMapping을 붙이기만 하면 됨.
    - 타입 레벨에 @RequestMapping을 주지 않게되면 클래스 자체가 매핑 대상이 되지 않아 텅빈 @RequestMapping이라도 부여해줘야함.
    - 컨트롤러 클래스에 @Controller 애노테이션을 붙여 빈 자동스캔 방식으로 등록되게하면 @RequestMapping을 생략할 수 있음.(스프링이 @Controller 애노테이션을 보고 애노테이션 방식을 사용한 클래스라고 판단)

#### 타입 레벨 단독 매핑
* 애노테이션 영향으로 매핑 방법이 메소드까지 세분화되긴 했지만, 다른 컨트롤러와의 일관성을 위해 애노테이션 방식의 핸들러 매핑에서도 일단 오브젝트까지만 매핑하고, 최종 실행할 메소드는 핸들러 어댑터가 선정함.
* 그래서 @RequestMapping을 타입 레벨에서 단독으로 사용해서 다른 컨트롤러에 대한 매핑을 위해 사용할 수도 있음.
```java
@RequestMapping("/hello")
public class HelloController implements Controller {
    ...
}
```
* 원칙적으로 핸들러 매핑과 핸들러 어댑터는 독립적으로 조합될 수 있기 때문에 적용가능한 방식.

* 클래스 레벨에선 `/*`로 끝나게하고 메소드 레벨에는 빈 @RequestMapping 애노테이션만 부여해주면 메소드 이름이 URL 대신 적용됨.
```java
@RequestMapping("/user/*")
public class UserController {
    @RequestMapping public String add(...) {} // /user/add 에 매핑
    @RequestMapping public String edit(...) {} // /user/edit 에 매핑
}
```

### 4.1.2. 타입 상속과 매핑
1. @RequestMapping 정보는 상속됨.
2. 단, 서브클래스에서 @RequestMapping을 재정의하면 슈퍼클래스의 정보는 무시됨.

* 인터페이스 구현에 의한 @RequestMapping 정보 상속은 클래스 상속과 약간 차이가 있음. 주의.

#### 매핑정보 상속의 종류
1. 상위 타입과 메소드의 @RequestMapping 상속
2. 상의 타입의 @RequestMapping과 하위 타입 메소드의 @RequestMapping 결합 
3. 상위 타입 메소드의 @RequestMapping과 하위 타입의 @RequestMapping 결합 
    - 인터페이스를 구현하는 메소드에 URL이 없는 빈 @RequestMapping을 부이면 인터페이스 메소드의 매핑정보가 무시됨. 주의.
    - 제네릭스와 결합하면 매우 편리하게 활용할 수 있는 추상 클래스 만들 수 있음.
4. 하위 타입과 메소드의 @RequestMapping 재정의 
5. 서브클래스 메소드의 URL 패턴 없는 @RequestMapping 재정의
    - 클래스 상속에서 오버라이드한 하위 메소드에 한해 URL 조건이 없는 @RequestMapping을 붙였을 경우 상위 메소드의 @RequestMapping의 URL 조건이 그대로 상속됨.

#### 제네릭스와 매핑정보 상속을 이용한 컨트롤러 작성
* 타입만 달라지는 중복코드이면 제네릭스의 타입 파라미터를 가진 슈퍼클래스로 공통코드를 뽑아내는 것이 좋음. 
    - 동시에 매핑정보의 일부, 즉 URL의 일부가 중복되는 것도 슈퍼 클래스에 미리 정의해둘 수 있음.
* CRUD성 컨트롤러의 코드는 도메인 오브젝트만 바뀐채로 반복될 것임.
* 컨트롤러의 역할에서 파라미터 파싱, 요청 정보 검증, 뷰 선택 로직 등은 모두 컨트롤러 밖으로 분리할 수 있음.(이부분 이해 못햇음.)


## 4.2. @Controller
* Controller interface를 구현하는 핸들러의 경우 호출될 메서드가 정해져있고 파라미터/리턴 타입이 결정되어있기때문에 핸들러 어댑터 입장에서 핸들러를 호출할 때 간단히 할 수 있음.
* @MVC의 컨트롤러는 특정 인터페이스를 구현하지도 않고, 메소드 이름, 파라미터 종류 및 갯수, 리턴타입이 정해져 있지 않음.
    - AnnotationMethodHandlerAdapter가 어떻게 이런 핸들러 메소드를 사용할 수 있을까?

> 컨트롤러가 DispatcherServlet으로부터 실행되는 과정.
> 
> DispatcherServlet이 HTTP 요청 정보를 이용해 HandlerMapping에게 매핑된 HandlerAdapter를 찾음.
> DispatcherServlet은 HttpServletRequest, HttpServletResponse를 HandlerAdapter에게 전달함.
> 그럼 HandlerAdapter는 전달받은 HttpServletRequest/Response를 컨트롤러가 사용할 수 있는 형태로 변환한 후에 제공함.
> 컨트롤러 호출 후 HandlerAdatper는 컨트롤러가 돌려준 결과를 ModelAndView에 담아서 DispatcherServlet에게 돌려줌.

* Controller 인터페이스의 경우 파라미터로 HttpServletRequest, HttpServletResponse를 전달받고 리턴타입으로 ModelAndView를 반환함.
    - 그래서 Controller 인터페이스를 구현한 핸들러를 담당하는 HandlerAdapter는 해줄일이 거의 없음.
    - DispatcherServlet으로부터 HttpServletRequest, HttpServletResponse 전달받은 걸 그대로 컨트롤러 호출시 넘기고, 컨트롤러 리턴값인 ModelAndView를 그대로 DispatcherServlet에게 리턴하면 되기 때문.

* 앞서 3장에서 구현한 커스텀 컨트롤러(SimpleController)를 위한 핸들러 어댑터가 DispatcherServlet에서 받은 파라미터를 조작 후 컨트롤러에게 넘겨줌. 또한 컨트롤러 호출시 모델을 담을 객체도 생성해서 컨트롤러로 던져줌.(컨트롤러에서 직접 Model을 만들지 않아도됨.)
    - 이렇게 편하기는 하지만, 문제는 DispatcherServlet에서 넘겨준 HttpServletRequest/HttpServletResponse를 컨트롤러에서 그대로 사용하고 싶은 경우임.
    - HttpServletRequest
        - e.g. 쿠키,HTTP 헤더 참조/멀티파트 정보 핸들링/HTTP 세션 핸들링
    - HttpServletResponse(e.g. ModelAndView로는 넣을 수 없는 부가적인 응답정보; 어떤게 있지)
    
* `@Controller를 사용하면 메소드의 파라미터 개수와 타입, 리턴 타입등을 자유롭게 결정할 수 있음.`
    - 그래서 @Controller를 담당하는 핸들러 어댑터는 상당히 복잡함.

* 리턴값이 없는 핸들러 메소드이면 스프링은 비어있는 모델 오브젝트와 뷰 이름을 돌려줌.
    - 뷰이름은 RequestToViewNameTranslator에 의해서 URL을 따라 지정될 것임. 

* 스프링은 메소드의 파라미터와 리턴값이 어떻게 선언됬는지 보고,이를 이용해 적절한 파라미터 값을 준비해서 호출함. 리턴 값도 타입에 따라 적절한 방식으로 사용.


### 4.2.1. 메소드 파라미터의 종류 
* HttpServletRequest,HttpServletResponse
    - 주의: HttpServletResponse를 파라미터에 추가하면 핸들러 리턴타입이 void일 떄 RequestToViewNameTranslator가 적용이 안됨.
* HttpSession
    - HttpSession은 HttpServletRequest를 통해 가져 올 수 있긴 하지만 세션만 필요한 경우라면 위의 파라미터 선언.
    - 서버에 따라 멀티스레딩 환경에서 안전성이 보장되지 않음 (why?)
* WebRequest, NativeWebRequest
    - HttpServletRequest의 요청정보를 대부분 그대로 가지고 있는 서블릿 API에 종속적이지 않은 오브젝트 타입.
* Locale
    - DispatcherServlet의 Locale Resolver가 결정한 Locale 오브젝트를 받을 수 있음.
* InputStream, Reader
    - HttpServletRequest의 getInputStream()을 통해 받을 수 있은 콘텐트 스트림 혹은 Reader타입 오브젝트 제공받을 수 있음.
* OutputStream, Writer
    - HttpServletResponse의 getOutputStream()을 통해 가져올 수 있는 출력용 콘텐트 스트림 또는 Writer 타입 오브젝트를 받을 수 있음.
* `@PathVariable`
    - @RequestMapping의 URL에 {}로 들어가는 path variable을 받음.
    - 요청 파라미터를 쿼리 스트링 대신 url 패스로 풀어 쓰는 방식을 쓸 때 유용
    - URL의 `{}`에는 패스 변수를 넣고, 이 이름을 `@PathVariable`  애노테이션의 값으로 넣어서 메소드 파라미터에 부여.
    - 여러개를 선언할 수도 있음. 
    - 타입이 맞지 않으면 400 에러
* `@RequestParam`
    - 단일 HTTP 요청 파라미터를 메소드 파라미터에 넣어주는 애노테이션
    - 요청 파라미터의 이름을 @RequestParam의 기본 값으로 지정해주면 됨.
    - 하나 이상의 파라미터에 적용가능.
    - 스프링 내장 변환기가 다룰 수 있는 모든 타입을 지원.
    - @RequestParam에 파라미터 이름을 지정하지 않고 `Map<String, String> 타입으로 선언하면 모든 요청 파라미터를 담은 맵을 받을 수 있음.`
    - @RequestParam을 사용했다면 해당 파라미터가 반드시 있어야만 함.
        - 없으면 400 에러 
        - 파라미터를 필수가 아니라 선택적으로 받게 하고 싶으면 `required` element를 false로 하면 됨.
    - 디폴트 값 지정 가능.
    - 요청 파라미터의 이름과 컨트롤러 파라미터의 이름이 같다면 @RequestParam의 이름을 생략가능.
    - String, int  같은 단순 타입은 @RequestParam생략가능.
        - 비추천
* @CookieValue
    - HTTP 요청의 쿠키 값을 파라미터에 넣어줌.
    - 애노테이션 기본값에 쿠키의 이름 지정
    - 메소드 파라미터 이름과 쿠키 값이 동일하다면 쿠키 이름 생략가능.
    - @RequestParam과 마찬가지로 무조건 값이 있어야하고, 필수가 아니면 required=false, 디폴트 값을 넣고싶으면 defaultValue를 지정.

* `Map, Model, ModelMap`
    - `다른 애노테이션이 붙어있지 않다면` java.unit.Map, org.springframework.ui.Model, org.springframework.ui.ModelMap 타입의 파라미터는 모두 모델정보를 담는데 사용할 수 있는 오브젝트가 전달됨. 
    - 파라미터로 정의해서 HandlerAdapter에서 미리 만들어 제공해주는 것을 사용하면 편리.
    - Model/ModelMap은 addAttribute()를 재공함.
        - 일반 Map의 put()처럼 이름을 지정해서 오브젝트를 넣을 수도 있음.
        - 자동 이름 생성 기능을 이용해서 오브젝트만 넣을 수 있음.
            - 타입정보를 이용해서 모델이름 자동 지정
    - addAllAttribute()를 이용하면 Collection에 담긴 모든 오브젝트를 자동 이름 생성 방식을 적용해서 모든 모델로 추가해줌.

* `@ModelAttribute`
    - 여기서 설명하는 것처럼 메소드 파라미터에 부여할 수도 있고, 메소드 레벨에 적용할 수도 있음.(사용 목적이 다르니 차이점에 주의!)
    - @ModelAttribute는 이름 그대로 모델로 사용되는 오브젝트.
        - _일반적으로 컨트롤러가 뷰에 출력할 정보를 전달하기 위해 ModelAndView에 담아서 전달하는 모델과는 조금 의미가 다름._
        - 컨트롤러는 모델 오브젝트 하나를 뷰에 전달하는게 아니라 여러개의 모델을 맵형태 컬렉션에 담아서 전달함.
            - 이 모델 맵을 보통 모델이라고 부르기도 함.(구분 잘 할 것.)
    - `@ModelAttribute는 모델맵에 담겨서 뷰에 전달되는 모델 오브젝트의 한가지라 볼 수 있음.`
    - 기본적으로 @ModelAttribute는 별도 설정없이도 자동으로 뷰에 전달됨.
    - 모델 정보를 생성하고 조작하는 건 컨트롤러의 몫이긴 하지만, 때로는 클라이언트로 부터 받는 HTTP 요청정보를 이용해서 생성되는 모델도 있음.
        - e.g. 웹 페이지의 form 정보 처럼 일단 컨트롤러가 받아서 내부 로직에 사용하고 필요에 따라 다시 화면에 출력하기도 하는 정보가 있음.
    - `클라이언트로 부터 받는 요청정보중에서 하나 이상의 값을 가진 오브젝트 형태로 만들 수 있는 구조적인 정보를 @ModelAttribute 모델`이라 부름.
    - 컨트롤러가 전달받는 오브젝트 형태의 정보를 가리키는 말.
    * 컨트롤러가 클라이언트에게 전달 받는 정보 중 가장 단순한 형태는 요청 파라미터.
        - GET 메소드: URL의 쿼리스트링을 통해 전달
        - POST 메소드: form 필드의 값으로 전달.
        - 단순한 파라미터는 @RequestParam 애노테이션으로 받으면 됨.
    * 요청 파라미터를 메소드 파라미터에서 1:1로 받으면 @RequestParam
    * 도메인 오브젝트나 DTO의 프로퍼티에 요청 파라미터를 바인딩해서 한번에 받으면 @ModelAttribute
        - 하나의 오브젝트에 클라이언트의 요청정보를 담아서 한번에 전달되는 것이기에 커맨드 패턴에서의 커맨드 오브젝트라 부르기도 함.
    * URL이 쿼리 스트링으로 들어오는 검색조건 같은 정보를 @ModelAttribute가 붙은 파라미터 타입에 모두 담아서 전달해주는 것을 커맨드라 부름.
    * 폼 데이터를 받는 경우도 @ModelAttribute사용.
        - 폼의 내용을 담을 수 있는 도메인 오브젝트나 DTO를 @ModelAttribute로 사용함.
        - submit된 폼의 내용을 저장해서 전달받거나 / 뷰로 넘겨서 출력하기 위해 사용되는 오브젝트를 모델 애트리뷰트라함.
    * 애노테이션 생략 가능 
        - @RequestParam/@ModelAttribute 둘다 생략가능한데, 스프링에서 어떤 애노테이션이 생략됬는지 판단하는 기준이, String, int 같은 단순 타입은 @RequestParam; 그외 복잡한 오브젝트는 @ModelAttribute가 생략됐다고 간주함.
    * 컨트롤러가 리턴하는 모델에 파라미터로 전달한 오브젝트를 자동으로 추가해줌.
        - 모델 이름은 기본적으로 파라미터 타입의 이름을 따름.(다른이름을 붙이기 위해선 애노테이션 밸류값 지정.)

* Errors, BindingResult
    - @ModelAttribute가 붙은 파라미터를 사용할 때는 @RequestParam과 달리 validation 작업이 진행됨.
        - @RequestParam은 스프링의 기본 타입 변환 기능을 사용해서 요청 파라미터를 메소드 타입으로 변환함.(URL의 쿼리 스트링이이나 폼필드는 멀티 타입이 아니면 문자열로 오기 때문)
        - @RequestParam의 타입 변환 작업에 실패하면 예외가 발생해 400 bad request가 전달됨.
        - @ModelAttribute에 데이터 바인딩 도중 타입 변환 에러가 발생해도 400 에러가 나지 않음.
            - 단지 BindException 타입의 오브젝트에담겨서 컨트롤러로 전달됨.
            - @ModelAttribute는 요청 파라미터의 타입이 모델 오브젝트의 프로퍼티 탕입과 일치하는지를 포함한 다양한 방식의 검증 기능을 수행하기 때문에 에러로 바로 처리하지 않음.
            - 파라미터 타입이 일치하지 않는 다는 것은 검증 작업의 한 결과일 뿐임. 예외상황이 아님.
    - 폼 필드는 사용자가 직접 입력하는 것이기 때문에 반드시 검증이 필요함. 
        - 입력 값 오류 처리는 컨트롤러에게 맡겨야하는데, 메소드 파라미터에 맞게 요청정보를 추출해서 제공해주는 책임을 가진 핸들러 어댑터가 변환 작업 실패에 대한 정보를 컨트롤러에게 전달해줘야함.
        - 이 예외 정보는 org.springframework.validation.Errors나 org.springframework.validation.BindingResult 타입의 파라미터를 같이 사용해야함.
    - 스프링 폼 처리 커스텀 태그를 사용하면 BindingResult에 담긴 오류 정보를 적절한 메시지로 변환해서 화면에 출력가능.
    - _이 파라미터들은 반드시 @ModelAttribute 파라미터 뒤에 나와야함._
        - 자신의 바로 앞에 있는 @ModelAttribute 파라미터의 검증 작업에서 발생한 오류만 전달해줌.

* SessionStatus
    - 모델 오브젝트를 세션에 저장해두고 다음 페이지에서 다시 활용할 수 있게하는 기능이 있는데, 이 기능을 사용중 더이상 사용하던 모델 오브젝트가 필요가 없을 때 직접 작업 완료 메소드를 호출해서 세션 안의 오브젝트를 제거해줘야함.
        - 이때 사용하는게 SessionStatus 오브젝트
* @RequestBody
    - 이 애노테이션이 붙은 파라미터에는 http 요청의 body 부분이 그대로 전달됨.
    - 일반적인 GET/POST 요청에선 잘 사용할 일이 없고, XML이나 JSON 기반의 메시지를 사용하는 요청의 경우 유용.
    - AnnotationMethodHandlerAdaptor엔 HttpMessageConverter 타입의 메시지 변환기가 여러 개 등록되어 있음. 
        - @RequestBody가 붙은 파라미터가 있으면 http 요청의 미디어 타입과 파라미터의 타입을 먼저 확인.
        - 메시지 변환기 중에서 해당 미디어 타입과 파라미터 타입을 처리할 수 있는 것이 있으면 HTTP 요청의 본문 부분을 통째로 변환해서 지정된 메소드 파라미터에 전달.
        * 변환기 종류 
            - StringHttpMessageConverter: 스트링 타입의 파라미터와 모든 종류의 미디어 타입 처리
            - MarshallingHttpMessageConverter: XML 본문을 가지고 들어오는 요청을 XML이 변환된 오브젝트로 전달 받을 수 있게 함. 
            - MappingJacksonHttpMessageConverter: JSON 타입 메시지의 경우 사용.
    - @RequestBody는 보통 @ResponseBody와 함꼐 사용됨.

> Q: `@Autowired`는 어떻게 동작하는가?

* @Value
    - 빈의 값 주입에 사용하던 @Value애노테이션을 컨트롤러 메소드의 파라미터에 부여할 수 있음.
    - 사용방법은 동일.
    - 주로 시스템 프로퍼티나 다른 빈의 프로퍼티, 똔느 복잡한 SpEL을 이용해 클래스의 상수를 읽어오거나 특정 메소드를 호출한 결과 값, 조건식등을 넣을 수 있음.
    - 컨트롤러도 일반적인 스프링 빈이기 때문에 @Value를 메소드 파라미터 대신 멤버필드에 DI해주는 것이 가능.
```java
// 컨트롤러 메소드 파라미터에 전달
@RequestMapping(...)
public String hello(@Value("#{systemProperties['os.name']}") String osName) {...}

// 멤버에 DI
public class HelloController {
    @Value("#{systemProperties['os.name']}") 
    String osName;

    @RequestMapping(...)
    public String hello() {
        String osName = this.osName;
    }
```

* @Valid
    - JSR-303의 빈 검증기를 통해 모델 오브젝트를 검증하도록 지시하는 지시자.
    - 모델 오브젝트의 검증 방법을 지정하는데 사용. 
    - 보통 @ModelAttribute와 함께 사용.

> Q: 왜 프로그래밍 랭귀지의 리턴 값은 하나인가? 컴구때 배운거 같기도 한데.. stack pointer 때문인가?


### 4.2.2. 리턴 타입의 종류 
* 컨트롤러가 핸들러 어댑터를 거쳐 최종적으로 DispatcherServlet에게 전달해야하는 정보는 `모델과 뷰`이고 ModelAndView 타입으로 리턴 값이 전달됨.
* 컨트롤러의 리턴 타입은 기타 정보와 결합해서 ModelAndView로 만들어짐.

#### 자동 추가 모델 오브젝트와 자동생성 뷰 이름
메소드 리턴 타입에 상관없이 조건만 맞으면 모델에 자동 추가되는 정보.
1. @ModelAttribute 모델 오브젝트 또는 커맨드 오브젝트
2. Map, Model, ModelMap 파라미터
    - 컨트롤러에 해당 파라미터를 지정하면 미리 생성된 모델 맵 오브젝트를 전달받아 오브젝트 추가가능.
    - 이런 파라미터에 추가한 모델 맵 오브젝트는 DispatcherServlet을 거쳐 뷰에 전달되는 모델에 자동으로 추가됨.(`Q: 여기 어감은 그맵이 그대로 들어가는게 아니라 어디 다른 맵에 내용이 옮겨진후 들어간다는 느낌이네? 한번 코드봐야알듯.`)
3. @ModelAttribute 메소드
    - @ModelAttribute를 _컨트롤러 클래스의 일반 메소드에 부여 가능_
    - 뷰에서 참고정보로 사용하는 모델 오브젝트를 생성하는 메소드를 지정하기 위해 사용
    - @ModelAttribute가 붙은 메소드는 컨트롤러 클래스 안에 정의하지만 컨트롤러 기능을 담당하지 않음.(@RequestMapping을 붙이면 안됨.)
    - 같은 클래스내의 모든 컨트롤러 메소드에서 공통적으로 활용하는 정보가 있을 때 적용하면 좋음.(모든 컨트롤러 메소드 실행시 모델에 담김.)
        - e.g. 폼이나 검색 조건 페이지등에서 select 태그를 써서 선택 가능한 목록등을 보여주는 경우
4. BindingResult
    - 뷰에서 사용되는 커스텀 태그나 매크로에서 사용되기 때문에 자동 추가
    - 주로 잘못 입력된 폼 필드의 잘못 입력된 값을 가져오거나 바인딩 오류 메시지를 생성할 때 사용.

5. 컨트롤러에서 어떤식으로든 뷰 이름을 제공해주지 않으면 RequestToViewNameTranslator 전략에 의해 자동으로 뷰이름 생성
    - ModelAndView 타입 오브젝트로 리턴했을 경우에도 뷰 이름이 설정되어 있지 않은 경우 자동생성의 대상이 됨.

#### 메소드 리턴 타입의 종류
* ModelAndView
    - 잘 사용되지 않음.(주로 옛 방식 포팅.)
* String
    - 뷰 이름으로 사용
    - 모델 정보는 모델 맵 파라미터를 가져와 추가하는 방식을 사용해야함.
    - 흔히 사용되는 @Controller 메소드 작성 방식. 깔끔.
* void
    - RequestToViewNameTranslator 전략을 통해 자동생성되는 뷰 이름 사용.
    - URL과 뷰 이름이 일관되게 사용된다면 적극 고려.
* 모델 오브젝트(일반 단순 오브젝트)
    - 뷰이름은 RequestToViewNameTranslator 전략을 사용하고 모델 하나밖에 없다면 해당 모델 오브젝트(모델 맵 X)을 리턴해도됨.
    - 스프링은 미리 지정한 타입이나 void가 아닌 단순 오브젝트면 이를 모델 오브젝트로 인식해서 모델 맵에 자동으로 추가함.(모델 이름은 타입 이름을 따름)
* Map/Model/ModelMap
    - 해당 오브젝트가 모델맵으로 사용됨.
    - 단일 오브젝트라해서 모델 오브젝트 하나가 모델맵에 추가된다고 착각하지 말 것.
* View
    - 뷰 이름대신 뷰 요브젝트를 사용하고 싶은 경우 사용
* @ResponseBody
    - @ResponseBody가 메소드 레벨에 부여되면 `메소드가 리턴하는 오브젝트는 메시지 컨버터를 통해 바로 HTTP 응답의 메시지 본문으로 전환됨.`(뷰를 통해 결과를 만들어내는 모델로 사용되는 대신)
    - 리턴 타입이 String이면 스트링 타입을 지원하는 메시지 컨버터가 이를 변환해서 HttpServletResponse의 출력스트림으로 넣어버림.
    - @ResponseBody가 적용된 컨트롤러는 리턴 값이 단일 모델 오브젝트이고 메시지 컨버터가 뷰와 같은 식으로 작동한다고 보면 됨.
    - 근본적으로 @RequestBody, @ResponseBody는 XML이나 JSON과 같은 메시지 기반의 커뮤니케이션을 위해 사용됨.

### 4.2.3. @SessionAttributes와 SessionStatus
* HTTP 요청에 의해 동작하는 서블릿은 기본적으로 상태를 유지하지 않음.
    - 그래서 매요청이 독립적으로 처리됨.
    - 하나의 HTTP 요청을 처리한 후에는 사용했던 모든 리소스를 정리해버림.


#### @SessionAttributes
스프링의 세션기능은 기본적으로 HTTP세션을 사용

* @SessionAttributes의 기능 
    1. 컨트롤러 메소드가 생성하는 모델정보중에서 @SessionAttributes에 지정한 이름과 동일한 것이 있다면 이를 세션에 저장해줌.
        - @SessionAttributes는 모델정보에 담긴 오브젝트 중에서 세션 애트리뷰트라고 지정한 모델이 있으면 이를 자동으로 세션에 저장해줌.
    2. @ModelAttribute가 지정된 파라미터가 있을 때 이 파라미터에 전달해줄 오브젝트를 세션에서 가져옴.
        - 원래 파라미터에 @ModelAttribute가 있으면 해당 타입의 새 오브젝트를 생성한 후에 요청 파라미터 값을 프로퍼티에 바인딩해줌.
        - @SessionAttributes에 선언된 이름과 @ModelAttribute의 모델 이름이 동일하면, 그때는 먼제 세션에 같은 이름의 오브젝트가 있는지 확인함.
        - 존재한다면 모델 오브젝트를 만드는 대신 세션에 있는 오브젝트를 가져와서 @ModelAttribute 파라미터로 전달할 오브젝트로 사용함.
        - @ModelAttribute는 폼에서 전달된 필드정보를 모델 오브젝트의 프로퍼티에 넣어줌.
* @SessionAttributes의 설정은 클래스의 모든 메소드에 적용됨.
* @SessionAttributes의 기본 구현인 HTTP세션을 이용하는 저장소는 기본적으로 모델의 이름을 세션의 attribute로 사용함.
    - 이름 충돌에 주의

#### @SessionStatus
* @SessionAttributes를 더이상 사용하지 않는다면 코드 레벨에서 직접 제거해줘야함.(스프링은 직접 지워주지않음.)
* SessionStatus 오브젝트의 setComplete() 메소드를 호출해서 세션의 오브젝ㄷ트를 제거해줘야함.
```java
@RequestMapping(value="/user/edit", method=RequestMethod.POST)
public String submit(@ModelAttribute User user, SessionStatus sessionStatus) {
    ...
    sessionStatus.setComplete();
    ...
}
```

#### 등록 폼을 위한 @SessionAttributes 사용 
* 스프링의 폼 태크는 등록/수정 화면에 상관없이 뷰에 노출할 때 폼의 내용을 담을 모델 오브젝트를 필요로 함.
    - 뷰 노출시 빈 오브젝트라도 던져줘야함.

## 4.3. 모델 바인딩과 검증
* 컨트롤러 메소드에 @ModelAttribute가 붙은 파라미터를 추가할 때 3가지 작업이 일어남.
    1. @ModelAttribute의 모델 오브젝트 생성
    2. HTTP 요청 파라미터를 @ModelAttribute 모델의 프로퍼티에 바인딩
    3. 모델 값 검증
        - 바인딩 단계에서 타입 변환 에러는 검증되었고, 그외 필요한 검증이 있다면 적절한 검증기를 등록해서 검증.
* 스프링에서의 바인딩은 오브젝트의 프로퍼티에 값을 넣는 것/읽는 것
* 스프링의 2가지 프로퍼티 바인딩
    1. XML 설정파일을 이용해 빈을 정의할 떄 property 태그로 값을 주입하는 것.(XML은 텍스트 파일이기 때문에 문자열 형태임.)
    2. HTTP 요청으로 들어오는 요청 파라미터를 모델 오브젝트 등에 변환할 떄.(HTTP 요청또한 전부 텍스트로 들어옴.)
* 스프링의 바인딩은 프로퍼티 값을 타입에 맞게 변환하는 작업과, 오브젝트에 setter를 호출해서 세팅하는 작업이 필요함.

### 4.3.1. PropertyEditor
* 스프링이 기본으로 제공하는 바인딩용 타입 변환 API(근데 사실 자바빈 표준에 정의된 인터페이스임.)
> 자바빈은 원래 GUI 환경에서 비주얼 컴포넌트를 만들떄 활용하도록 설계되었음.
> PropertyEditor는 GUI 환경에서 프로퍼티 창의 문자열과 자바빈의 프로퍼티 타입사이의 타입 변환을 담당.
> 하지만 GUI보다는 자바빈이 정의한 수정자, 접근자 규약을 이용해서 애플리케이션의 로직이나 정보를 저장하는 비 GUI 컴포넌트로 더 많이 활용됨.
* 자바빈은 리플렉션을 통한 오브젝트 자동생성에 필요한 파라미터 없는 기본 생성자와 프로퍼티 값을 읽고 쓰는 데 필요한 수정자, 접근자에 대한 규약정도만 남아 JSP빈이나 EJB등에서 프로퍼티를 표현하는 관례로 활용되는 정도임.
* 스프링도 관례를 따라서 `컨테이너가 관리하는 기본 오브젝트를 빈`이라 부름.

#### 디폴트 프로퍼티 에디터
* HTTP 요청에서 들어온 파라미터를 바인딩 과정에서 메소드 파라미터 타입으로 변환해주기 위해 스프링이 디폴트로 등록해준 프로퍼티 에디터 적용.
* 바인딩 과정에서 메소드 파라미터의 타입에 맞는 프로퍼티 에디터가 자동으로 선정돼서 사용됨.
* 스프링이 지원하지 않는 타입을 파라미터로 사용하면 직접 프로퍼티 에디터를 만들어서 적용할 수도 있음.

#### 커스텀 프로퍼티 에디터
* enum 용 PropertyEditor를 제공해주지 않으면 컨트롤러에 /user/search?level=1 과 같은 형식으로 요청이 오면 ConversionNotSupportedException 예외 때문에 500 에러가 날 것임.

#### @InitBinder
* @MVC에는 스프링 컨테이너에 정의된 디폴트 프로퍼티 에디터만 등록되있음.
> 
> 컨트롤러 메소드에서의 바인딩 과정:  AnnotationMethodHandlerAdapter는 @RequestParam, @ModelAttribute, @PathVariable등처럼 HTTP 요청을 파라미터 변수에 바인딩해주는 작업이 필요한 애노테이션을 만나면 가장 먼저 WebDataBinder를 생성함.   
* WebDataBinder는 여러 기능을 제공하는데, HTTP 요청에서 가져온 문자열을 파라미터 타입의 오브젝트로 변환하는 기능도 있음.  
* 커스텀 프로퍼티 에디터를 메소드 파라미터 바인딩에 적용하기 위해선 WebDataBinder에 직접 등록해줘야함.
* 스프링이 제공하는 WebDataBinder 초기화 메소드를 이용해서 등록.
* @InitBinder 애노테이션이 부여되있고 WebDataBinder 파라미터가 있는 메소드를 컨트롤러에 생성.
    - 커스텀 프로퍼티 에디터를 등록
```java
@InitBinder
public void initBinder(WebDataBinder dataBinder) {
dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
}
```
* @InitBinder가 붙은 메소드는 메소드 파라미터 바인딩이 일어나기 전에 자동으로 호출됨.
    - 그래서 디폴트 프로퍼티 에디터 외에도 WebDataBinder에 커스텀 프로퍼티 바인더를 추가할 기회를 제공.
* @InitBinder를 통해 등록된 커스텀 에디터는 같은 컨트롤러의 메소드에서 HTTP 요청을 바인딩하는 모든 작업에 적용됨.
* WebDataBinder에 커스텀 프로퍼티 에디터를 등록하는 방법 두가지
    - 특정 타입에 무조건 적용되는 프로퍼티 에디터 
    - 특정 이름의 프로퍼티에만 적용되는 프로퍼티 에디터
        - @ModelAttribute로 지정된 모델 오브젝트의 프로퍼티 바인딩에 적용됨.
        - 같은 타입이더라도 프로퍼티 이름이 같지않으면 바인딩 되지 않음.
        - 기존에 프로퍼티 에디터가 있는경우 우선순위가 더 높은 프로퍼티 에디터를 등록하고 싶은 경우에 적용.
        - 타입 변환 뿐만 아니라 부가적인 조건을 부여해주고 싶을 때 

#### WebBindingInitializer
* 프로퍼티 에디터를 한번에 모든 컨트롤러에 적용하고 싶을 때 사용.
* WebBindingInitializer를 구현해서 만든 클래스를 빈으로 등록하고 @Controller를 담당하는 AnnotationMethodHandlerAdapter의 webBindingInitializer 프로퍼티에 DI 해줌.
    - 프로퍼티 설정을 위해선 AnnotationMethodHandlerAdapter 또한 빈으로 직접 등록해줘야함.

#### 프로토타입 빈 프로퍼티 에디터
* 프로퍼티 에디터는 싱글톤 빈으로 등록될 수 없음.
    - 짧지만 잠시나마 상태(요청 파라미터에서 넘어온 텍스트)를 가지고 있음.
    - 멀티스레드 환경에서 다른 스레드의 값이 덮어질 수도 있음.
* **다른 스프링 빈을 사용하기 위해선 DI로 빈을 주입받아야하는데, 이를 위해선 자기 자신도 빈이 되어야만 함.**
    - `프로퍼티 에디터가 다른 빈을 DI 받을 수 있도록 자신도 빈으로 등록되면서, 동시에 매번 새로운 오브젝트를 만들어서 사용할 수 있으려면 프로토타입 스코프의 빈(매번 새로운 빈 오브젝트를 가져와 사용)으로 만들어져야함.`

* 프로토타입 빈을 등록하고 이를 싱글톤 빈에서 사용하는 방법에 대해서는 여러 방법이 있지만 예제애선 그중 Provider를 이용.

### 4.3.2. Converter와 Formatter
> PropertyEditor는 매번 요청이 들어올 때마다 새로운 오브젝트를 만들어야 한다는 단점이 있음.
수* PropertyEditor와는 달리 Converter는 호출과정에서 메소드가 한번만 호출됨.
    - 상태를 가지고 있지 않음.
        - 멀티스레드 환경에서도 안전하게 공유해서 사용가능.
        - 싱글톤 스프링 빈으로 등록해서 필요한 오브젝트에서 DI 받아 사용가능.

#### Converter
* PropertyEditor와 다르게 소스 타입에서 타깃 타입으로 단방향 변환만 지원.
* 소스/타깃 타입을 임의로 지정가능.(제네릭스를 이용해 미리 지정)

#### ConversionService
* Converter 오브젝트는 PropertyEditor와 처럼 개별적으로 추가하는 대신 ConversionService 타입 오브젝트를 통해 WebDataBinder에 추가해줘야함.
* ConversionService는 여러 Converter 종류를 이용해서 하나 이상의 타입 변환 서비스를 제공해주는 오브젝트를 만들때 사용하는 인터페이스
* 스프링 3.0의 새로운 타입 변환 오브젝트는 Converter외에도 GenericConverter와 ConverterFactory를 통해 만들 수 있음.
* GenericConverter
    - GenericConverter를 이용하면 하나 이상의 소스-타킷 변환을 처리할 수 있는 컨버터를 만들 수 있음.
    - 또 필드 컨텍스트를 제공받을 수 있음.
        - 필드 컨텍스트란 모델의 프로퍼티에 대한 바인딩 작업시 제공받을 수 있는 메타정보
        - 이 메타정보는 오브젝트 타입 뿐만 아니라 클래스의 필드에 부여된 애노테이션, 제네릭스 파라미터, 메소드 파라미터 정보등을 말함.
        - 따라서 GenericConverter를 이용하는 단순 타입 종류 뿐만 아니라 메타정보의 부가정보를 활용한 변환 로직을 작성할 수 있음.
* GenericConversionService는 일반적으로 빈으로 등록하고 필요한 컨트롤러에서 DI 받은 후 @InitBinder를 통해 WebDataBinder 에 설정하는 방식으로 사용함.

* WebDataBinder에 GenericConversionService 설정방법
    1. @InitBinder를 통한 수동 등록
        - 일부 컨트롤러에만 적용하고 싶거나 하나 이상의 ConversionService를 만들고 컨트롤러에서 선택해 사용하고 싶을 때 컨트롤러에서 원하는 ConversionService를 @InitBinder를 통해 등록 
        - GenericConversionService에 변환 오브젝트를 추가하는 방법 두가지 
            1. GenericConversionService를 상속해서 새로운 클래스를만들고 생성자에서 addConverter()로 추가할 컨버털들 등록
            2. GenericConversionServiceFactoryBean을 이용해서 프로퍼티로 DI받은 Converter로 구성된 GenericConversionService를 가져오는 방법
        * WebDataBinder에는 하나의 ConversionService 오브젝트만 허용함.
    2. ConfigurableWebBindingInitializer를 이용한 일괄 등록
        - WebBindingInitialzer를 통해 모든 컨트롤러에 한번에 적용
        - WebBindingInitialzer를 구현한 클래스를 만들고 빈으로 등록해도 되지만 ConfigurableWebBindingInitializer를 사용하면 편리함.(빈설정 만으로 WebBindingInitializer를 빈으로 등록할 수 있음.)

#### Formatter와 FormattingConversionService
* Formatter는 Converter와 달리 양방향 적용 가능.(두개 메서드)
* GenericConversionService에 직접 등록할 수 없고, Formatter 구현 오브젝트를 GenericConverter타입으로 포장해서 등록해주는 FormattingConversionService를 통해 적용 가능.
* Formatter의 메소드에는 Locale타입 현재 지역정보도 함께 제공
* Formatter를 본격적으로 도입하려면 필드의 애노테이션까지 참조할 수 있는 AnnotationFormatterFactory를 사용해야 함.

* FormattingConversionServiceFactoryBean을 사용했을 때 자동으로 등록되는 Formatter 두 가지
    1. @NumberFormat
        - 문자열로 표현된 숫자를 java.lang.Number 타입의 오브젝트로 상호 변환
    2. @DateTimeFormat
        - 강력한 날짜/시간 정보 관리 라이브러리인 Joda Time을 이용하는 애노테이션 기반 포맷터
        - 스타일을 style 엘리먼트를 이용해 지정할 수 있음.(S(Short), M(Medium), L(Long), F(Full))
        - 위의 4개의 문자를 `날짜와 시간`에 대해 각각 한글자씩 사용해서 두개의 문자로 만들어 지정.(한쪽을 사용하지 않는다면 ?로 두면됨.)
        - LocaleResolver에 의해 결정된 지역정보가 자동으로 반영되기 때문에 지역화 기능을 이용할 때 매우 편리
        - 기본 스타일이 마음에 들지 않으면 직접 패턴을 지정해도됨.
        ```java
        @DateTimeFormat(pattern="yyyy/MM/dd")
        Date orderDate;
        ```

#### 바인딩 기술의 적용 우선순위와 활용 전략
* 사용자 정의 타입의 바인딩을 위한 일괄 적용: Converter
    - enum같이 애플리케이션에서 정의한 타입이면서 모델에서 자주 활용된다면 Converter로 만들고 ConversionService로 묶어서 일괄적용하는 것이 편리
* 필드와 메소드 파라미터, 애노테이션등의 메타정보를 활용하는 조건부 변환 기능: ConditionalGenericConverter
    - 바인딩이 일어나는 필드와 메소드 파라미터 등의 조건에 따라 변환할지 말지를 결정한다거나, 이런 조건을 변환 로직에 참고할 필요가 있을 때.
* 애노테이션 정보를 활용한 HTTP 요청과 모델 필드 바인딩: AnnotationFormatterFactory와 Formatter
    - @NumberFormat, @DateTimeFormat처럼 필드에 부여하는 애노테이션 정보를 이용해서 변환기능을 지원하려면
* 특정 필드에만 적용되는 변환 기능: PropertyEditor
    - 단순 필드 조건만 판별하는 경우라면 프로퍼티 에디터로 만드는 편이 나음.
    - 이런 경우 WedBindingInitializer를 통해 모든 컨트롤러에 일괄적용하는 건 바람직하지 못함.
        - PropertyEditorRegister를 통해 하나 이상의 프로퍼티 에디터 등록 작업을 여러 컨트롤러에서 한번에 등록하도록 하자.
* 커스텀 프로퍼티 에디터가 가장 우선순위가 높고, 그다음이 컨버전 서비스의 컨버터, 마지막으로 스프링 내장 디폴트 프로퍼티 에디터 순
* WebBindingInitializer로 일괄 적용한 컨버전 서비스나 프로퍼티 에디터는 @InitBinder로 직접 등록한 프로퍼티 에디터나 컨버전 서비스보다 우선순위가 뒤진다.


### 4.3.3. WebDataBinder
* HTTP 요청정보를로 등로 컨트롤러 메소드의 파라미터나 모델의 프로퍼티에 바인딩할 때 사용되는 바인딩 오브젝트

#### 자주 활용되는 설정 항목
* allowedFields, disallowedFields
    - 바인딩이 허용된/허용되지 않는 필드목록 지정
* requiredFields
    - 컨트롤러 자신이 필요로 하는 파라미터가 다 들어왔는지 확인 
* fieldMarker 
    - HTML 폼의 체크박스는 체크를 하지 않으면 아예 해당 필드의 값을 서버로 전송하지 않음.
    - 해결책은 바인더에게 특정 필드를 체크박스라고 알려줘서, 해당 필드의 HTTP 요청 파라미터가 전달되지 않으면 체크박스를 해제했다는 것으로 판단하게 해줘야함.
    - 스프링은 이를 위해 marker 히든 필드를 추가하는 방식 사용.
    - 필드의 이름 앞에 붙는 언더바 같은 것을 필드마커라고 함.
    - 스프링은 필드마커가 있는 HTTP 파라미터를 발견하면 필드마커를 제외한 이름의 필드가 존재한다고 생각함.
        - 없으면 체크박스를 해제했다고 인식해 그 이름에 해당하는 프로퍼티 값을 리셋해줌.
    - 스프링의 form 태그를 사용하면 checkbox를 위한 필드마커를 자동으로 추가해주므로 신경쓰지 않아도 됨.
```html
<input type="checkbox" name="autoLogin" />
<input type="hidden" name="_autoLogin" value="on" /> <!-- marker 필드 -->
```
* fieldDefaultPrefix
    - 히든 필드를 이용해서 체크박스에 대한 디폴트 값을 지정하는데 사용
```html
<input type="checkbox" name="type" value="admin" />
<input type="hidden" name="!type" value="member" /><!-- type 체크박스가 해제되었을 떄 디폴트로 member가 들어감. -->
```

### 4.3.4. Validator와 BindingResult, Errors
* @ModelAttribute로 지정된 모델 오브젝트의 바인딩 작업이 실패로 끝나는 경우는 두가지 
    1. 타입 변환이 불가능한 경우
    2. validator를 통한 검사를 통과하지 못한 경우.
* 스프링은 검증 과정에서 사용할 수 있는 Validator라는 인터페이스 제공.
    - Validator를 통한 검증 결과는 BindingResult를 통해 확인할 수 있음.(BindingResult는 Errors의 서브 인터페이스)


#### Validator
* 스프링에서 범용적으로 사용할 수 있는 오브젝트 검증기를 정의할 수 있는 API
* Validator는 보통 미리 정해진 단순 조건을 이용해 검증하는데 사용됨.(필수 값 입력여부, 값의 범위, 길이, 형식등.)
* Validator는 싱글톤 빈으로 등록돼서 사용할 수 있음.

> `검증 로직은 어느 계층의 로직인가?`라는 논쟁
> 여러 의견이 있지만 검증 로직은 특정 계층에 종속되기보단 도메인 오브젝트처럼 독립적으로 만드는 것이 좋음.

##### 스프링의서의 Validator 적용 방법
1. 컨트롤러 메소드 내의 코드
    - Validator를 빈으로 등록해서 컨트롤러에서 빈 오브젝트를 주입받아 각 메소드에서 필요에 따라 직접 validate() 메소드를 호출해서 검증 작업 진행.
2. @Valid를 이용한 자동검증(JS-303(Bean VAlidation)의 @javax.validation.Valid 이노테이션)
    - validate()를 사용하는 대신, 바인딩 과정에서 자동으로 검증이 일어나도록.
    - WebDataBinder에서 Validator 타입 검증용 오브젝트도 설정가능
3. 서비스 계층 오브젝트에서의 검증 
4. 서비스 계층을 활용하는 Validator
    - 빈으로 등록한 Validator에서 서비스 빈을 주입받아 활용

#### JSR-303 빈 검증 기능 
* 스프링에서는 LocalValidatorFactoryBean을 이용해서 JSR-303의 검증 기능 사용 가능 
* LocalValidatorFactoryBean은 JSR-303의 검증 기능을 스프링의 Validator처럼 사용할 수 있게 해주는 일종의 어댑터
* JSR-303 빈검증 기술의 특징은 모델 오브젝트의 필드에 달린 제약조건 애노테이션을 이용해 검증을 진행할 수 있다는 점.
* 제약조건 애노테이션을 통해 검증을 하려면 LocalValidatorFactoryBean을 빈으로 등록해줘야 함.
    - 등록된 빈은 DI를 통해서 주입 받아서 validate()를 직접 호출해도되고, @InitBinder를 통해 WebDataBinder에 세팅해 자동 바인딩시 검증을 할 수 있음.
    - e.g. @NotNull, @Size, @Min. 
        - 이런 애노테이션같은 제약조건은 JSR-303 ConstraintValidator를 만들고 이를 애노테이션에서 이용할 수 있음.

#### BindingResult와 MessageCodeResolver
* BindingResult는 모델 바인딩 작업중 나타는 타입 변환 에러와 검증 오류 모두를 저장.
* 스프링은 기본적으로 messages.properties와 같은 프로퍼티 파일에 담긴 메시지를 가져와 에러 메시지로 활용.
    - 프로퍼티 파일에 영어를 제외하고는 유니코드로 변환해 넣어야함.(WHY?)
* 에러코드가 BindingResult에 등록되면 MessageCodeResolver는 이를 확장해서 메시지 키 후보를 생성함.
    - 그중에서 우선순위에 따라 선정.

#### MessageSource
* MessageCodeResolver를 통해 만들어진 후보 메시지 코드들은 MessageSourceResolver를 한번 더 거쳐서 최종적인 메시지로 만들어짐.
* 스프링의 MessageSource 구현은 두가지 종류가 있음.
    1. StaticMessageSource : 코드로 메시지를 등록할 수 있음.
    2. ResourceBundleMessageSource : messages.properties 리소스 번들 방식을 사용.
* MessageSource가 최종 메시지를 만들기 위해 사용하는 정보.
    1. 코드 : BindingResult/Errors의 에러코드를 DefaultMessageCodeConverter를 이용해 필드와 오브젝트 이름의 조합으로 만들어낸 메시지 키 후보값.
    2. 메시지 파라미터 배열 : messages.properties의 메시지에 {0}과 같은 파라미터 값을 지정.
    3. 디폴트 메시지 : 후보 메시지 코드에서 찾을 수 없을 때 선택하는 메시지
    4. 지역정보 : LocaleResolver에 의해 결정된 현재의 지역정보.
        - 지역에 따른 다른 메시지 프로퍼티를 사용가능.
        - 해당 지역에 해당하는 메시지 파일 이 없다면 디폴트인 messages.properties 가 사용됨.
* MessageSource는 디폴트로 등록되지 않음.


<hr>



