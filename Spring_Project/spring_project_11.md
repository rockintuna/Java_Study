## 스프링 부트에서 JSP 사용하기

### 의존성 추가 (maven)
jstl, jsp 사용을 위한 의존성 추가가 필요하다.  
```
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>
```

### WAR 프로젝트
스프링 부트 프로젝트에서 jsp를 사용하려면 WAR 패키지 설정으로 생성해야 한다.  
WAR 패키지 설정의 스프링 부트 프로젝트의 특징은 @SpringBootApplication 클래스 외에
WebApplicationInitializer를 구현한 ServletInitializer 클래스가 웹서버 배포를 위해 추가로 생성된다.

```
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoJspApplication.class);
    }

}
```

### Controller 생성
메서드의 리턴(String)으로 jsp view를 찾을 수 있도록 생성
```
@Controller
public class EventController {

    @GetMapping("/events")
    public String getEvents(Model model) {
        Event event1 = new Event();
        event1.setName("study 1");
        event1.setStart(LocalDate.of(2020,10,10));
        Event event2 = new Event();
        event2.setName("study 2");
        event2.setStart(LocalDate.of(2020,10,20));

        List<Event> events = List.of(event1,event2);

        model.addAttribute("events",events);
        model.addAttribute("message","Study Harder");

        return "events/list";
    }
}
```

### jsp view 생성 
jsp view는 webapp 디렉토리에 있어야하며 스프링 부트가 이 디렉토리를 미리 만들어주지 않기 때문에 직접 생성해야 한다.  
webapp/WEB-INF/jsp/events/list.jsp  
태그 선언을 통해 core 라이브러리 사용

```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Events</title>
</head>
<body>
    <h1>이벤트 목록</h1>
    <h2>${message}</h2>
    <table>
        <tr>
            <th>이름</th>
            <th>시작</th>
        </tr>
        <c:forEach items="${events}" var="event">
            <tr>
                <td>${event.name}</td>
                <td>${event.start}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
```

### application.properties 설정
viewResolver가 view를 찾을 수 있도록 prefix/suffix 설정
```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

### 패키징
스프링 부트 애플리케이션은 Maven이 따로 설치되어있지 않더라도 mvnw 명령을 통해 
Maven으로 빌드할 수 있다.
```
./mvnw package
```

빌드가 완료되면 WAR 파일이 생성된다. (target/???-0.0.1-SNAPSHOT.war)

### 실행
#### java -jar
WAR파일도 java -jar 를 통해 독립적으로 실행할 수 있다.
```
java -jar target/*-0.0.1-SNAPSHOT.war
```
@SpringBootApplication 클래스의 SpringApplication.run 이 사용되며,
스프링 부트 애플리케이션의 내장 톰캣이 사용된다.

#### 서블릿 컨테이너에 배포하는 경우
ServletInitializer 클래스가 있기 때문에 톰캣 등 웹서버에 배포할 수 있다.
SpringBootServletInitializer를 사용하여 실행된다.
내장 톰캣을 사용하는게 아니고 별개의 웹서버에 서블릿을 등록하여 실행된다.

참고로, 스프링 부트는 몇가지 제약 사항때문에 jsp 사용을 피하길 권고한다.
 - JAR 프로젝트로 만들 수 없다.
 - WAR는 java -jar로 실행할 수는 있지만, "실행가능한 jar 파일"은 지원하지 않는다.
 - 언더토우 서블릿 컨테이너는 jsp를 지원하지 않는다.
 - Whitelabel 에러 페이지를 error.jsp로 오버라이딩할 수 없다.

