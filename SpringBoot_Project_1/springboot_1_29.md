# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 29. 프로젝트 분리    

./gradlew build  
./gradlew assamble  
./gradlew test //전체 테스트 실행  
./gradlew check  

사용자에 따른 API 분리  

기존 settings.gradle
```
rootProject.name = 'eatgo'

include 'eatgo-api'
```
변경 후 settings.gradle  
eatgo-common : 공동으로 사용될 package들 (domain)   
eatgo-admin-api : 가게 관리자용   
eatgo-customer-api : 고객용    
```
rootProject.name = 'eatgo'

include 'eatgo-common'
include 'eatgo-admin-api'
include 'eatgo-customer-api'
```

eatgo-common의 build.gradle 수정  
참고 : https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#packaging-executable-and-normal
```
jar { 
    enabled = true //default가 false이기때문에, false면 import에서 문제 발생  
}

bootJar {
    enabled = false //true면 gradle assamble/build 에서 문제발생 
}
```

eatgo-admin-api, eatgo-customer-api의 build.gradle 수정  
eatgo-common 의존성 추가  
```
dependencies {
	implementation project(':eatgo-common')

    ~
}
```
    
    