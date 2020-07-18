# 스프링 부트 프로젝트
### 지인 정보 관리 시스템 만들기  

#### 20. 리팩토링 도메인코드  


bloodType 속성 제거
block 속성 제거
age 속성은 제거하고 birthday를 통해 나이를 계산하는 getAge 메서드 추가
오늘이 생일인지 확인하는 기능 추가 (isBirthdayToday)
   
```
@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted = false")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String hobby;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;

    public Integer getAge() {
        if (this.birthday != null) {
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        } else {
            return null;
        }
    }

    public boolean isBirthdayToday() {
        return LocalDate.now().equals(LocalDate.of(
                this.birthday.getYearOfBirthday(),this.birthday.getMonthOfBirthday(),this.birthday.getDayOfBirthday()));
    }

    public void set(PersonDto personDto) {
        if (!StringUtils.isEmpty(personDto.getAddress())) {
            this.setAddress(personDto.getAddress());
        }
        if (!StringUtils.isEmpty(personDto.getHobby())) {
            this.setHobby(personDto.getHobby());
        }
        if (!StringUtils.isEmpty(personDto.getJob())) {
            this.setJob(personDto.getJob());
        }
        if (!StringUtils.isEmpty(personDto.getPhoneNumber())) {
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
        if (personDto.getBirthday() != null) {
            this.setBirthday(Birthday.of(personDto.getBirthday()));
        }
    }
```

Birthday 속성 출력 변경을 위하여 Configuration 생성 및 JsonSerializer 사용  
```
public class BirthdaySerializer extends JsonSerializer<Birthday> {
    @Override
    public void serialize(Birthday value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value != null) {
            gen.writeObject(
                    LocalDate.of(value.getYearOfBirthday(), value.getMonthOfBirthday(), value.getDayOfBirthday()));
        }
    }
}
```
```
@Configuration
public class JsonConfig {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BirthdayModule());
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    static class BirthdayModule extends SimpleModule {
        BirthdayModule() {
            super();
            addSerializer(Birthday.class, new BirthdaySerializer());
        }
    }
}
```

Controller 테스트 구체화
```
@SpringBootTest
@Transactional
class PersonControllerTest {

    @Autowired
    private PersonController personController;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).setMessageConverters(messageConverter).build();
    }

    @Test
    void getPerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                //아래처럼 birthday를 체이닝할 필요 없이 serializing된 값을 사용  
                .andExpect(jsonPath("$.birthday").value("1992-01-30"))
                //.andExpect(jsonPath("$.birthday.yearOfBirthday").value(1992))
                //.andExpect(jsonPath("$.birthday.monthOfBirthday").value(1))
                //.andExpect(jsonPath("$.birthday.dayOfBirthday").value(30))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                //age나 birthdayToday는 시간에 따라 바뀌므로 타입만 검증  
                .andExpect(jsonPath("$.age").isNumber())
                .andExpect(jsonPath("$.birthdayToday").isBoolean());

    }

    @Test
    void postPerson() throws Exception {
        PersonDto personDto = PersonDto.of("martin",
                "programming",
                "판교",
                "programmer",
                "010-1111-1111",
                LocalDate.now());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(personDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("\"{}\""));

        Person result = personRepository.findAll(Sort.by(Direction.DESC, "id")).get(0);

        //assertAll에 속한 모든 테스트를 모두 실행한다.
        //assertAll이 아니면 순차적으로 실행하며 실패할 시 다음 테스트를 진행하지 않는다. 
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("판교"),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-1111"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now()))
        );
    }

    @Test
    void modifyPerson() throws Exception {
        PersonDto personDto = PersonDto.of("martin",
                "programming",
                "판교",
                "programmer",
                "010-1111-1111",
                LocalDate.now());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(personDto)))
                .andDo(print())
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("판교"),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-1111"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now()))
        );
    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception {
        PersonDto personDto = PersonDto.of("martin",
                "programming",
                "판교",
                "programmer",
                "010-1111-1111",
                LocalDate.now());

        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.put("/api/person/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(personDto)))
                .andDo(print())
                .andExpect(status().isOk()));
    }

    @Test
    void modifyName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/person/1")
                .param("name","martinModified"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    //PersonDto를 json으로 변환해주는 메서드를 생성하여 사용한다.  
    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}
```
    
    