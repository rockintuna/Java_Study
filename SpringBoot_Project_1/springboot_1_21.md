# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 21. Validation, 에러 처리  

Validation : 유효성 검사     
사용자들이 입력한 데이터가 올바른지 검증하는 작업이다.  

@Valid

@NotNull  
@NotEmpty  
@Size(max=10)

올바르지 않을 시 Error(http status 400)를 리턴하도록

Controller의 기능에 @Valid 추가   
```
    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) //Valid: 검증할 것이라 정의
            throws URISyntaxException {

                Restaurant restaurant = Restaurant.builder()
                .name(resource.getName())
                .address(resource.getAddress())
                .build();
        restaurantService.addRestaurant(restaurant);

        URI location = new URI("/restaurants/"+restaurant.getId());
        return ResponseEntity.created(location).body("{}"); //빈 내용
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @RequestBody Restaurant resource) {

        restaurantService.updateRestaurant(id, resource.getName(), resource.getAddress());
        return "{}";
    }
```

Model에서 속성 특징 변경
```
    @NotEmpty //비어있으면 안된다고 정의
    private String name;
    @NotEmpty
    private String address;
```

Controller Test
```
    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
```
    
Not Found에 대한 처리 => Exception (http status 404)
ex) 없는 URL

@ControllerAdvice : 예외를 받아 처리할 클래스  

Demo  
존재하지 않는 id의 가게를 조회했을 때 http status 404를 response하려면

id:404(없는 id)의 디테일을 Get했을때 not found를 받고싶다.  
```
    @Test
    public void detailWithNotExist() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L)); //서비스는 사용자 예외를 던질건데  

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/404"))
                .andExpect(status().isNotFound()) //status 404인지 확인하고싶다.
                .andExpect(content().string("{}")); //빈 body도 받는지 확인하고싶다.  
    }
```  

사용자 예외 클래스 생성 
```
public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(long id) {
        super("Could not find restaurant "+id); //RuntimeException의 매개변수 massage(String)
    }
}
```

Controller에서 try/catch로 예외처리 해줄수도 있겠지만  
예외처리를 맡아줄 ControllerAdvice를 생성해준다.
```
@ControllerAdvice //예외를 처리할 클래스  
public class RestaurantErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND) //response 404 할것임
    @ExceptionHandler(RestaurantNotFoundException.class) //어떤 예외에 대한 정의
    public String handleNotFound() {
        return "{}"; //예외 처리하고 비어있는 body를 보낼것이다.
    }
}
```

Service에서도 test 진행
```
    @Test(expected = RestaurantNotFoundException.class) //사용자 예외가 발생할까?  
    public void getRestaurantWithNotExisted() {

        Restaurant restaurant = restaurantService.getRestaurant(404L);
    }
```

orElseThrow()로 예외 처리
```
    public Restaurant getRestaurant(Long id) {

        Restaurant restaurant =  restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id)); //사용자 예외 처리
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        return restaurant;
    }
```
    
    