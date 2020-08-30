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

### 스프링 부트의 스프링 MVC 설정
추가적인 전략이 자동으로 DispatcherServlet에 추가된다.  

handlerMapping  
resourceHandlerMapping(SimpleUrlHandlerMapping) 추가, 정적 resource 지원, 캐싱관련 정보가 응답 헤더에 추가됨
welcomePageHandlerMapping(WelcomePageHandlerMapping) 추가, 인덱스 페이지 지원  

viewResolver  
ContentNegotiatingViewResolver, 다른 viewResolver에 작업 위임하는 역할  

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

