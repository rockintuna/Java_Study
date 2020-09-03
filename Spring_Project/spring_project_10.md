## Spring MVC 설정

### Spring MVC Bean 설정

DispatcherServlet의 기본 전략에만 의존하기에는 확장성이 저하될 수 있기 때문에 추가적으로 설정할 수 있어야 한다.  

@Configuration을 사용한 자바 파일에 스프링 MVC 구성요소를 직접 빈으로 등록할 수 있다.

```
@Configuration
public class WebConfig {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
```

### @EnableWebMvc
애노테이션 기반 스프링 MVC를 사용할 때 편리한 웹 MVC 기본 설정  
@EnableWebMvc를 사용하면 DelegatingWebMvcConfiguration.class를 import하여 
애노테이션 기반 스프링 MVC 환경에 적합한 여러가지 설정 및 전략들을 추가하고 변경한다.

```
@Configuration
@EnableWebMvc
public class WebConfig {

}
```

@EnableWebMvc를 사용할 때는 ApplicationContext에 Servlet Context가 등록되어야 한다.
```
public class WebApplication implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setServletContext(servletContext);
        context.register(WebConfig.class);
        context.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic app = servletContext.addServlet("app", dispatcherServlet);
        app.addMapping("/app/*");
    }
}
```


### WebMvcConfigurer 설정
특히, DelegatingWebMvcConfiguration을 사용하면 Formatter나 Interceptor를 추가하는 등 추가적인 설정이 더 쉬워진다는 장점도 있다.  
WebMvcConfigurer는 @EnableWebMvc가 제공하는 빈을 커스터마이징할 수 있는 기능을 제공하는 인터페이스이다.  

```
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF",".jsp");
    }
}
```

새로운 빈을 추가할 필요 없이 @EnableWebMvc가 제공하는 빈에 추가 설정을 한다는 장점이 있다.  

#### 포매터 설정
Formatter를 통해 문자열을 객체로 또는 객체를 문자열로 변환할 수 있다.  

```
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") Person person) {
        return "hello" + person.getName();
    }
```
예를들어 위와 같은 get 메서드에서 url의 name 문자열을 Person이라는 객체로 받고 싶을 때 포매터를 사용한다.  

Formatter 인터페이스는 사실 Printer 인터페이스와 Parser 인터페이스를 합친것이다.  
Printer : 객체를 문자열로 어떻게 보여줄 것인가  
Parser : 문자열을 객체로 어떻게 변환할 것인가  
각각에 해당하는 메서드를 구현해주면 된다. (print(), parse())

```
public class PersonFormatter implements Formatter<Person> {
    @Override
    public Person parse(String s, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(s);
        return person;
    }

    @Override
    public String print(Person person, Locale locale) {
        return person.toString();
    }
}
```

포매터를 등록하는 방법
 - WebMvcConfigurer의 addFormatters 메서드 정의
```
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PersonFormatter());
    }
}
```

 - 포매터를 빈으로 등록(스프링 부트에서만 가능) 
```
@Component
public class PersonFormatter implements Formatter<Person> {
    @Override
    public Person parse(String s, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(s);
        return person;
    }

    @Override
    public String print(Person person, Locale locale) {
        return person.getName();
    }
}
```

#### 핸들러 인터셉터
HandlerInterceptor
 : 핸들러 맵핑에 설정할 수 있는 인터셉터  
 핸들러 실행 전후와 완료(랜더링까지)시점에 부가 작업을 하고싶은 경우 사용하거나
 여러 핸들러에서 반복적으로 사용되는 코드를 줄이고 싶을 때 사용한다.
 
순서 
 - preHandle(request, response, handler)
 : 핸들러가 실행되기 전에 호출 됨, 핸들러에 대한 정보를 사용할 수 있기 때문에 서블릿 필터보다 더 세밀한 로직을 구현할 수 있다.  
 boolean을 return하는데, 다음 인터셉터나 핸들러로 전달할지 아니면 끝인지를 알린다.  
 - 요청 처리
 - postHandle(request, response, modelAndView)
 : 핸들러 실행이 끝나고 아직 뷰를 랜더링 하기 전에 호출 됨, 뷰에 전달할 추가적이거나 여러 핸들러에 공통적인 모델 정보를 담는데 사용할 수 있다.  
 여러 핸들러 인터셉터들이 있으면 역순으로 호출된다.  
 비동기적인 요청 처리 시에는 호출되지 않는다.
 - 뷰 랜더링
 - afterCompletion(request, response, handler, ex)
 : preHandle에서 true를 리턴 한 경우, 요청 처리가 완전히 끝난 뒤(뷰 랜더링 이후) 호출됨
 여러 핸들러 인터셉터들이 있으면 역순으로 호출된다.  
 비동기적인 요청 처리 시에는 호출되지 않는다.
 
핸들러 인터셉터 구현하기
```
public class GreetingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle 1");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle 1");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 1");
    }
}
```

핸들러 인터셉터 등록하기
```
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreetingInterceptor());
    }
}
```

#### 리소스 핸들러
이미지, 자바스크립트, css, html 등 정적인 리소스를 처리하는 핸들러 등록 방법

디폴트(Default) 서블릿
 : 서블릿 컨테이너가 기본으로 제공하는 서블릿으로 정적인 리소스 처리에 사용  
 
스프링 MVC 리소스 핸들러를 맵핑 등록 할때
 : 가장 낮은 우선순위로 등록, DefaultServletHandlerConfigurer 

리소스 핸들러 설정
 - 어떤 요청 패턴을 지원할 것인가
 - 어디서 리소스를 찾을 것인가
 - 캐싱
 - ResourceResolver : 요청에 해당하는 리소스를 찾는 전략.  
 캐싱, 인코딩, WebJar, ...
 - ResourceTransformer : 응답으로 보낼 리소스를 수정하는 전략.  
 캐싱, CSS 링크, HTML 5 AppCache, ...
```
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mobile/**")
                .addResourceLocations("classpath:/mobile/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
    }
```
스프링 부트에서는 기본적으로 정적 리소스 핸들러와 캐싱을 제공한다.(resources/static)  
 
#### HTTP 메시지 컨버터
요청 본문에서 메시지를 읽어들이거나(@RequestBody), 응답 본문에 메시지를 작성(@ResponseBody)할 때 사용한다.  

HTTP 메서드에서 @RequestBody 어노테이션을 통해 json 등 contentType을 특정 타입이나 객체로 받을 수 있게 해준다.  

메시지 컨버터 설정 방법  
 - configureMessageConverters 메서드 사용
```
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        
    }
```
이렇게 컨버터를 추가하면 기본으로 제공되는 메시지 컨버터들이 무시된다.

 - extendMessageConverters 메서드 사용
```
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                
    }
```
기본으로 제공되는 메시지 컨버터들에 더해 새로운 컨버터가 추가된다.  

 - 의존성 추가로 컨버터 등록  
메이븐 또는 그래들 설정에서 의존성을 추가하면 WebMvcConfigurationSupport 클래스에 의해 그에 맞는 컨버터가 자동으로 등록된다.

메시지 컨버터 등록하기
 - Json 용 메시지컨버터
    - 스프링 부트를 사용하지 않는 경우  
    사용하고 싶은 Json 라이브러리를 의존성으로 추가
    - 스프링 부트를 사용하는 경우   
    기본적으로 Jackson2JSON이 의존성에 들어있다.  
    즉, Json 용 HTTP 메시지 컨버터가 기본으로 등록되어 있다.  

 - XML 용 메시지컨버터
    OXM 라이브러리 중 스프링이 지원하는 의존성 추가(JacksonXML, JAXB)  
    스프링 부트를 사용하더라도 기본적으로 XML 의존성을 추가해주지 않는다.  
    
JAXB 의존성 추가
```
        <dependency>
            <groupId>javax.xml.bind</groupId>
            <artifactId>jaxb-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-oxm</artifactId>
            <version>${spring-framework.version}</version>
        </dependency>
```

Marshaller 빈 추가
XmlRootElement를 스캔할 수 있도록 setPackagesToScan 설정 필요
```
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(Person.class.getPackageName());
        return jaxb2Marshaller;
    }
```

객체를 Json String으로 만들기
```
    @Autowired
    ObjectMapper objectmapper;

    @Test
    public void jsonString() throws JsonProcessingException {
        Person person = new Person();
        person.setId(99L);
        person.setName("이정인");

        String jsonString = objectMapper.writeValueAsString(person);

        ~
    }
```

객체를 XML String으로 만들기
```
    @Autowired
    Marshaller marshaller;

    @Test
    public void xmlString() throws JAXBException {
        Person person = new Person();
        person.setId(99L);
        person.setName("이정인");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();
   
        ~
    }
```

테스트에서는 jsonPath 또는 xPath로 응답 본문을 체크할 수 있다.

#### 기타 설정
 - 기본 제공 외의 ReturnValueHandler 추가 
 - 기본 제공 외의 ArgumentResolver 추가
 - 뷰 컨트롤러 설정, 단순하게 요청 URL을 특정 뷰로 연결하고 싶을 때 사용
 - 비동기 설정, 비동기 요청 처리에 사용할 타임아웃이나 TaskExecutor 설정
 - Content Negotiation 설정, 요청 본문 또는 응답 본문을 어떤 타입으로 보내야 하는지의 전략 설정
 - CORS 설정, Cross Origin 요청 처리 설정, 같은 도메인에서 온 요청이 아니더라도 처리를 허용하고 싶을 때
 - 뷰 리졸버 설정, 핸들러에서 리턴하는 뷰 이름에 해당하는 문자열을 View 인스턴스로 바꿔줄 뷰 리졸버 설정 

### 스프링 부트의 스프링 MVC 설정
추가적인 전략이 자동으로 DispatcherServlet에 추가된다.  

 - handlerMapping  
    - resourceHandlerMapping(SimpleUrlHandlerMapping) 추가, 정적 resource 지원, 캐싱관련 정보가 응답 헤더에 추가됨
    - welcomePageHandlerMapping(WelcomePageHandlerMapping) 추가, 인덱스 페이지 지원  

 - viewResolver  
    - ContentNegotiatingViewResolver, 다른 viewResolver에 작업 위임하는 역할  

스프링 부트에서는 spring-boot-autoconfigure jar의 META-INF/spring-factories 파일에서 다양한 자동 설정을 확인할 수 있고
그 중 DispatcherServletAutoConfiguration은 DispatcherServlet을 자동으로 만드는 코드,
WebMvcAutoConfiguration은 Spring MVC를 자동으로 설정하는 코드이다.  

#### 스프링 부트의 프로퍼티 변경
application.properties

#### 스프링 부트가 지원하는 스프링 MVC 설정을 사용하면서 추가 설정을 원하는 경우
@Configuration + implements WebMvcConfigurer

#### 스프링 부트가 지원하는 스프링 MVC 설정을 사용하고 싶지 않은 경우
@Configuration + @EnableWebMvc  

@EnableWebMvc에서 사용하는 DelegatingWebMvcConfiguration.class는 WebMvcConfigurationSupport.class를 상속받는데,
스프링 부트는 WebMvcConfigurationSupport.class 타입의 빈이 있으면 스프링 MVC 자동 설정을 스킵하기 때문이다.  
(추가로 커스터마이징까지 하려면 @Configuration + implements WebMvcConfigurer + @EnableWebMvc)

참고로 스프링 부트에서는 Formatter나 Converter를 추가할 때 빈으로만 등록하면 알아서 추가해준다.  

