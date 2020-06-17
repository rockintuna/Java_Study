# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 35. 가게 목록 필터링  

필터링 (지역, 분류 (Region, Category))

Region과 Category를 도메인 모델로 관리

 - eatgo-admin-api  
GET /regions  
POST /regions
GET /categories  
POST /categories  

 - eatgo-customer-api   
GET /regions  
GET /categories  

JPA  
findAllByAddressContaining  
findAllByAddressStartingWith  

H2 Console  
http://localhost:8080/h2-console 에서 h2DB를 관리할 수 있다.  

eatgo-customer-api
Restaurant Controller 수정
```
    @GetMapping("/restaurants")
    public List<Restaurant> list(
            @RequestParam("region") String region, //region 파라미터 추가
            @RequestParam("category") Long categoryId //category 파라미터 추가 
    ) {
        List<Restaurant> restaurants = restaurantService.getRestaurants(region,categoryId);
        return restaurants;
    }
```

Restaurant Service의 getRestaurants 메서드 매개변수 추가 
```
    public List<Restaurant> getRestaurants(String region, long categoryId) {
        List<Restaurant> restaurants =
                restaurantRepository.findByAddressContainingAndCategoryId(region,categoryId);
                //입력받은 region을 포함하는 address / 일치하는 categoryId 
        return restaurants;
    }
```

Restaurant Repository JPA
```
    List<Restaurant> findByAddressContainingAndCategoryId(
            String region, long categoryId);
```

TEST
```shell script
http GET "localhost:8080/restaurants?region=서울&category=1"
```
    
    