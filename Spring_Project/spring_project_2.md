# 스프링 프로젝트
### 스프링 프레임워크 실습 및 프로젝트    

#### 02. IoC (Inversion of Control)

Spring의 대표적인 특징 중 하나.

어떤 객체가 사용하는 의존 객체를 직접 만들어서 사용하는게 아니라, 주입(DI)받아서 사용하는 것.  

Spring IoC Container
 : 애플리케이션 컴포넌트의 중앙 저장소.

ApplicationContext
 : IoC 컨테이너의 핵심적인 인터페이스.  
 IoC 컨테이너의 최상위 인터페이스인 BeanFactory를 상속받아 여러 기능이 추가됨.
  - 메시지 소스 처리 (i18n)  
  - 이벤트 발행
  - 리소스 로딩  
 
 IoC 컨테이너에 객체를 생성하고 생성된 객체들(Bean)의 의존성을 관리해준다.  
 이해만 해둘뿐, 딱히 이 인터페이스에 대해 직접 보거나 사용할 일은 없는 듯.  
 ClassPathXmlApplicationContext : XML 설정 파일 사용
 AnnotationConfigApplicationContext : 자바 설정 파일 사용 (@Configuration)
 
Bean
 : Spring IoC 컨테이너가 관리하는 객체.  
Class를 Bean으로 등록하려면 Component Scan을 사용하거나
또는 직접 XML이나 자바 설정 파일(@Configuration)에 등록해야 한다.  

IoC 컨테이너에 등록되는 Bean들의 특징
 - 의존성 관리, 특히 테스트에서 가짜 객체(@Mock)를 활용한 단위테스트에 용이하다.
 - 스코프 : 기본적으로 싱글톤 스코프로 등록이된다. (or 프로토타입)
 싱글톤 스코프로 등록되는 클래스는 애플리케이션이 구동되는 초기에 Bean으로 생성된다.  
 - 라이프사이클 인터페이스
 
Component Scanning 
 : Annotation 처리 프로세서 역할을 하는 @ComponentScan에 의하여
 모든 @Component Annotation이 붙은 Class들을 Bean으로 등록한다.
 
```
@Component
    @Repository
    @Service
    @Controller
```

참고로 @ComponentScan은 기본적으로 이 어노테이션이 붙은 클래스가 위치한 곳 부터 Component Scanning을 실시한다.  
또는 특정 위치에서 부터 Scanning을 하도록 설정할수도 있고 Filter 옵션을 통해 어떤 클래스를 스캔할 지 또는 하지 않을 지 정할 수도 있다.   

직접 등록하기
```
@Configuration
public class Config() {
    @Bean
    public String test() {
        return "test";
    }   
    
    @Bean
    public BookRepository bookRepository() {
        return new BookRepository();
    }

    @Bean
    public BookService bookService {
        return new BookService(bookRespsitory());
    }
}
```

function을 이용한 직접 등록
```
public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(Demospring51Application.class)
        .initializers((ApplicationContextInitializer<GenericApplicationContext>)
            applicationContext -> {
                applicationContext.registerBean(MyBean.class);
            })
        .run(args);
}
```
이 방법은 리플랙션이나 프록시를 사용하지 않기 때문에 애플리케이션 구동 시 성능상의 이점이 있다지만..
ComponentScan을 대체하기에는 쓰기 불편해서 좋은 방법은 아닌 것 같다. 


Bean을 꺼내어 쓰는 방법 
 : @Autowired 또는 @Inject 또는 ApplicationContext.getBean()으로 직접 꺼내기     
Bean으로 등록된 Class만 Bean을 꺼내어 쓸 수 있다.


의존성 주입(Dependency Injection) 
 : @Autowired 또는 @Inject를 사용하는데,
 예외로 생성자가 오직 하나이면서 Bean을 파라미터로 받는다면 @Autowired를 생략하고도 DI를 자동으로 해준다.  
 @Autowired/@Inject를 붙인다면 생성자/필드/Setter에 붙이게 된다.
   
###@Autowired에 대하여...  
어노테이션을 setter에 달려있고 의존성의 Bean이 없을 때, 
setter임에도 불구하고 생각과 달리 다른 경우와 마찬가지로 객체생성이 불가능할 수 있는데
Spring이 해당 의존성을 위해 Bean을 찾기 때문이다.
이를 피하기 위해서는 @Autowired(required = false)로 설정하여 의존성 주입이 안된 상태로 생성할 수 있다.  
필드에 어노테이션을 다는 경우도 비슷함.  

그리고 의존성 타입의 Bean이 여러가지 일 경우에 객체생성이 안될 수 있는데,
이때는 주입받으려는 Bean을 @Primary로 마킹하거나,
```
@Repository @Primary
public class PrimaryBookRepository implements BookRepository {
}
```
해당 타입의 모든 Bean을 주입 받거나
```
@Autowired
List<BookRepository> bookrepositories;
```
@Qualifier("beanName")으로 특정 Bean을 주입받는다.
```
@Autowired @Qualifier("sampleBookRepository")
BookRepository bookrepository;
```

BeanPostProcessor
 : 빈 라이프 사이클 인터페이스이다.
 빈 인스턴스의 생성 전후에 부가적인 작업을 할 수 있다.  
 특히, AutowiredAnnotationBeanPostProcessor의 postProcessorBeforeInitialization 메서드로 인해 
 빈 인스턴스 생성 전에 @Autowired 어노테이션을 처리한다.
 
BeanFactoryPostProcessor
 : 다른 모든 Bean들을 만들기 이전에 수행된다.  
 특히, BeanFactoryPostProcessor를 구현한 ConfigurationClassPostProcessor는 @ComponentScan 어노테이션을 처리한다.  
 
###빈의 스코프에 대하여...  
빈의 스코프는 기본적으로 싱글톤이고, 그 외에 프로토타입이 있다.  
프로토타입
 - Request
 - Session
 - WebSocket
 - ...  

@Component @Scope("prototype")
위의 어노테이션처리된 Bean은 받아 올 때마다 새로운 인스턴스가 생성된다.  

참고로 프로토타입 빈이 싱글톤 빈을 의존하는 것은 문제없지만,
싱글톤 빈이 의존하는 프로토타입 빈은 의도한 것처럼 새로운 인스턴스가 생성되지 않는다는 문제가 있다.  
이 문제를 해결하는 방법으로
 - Proxy mode  
 프로토타입 빈을 Proxy 빈으로 감싸서 다른 빈이 이 프로토타입 빈을 참조할 때 Proxy를 거쳐서 참조하도록
 이때, Proxy 빈은 해당 프로토타입 빈을 상속하며, 참조하는 빈은 Proxy 빈을 참조한다. 
```
##for class
@Component @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
##for interface
@Component @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
```
 - Object-Provider
```
@Autowired
ObjectProvider<Proto> proto;
```
 - Provider

###ApplicationContext의 기능 Environment...  
EnvironmentCapable
 : 프로파일과 프로퍼티를 다루는 인터페이스이다.  

프로파일 
 : 빈들의 그룹, Environment가 활성화할 프로파일을 확인하고 설정한다.  
 환경에따라 사용할 빈을 구분할 때 사용된다. (ex 프로덕션에서는 A 빈, 테스트환경에서는 B 빈을 사용)

프로파일 정의하기
 - 클래스에 정의  
test 프로파일로 애플리케이션을 실행할 때 사용될 Java Configuration
```
@Configuration @Profile("test")
``` 
proc 프로파일이 아닌 애플리케이션을 실행할 때 사용될 Component 
```
@Component @Profile("!proc")
```
 - Bean 생성 메서드에 정의
```
@Bean @Profile("test")
```

@Profile에는 표현식으로 !(not), &(and), |(or) 가능.

프로파일 지정하기
 - JVM 시스템 프로퍼티 [-Dspring.profiles.avtive=”test,A,B,...”]
  == intelliJ의 Run/Debug Configuration의 Active profiles 
 
 - @ActiveProfiles (테스트용)

프로퍼티
 : 다양한 방법으로 정의할 수 있는 설정 값, Environment가 프로퍼티 소스를 설정하고 프로퍼티 값을 가져온다.  
 프로퍼티는 우선순위가 있다.(계층형이다.)
 1.ServletConfig
 1.ServletContext
 1.JNDI (java:comp/env/)
 1.JVM 시스템 프로퍼티 (-Dkey="value")
 1.JVM 시스템 환경 변수 (OS 환경 변수)
 

Environment를 통해 프로퍼티 추가하기
```
@PropertySource("classpath:/app.properties")
```
app.properties 파일을 참조한다.
참고로 스프링부트에서는 application.properties라는 기본 프로퍼티 소스를 지원한다.  

###ApplicationContext의 기능 MessageSource...
MessageSource
 : i18n(국제화) 기능을 제공하는 인터페이스이다.  
 
MessageSource의 getMessage로 참조할 수 있다.
Locale에 따라서 다른 메세지를 보여준다.  
```
    @Autowired
    MessageSource messageSource;
    
    public void run() {
        System.out.println(messageSource.getMessage("code1"), Locale.KOREA);
    }
```
 
스프링부트에서는 messages로 시작하는 소스들을 자동으로 참조한다.
messages.properties
messages_ko_KR.properties

리로딩 기능이 있는 메시지 소스를 @Bean으로 직접 만들어서 참조
```
    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
```

###ApplicationContext의 기능 ApplicationEventPublisher...  
ApplicationEventPublisher
 : 이벤트 프로그래밍에 필요한 인터페이스. 옵저버 패턴의 구현체이다.  
 
spring 4.2 전에는  
이벤트 만들기
ApplicationEvent Class를 상속받아야 한다.
```
public class MyEvent extends ApplicationEvent {
    public MyEvent(Object source) {
        super(source);
    }
}
```

이벤트 핸들러 만들기
ApplicationListener<이벤트> 인터페이스를 구현하고 빈으로 등록되어야 한다.
```
@Component
public class MyEventHandler implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("이벤트 확인");
    }
}
```

spring 4.2 부터는 이벤트나 이벤트 핸들러 생성에서 상속/구현이 빠지고 어노테이션으로 처리한다.
이벤트 만들기
```
public class MyEvent {
    private Object source;

    public MyEvent(Object source) {
        this.source = source;
    }
    
    public Object getSource() {
        return source;
    }
}
```

이벤트 핸들러 만들기
```
@Component
public class MyEventHandler {
    
    @EventListener
    public void handle(MyEvent event) {
        System.out.println("이벤트 확인");
    }
}
```

이벤트 발생시키기
```
    ApplicationEventPublisher.publishEvent(new MyEvent(this));
```

만약 이벤트 핸들러가 여러개라면, Spring은 기본적으로 이벤트를 여러 핸들러로 순차적으로 처리하게 된다.  
이때, 순서를 정하고 싶다면 핸들러에 @Order 어노테이션을 사용한다. 
또는 비동기적(여러 쓰레드)으로 처리하고 싶다면 @Async 사용한다. (Java Configuration File에서는 @EnableAsync 필요)  

Spring에서 제공하는 기본 이벤트
 - ContextRefreshedEvent
 - ContextStartedEvent
 - ContextStoppedEvent
 - ContextClosedEvent
 - RequestHandledEvent

