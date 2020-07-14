# 스프링 부트 프로젝트
### 지인 정보 관리 시스템 만들기  

#### 05.HelloWorld

- Junit5, MockMvc  

@RestController 에는 @Controller와 @ResponseBody가 포함되어 있다.

```
@RestController
public class HelloWorldController {

    @GetMapping(value = "/api/helloWorld")
    public String helloWorld() {
        return "Hello World!";
    }

}
```
  
junit5에서는 junit4와 다르게 테스트 메서드가 public이 강제되지 않는다.
여기서는 public 대신 default 접근자를 사용하였다.  
모의 http request와 response를 만들어서 테스트하는 것이 MockMvc test이다.  

```
@SpringBootTest //Spring Boot Test임을 명
class HelloWorldControllerTest {

    @Autowired
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    @Test
    void mockMvcTest() throws Exception {
        //MockMvc setting 
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld")
        ).andDo(MockMvcResultHandlers.print()) //request, response 상세 출력  
        .andExpect(MockMvcResultMatchers.status().isOk()) //status 200?  
        .andExpect(MockMvcResultMatchers.content().string("Hello World!"));  //body 확인  
    }
}
```

- JPA  

JPA 및 H2 DB 의존성 추가  
```
dependencies {
    ~
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'com.h2database.h2'
    ~
    }
}

```

Entity, Repository 생성 및 테스트  

```
@Entity
@Builder
@Setter
@Getter
@ToString//toString 메서드 오버라이딩  
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode //속성이 모두 동일하면 같은 객체로 인식하도록
//@RequiredArgsConstructor //NonNull 어노테이션으로 필수적인 속성 정의
public class Person {

    @Id
    @GeneratedValue
    private long id;

    //@NonNull
    private String name;

    //@NonNull
    private int age;

    private String hobby;

    private String bloodType;

    private String address;

    private LocalDate birthDay;

    private String job;

    @ToString.Exclude //toString 오버라이딩에서 제외  
    private String phoneNumber;
}
```
```
public interface PersonRepository extends JpaRepository<Person, Long> {
//Person을 Entity로 가지는 JPA Repository 생성, @Id의 타입은 Long  
}
```

JPA 실제 수행되는 SQL문 확인하기
application.yml
```
spring:
  jpa:
    show-sql: true
```

Create / Read Test
```
@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = Person.builder()
                .name("robin")
                .age(10)
                .build();
        personRepository.save(person);

        List<Person> people = personRepository.findAll();
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("robin");
        assertThat(people.get(0).getAge()).isEqualTo(10);
    }

}
```
    
    