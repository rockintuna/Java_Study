# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 48. 인증     

POST /session
session이라는 resource를 구현할것이다     
session이 올바르게 만들어졌다는 정보를 유지하기위해 accessToken을 사용한다   
DTO(Data Transfer Object)를 통해 데이터 교환
SessionResponseDto session 결과(accessToken 등) 교환
SessionRequestDto session을 만들기위한 request 데이터 교환

SessionResponseDto
```
//SessionDTO는 accessToken등 그 외 데이터를 처리하는 Class
@Data //DTO는 순수한 data객체이기 때문에 @Data 사용하면 유용하다
@Builder
public class SessionResponseDto {

    private String accessToken;

}
```

SessionRequestDto
```
@Data
public class SessionRequestDto {

    private String email;
    private String password;

}
```

Session Controller
```
public class SessionController {

    @Autowired
    private UserService userService;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource  //리소스 확인용 requestDTO 
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String password = resource.getPassword();
        User user = userService.authenticate(email,password);

        String accessToken = user.getAccessToken();

        String url = "/session";
        return ResponseEntity.created(new URI(url)).body(
                SessionResponseDto.builder()  //responseDTO 객체를 전달  
                        .accessToken(accessToken)
                        .build());
    }
}
```

User 도메인 모델에 토큰 확인 기능 추가
```
    //@JsonIgnore : requestedbody에 password가 없는경우 그냥 무시할수도 있음
    public String getAccessToken() {
        if (password == null) {
            return "";
        }
        return password.substring(0, 10);
    }
```

JPA - User Repository findByEmail(email) 추가    
```
    Optional<User> findByEmail(String email);
```

Service에 인가 기능 추가   
```
    public User authenticate(String email, String password) {
        //email확인 후 없는 경우(아마도 비어있는 Optinal) 예외 처리
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistedException(email)); 

        //저장되어있는 암호화된 password와 비교하여 일치하지 않는 경우 예외처리
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }

        return user;
    }
```

SpringSecurityConfig에 
encode(), matches() 사용을 위한 PasswordEncoder 종속 추가
```
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

```

Session 예외처리를 위한 Advice 추가
```
@ControllerAdvice
public class SessionErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordWrongException.class)
    public String handlePasswordWrong() {
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotExistedException.class)
    public String handleEmailNotExisted() {
        return "{}";
    }
}
```

사용자 예외 2가지 추가  
  
패스워드 불일치 예외
```
package kr.co.fastcampus.eatgo.application;

public class PasswordWrongException extends RuntimeException{

    PasswordWrongException() {
        super("Password is wrong.");
    }
}
```
존재하지 않는 메일 주소 예외
```
package kr.co.fastcampus.eatgo.application;

public class EmailNotExistedException extends RuntimeException{

    EmailNotExistedException(String email) {
        super("Email is not registered : "+email);
    }
}
```
    
    