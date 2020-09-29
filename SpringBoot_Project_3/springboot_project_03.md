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



