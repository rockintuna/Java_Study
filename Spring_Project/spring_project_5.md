## Resource / Validation

###Resource 추상화
Spring에서는 java.net.URL을 추상화한 Resource 가 있다.  

추상화한 이유...  
Resource는 classpath 기준으로 읽어올 수 있다.  
ServletContext를 기준으로 읽어올 수 없다.  
구현이 복잡하고 편의성 메소드가 부족하다.  

구현체...  
UrlResource  
ClassPathResource (classpath: 접두어 지원)  
FileSystemResource  
ServletContextResource : 웹 어플리케이션 루트에서 상대 경로로 리소스를 찾는다.  

Resource의 타입은 ApplicationContext의 타입에 따라 결정된다.  
ex)
 - ClassPathXmlApplicationContext -> ClassPathResource
 - FileSystemXmlApplicationContext -> FileSystemResource
 - WebXmlApplicationContext -> ServletContextResource
 
ApplicationContext 타입과 무관하게 Resource 타입을 정하려면
java.net.URL 접두어를 사용한다. (classpath:, file:)

접두어가 있으면 가시성이 좋아지기 때문에 접두어를 쓰는 것이 좋다.
(ApplicationContext 타입을 모르면 복잡해지기 때문에)

###Validation 추상화

애플리케이션에서 사용하는 객체들을 검증할때 사용하는 인터페이스.  

Validation 인터페이스는 두개의 메소드를 구현해야 한다.
boolean supports(Class clazz) : 검증할 Class가 Validator가 지원하는지 확인하는 메소드
void validate(Object target, Errors errors) : 실제 검증 로직을 구현 (ValidationUtils를 사용)

```
public class EventValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace
            (errors, "title", "NotEmpty", "Empty title is not allowed.");
    }
```

한편, 스프링 부트에서는 간단한 검증은 Validator를 따로 작성하지 않아도
LocalValidatorFactoryBean이 빈으로 등록되어 있다.
    
    