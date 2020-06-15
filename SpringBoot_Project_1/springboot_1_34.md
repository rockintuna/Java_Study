# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 34. 진짜 영속화     

database(h2)를 이제부터 file로 생성할 예정이다.  
file을 사용하게 되면 test에 실행되는 내용도 file에 작성될 수 있기때문에
test, 개발, 서비스 등 다른 영역에서 각각 다른 설정으로 처리될 수 있도록 profiles를 사용한다.    

eatgo-admin-api & eatgo-customer-api
resource에 application.yml 파일 추가
```
##spring:
##  key: value
spring:
  datasource:
    url: jdbc:h2:~/data/eatgo
  jpa:
    hibernate:
      ddl-auto: update
      
---

spring:
  profiles: test
  datasource:
    url: jdbc:h2:mem:test
```

여러개의 설정을 나누려면 '---'     
SPRING_PROFILES_ACTIVE=test 일때, profiles: test의 설정이 적용된다.   
ex) SPRING_PROFILES_ACTIVE=test ./gradlew test  
intellij에서는 Test edit configuration에서 Environment value에 추가해주면 된다.
    
      