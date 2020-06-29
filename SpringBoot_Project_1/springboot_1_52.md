# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 52. JWT  

JWT (Json Web Tokens)   
Json 포맷을 이용해 웹에서 활용할 수 있는 Access Token을 다루는 표준  

3 Parts
Header - Type, 알고리즘  
Payload - 실제 데이터(Claims)  
Signature - 위변조 서명  

Base64 URL Encoding을 통해 Json 포맷을 문자열로 변경한다.  
문자열로 변경되면 Header.Payload.Signature 형식으로 된다.       
위변조 되지 않았음을 증명하기 위해 HS256 알고리즘을 이용할 것이다.  

