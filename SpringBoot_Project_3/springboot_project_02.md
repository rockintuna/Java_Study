## 스프링 부트 활용 (핵심 기능)

### SpringApplication

 - 기본 로그 레벨은 INFO이다.  
 - 여러 FailureAnalyzer가 등록되어 있어 애플리케이션 에러 출력 효과를 볼 수 있다.  
 - 배너를 변경할 수 있으며(classpath:banner.txt 또는 Banner 객체로 구현) 여러 변수를 사용할 수 있다.  
배너 끄기 
```
app.setBannerMode(Banner.Mode.OFF);
```

 - 스프링부트는 여러가지 ApplicationEvent를 지원한다.  
예를들어 애플리케이션이 시작될 때 또는 시작을 마쳤을 때 등이 이벤트로 등록되어 있다.  
보통 이벤트 핸들러(리스너)는 빈으로 등록하면 되지만,
만약 애플리케이션이 시작되는 이벤트 같이 ApplicationContext가 생성되기 전의 이벤트들의 처리는 직접 SpringApplication에 리스너를 등록해야 한다.  

```
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("Application Starting");
    }
}
```
```
@SpringBootApplication
public class DemoWebMvcApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoWebMvcApplication.class);
        app.addListeners(new SampleListener());
        app.run(args);
    }

}
```

 - WebApplicationType 설정  
WebApplicationType은 기본적으로 Spring MVC를 사용하면 SERVLET,  
Spring WebFlux를 사용하면 REACTIVE,  
둘다 아니면 NONE이 되지만 직접 설정하여 타입을 변경할 수 있다.  
```
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoWebMvcApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
```

 - 애플리케이션 아규먼트 사용하기  
 프로그램 아규먼트(--)가 ApplicationArguments 빈으로 등록된다.  

--var
```
@Component
public class SampleArguments {

    public SampleArguments(ApplicationArguments arguments) {
        System.out.println(arguments.containsOption("var"));
    };
}
```

 - 애플리케이션이 실행된 뒤에 추가로 뭔가 실행하고 싶을 때  
 ApplicationRunner 또는 CommandLineRunner
```
@Component
@Order(1)
public class SampleRunner implements ApplicationRunner {
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(args.containsOption("var"));
    }
}
```
@Order를 사용하여 여러 ApplicationRunner의 순서를 정할 수 있다.  

### 외부 설정
사용할 수 있는 외부 설정
 - properties
 - YAML
 - 환경 변수
 - 커맨드 라인 아규먼트
 
#### Properties
 - 프로퍼티 참조하기
```
@Value("${fish.name}")
private String name;
```
```
@Autowired
Environment environment;

public void getProperties() {
    String name = environment.getProperty("fish.name");
    System.out.println(name);
}
```

 - 프로퍼티 랜덤
```
fish.age = ${random.int}
```

 - 플레이스 홀더
```
fish.name = tuna
fish.fullName = rock in ${fish.name}
```

 - Test용 프로퍼티  
test/resources/application.properties를 만들어 등록하거나
애노테이션 직접 설정 
```
@SpringBootTest(properties = "fish.name = tuna")
```
```
@TestPropertySource(properties = "fish.name = tuna")
```
```
@TestPropertySource(locations = "classpath:/test.properties")
```

 - application.properties 파일 우선순위
1. file:./config/
2. file:./
3. classpath:/config/
4. classpath:/

#### 타입-세이프 프로퍼티 @ConfigurationProperties
 - 여러 프로퍼티를 묶어서 읽어올 수 있다.
 - 빈으로 등록할 수 있다. (스프링부트가 아닌 경우 main 핸들러에서 @EnableConfigurationProperties 설정 필요)
 
@ConfigurationProperties("{prefix}")
```
@Component
@ConfigurationProperties("fish")
public class FishProperties {

    private String name;

    private int age;

    private String fullName;

//  ...
//  getter / setter
//  ...
}
```

메타정보 자동 추가를 위한 의존성
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
```

프로퍼티 빈을 주입받아 프로퍼티 사용
```
@Component
public class SampleRunner implements ApplicationRunner {

    @Autowired
    private FishProperties fishProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(fishProperties.getName());
        System.out.println(fishProperties.getAge());
        System.out.println(fishProperties.getFullName());
    }
}
```

 - 융통성 있는 바인딩  
케밥('_'), 언드스코어('-'), 캐멀 케이스 등 지원  
full_name , full-name, fullName, FULLNAME
```
fish.last_name = tuna
fish.full-name = rock in tuna
```
 - 프로퍼티 타입 컨버젼  
 Spring Framework이 지원하는 컨버터에 의하여 타입 변환을 해준다.  
 특히, Duration 타입은 DurationUnit을 설정 할 수 있는데
 프로퍼티 내에서 DurationUnit을 먼저 설정할 수도 있다. (s seconds, m minutes 등등..)
```
fish.sessionTimeout = 25s
```
```
//@DurationUnit(ChronoUnit.SECONDS)
private Duration sessionTimeout;
```

 - 프로퍼티 값 검증하기
@Validated 
```
@Component
@ConfigurationProperties("fish")
@Validated
public class FishProperties {

    @NotEmpty
    private String name;

```

### 프로파일

@Profile : 
특정 프로파일에서 사용할 @Configuration 또는 @Component를 등록한다.
```
@SpringBootApplication
@Profile("test")
public class TestWebMvcApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoWebMvcApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
```

프로퍼티를 통해 설정할 수 있다.  
spring.profile.active : 어떤 프로파일을 활성화할 것인지 결정한다.  
spring.profile.include : 어떤 프로파일을 추가할 것인지 결정한다.  

application-{profile_name}.properties : 프로파일 용 프로퍼티 파일

### 로깅

대표적으로 두가지 로깅 퍼사드가 있다.  
Commons Logging 와 SLF4j  
로거는 로깅 퍼사드의 구현체이다.(JUL, Log4J2, Logback)

spring framework core에는 Commons Logging이 들어있는데
SLF4j를 사용하려면 여러 의존성 설정이 필요했었다.

Spring 5 부터는 Spring-JCL을 통해 exclusion 없이 로깅 퍼사드를 변경할 수 있게 되었다.  

사실 로거를 JUL이나 Log4J2를 사용하더라도 로깅은 자동으로 SLF4j로 전달되며
결과적으로 spring은 로거로써 Logback을 사용하게 된다.  

#### 스프링 부트 로깅

--debug : 일부 핵심 라이브러리만 디버그 모드로 실행  
--trace : 전부 다 디버그 모드로 실행  

spring.output.ansi.enabled : 컬러 출력  
logging.file 또는 logging.path : 파일 출력  
logging.level.패키지 : 로그 레벨 조정  

로깅 사용 예시
```
@Component
public class SampleRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(SampleRunner.class);

    @Autowired
    private FishProperties fishProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("======================");
        logger.info(fishProperties.getName());
        logger.info(Integer.toString(fishProperties.getAge()));
        logger.info(fishProperties.getFullName());
        logger.info("======================");
    }
}
```

#### 로깅 커스터마이징

커스텀 로그 설정 파일 사용  
Logback : logback-spring.xml
Log4J2 : log4j2-spring.xml
JUL : logging.properties

```
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml"/>
    <logger name="me.rockintuna" level="DEBUG"/>
</configuration>
```

Logback extension

 - 프로파일 사용가능
```
<springProfile name="test">
    <configuration>
        ~~
    </configuration>
</springProfile>
```
 - Environment 프로퍼티 <springProperty>
```
<springProperty scope="context" name="fishName" source="fish.name">
${fishName}
```
 
### 테스트

의존성
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```

#### 테스트 관련 애노테이션

@SpringBootTest : 통합 테스트용 애노테이션  
 - @RunWith(SpringRunner.class)과 함께 사용해야 한다.
 - @SpringBootApplication을 참조자여 자동으로 빈 추가
 - webEnvironment 설정
   - MOCK : mock servlet environment. 내장 톰캣을 구동하지 않는다.
   - RANDOM_PORT, DEFINED_PORT : 내장 톰캣을 사용한다.
   - NONE : 서블릿 환경을 제공하지 않는다.
   
```
@Runwith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class EventApiTest {
}
```

@MockBean : ApplicationContext에 있는 빈을 Mock으로 만든 객체로 교체한다.  
모든 테스트(@Test)마다 자동으로 리셋한다.  
(ex 컨트롤러 테스트에서 서비스는 Mock으로 만들고 싶을 때)

```
    @MockBean
    private EventService mockEventService;

    @Test
    public void getName() throws Exception {
        when(mockEventService.getEventName()).thenReturn("Test Event");
        ~~
    }
```

#### 슬라이스 테스트
Bean Mocking을 최소화하기 위해 레이어 별로 잘라서 테스트를 할 수 있다.  

@JsonTest : Model이 json에 들어갔을때의 상황을 테스트 할 수 있다.  
@WebMvcTest(대상) : 특정 Web관련 컴포넌트 하나만 테스트 할 때 사용한다.  
@WebFluxTest  
@DataJpaTest  
...



