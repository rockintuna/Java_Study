## 스프링 부트 활용 (기술 연동)

### 스프링 웹 MVC

스프링 부트 MVC는 자동 설정(WebMvcAutoConfiguration)을 통해 여러 기본 기능을 제공한다.  

스프링 MVC 기능 확장하기
 - @Configuration + WebMvcConfigurer

```
@Configuration
public class WebConfig implements WebMvcConfigurer {
}
```
 
스프링 MVC 기능 재정의하기
 - @Configuration + @EnableWebMvc
 
```
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
}
```

#### HttpMessageConverters
Http 요청 본문을 객체로 변경하거나, 객체를 Http 응답 본문으로 변경할 때 사용한다.  
@RequestBody, @ResponseBody

관련 스프링 부트 자동 설정, HttpMessageConvertersAutoConfiguration에 의해
여러가지 응답 타입과 객체 간의 변환을 담당하는 메시지 컨버터가 자동으로 설정 되어있다.

#### ViewResolver
스프링 부트의 ContentNegotiatingViewResolver는
요청 header의 accept에 해당하는 타입에 따라 서로 다른 응답을 제공하며 
ViewResolver에 작업을 위임한다.

#### 정적 리소스 지원
스프링 부트는 정적 리소스를 맵핑해주는 리소스 핸들러를 기본적으로 제공한다.
(ResourceHttpRequestHandler)

프로퍼티를 통해 추가 설정
spring.mvc.static-path-pattern : 맵핑 패턴 변경
spring.mvc.static-locations : 리소스를 찾는 기본 위치 변경

WebMvcConfigurer의 addResourceHandlers로 커스터마이징
```
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/s/**")
                .addResourceLocations("classpath:/s/")
                .setCachePeriod(20);
    }
```

정적 리소스의 변경 여부에 따라 이미 캐싱되어있는 리소스를 사용하면 http code 304를 리턴한다.

#### 웹 Jar

기본적으로 웹 Jar에 대한 기본 리소스 리졸버를 제공하여 
"/webjars/**" 맵핑. 

webjars-locator-core 의존성을 추가하여 버전을 생략할 수도 있다.

#### index 페이지와 파비콘

웰컴 페이지 : 
기본 리소스 위치에서 index.html/index.템플릿 을 찾아서 사용

파비콘 : 브라우저에서 사용되는 아이콘  
favicon.ico 파일을 리소스 위치에 생성(favicon.io에서 만들 수 있다.)  

#### 템플릿 엔진
스프링 부트가 템플릿 엔진의 자동 설정을 지원
(FreeMarker, Groovy, Thymeleaf, Mustache)  
특히, 서블릿 엔진과 독립적으로 동작하기 때문에 테스트 할 때 좋다.  

JSP는 권장하지 않는다. (JAR 패키징 불가 및 Undertow 미지원 등등)

Thymeleaf 의존성 추가
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```

Thymeleaf view 작성  

name space 추가
```
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

Model Attribute 랜더링 방법
```
${...}
```

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>
<h1 th:text="${name}">Name<h1/>
</body>
</html>
```

#### ExceptionHandler

스프링 부트는 기본적인 예외 처리기를 지원한다.  
 - BasicErrorController : HTML과 Json 응답 지원, ErrorController를 구현하여 커스터마이징 할 수 있다.


커스텀 에러 페이지   
classpath:/static|templetes/error 에 {code}.html 생성

404.html : 404 error 발생 시 이 뷰로 처리  
4xx.html : 400번대 error 발생 시 이 뷰로 처리  
ErrorViewResolver를 구현하면 좀더 구체적으로 커스터마이징 할 수 있다.  

#### Spring HATEOAS
Hypermedia As The Engine Of Application Status
 - 서버 : 현재 리소스와 연관된 링크 정보를 클라이언트에 제공한다.
 - 클라이언트 : 연관된 링크 정보를 바탕으로 리소스에 접근한다.
 - 연관된 링크 정보 : Relation, Hypertext Reference
 
스프링 부트는 HATEOAS에 대한 자동 설정을 지원한다.

의존성 추가
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-hateoas</artifactId>
        </dependency>
```

리소스에 링크 추가하기
```
    @GetMapping("/hello")
    public EntityModel<Sample> hello() {
        Sample sample = new Sample();
        sample.setName("jilee");
        sample.setPrefix("Hi");

        //hateoas.EntityModel을 이용한 링크 정보 (rel:self) 추가
        EntityModel<Sample> sampleEntityModel = new EntityModel<>(sample);
        sampleEntityModel.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());

        return sampleEntityModel;
    }
```

#### CORS
Cross-Origin Resource Sharing : 
서로 다른 Origin 사이에서 Resource를 공유하기위한 표준 기술 (<-> SOP(기본))

Origin ? 
 - URL 스키마 (http, https)
 - hostname
 - 포트
 
스프링 부트에서 제공하는 @CrossOrigin 애노테이션을 이용할 수 있다.  
@Controller 또는 @RequestMapping에 추가하여 사용하거나,
WebMvcConfigurer에서 글로벌 설정한다.  

모든 컨트롤러에 대한 CORS 설정
```
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:18080");
    }
```

### 스프링 데이터

#### Spring JDBC
스프링 부트는 Spring JDBC가 classpath에 있으면 자동 설정에 의하여 빈을 설정한다.  
DataSource, JdbcTemplete

의존성 추가
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
```

DataSource는 DB에 access하거나 DB의 메타데이터를 확인할 수 있다.  
```
@Component
public class DataTestRunner implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(Connection connection = dataSource.getConnection()) {
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            String sql = "create table test (id int, name varchar(30));";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } 
    }
}
```
JdbcTemplete는 SQL을 작성할 때 좀더 간결하고 안전하게 할 수 있다.  

인메모리 지원 DB(H2, HSQL, Derby)가 의존성에 존재하고 별도의 DataSource 설정을 하지 않았을 때, 
스프링 부트는 자동 설정에 의해 embeded 인메모리 DB를 사용한다.

#### DBCP
DataBase Connection Pool

스프링 부트는 기본적으로 HikariCP DBCP를 지원한다.  
DBCP는 Connection에 대한 여러가지 설정을 할 수 있다. (autoCommit, connectionTimeout, maximumPoolSize, ...)
spring.datasource.hikari.*
```
spring.datasource.hikari.maximum-pool-size=4
```

#### MySQL

MySQL Connector 의존성 추가
```
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

MySQL Datasource 설정 (application.properties)
```
spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC
spring.datasource.username=todolist
spring.datasource.password=password
```
MySQL은 기본적으로 3306 port를 사용한다.  

#### Spring-Data-JPA
의존성을 추가하면 스프링 부트는 JPA와 관련된 자동 설정을 한다.  

ORM(Object-Relation Mapping) : 객체와 릴레이션을 맵핑할 때 발생하는 개념적 불일치를 해결하는 프레임워크  
JPA(Java Persistence API) : ORM을 위한 Java EE 표준
Hibernate : 대표적인 JPA 구현체

Spring Data JPA : JPA 표준 스팩을 아주 쉽게 사용할 수 있게끔 추상화 함.  
 - Repository 빈 자동 생성
 - 쿼리 메서드 자동 구현
 - @EnableJpaRepository (스프링 부트는 자동으로 설정됨)
 
Spring Data JPA -> JPA -> Hibernate -> Datasource(JDBC)

JPA 의존성 추가
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```

Repository 만들기 (extends JpaRepository<"Entity type","ID type">)
```
public interface UserRepository extends JpaRepository<User,Long> {
}
```

@DataJpaTest : JPA 관련 슬라이싱 테스트, embedded DB만 지원한다.

```
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Test
    public void getMetaInfo() {
        User user = new User();
        user.setName("tester");
        user.setEmail("tester@email.com");
        user.setPassword("password");

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getName()).isEqualTo("tester");
    }
}
```

#### 데이터베이스 초기화

JPA를 이용한 DB 초기화 관련 프로퍼티
spring.jpa.hibernate.ddl-auto
 - update : 변경된 스키마(alter table)나 추가된 스키마(create table)만 새로 생성, 기존 데이터 유지
 - create : App 실행시 기존 스키마를 지우고 새로 생성
 - create-drop : App 종료시 스키마 제거
 - none : ddl 핸들링 끄기 (아무런 작업도 하지 않음)
 - validate : Entity와 DB의 테이블 사이의 맵핑을 검증 (운영에서 사용)

spring.jpa.generate-ddl
 - false : 초기화 설정 종료
 - true : App 실행시 @Entity에 근거하여 DDL문을 생성하고 실행한다.  
true로 설정해야 ddl-auto 설정이 동작한다.
 
spring.jpa.show-sql=true : SQL 출력

SQL 스크립트를 사용한 DB 초기화
 - schema.sql 또는 schema-${platform}.sql : App 실행시 스키마 작업
 - data.sql 또는 data-${platform}.sql : App 실행시 데이터 작업
 - ${platform} 값은 spring.datasource.platform 으로 설정 가능
