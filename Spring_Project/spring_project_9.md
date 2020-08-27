## Spring MVC 동작 원리

### Spring 웹 MVC
서블릿 기반의 웹 애플리케이션에서 MVC 패턴을 사용하기 쉽게끔 도와주는 프레임워크  
 
M : 모델, 평범한 자바 POJO 객체(도메인 객체 또는 DTO), 전달하거나 전달받을 데이터를 담은 객체  
V : 뷰, 데이터를 보여주는 역할, HTML, JSP, 타임리프, ...   
C : 컨트롤러, 사용자의 입력을 받아 모델의 데이터를 변경하거나 뷰에 전달하는 역할  

#### MVC 패턴의 장점
 - 동시 다발적 개발 : 백엔드와 프론트엔드에서 독립적으로 개발할 수 있다.  
 - 높은 결합도 : 관련있는 기능을 하나의 컨트롤러로 묶거나 뷰를 그룹화 할 수 있다.  
 - 낮의 의존도 : 모델, 뷰, 컨트롤러는 서로 독립적이다.  
 - 개발 용이성 : 책임이 구분되어 있어서 코드 수정이 편리하다.  
 - 한 모델에 대한 여러 형태의 뷰를 가질 수 있다.  
 
#### MVC 패턴의 단점
 - 코드 네비게이션의 복잡함
 - 코드 일관성 유지에 노력이 필요하다. 
 - 높은 학습 곡선
 
### 서블릿 애플리케이션 

#### 서블릿(Servlet)
JAVA EE 웹 애플리케이션 개발용 스팩과 API 제공한다.  
요청 당 쓰레드를 사용한다. (요청 당 프로세스에 비해 빠르다.)  
JAVA 기반이기 때문에 플랫폼에 대해 독립적이고 이식성이 좋다. 또한 JAVA가 제공하는 보안 기술을 사용할 수 있다.  

서블릿 클래스 생성
```
public class SimpleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Hello World!!");
    }
}
```

기본적으로 서블릿 애플리케이션의 실행 및 종료는 서블릿 컨테이너에 의해 이루어진다. 

서블릿 엔진 또는 서블릿 컨테이너의 역할(톰캣, 제티, 언더토, ...)  
 : 서블릿 라이프 사이클 관리, 세션 관리, 네트워크 서비스, MIME기반 메시지 인코딩 디코딩, ...

서블릿 라이프 사이클 (서블릿 컨테이너가 관리)
1. 서블릿 인스턴스(HttpServlet을 상속받은 클래스)의 init() 메소드 호출&초기화, 최초 요청 후부터 이 과정을 생략
2. 클라이언트로부터의 요청 처리, 각 요청마다 별도의 쓰레드로 처리되며 서블릿 인스턴스의 service() 메소드 호출  
  이 과정에서 보통 Http Method에 따라 doGet(), doPost() 등의 메소드를 구현하여 처리를 위임한다.  
3. 서블릿 컨테이너의 판단에 따라 해당 서블릿을 메모리에서 내려야 할 시점에 destroy() 호출  

서블릿은 web.xml에 등록된다.
```
    <servlet>
        <servlet-name>simple</servlet-name>
        <servlet-class>kr.co.myapp.web.SimpleServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>simple</servlet-name>
        <url-pattern>/simple</url-pattern>
    </servlet-mapping>
```


#### 서블릿 리스너와 서블릿 필터  

- 서블릿 리스너
: 웹 애플리케이션에서 발생하는 주요 이벤트를 감지하고 각 이벤트에 특별한 작업이 필요한 경우 사용할 수 있다.
  - 서블릿 컨텍스트 수준의 이벤트
  : 컨텍스트 라이프 사이클, 애트리뷰트 변경 이벤트
  - 세션 수준의 이벤트 
  : 세션 라이프 사이클, 애트리뷰트 변경 이벤트
  
서블릿 컨텍스트 : 서블릿들이 공용으로 사용할 자원의 저장소  
  
```
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context Initialized");
        sce.getServletContext().setAttribute("name","jilee");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context Destroied");
    }
}
```

- 서블릿 필터
: 들어온 요청을 서블릿으로 보내기 전, 또는 서블릿이 작성한 응답을 클라이언트로 보내기 전에 특별한 작업이 필요한 경우 사용할 수 있다.  
서블릿 필터는 체인 형태의 구조를 가지고 있다.  

```
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter");
        //doFilter에서는 Chainning을 해줘야한다.
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("Filter destroy");
    }
}
```

리스너와 필터도 서블릿과 마찬가지로 web.xml에 등록되어야 한다.
```
    <listener>
        <listener-class>kr.co.myapp.web.MyListener</listener-class>
    </listener>

    <filter>
        <filter-name>myFilter</filter-name>
        <filter-class>kr.co.myapp.web.MyFilter</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>myFilter</filter-name>
        <servlet-name>simple</servlet-name>
    </filter-mapping>
```

### 서블릿 애플리케이션에서 Spring 연동  
 
#### IoC 컨테이너를 활용하는 방법  

Spring 웹 MVC 의존성 추가
```
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.7.RELEASE</version>
        </dependency>
```

ContextLoaderListener
 : Spring이 제공하는 서블릿 리스너, ApplicationContext를 생성하고 서블릿 컨텍스트에 등록해준다.  
 서블릿 컨텍스트의 라이프 사이클에 맞춰서 Spring이 제공하는 ApplicationContext를 사용할 수 있도록 연동해서 서블릿이 ApplicationContext를 사용할 수 있도록 해준다.
      
```
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
```

ApplicationContext가 있어야 하기 때문에 xml 또는 java를 이용한 Spring 설정 파일이 필요하다.  

```
    //생성할 ApplicationContext 타입 설정
    <context-param>
        <param-name>contextClass</param-name>
        <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
    </context-param>

    //참조할 ApplicationContext java 설정 파일 등록
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>kr.co.myapp.web.AppConfig</param-value>
    </context-param>
```


#### DispatcherServlet을 활용하는 방법
 
DispatcherServlet은 스프링이 제공하는 스프링 MVC의 핵심적인 클래스이며 Front Controller 역할을 한다.  

Front Controller : 다른 Controller로 요청을 Dispatch 해주는 역할을 하는 Controller

DispatcherServlet의 특징은 ApplicationContext를 추가로 생성하며 계층구조를 가지게 되는데,  
이때 기존에 서블릿 컨택스트에 있던 ApplicationContext는 "Root WebApplicationContext",  
DispatcherServlet으로 인해 추가로 생성되어 Root WebApplication을 부모로 가지는 ApplicationContext를 "Servlet WebApplicationContext" 라고 한다.  

Root WebApplicationContext : 다른 서블릿들도 공용으로 사용할 수 있는 부분, 웹과 관련된 빈들을 등록하지 않는다.
주로 Serivce / Repository가 등록됨.

Servlet WebApplicationContext : 해당 DispatcherServlet에서만 한정적으로 사용할 수 있는 부분, 웹 관련 빈들이 등록된다.
주로 Controller / View resolver 가 등록됨 

DispatcherServlet 등록, Servlet WebApplicationContext용 Config 파일이 필요하다.  
(Root 용으로는 웹관련 빈이 제외하도록하고 DispatcherServlet 용으로는 웹관련 빈이 등록되게 하는 등 작업필요)
```
    <servlet>
        <servlet-name>app</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextClass</param-name>
            <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
            <param-name>contextConfigLocation</param-name>
            <param-value>kr.co.myapp.web.WebConfig</param-value>
        </init-param>
    </servlet>

    // /app밑으로의 모든 요청은 DispatcherServlet을 통하도록
    <servlet-mapping>
        <servlet-name>app</servlet-name>
        <url-pattern>/app/*</url-pattern>
    </servlet-mapping>
```

또는 WebApplicationInitializer 인터페이스를 통해 Java 코드로 등록할 수 있다.
```
public class WebApplication implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        context.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic app = servletContext.addServlet("app", dispatcherServlet);
        app.addMapping("/app/*");
    }
}
```

DispatcherServlet을 하나만 사용할 경우는 계층구조를 하지 않아도 된다.
(Servlet WebApplicationContext에 모든 빈을 등록하면 된다.)

참고로 스프링 부트에서는 구조가 완전히 다른데,  
보통 서블릿 컨테이너에 등록되는 웹 애플리케이션에 Spring을 연동하는 반면,  
스프링 부트는 스프링 부트 자바 애플리케이션이 먼저 뜨고 그 안에 내장되어 있는 톰캣이 뜨게된다.  

### DispatcherServlet 동작 원리

#### DispatcherServlet 초기화

 - 특별한 타입의 빈들을 찾거나 기본 전력에 해당하는 빈들을 등록한다.
 - HandlerMapping : 핸들러를 찾아주는 인터페이스
 - HandlerAdapter : 핸들러를 실행하는 인터페이스
 - HandlerExceptionResolver : 예외 처리 인터페이스
 - ViewResolver : 뷰를 찾아주는 인터페이스
 - ...  

#### DispatcherServlet 동작 순서

 1. 요청 분석 (Locale, 테마, 멀티파트 등)
 1. HandlerMapping에 위임하여 요청을 처리할 핸들러를 찾는다.  
 (기본적인 HandlerMapping : BeanNameUrlHandlerMapping, RequestMappingHandlerMapping)
 1. 찾아진 핸들러를 실행할 수 있는 HandlerAdapter를 찾는다.
 1. 찾아낸 HandlerAdapter를 이용해서 핸들러의 응답을 처리한다.
    - 핸들러의 리턴값을 보고 어떻게 처리할지 판단한다.
      - 리턴값의 뷰 이름에 해당하는 뷰를 찾아서(ViewResolver) 모델 데이터를 랜더링한다.
      - @ResponseBody가 있다면 Converter를 사용해서 응답 본문을 만들고(리턴 String을 본문에 넣는 등) ModelAndView는 null이 된다.
 1. 만약 예외가 발생했다면 HandlerExceptionResolver에 요청 처리를 위임한다. 
 1. 최종적으로 응답을 보낸다.
 
#### 커스텀 ViewResolver

DispatcherServlet이 사용하는 여러가지 멀티파트 전략들이 구성될때,  
만약 ApplicationContext에 커스텀하게 특정 전략 타입의 빈으로 등록되어 있다면 해당 전략을 사용하고,  
ApplicationContext에 그런 빈이 없다면 기본적으로 제공되는 전략(defaultStrategies : DispatcherServlet.properties)을 사용하게 된다.  


/WEB-INF 에서 .jsp로 끝나는 뷰를 찾아주는 커스텀 ViewResolver
```
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
```
=> 커스텀을 사용하면 기본 ViewResolver는 추가되지 않는다.

참고로 Strategy interface의 구현체들을 찾아내고 빈으로 등록하는 일련의 과정들은 DispatcherServlet이 초기화될 때의 한번만 일어나기 때문에 이 후 요청에서는 생략된다.  
(서블릿의 라이프 사이클에서 init()이 한번만 수행되는 것과 동일함)

### 스프링 MVC 구성 요소

#### MultipartResolver
파일 업로드 요청 처리에 필요한 인터페이스  
HttpServletRequest를 MultipartHttpServletRequest로 변환해주어 요청이 담고있는 File을 꺼낼 수 있는 API제공  

Spring의 DispatcherServlet에서는 default로 null인데, Spring Boot에서는 StandardServletMultipartResolver가 등록된다.  

#### LocaleResolver
클라이언트의 위치 정보를 파악하는 인터페이스 (요청 분석 단계)  
기본 전략은 요청의 accept-language를 보고 판단한다.  (AcceptHeaderLocaleResolver)

#### ThemeResolver
애플리케이션에 설정된 테마를 파악하고 변경할 수 있는 인터페이스  
Spring MVC의 Theme switch 기능을 담당한다.  
(theme 값에 따라 다른 css를 사용하는 등의 기능 처리)

#### HandlerMapping
요청을 처리할 핸들러를 찾는 인터페이스  
주로 RequestMappingHandlerMapping(annotation 기반)이 사용됨

#### HandlerAdapter
HandlerMapping이 찾아낸 핸들러를 처리하는 인터페이스  
핸들러를 개발자들이 원하는대로 만들 수 있게 해주는 스프링 MVC 확장력의 핵심이다.  
주로 RequestMappingHandlerAdapter(annotation 기반)가 사용됨

#### HandlerExceptionResolver
요청 처리 중 발생한 에러를 처리하는 인터페이스  
주로 ExceptionHandlerExceptionResolver(@ExceptionHandler annotation 기반)가 사용됨

#### RequestToViewNameTranslator
핸들러에서 뷰 이름을 명시적으로 리턴하지 않은 경우, 요청을 기반으로 뷰 이름을 판단하는 인터페이스  

#### ViewResolver
뷰 이름에 해당하는 뷰를 찾아내는 인터페이스  

#### FlashMapManager
FlashMap 인스턴스를 가져오고 저장하는 인터페이스  
FlashMap은 주로 리다이렉션을 사용할 때 요청 매개변수를 사용하지 않고 데이터를 전달하고 정리할 때 사용한다.  
폼서브미션을 방지하기 위한 일종의 패턴  