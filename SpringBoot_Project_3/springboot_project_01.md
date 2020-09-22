## 스프링 부트의 원리

### 의존성 관리
spring-boot-parent POM의 부모인 spring-boot-dependencies에는
dependencyManagement로 여러 의존성에 대한 버전을 정해주고 있다.  
이를 통해서 의존성을 추가할 때 버전에 정보를 직접 기입하지 않더라도
사용하는 spring boot의 버전에 맞는 의존성 버전이 선택된다.  

```
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.apache.activemq</groupId>
        <artifactId>activemq-amqp</artifactId>
        <version>${activemq.version}</version>
      </dependency>
  ...
  </dependencyManagement>
```

### 의존성 관리 응용 (Maven)
spring-boot-data-jpa 의존성 추가하기  
버전을 명시하지 않아도 된다.  
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```
만약 버전을 명시하면 spring-boot-dependencies POM의 버전 정보를 무시하고
명시된 버전으로 의존성이 추가된다.  
당연히 spring boot가 관리해주는 의존성이 아닌경우에는
버전까지 명시를 해주는 것이 좋다.  

만약 spring boot가 관리해주는 기본 의존성 버전을 바꾸고 싶은 경우 프로퍼티를 추가할 수 있다.
```
    <properties>
        <spring-framework.version>5.2.6.RELEASE</spring-framework.version>
    </properties>
```

### 자동 설정의 이해

@SpringBootApplication 애노테이션은 크게 3가지 역할을 한다.

 - @SpringBootConfiguration(@Configuration 역할)
 - @ComponentScan
 - @EnableAutoConfiguration

@EnableAutoConfiguration은 @ComponentScan으로 찾아진 빈이 등록된 뒤에
여러가지 필요한 빈들을 추가로 등록한다.  

@EnableAutoConfiguration 애노테이션이 있는
spring-boot-autoconfigure 프로젝트의 META 데이터에는 
spring.factories라는 파일이 존재하는데,  
이 파일을 통해 @EnableAutoConfiguration를 사용했을 때 어떤 @Configuraton 들이 적용될 지 알 수 있다.    

### 자동 설정 만들기

자동 설정을 제공할 패키지를 포함시킬 프로젝트를 생성한다.  

 - Xxx-Spring-Boot-AutoConfigure  
 자동 설정을 등록할 모듈
 - Xxx-Spring-Boot-Starter
 의존성 정의용 모듈 (자동 설정 등록을 여기에해서 하나만 만들수 도 있다.)
 
의존성 추가 및 버전 정보를 가져오기 위해 dependencyManagement 추가
```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure-processor</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.3.3.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

다른 프로젝트에서 사용될 Java 설정 파일을 이 프로젝트에서 작성한다.  

classpath:/META-INF/spring.factories 파일을 만들고 설정 파일을 명시해준다.
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.rockintuna.FishConfiguration
```

다른 프로젝트에서 사용할 수 있도록 로컬 메이븐 저장소에 설치
```
mvc install
```

자동 설정을 사용할 프로젝트에서 의존성 추가
```
        <dependency>
            <groupId>com.rockintuna</groupId>
            <artifactId>rockintuna-spring-boot-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

만약 자동 설정을 사용하는 프로젝트에서 설정을 재정의할 필요가 있을 때는,
설정 파일을 만들 때 Condition으로 조건을 주어야 한다.  
```
@Configuration
public class FishConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Fish fish() {
        Fish fish = new Fish();
        fish.setName("tuna");
        return fish;
    }
}
```
여기서는 @ConditionalOnMissingBean을 적용하지 않으면 다른 프로젝트에서 이 빈을 재정의 할 수 없는데,  
빈 등록 순서가 ComponentScan -> @EnableAutoConfiguration인 것 처럼,  
재정의(ComponentScan)를 마친 후에 @EnableAutoConfiguration에 의해 재정의된 설정이 덮어씌여지기 때문이다.  

빈을 등록하여 설정을 재정의하는 방법이 있지만, 좀더 편하게 재정의할 수 있도록 프로퍼티를 사용할 수 있다.  

자동 설정 프로젝트에 프로퍼티 클래스 추가  
@ConfigurationProperties("{prefix}")
```
@ConfigurationProperties("fish")
public class FishProperties {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

설정에서 해당 프로퍼티를 사용하도록 등록
```
@Configuration
@EnableConfigurationProperties(FishProperties.class)
public class FishConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Fish fish(FishProperties properties) {
        Fish fish = new Fish();
        fish.setName(properties.getName());
        return fish;
    }
}
```

설정을 사용하는 프로젝트에서는 application.properties를 통해 쉽게 재정의할 수 있다.  
{prefix}.{property}={value}
```
fish.name=참치
```

### 내장 웹 서버 이해
스프링부트 자동 설정을 사용하지 않으면 서블릿 웹 서버 사용을 위한 작업이 필요하다.  
(톰캣 객체 생성, 컨택스트와 서블릿 추가 및 맵핑, 등등..)  
스프링부트는 내장 웹 서버를 사용하며 서블릿 컨테이너와 관련된 일련의 설정들이 자동으로 이루어진다.  

 - ServletWebServerFactoryAutoConfiguration  
서블릿 웹 서버 생성 자동 설정
   - ServletWebServerFactoryCustomizer
   웹 서버 커스터마이징
  
 - DispatcherServletAutoConfiguration  
DispatcherServlet 생성 및 서블릿 컨테이너에 등록 자동 설정 

### 내장 웹 서버 응용

#### 다른 서블릿 컨테이너로 변경하기
스프링부트의 기본 서블릿 컨테이너 tomcat 대신 jetty 사용하기

tomcat 의존성 제외하고 jetty 의존성 추가
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

```
```
        <dependency>
            <groupId>org.springframework.boot </groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```


#### 웹 서버 사용하지 않기
웹 어플리케이션 설정 변경
```
    public static void main(String[] args) {
//        SpringApplication.run(SpringBootStartApplication.class, args);
        SpringApplication application = new SpringApplication(SpringBootStartApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

```

또는 프로퍼티를 사용하여 변경
```
spring.main.web-application-type=none
```

#### 포트 변경하기
프로퍼티를 사용하여 변경
```
server.port=8090
```
랜덤 포트 사용하기
```
server.port=0
```
 
### 내장 웹 서버에 HTTPS 또는 HTTP2 적용

#### HTTPS 설정
keytool 명령을 이용한 키스토어 생성
https://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html#CHDBGFHE

```
keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 4000
```

프로퍼티 설정
```
server.ssl.key-store=keystore.p12
server.ssl.key-store-password=123456
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
```

HTTP용 커넥터를 추가하여 HTTP와 같이 사용할 수도 있다.

#### HTTP2 설정
프로퍼티 설정
```
server.http2.enabled=true
```

톰캣 8.5.x 에서는 추가적인 설정이 필요하지만
9.0.x / JDK9 에서는 추가 설정이 필요없다.  
https://docs.spring.io/spring-boot/docs/2.0.0.M6/reference/html/howto-embedded-web-servers.html

### 독립적으로 실행 가능한 JAR
기본적으로 Java에는 Jar에 있는 내장 Jar를 로딩할 수 있는 표준이 없다.  
때문에 과거에는 모든 Jar를 하나로 만드는 Uber Jar를 사용했었는데,
어떤 라이브러리를 쓰는 것인지 불분명해지고 동일한 이름의 파일이 있을 때의 문제가 있었다.  

스프링부트에서는 애플리케이션 클래스와 라이브러리가 구분되어 있으며
패키징 후에 내장 Jar 읽고 실행하는 역할을 하는 클래스가 추가된다.   
spring-boot-maven-plugin이 패키징을 담당한다.  
org.springframework.boot.loader.jar.JarFile (읽기)  
org.springframework.boot.loader.Launcher (실행)  

mvc package 명령어를 통해 실행 가능한 JAR 파일 하나가 생성된다.  
```
mvc package
```
```
java -jar spring-boot-start-0.0.1-SNAPSHOT.jar
```