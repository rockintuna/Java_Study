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

eatgo-customer-api

JwtAuthenticationFilter 클래스 작성
(BasicAuthenticationFilter 클래스를 참조하고 
doFilterInternal 메서드를 오버라이딩)
```
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    //
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain
    ) throws IOException, ServletException {
        Authentication authentication = getAuthentication(request);

        if (authentication != null) {
            //SecurityContext 객체를 통해 인증정보 관리
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }

        chain.doFilter(request,response); //다음 작업으로 연결됨
    }

    //request Header의 Authrization부분(token)으로부터 Claims 객체 생성
    //Claims으로 authentication 객체 생성 및 반환
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token==null) {
            return null;
        }

        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                claims, null); //간단하게 principal로 claims 만 사용
        return authentication;
    }
}
```

SecurityJavaConfig 클래스 변경   
```
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Filter 객체 생성
        Filter filter = new JwtAuthenticationFilter(
                authenticationManager(), jwtUtil());

        
        http
                .formLogin().disable()
                .cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
        //filter 추가, session stateless 처리
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
```

JwtUtil 클래스에 claim을 얻는 메서드 추가  
```
    public Claims getClaims(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(key) //key는 기존 HSkey를 활용
                .parseClaimsJws(token) //Jws : sign이 포함된 Jwt
                .getBody();

        return claims;
    }
```

ReviewController의 POST 메서드  
name을 Claim으로부터 가져오도록 변경  
```
    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();

        Review review = reviewService.addReview(
                restaurantId,
                claims.get("name", String.class),
                resource.getScore(),
                resource.getDescription());

        String url = "/restaurants/"+restaurantId+"/reviews/"+review.getId();
        return ResponseEntity.created(new URI(url))
                .body("{}");

    }
```

test(http)
header의 "Authorization"을 참조하여 인증 및 name 활용

```shell script
$ http POST localhost:8080/restaurants/1/reviews score=3 description="종습니다." "Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiLsnbTsoJXsnbgifQ.wKrAIT-cOCfQwBO_8UkhK_IOy4tl8uuIeBS4nppu_Vw"
HTTP/1.1 201 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Length: 2
Content-Type: application/json;charset=UTF-8
Date: Fri, 03 Jul 2020 11:16:35 GMT
Expires: 0
Keep-Alive: timeout=60
Location: /restaurants/1/reviews/22
Pragma: no-cache
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block

{}

$ http GET localhost:8080/restaurants/1                                                                                     HTTP/1.1 200                                                                               
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Date: Fri, 03 Jul 2020 11:16:37 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block

{
    "address": "서울 마포구",
    "categoryId": 1,
    "id": 1,
    "information": "밥집 in 서울 마포구",
    "menuItems": [
        {
            "id": 2,
            "name": "Kimchi",
            "restaurantId": 1
        },
        {
            "id": 3,
            "name": "Gukbob",
            "restaurantId": 1
        },
        {
            "id": 6,
            "name": "Kimchi",
            "restaurantId": 1
        },
        {
            "id": 7,
            "name": "Rice",
            "restaurantId": 1
        }
    ],
    "name": "밥집",
    "reviews": [
        {
            "description": "Cool",
            "id": 10,
            "name": "jilee",
            "restaurantId": 1,
            "score": 4
        },
        {
            "description": "종습니다.",
            "id": 22,
            "name": "이정인",
            "restaurantId": 1,
            "score": 3
        }
    ]
}

```
    
    