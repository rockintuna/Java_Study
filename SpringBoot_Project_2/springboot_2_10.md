# 스프링 부트 프로젝트
### 지인 정보 관리 시스템 만들기  

#### 10. JPA Relation

차단 사람을 구분하기 위한 Block Entity 생성  
```
@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Block {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String name;

    private String reason;

    private LocalDate startDate;

    private LocalDate endDate;
}
```

Person Entity와 Relation 설정  
Block의 유무만 판단할 것이므로 일대일 단방향
@JoinColumn 어노테이션이 없으면 @Id로 제약조건이 생성됨  
```
    //CascadeType.PERSIST는 이 엔티티에서 Block에 대한 영속성을 관리하겠다는 의미이다.
    //MERGE, REMOVE, REFRESH, DETACH 등은 엔티티의 변경에 있어서 보유된 엔티티(Block)의 해당 작업에 대해서도 변경하는 것이다.   
    //ALL은 위의 5가지를 모두 적용하는 것이다.
    //@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    //orphanRemoval = true는 해당 Block이 해제(setBlock(null))되는 순간 삭제시킨다. -> 불필요 엔티티 관리  
    //fetch type 은 EAGER(default)와 LAZY가 있는데 LAZY의 경우에는 이 엔티티를 호출(select)할때 Block을 호출하지 않고 필요할때 호출한다.  
    //optional = true(default), false인 경우는 이 속성이 null일 수 없게 된다.  
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Block block;
```

Person Service에서 차단된 사람을 제외하고 가져오는 메서드와 id로 조회하는 메서드 생성     
```
@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleExcludeBlocks() {
        List<Person> people = personRepository.findAll();

        return people.stream().filter(
                person -> person.getBlock() == null
        ).collect(Collectors.toList());
    }

    public Person getPerson(Long id) {
        Person person = personRepository.findById(id).get();

        //System.out은 모든 로그를 출력하지만
        //log.info(Slf4j)는 로그백을 이용하여 로그 출력을 제한할 수 있다.  
        //System.out.println("person : "+ person);
        log.info("person : {}", person);
        //ex) 2020-07-17 17:01:52.918  INFO 2781 --- [    Test worker] c.f.j.p.mycontact.service.PersonService  : person : Person(id=3, name=Dennis, age=7, hobby=null, bloodType=O, address=null, birthDay=null, job=null, block=Block(id=4, name=Dennis, reason=null, startDate=null, endDate=null))
        //이때 로그를 출력하는 중에 사용되는 SQL이 fetch type에 따라 다른데
        //EAGER에서는 모든 Block을 Person이 호출될때(toString(person)에서) 호출한다. (left join을 사용한 1개 select문)
        //이때 optional = false 인 경우에 inner join을 사용하게 된다.
        //LAZY에서는 Person 먼저 호출하고 그 후 해당되는 id의 Block을 따로 호출한다. (2개 select문)
        //또한 LAZY인 경우에 Block 속성에 @ToString.Exclude 처리를 하면 toString에서 빠지게 되므로 block에 대한 호출은 없어진다.   
        //LAZY는 간단하지 않지만 불필요한 쿼리 호출을 줄여 성능을 개선할 수 있다는 장점이 있다.  

        return person;
    }

}
```
    
    