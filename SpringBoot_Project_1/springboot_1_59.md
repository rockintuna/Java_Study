# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 59. 테이블 예약 기능  

eatgo-restaurant-api 를 추가하여 가게 주인이 사용하는 부분 추가할 것이다.  

Reservation 도메인 모델을 추가 할 것이다.  
속성 : userId, name, date, time, partySize...  

예약  
POST /reservations  

예약 확인
GET /reservations  

eatgo-common-api
Reservation 모델 생성
```
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    private Long userId;

    @Setter
    private String name;

    @Setter
    @NotEmpty
    private String date;

    @Setter
    @NotEmpty
    private String time;

    @Setter
    @NotNull
    private Integer partySize;

}
```

Reservation Repository
```
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Reservation save(Reservation reservation);

    List<Reservation> findAllByRestaurantId(Long restaurantId);
}
```

eatgo-customer-api
Reservation Controller
```
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> create(
            Authentication authentication,
            @RequestBody Reservation resource,
            @Valid @PathVariable("restaurantId") Long restaurantId
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();

        Reservation reservation = reservationService.addReservation(
                restaurantId,
                claims.get("userId", Long.class),
                claims.get("name", String.class),
                resource.getDate(),
                resource.getTime(),
                resource.getPartySize()
        );

        String url = "/restaurants/"+restaurantId+"/reservations"+reservation.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
```

Reservation Service
```
@Service
@Transactional
public class ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(
            Long restaurantId,
            Long userId,
            String name,
            String date,
            String time,
            Integer partySize) {

        Reservation reservation = Reservation.builder()
                        .restaurantId(restaurantId)
                        .userId(userId)
                        .name(name)
                        .date(date)
                        .time(time)
                        .partySize(partySize)
                        .build();

        return reservationRepository.save(reservation);
    }
}
```

test(http) 예약
```shell script
$ http POST localhost:8080/restaurants/1/reservations date=2019-12-24 time=30:00 partySize=10 "Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiLsnbTsoJXsnbgifQ.wKrAIT-cOCfQwBO_8UkhK_IOy4tl8uuIeBS4nppu_Vw"         
HTTP/1.1 201 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Length: 2
Content-Type: application/json;charset=UTF-8
Date: Fri, 03 Jul 2020 16:36:17 GMT
Expires: 0
Keep-Alive: timeout=60
Location: /restaurants/1/reservations24
Pragma: no-cache
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block

{}

```


eatgo-restaurant-api에서 예약 확인용으로 사용될 Reservation Controller와 Service를 다르게 생성  

Reservation Service
```
@Service
@Transactional
public class ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    //Claim으로부터 얻은 restaurantId 기준으로 예약을 조회할것이다.
    public List<Reservation> getReservations(Long restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId);
    }
}
```

Reservation Controller
```
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> list(
            Authentication authentication
    ) {
        Claims claims = (Claims) authentication.getPrincipal();

        Long restaurantId = claims.get("restaurantId",Long.class);
        return reservationService.getReservations(restaurantId);
    }
}
```

test(http) 예약 확인  
```shell script
$ http localhost:8080/reservations "Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsIm5hbWUiOiLqsIDqsozso7zsnbgiLCJyZXN0YXVyYW50SWQiOjF9.0mje-W_SREwyp6JLxkVPl-MsxwzAiQo4xVZcb-a7pFo"                                              
HTTP/1.1 200 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Date: Fri, 03 Jul 2020 16:42:27 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block

[
    {
        "date": "2019-12-24",
        "id": 24,
        "name": "이정인",
        "partySize": 10,
        "restaurantId": 1,
        "time": "30:00",
        "userId": 1004
    }
]
```
    
    