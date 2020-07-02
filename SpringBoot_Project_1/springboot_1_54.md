# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 52. 인가(Authorization)  

인증은 사용자를 증명하는 단계였다면,
인가는 발급된 access Token을 어떻게 다루고  
어떤 서비스를 사용할 수 있는지 다루는 부분이다.  

Http의 Header를 통해서 Access Token을 전달한다.  
Header의 key와 value는 아래와 같은 쌍으로 전달할것이다.  
Authorization : Bearer  

StateLess   
따로 세션에 대한 정보를 저장하지 않고 받은 Token을 Filter가 계속해서 작업하도록 할 것이다.  

BasicAuthenticationFilter  
Filter 추가로 모든 요청에 대해 JWT Token이 실제로 세팅되었는지 확인하고
Access Token에서 정보를 얻어서 사용자 정보를 활용한다.

UsernamePasswordAuthenticationToken
AuthenticationToken 객체를 활용하여 어떤사용자가 사용중인지 확인   
JWT를 분석하여 내부적으로 사용할 AuthenticationToken을 만들것이다.  

