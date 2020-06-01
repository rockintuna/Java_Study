# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 14. 가게 추가  

POST 메서드를 사용하여 /restaurants 에 추가할 것임    
성공하면 HTTP status 201    
넣을 레스토랑 정보는 Header에 담아 보내게 되는데  
Header Location에 리소스 주소가 들어가게 된다.   

Client에서 보내준 결과를 JSON parser가 수행되도록 할 수 있다.  
Empty {}

HTTPie 활용하여 확인할 것이다.       
HTTPie 설치 (MAC OS)
```shell script
$ brew install httpie
```

Controller에 가게를 추가하는 작업을 추가할 것이고,   
Service에 실제로 기능을 구현할 것이다.   

1.POST 요청을 처리하기 위한 Controller 생성
```
    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@RequestBody Restaurant resource) //content로 넘겨준 내용을 매개변수로 받기
            throws URISyntaxException {

        String name=resource.getName();
        String address=resource.getAddress();

        Restaurant restaurant = new Restaurant(1234L,name,address);
        restaurantService.addRestaurant(restaurant);

        URI location = new URI("/restaurants/"+restaurant.getId());
        return ResponseEntity.created(location).body("{}"); //빈 내용
    }
```   
Controller Test
```
    @Test
    public void create() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON) //JSON 타입임을 알려준다
                .content("{\"name\":\"BeRyong\",\"address\":\"Busan\"}")) //JSON 타입으로 데이터 전달
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}")); //비어있는지 확인

        verify(restaurantService).addRestaurant(any()); //뭘 넣든지 작동할 수 있도록, Mockito에서 제공
    }
```

2.Service 및 Repository 추가    
Restaurant Service Test
```
    @Test
    public void addRestaurant() {
        Restaurant restaurant = new Restaurant("BeRyong", "Busan");

        //가짜 saved
        Restaurant saved = new Restaurant(1234L,"BeRyong", "Busan");
        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));
    }
```

Restaurant Service
```
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
```

Restaurant Repository Test
```
public class RestaurantRepositoryImplTest {

    @Test
    public void save() {
        RestaurantRepository repository = new RestaurantRepositoryImpl();

        int oldCount = repository.findAll().size();
        Restaurant restaurant = new Restaurant("BeRyong","Busan");
        repository.save(restaurant);

        assertThat(restaurant.getId(), is(1234L)); //저장된 뒤 id가 생성되었는지 확인    
        int newCount = repository.findAll().size();

        assertThat(newCount-oldCount, is(1)); //개수가 늘어났는지 확인
    }

}
```

Restaurant Repository
```
    @Override
    public Restaurant save(Restaurant restaurant) {
        restaurant.setId(1234L);
        restaurants.add(restaurant);
        return restaurant;
    }
```

POST by HTTPie
```shell script
$ http POST localhost:8080/restaurants name=BeRyong address=Busan
```

GET by HTTPie
```shell script
$ http GET localhost:8080/restaurants

HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Date: Mon, 01 Jun 2020 21:07:17 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "address": "Seoul",
        "id": 1004,
        "information": "Bob zip in Seoul",
        "menuItems": [],
        "name": "Bob zip"
    },
    {
        "address": "Seoul",
        "id": 2020,
        "information": "Cyber Food in Seoul",
        "menuItems": [],
        "name": "Cyber Food"
    },
    {
        "address": "Busan",
        "id": 1234,
        "information": "BeRyong in Busan",
        "menuItems": [],
        "name": "BeRyong"
    }
]
```
    
    