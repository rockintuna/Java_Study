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

### 자동 설정 이해

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



### 내장 웹 서버 이해

### 내장 웹 서버 응용

### 톰캣 HTTP2

### 독립적으로 실행 가능한 JAR