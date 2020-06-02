# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 16. JPA    

데이터의 영속화(Persistence) 작업이 필요하다.
Java는 영속화에 대한 표준을 가지고 있다.   
= JPA (Java Persistence API)    

Hibernate : JPA에서 유명한 Library
@Entity : Identifier로 구분되는 객체   

Spring Data JPA를 활용하여 쉽게 JPA를 사용할 수 있음  

H2 DBMS를 In-memory로 사용할 것임

build.gradle의 dependencies 부분에 아래와 같이 추가했다.
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
    //추가1 Spring Data JPA
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    //추가2 H2 Database
	implementation 'com.h2database:h2'

	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

View > Tool Windows > Gradle > Refresh Gradle Dependencies


Restaurant 와 MenuItem 를 Entity로 정의  
```
@Entity
public class Restaurant {
    private String name;
    private String address;
    @Id
    @GeneratedValue
    private Long id;

    @Transient //통과ll
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

~
}
```
```
@Entity
public class MenuItem {

    @Id //Identifier for Entity
    @GeneratedValue //1부터 순차적으로 들어감
    private Long id;

    private Long restaurantId;

~
}
``` 

Repository 인터페이스를 통해 JPA 사용     
```
public interface MenuItemRepository
        extends CrudRepository<MenuItem, Long> { //인터페이스만 이용하여 JPA와 연결하여 사용 <T, ID>
                                                 //T : Entity의 타입, ID : id의 타입
    List<MenuItem> findAllByRestaurantId(Long restaurantId);

}
```
```
public interface RestaurantRepository
        extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();

    Optional<Restaurant> findById(Long id); //Optional : 존재할 수도 있지만 안할수도 있는 객체
                                            //NPE 회피..
    Restaurant save(Restaurant restaurant);
}
```