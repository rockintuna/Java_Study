## 스프링 부트 활용

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

