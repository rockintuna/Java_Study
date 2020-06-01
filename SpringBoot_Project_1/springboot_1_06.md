# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 06. REST API   

다양한 환경(Web, Mobile)을 지원하려면,     
서로 다른 Front-end로 구성되어야 한다.
대신 공통으로 사용하는 기능들은 하나의 Back-end를 통해 제공한다.    

이때 Back-end를 만드는 기술이 Rest API.  

REST(REpresentational State Transfer)   
표현 상태 전달, 곧 Resource를 처리하는 방식을 의미한다.

Resource의 처리 방식은 4가지(CRUD)     
CRUD와 http의 표준 4가지 메서드와 연결  
Create > POST   
Read > GET  
Update > PUT/PATCH  
Delete > DELETE     

URI(Uniform Resource Identifier)    
리소스를 지정할 때 사용하는 식별자(resource 식별)     
URL(Uniform Resource Locator)    
리소스를 지정할 때 사용하는 지시자(resource 위치)   

리소스의 분류     
1.Collection    
2.Member    

* Collection : 여러 리소스 목록    
Read(List), Create

* Member : 개별 리소스   
Read(Detail), Update, Delete

Collection 표현 : http://hostname/restorants  
Member표현 : http://hostname/restorants/{id}, {id} : 개별 리소스의 id   

JSON(JavaScript Object Notation)
결과를 받거나 넘길때 사용되는 포맷     
자바 스크립트에서 오브젝를 표현할때 쓰는 방식을 여러 언어 또는 환경에서 표준으로 사용할 수 있음  

JSON 에서 Member 표현
```
{
"id":2019,
"name":"식당",
"address":"골목"
}
```
JSON 에서 Collection 표현
```
[
{
"id":2001,
"name":"오디세이",
"address":"우주"
},
{
"id":2019,
"name":"식당",
"address":"골목"
}
]
```

APIs를 Rest API에 맞춰 정의하기     
가게 목록 얻기(read Collection)    
GET /restaurants    
개별 가게 정보 얻기(read Member)    
GET /restaurants/{id}  
가게 추가(create Collection)    
POST /restaurants   
가게 수정(update Member)    
PATCH /restaurants/{id}     
가게 삭제(delete Member)    
DELETE /restaurants/{id}    

    
