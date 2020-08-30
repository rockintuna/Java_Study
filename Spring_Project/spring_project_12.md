## 도메인 클래스 컨버터 자동 등록
스프링 데이터 JPA는 도메인 클래스 컨버터를 제공한다.  

 - 도메인 클래스 컨버터  
 : 스프링 데이터 JPA의 Repository를 사용해서 ID에 해당하는 엔티티를 읽어온다.

### 스프링 데이터 JPA 및 h2DB 의존성 설정
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
```

### Repository 추가
```
public interface PersonRepository extends JpaRepository<Person, Long> {
}
```

### 엔티티 맵핑
```
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### 도메인 클래스 컨버터 

```
    @GetMapping("/hello")
    public String hello(@RequestParam("id") Person person) {
        return "hello" + person.getName();
    }
```

get 메서드는 ID를 파라미터로 받는데, 받은 파라미터가 문자열이더라도
자동으로 등록된 도메인 클래스 컨버터에 의해 Person 객체로 변환될 수 있다.    

#### 테스트
```
    @Test
    public void hello() throws Exception {
        Person person = new Person();
        person.setName("이정인");
        Person savedPerson = personRepository.save(person);

        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .param("id",savedPerson.getId().toString()))
                .andDo(print())
                .andExpect(content().string("hello이정인"));
    }
```
