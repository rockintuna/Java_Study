# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 56. 로그인 API 분리  

eatgo-login-api를 새로 만들어 로그인 처리를 할것이다.
이게 가능한 이유는 Stateless이기 때문인데 
사용자의 로그인 등 활동에 대해서는 관리하지 않지만 JWT의 accessToken을 활용할 수 있다.  

eatgo-login-api
UserService에서 인증 부분만 login API로 옮겼다.
```
@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistedException(email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }

        return user;
    }
}
```

Session과 관련된 부분을 모두 login API로 Move하였다.  
SessionController, SessionRequestDto, SessionResponseDto, SessionErrorAdvice  

일반 고객과 가게 주인의 계정 정보를 구분하기 위하여  
User 객체의 restaurantId 속성을 만들어서 null이 아니면 가게 주인으로 인식되도록 하였고
setrestaurantId()를 실행하면 level이 자동으로 바뀌도록 하였다.

User Class
```
    private Long restaurantId;

    public void setRestaurantId(Long restaurantId) {
        this.level = 50L;
        this.restaurantId = restaurantId;
    }

    public boolean isRestaurantOwner() {
        return level == 50;
    }
```

로그인 할때 JWT token을 생성하는 부분에서  
가게 주인일 경우에만 Claims에 restaurantId까지 추가하기 위해  
JwtUtil, SessionController 수정  

JwtUtil.creation() 메서드 수정
```
    public String createToken(long userId, String name, Long restaurantId) {
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name);

        //restaurantId가 null이 아닌경우만 restaurantId를 claims에 추가
        if (restaurantId != null) {
            builder.claim("restaurantId",restaurantId);
        }

        String token = builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token ;
    }
```

SessionController POST 메서드 수정
```
    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email,password);

        //가게 주인인경우만 restaurantId를 보내고 아니면 null
        String accessToken = jwtUtil.createToken(
                user.getId(),
                user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId(): null);

        String url = "/session";
        return ResponseEntity.created(new URI(url)).body(
                SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }
```
    
    