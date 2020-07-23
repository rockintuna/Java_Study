# 스프링 부트 프로젝트
### 지인 정보 관리 시스템 만들기  

#### 34. getAll() 추가 및 Paging

Controller
```
    @GetMapping()
    public Page<Person> getAll(@PageableDefault Pageable pageable) {
        return personService.getAll(pageable);
    }
```

Service
```
    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }
```

Controller Test
```
    @Test
    void getAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person")
                    //default 대신 사용
                    .param("page","1") //2번째 페이지
                    .param("size","2")) //페이지 별 사이즈
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(3)) //페이지 수
                .andExpect(jsonPath("$.totalElements").value(6)) //총 요소 수
                .andExpect(jsonPath("$.numberOfElements").value(2)) //페이지의 요소 수 
                .andExpect(jsonPath("$.content.[0].name").value("dennis"))
                .andExpect(jsonPath("$.content.[1].name").value("sophia"));
    }
```
    
    