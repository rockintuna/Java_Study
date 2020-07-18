# 스프링 부트 프로젝트
### 지인 정보 관리 시스템 만들기  

#### 16. Controller HTTP Method

Controller에 각 HTTP 메서드를 이용한 기능 생성  
```
@RestController
@Slf4j
@RequestMapping(value = "/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    //회원 정보 변경에서 받을 용도로 PersonDto Class를 만들었다.  
    @PostMapping
    public ResponseEntity<?> postPerson(@RequestBody Person resource) throws URISyntaxException {
        Person person = personService.put(resource);

        log.info("person -> {}",personRepository.findAll());

        String url = "/api/person/"+person.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id,@RequestBody PersonDto personDto) {
        personService.modify(id, personDto);

        log.info("person -> {}",personRepository.findAll());
    }

    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id,String name) {
        personService.modify(id, name);

        log.info("person -> {}",personRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.delete(id);

        log.info("person -> {}",personRepository.findAll());
    }
}
```
회원 정보 변경에서 받을 용도로 PersonDto Class를 만들었다.  
```
@Data
public class PersonDto {
    private String name;
    private int age;
    private String hobby;
    private String address;
    private String bloodType;
    private String job;
    private String phoneNumber;
    private LocalDate Birthday;
}
```
personService.modify 메서드 오버로딩을 통해 회원 정보 변경과 이름 변경을 따로 구현하였다.  
```
@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleExcludeBlocks() {
        return personRepository.findByBlockIsNull();
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
        Person person = personRepository.findById(id).orElse(null);

        log.info("person : {}", person);

        return person;
    }

    public List<Person> getPeopleByName(String name) {
        return personRepository.findByName(name);
    }

    public List<Person> getPeopleNameContaining(String name) {
        return personRepository.findByNameLike(name);
    }

    @Transactional
    public Person put(Person person) {
        return personRepository.save(person);
    }

    public void modify(Long id, PersonDto personDto) {
        Person personAtDb =  personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        if (!personAtDb.getName().equals(personDto.getName())) {
            throw new RuntimeException("이름이 다릅니다.");
        }

        //Person 클래스에 정보 변경을 위한 set 메서드를 만들었다.  
        personAtDb.set(personDto);

        personRepository.save(personAtDb);
    }

    //오버로딩(이름만 변경)
    public void modify(Long id, String name) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setName(name);
        personRepository.save(person);
    }

    //DB에서 delete하는 대신 flag(deleted)를 사용하였다.  
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setDeleted(true);
        personRepository.save(person);
    }
}
```

Person Class  
```
~
// 모든 JPA 작업에서 아래 조건이 자동으로 추가된다.
@Where(clause = "deleted = false")
public class Person {

    ~
    //디폴트값은 false로 설정
    @ColumnDefault("0")
    private boolean deleted;

    //정보 변경을 위한 메서드 구현, 빈값이면 변경하지 않도록  
    public void set(PersonDto personDto) {
        if (personDto.getAge() != 0) {
            this.setAge(personDto.getAge());
        }
        if (!StringUtils.isEmpty(personDto.getAddress())) {
            this.setAddress(personDto.getAddress());
        }
        if (!StringUtils.isEmpty(personDto.getAge())) {
            this.setAge(personDto.getAge());
        }
        if (!StringUtils.isEmpty(personDto.getHobby())) {
            this.setHobby(personDto.getHobby());
        }
        if (!StringUtils.isEmpty(personDto.getBloodType())) {
            this.setBloodType(personDto.getBloodType());
        }
        if (!StringUtils.isEmpty(personDto.getJob())) {
            this.setJob(personDto.getJob());
        }
        if (!StringUtils.isEmpty(personDto.getPhoneNumber())) {
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
        if (personDto.getBirthday() != null) {
            this.setBirthday(new Birthday(personDto.getBirthday()));
        }
    }
}
```

@Where(clause = "deleted = false") 를 걸어두었기 때문에
만약, deleted = true인 값을 조회하고 싶을땐 nativeQuery를 사용하여야 한다.  
```
    @Query(value = "select * from person where deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();
```
    
    