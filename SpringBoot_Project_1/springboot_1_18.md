# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 18. 가게 수정  

PATCH /restaurants/{id}
성공 http status 200  

Controller Test에서 새로운 기능 테스트    
```
    @Test
    public void update() throws Exception {
        mvc.perform(patch("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"GukBobZip\",\"address\":\"Busan\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1L, "GukBobZip", "Busan");
    }
```

Controller에 실제 기능 추가    
```
    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestBody Restaurant resource) {

        String name = resource.getName();
        String address = resource.getAddress();

        restaurantService.updateRestaurant(id, name, address);
        return "{}";
    }
```

Service Test
```
    @Test
    public void updateRestaurant() {

        Restaurant restaurant = new Restaurant(1L, "BobZip", "Seoul");
        given(restaurantRepository.findById(1L)).willReturn(java.util.Optional.of(restaurant));

        restaurantService.updateRestaurant(1L,"GukBobZip","Busan");

        assertThat(restaurant.getName(), is("GukBobZip"));
        assertThat(restaurant.getAddress(), is("Busan"));
    }
```

Service 추가
```
    @Transactional //트랜젝션 범위에서 처리되고 범위에서 처리가 벗어났을때 내용이 적용됨
    public Restaurant updateRestaurant(Long id, String name, String address) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        restaurant.setInformation(name,address);
        return restaurant;
    }
```

테스트
```
$ http PATCH localhost:8080/restaurants/1 name=BeRyong address=Seoul
HTTP/1.1 200 
~
```
    
    