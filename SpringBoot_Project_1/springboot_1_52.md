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


eatgo-common-api, build.gradle  
Java JWT library 추가  
```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'com.h2database:h2'
    implementation 'io.jsonwebtoken:jjwt-api:0.10.7'

```


JwtUtil 클래스 추가
```
public class JwtUtil {


    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes()); //secret HS key 생성  
    }

    public String createToken(long userId, String name) {
        String token = Jwts.builder()  
                .claim("userId",userId)  
                .claim("name",name)  
                .signWith(key, SignatureAlgorithm.HS256)  //HS256 알고리즘 사용  
                .compact();

        return token;
    }
}
```

SecurityJavaConfig 클래스에서  
secret 참조   
JwtUtil Bean 설정  
```
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secret);
    }
```

SessionController accessToken 변경  
```
    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String password = resource.getPassword();
        User user = userService.authenticate(email,password);

        String accessToken = jwtUtil.createToken(user.getId(), user.getName());

        String url = "/session";
        return ResponseEntity.created(new URI(url)).body(
                SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }

```

application.yml 파일에  
Signature를 위한 코드 저장  
```
jwt:
  secret: 4abcdef2148724243984789347298634934
```

test
accessToken : Header.Payload.Signature
```shell script
http POST localhost:8080/session email=test.example.com password=test
HTTP/1.1 201 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Date: Mon, 29 Jun 2020 14:37:00 GMT
Expires: 0
Keep-Alive: timeout=60
Location: /session
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block

{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIxLCJuYW1lIjoidGVzdGVyIn0.GXdLiebW_gngO3Tpi8r-MbgCQzCJgxFZg1Kf5ojG0PI"
}
```
  
  