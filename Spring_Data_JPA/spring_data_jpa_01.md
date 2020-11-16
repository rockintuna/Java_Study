## Spring Data JPA

Spring Data JPA 원리

JpaRepository<Entity, Id> 인터페이스
 - 데이터 접근 오브젝트 역할을 하는 매직 인터페이스.
 - @Repository 애노테이션 없이 빈으로 등록 됨.
 - 기본적으로 CRUD 메서드를 제공해준다.

```
public interface PostRepository extends JpaRepository<Post, Long> {
}
```

@EnableJpaRepository 
: @Configuration 스프링 설정 클래스에 붙여야 JpaRepository 인터페이스를 사용할 수 있다.
(스프링 부트에서는 자동으로 설정된다.)

@EnableJpaRepository는 JpaRepositoriesRegistrar.class를 import 하는데,
이 클래스가 JpaRepository 인터페이스를 상속받는 인터페이스 타입의 빈을 만들어서 등록해주는 역할을 한다.
(핵심은 spring의 ImportBeanDefinitionRegistrar 인터페이스, 애노테이션이 아닌 프로그래밍을 통해 빈을 등록할 수 있음.)

특히 스프링 부트 스타터 JPA 같은 경우,
HibernateJpaAutoConfiguration 자동 설정에 의하여 EntityManagerFactory가 빈으로 등록된다.
또한 EntityManager나 TransactionManager를 빈으로 주입 받아 사용할 수 있다.

SQL을 확인하는 법(application.properties)
```
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true 
logging.level.org.hibernate.type.descriptor.sql=true
```

---
### Spring Data Common
여러 저장소 지원 프로젝트(Spring Data JPA, JDBC, KeyValue, 등)의 공통 기능을 제공하는 프로젝트.

#### 리포지토리

Spring Data Common
 - Repository : 단순히 리포지토리라는 마크.
 - CrudRepository : CRUD 기능  
 save(entity), saveAll(entities), findById(id), ...
 - PagingAndSortingRepository : 페이징, 정렬 기능  
 findAll(pageable), findAll(sort)
 
Spring Data JPA
 - JpaRepository : JPA 기능 추가

---
#### 인터페이스 정의하기
제공되는 메소드를 사용하지 않고 직접 정의하고 싶다면
@RepositoryDefinition 애노테이션 사용.

```
@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findAll();

}
```
직접 정의하지 않으면 아무 기능이 없으며 Spring Data JPA가 구현을 도와준다. 

공통 인터페이스를 따로 정의하고 싶다면
@NoRepositoryBean 애노테이션 사용.

```
@NoRepositoryBean
public interface MyRepository<T, Id extends Serializable> extends Repository<T, Id> {

    <E extends T> E save(E entity);
    
    List<T> findall();
    
}
```
다른 리포지토리에서 이 인터페이스를 상속받아 사용하면 된다.

---
#### Null 처리
 - Spring Data 2.0부터 자바 8의 Optional을 지원한다.
 => 반환 값이 없을 때 Null과는 다르며 Optional 객체에서 사용할 수 있는 메소드를 사용할 수 있다.

 - 콜렉션은 Null을 리턴하지 않고 비어있는 콜렉션을 리턴한다.
 => 콜렉션 Null 체크 하지 말자.

 - Null 애노테이션(@NonNullApi, @NonNull, @Nullable)을 지원한다.
 => 런타임 시에 Null여부를 체크할 수 있다.

---
#### 쿼리 만들기 개요

쿼리 탐색 전략
 - 메소드 이름을 분석해서 쿼리 만들기(CREATE)
```
    List<Comment> findByTitleContains(String keyword);
```
Spring Data JPA가 이름을 분석하여 쿼리를 만들어준다.

 - 미리 정의된 쿼리를 찾아 사용하기(USE_DECLARED_QUERY)
```
    @Query("select c from Comment c where c.title like :keyword")
    List<Comment> findByTitleContains(String keyword);
```
@Query, @NamedQuery  
메소드 이름만으로는 쿼리를 표현하기 힘든 경우 사용한다.

 - 미리 정의된 쿼리를 찾아 사용하되 없으면 메소드 이름 분석하여 쿼리 만들기(CREATE_IF_NOT_FOUND)
, 기본 전략

전략 설정은 @EnableJpaRepositories 애노테이션에서 한다.
```
@Configuration
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
public class AppConfig {
    ~
}
```

메소드 이름으로 쿼리 만들 때 주의할 점.
 - 카멜 케이스를 사용한다.
 - 프로퍼티를 표현할 때 경로 표현식은 '.' 대신 '_'를 사용한다.  
 ex) Address.zipcode => Address_Zipcode
 
 - 리턴 타입을 Page<>로 받으려면 Pageable 파라미터를 받아야한다. 
```
Page<Comment> findByTitleContains(String keyword, Pageable pageable);
```

정렬 쿼리 예시
```
List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
```

페이징 쿼리 예시
```
Page<Person> findByLastname(String lastname, Pageable pageable);
Slice<Person> findByLastname(String lastname, Pageable pageable);
List<Person> findByLastname(String lastname, Pageable pageable);
List<Person> findByLastname(String lastname, Sort sort);
```

스트리밍 쿼리 예시
```
Stream<User> readAllByFirstnameNotNull();
```
 - try-with-resource를 사용할 것. (Stream을 다 사용한 뒤에 close() 해야하며 AutoCloseable을 구현하였으므로)
```
        try(Stream<Post> postStream = postRepository.findByTitleContains("good")) {
            String title = postStream.findFirst().get().getTitle();
            assertThat(title).isEqualTo("good");
        }
```

---
#### 비동기 쿼리 메소드

비동기 실행을 위해서 설정 필요 (@EnableAsync)
```
@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
public class Application {
    ~
}
```
비동기 쿼리 예시
```
@Async 
Future<User> findByFirstname(String firstname);
@Async 
CompletableFuture<User> findOneByFirstname(String firstname);
@Async 
ListenableFuture<User> findOneByLastname(String lastname); 
```
 - 메서드를 스프링 TaskExecutor에 전달하여 별도의 쓰레드에서 실행된다. Reactive와는 다름.
 
비동기 쿼리가 트랜젝션이 열려있는 도중에 실행된다면 해당 트랜잭션의 작업을 알지 못하는 것을 주의해야 한다.
(테스트에서는 그렇게 동작하는데 실제 애플리케이션에서 어떻게 동작할지는 모르겠음.)

---
#### 커스텀 리포지토리
쿼리 메소드(쿼리 만들기와 쿼리 찾아 사용하기)로 해결이 안되는 경우 직접 코딩으로 구현할 수 있다.
 - 스프링 데이터 리포지토리 인터페이스에 기능 추가.
 - 스프링 데이터 리포지토리 기본 기능 덮어쓰기(재정의) 가능.(커스텀이 더 높은 우선순위를 가진다.)
 
구현하는 방법
 - 커스텀 리포지토리 인터페이스 정의
```
public interface PostCustomRepository<T> {

    List<T> findMyPosts();

}
```

 - 인터페이스 구현 클래스 만들기(Impl, @Repository)
```
@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository<Post> {

    @Autowired
    private EntityManager em;

    @Override
    public List<Post> findMyPosts() {
        return em.createQuery("select p from Post p",Post.class).getResultList();
    }

}
```
기본적으로 접미어는 "Impl"를 사용해야 한다.  
사용할 접미어는 @EnableJpaRepositories 애노테이션에서 바꿀 수 있다.
```
@EnableJpaRepositories(repositoryImplementationPostfix = "Impl")
```

 - 엔티티 리포지토리에 커스텀 리포지토리 인터페이스 추가
```
public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {
}
```

---
#### 기본 리포지토리 커스터마이징
모든 엔티티 리포지토리에 공통적으로 추가하거나 덮어 쓰고 싶은 기능이 있다면

 - JpaRepository를 상속받는 인터페이스 추가 (@NoRepositoryBean)
```
@NoRepositoryBean
public interface MyRepository<T, Id extends Serializable> extends JpaRepository<T, Id> {

    boolean contains(T entity);
}
```

 - 기본 구현체(SimpleJpaRepository)를 상속받는 커스텀 구현체 만들기
```
public class MyRepositoryImpl<T, Id extends Serializable> extends SimpleJpaRepository<T, Id> implements MyRepository<T, Id> {

    private EntityManager em;

    public MyRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    public MyRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public boolean contains(Object entity) {
        return em.contains(entity);
    }
}
```

 - @EnableJpaRepositories 설정
```
@EnableJpaRepositories(repositoryBaseClass = MyRepositoryImpl.class)
```

 - JpaRepository 대신 생성한 기본 리포지토리를 상속받으면 정의한 기능을 사용할 수 있다.
```
public interface PostRepository extends MyRepository<Post, Long> {
}
```

---
#### 도메인 이벤트  
도메인의 변경을 감지할 이벤트 리스너를 만들고 이벤트를 발생할 수 있다. (Spring의 이벤트 기능 사용)  

이벤트 클래스 생성
```
public class PostPublishedEvent extends ApplicationEvent {

    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post) source;
    }

    public Post getPost() {
        return post;
    }
}
```

리스너(이벤트 핸들러) 클래스 생성
```
@Component
public class PostListener {

    @EventListener
    public void handle(PostPublishedEvent postPublishedEvent) {
        System.out.println("--------------------------");
        System.out.println(postPublishedEvent.getPost().getTitle() + " is published");
        System.out.println("--------------------------");
    }
}
```

스프링 데이터의 도메인 이벤트 Publisher : save() 할때 이벤트 발생  
@DomainEvents : 이벤트를 받아 콜렉션으로 모아놓음
@AfterDomainEventPublication : 이벤트 콜렉션 클린업  

extends AbstractAggregationRoot<T> : 위의 두개의 기능이 구현되어 있는 스프링 데이터에서 제공하는 구현체
```
@Entity
public class Post extends AbstractAggregateRoot<Post> {
    ~
    
    //엔티티에 해당 엔티티에 대한 이벤트를 담는 메서드 생성
    public Post publish() {
        this.registerEvent(new PostPublishedEvent(this));
        return this;
    }
}
```

```
        Post post = new Post();
        post.setTitle("good");
        post.publish();
        postRepository.save(post);
```
save()하기 전에 이벤트만 넣어주면??  
담겨있던 이벤트가 save() 시점에 자동으로 발생하며 리스너가 동작하게 된다.

```
--------------------------
good is published
--------------------------
```
=> Event 객체를 생성하고 publish 하는 코드가 생략될 수 있다.

---
#### QueryDSL 연동
QueryDSL을 사용하는 이유중 하나는 조건문을 표현하는 방법이 타입 세이프 하다는 것. (자바 코드로 표현 가능)

http://www.querydsl.com/static/querydsl/4.1.3/reference/html_single/#jpa_integration  

의존성 추가
```
<dependency>
  <groupId>com.querydsl</groupId>
  <artifactId>querydsl-apt</artifactId>
  <version>${querydsl.version}</version>
  <scope>provided</scope>
</dependency>

<dependency>
  <groupId>com.querydsl</groupId>
  <artifactId>querydsl-jpa</artifactId>
  <version>${querydsl.version}</version>
</dependency>

<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
  <version>1.6.1</version>
</dependency>
```

maven 플러그인 설정
```
            <plugin>
                <groupId>com.mysema.maven</groupId>
                <artifactId>apt-maven-plugin</artifactId>
                <version>1.1.3</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>process</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>target/generated-sources/java</outputDirectory>
                            <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

이 상태에서 컴파일을 하면 'target/generated-sources/java' 위치에 QueryDSL이 쿼리 생성용 클래스를 만들게 된다.
(ex) Post 클래스가 있으면 QPost
 

QuerydslPredicateExecutor<T> : QueryDSL이 제공하는 인터페이스
 - Optional<T> findOne(Predicate) : 하나를 찾을 때
 - List<T>|Page<T>|.. findAll(Predicate) : 여러개 찾을 때
QuerydslPredicateExecutor 인터페이스는 QuerydslJpaRepository 클래스가 구현하고 있다.

리포지토리 QuerydslPredicateExecutor 상속 
```
public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
}
```

---
#### 웹 지원 기능

 - DomainClassConverter  
 Spring Data JPA가 지원하는 String을 도메인 객체로 변환해주는 컨버터이다.
 보통 URI를 @PathVariable 애노테이션을 이용하여 객체로 바로 바인딩할 때 사용한다.
 
```
    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable("id") Post post) {
        return post; 
    }
```

 - Pageable / Sort 매개변수
 Spring Data JPA를 사용하면 핸들러 메소드에서 페이징과 정렬 관련된 매개 변수를 받을 수 있다.  
 
```
    @GetMapping("/posts")
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable); 
    }
```

관련 매개 변수
```
    mockMvc.perform(get("/posts")
            .param("page","0")
            .param("size","20")
            .param("sort","created,desc")
            .param("sort","title"))
```

 - HATEOAS  
Spring HATEOAS와 연계하여 응답에 페이징과 관련된 링크를 추가할 수 있다. 
 
의존성
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-hateoas</artifactId>
        </dependency>
```

PagedResourcesAssembler를 매개변수로 받을 수 있으며 그를 통해 Page를 PagedModel(구 PagedResource)로 변환할 수 있다.
```
    @GetMapping("/posts")
    public PagedModel<EntityModel<Post>> getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        return assembler.toModel(postRepository.findAll(pageable));
    }
```

---
### Spring Data JPA

#### @EnableJpaRepositories

@EnableJpaRepositories
 - @Configuration에서 설정해야 JpaRepository를 사용할 수 있다. 
 - 부트에서는 자동 설정이다.
 
```
@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```
 
---
#### JpaRepository save()

JpaRepository의 save()는 단순히 새 엔티티를 추가하는 메서드가 아니다.
 - Transient 상태의 객체는 EntityManager.persist()
 - Detached 상태의 객체는 EntityManager.merge()

둘 다 Persistent 상태로 만든다는 것은 동일하지만 내부적인 차이점이 있다.  
persist()는 파라미터로 받은 객체를 Persistent 상태로 만들지만,  
merge()는 파라미터로 받은 객체의 복사본을 만들어서 그 복사본을 Persistent 상태로 만든다.  
save()는 Persistent 상태로 만든 엔티티를 반환하게 되는데 
그렇기 때문에 persist()는 파라미터와 반환값은 서로 동일한 인스턴스인 반면 merge()는 파라미터와 반환값이 서로 다른 인스턴스이다. 
=> persist() 이든 merge() 이든 파라미터를 재사용하기보다는 반환값을 이용하자.

엔티티의 상태가 Transient인지 Detached인지 판단하는 기준
 - 엔티티의 @Id 프로퍼티를 찾아 null이면 Transient, null이 아니면 Detached
 - 엔티티가 Persistable 인터페이스를 구현하고 있다면 isNew() 메소드에 위임한다.
 - JpaRepositoryFactory를 상속받는 클래스를 만들고 getEntityInformation()을 오버라이딩해서 자신이 원하는 판단 로직을 구현할 수도 있다.
 
---
#### 쿼리 메소드

쿼리 생성하기

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

쿼리 찾아쓰기

 - 엔티티에 정의한 쿼리 찾아 사용하기 (JPA Named 쿼리)
    - @NamedQuery
    - @NamedNativeQuery
 - 리포지토리 메소드에 정의한 쿼리 사용하기
    - @Query
    - @Query(nativeQuery=true)

```
@Entity
@NamedQuery(name = "Post.findByTitle", query = "SELECT p From Post p WHERE p.title = ?1")
public class Post {
    ~
}
```

```
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);
```

```
List<Post> posts = postRepository.findByTitle("go");
```
리포지토리에 정의한 findByTitle()는 쿼리를 생성하지 않고 Post Entity에서 Post.findByTitle를 찾아서 사용한다.

엔티티에 정의하면 엔티티 클래스가 지저분해지는 단점이 있다.  
 => 리포지토리 메소드에 정의

```
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.title = ?1")
    List<Post> findByTitle(String title);
```

---
#### Sort
```
    @Query("SELECT p FROM Post p WHERE p.title = ?1")
    List<Post> findByTitle(String title, Sort sort);
```
```
        List<Post> posts = postRepository.findByTitleStartsWith("good", Sort.by("title"));
```

@Query에서 Pageable이나 Sort를 매개변수로 사용할때의 제약사항 :  
ORDER BY 절에서 함수를 호출하는 경우 Sort를 사용할 수 없다.  
 => JpaSort.unsafe() 사용.
 
Sort의 안에서 사용한 프로퍼티 또는 alias가 엔티티에 없는 경우 예외가 발생한다.

예)
```
        List<Post> posts = postRepository.findByTitle("good", Sort.by("LENGTH(title)"));
```

JpaSort.unsafe()를 사용하면 함수 호출이 가능하다.
```
        List<Post> posts = postRepository.findByTitle("good", JpaSort.unsafe("LENGTH(title)"));
```

---
#### NamedParameter와 SpEL

NamedParameter (@Param)
```
    @Query("SELECT p FROM Post p WHERE p.title = :title")
    List<Post> findByTitle(@Param("title") String keyword);
```

SpEL
 : 스프링 표현 언어, @Query에서 엔티티 이름을 #{#entityName}으로 표현할 수 있다.
```
    @Query("SELECT p FROM #{#entityName} p WHERE p.title = :title")
    List<Post> findByTitle(@Param("title") String keyword);
```
특정 엔티티의 리포지토리 안에서 해당 엔티티의 이름이 자동으로 들어간다.

---
#### Update 쿼리 메소드
보통은 JPA의 Dirty Checking을 통해서 flush 할때 Update 쿼리가 자동으로 발생하게 되지만,
만약 Update 쿼리를 직접 정의해서 사용하고 싶다면?  
 => @Modifying와 @Query 어노테이션 사용

```
    @Modifying
    @Query("UPDATE Post p SET p.title = :title WHERE p.id = :id")
    int updateTitle(@Param("title") String title,
                    @Param("id") Long id);
```

이렇게 정의한 메소드를 사용하여 발생한 Update 쿼리는 실제로 DB에서 실행되지만,
JPA의 영속성 컨텍스트는 이 변경을 감지하지 못하기 때문에
영속성 컨텍스트의 1차 캐시에 이미 있던 정보는 변하지 않는다.  
그렇기 때문에 Update 쿼리 발생 후에도 1차 캐시에서 엔티티를 조회하면 변경되지 않은 데이터를 참조하게 될 수도 있다.

clearAutomatically 옵션을 사용하여 1차 캐시를 미리 비워두는 우회법이 있긴 하다.  
```
@Modifying(clearAutomatically = true)
```
그렇다 하더라도 별로임. => 애플리케이션 로직으로 Update 하는 것이 맞다.

---
#### EntityGraph
쿼리 메소드마다 Fetch 모드를 설정 할 수 있다.

@NamedEntityGraph : @Entity에서 재사용할 여러 엔티티 그룹을 정의할 때 사용한다.  

@NamedEntityGraph(name = 이름, attributeNodes = @NamedAttributeNode(EAGER로 가져올 연관관계))
```
@NamedEntityGraph(name = "Comment.post"
        ,attributeNodes = @NamedAttributeNode("post"))
@Entity
public class Comment {

    ~

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
```

@EntityGraph : @NamedEntityGraph에 정의되어 있는 엔티티 그룹을 사용.  

그래프 타입을 설정할 수 있다.
 - FETCH : 설정한 엔티티 애트리뷰트는 EAGER 패치, 나머지는 LAZY 패치. (default)
 - LOAD : 설정한 엔티티 애트리뷰트는 EAGER 패치, 나머지는 기본 패치 전략.
 
```
    @EntityGraph(value = "Comment.post")
    Optional<Comment> loadById(Long id);
```
엔티티에서는 LAZY 모드로 설정되어 있지만, 이 메소드를 사용할 때는 EAGER 모드를 사용하게 된다.

@NamedEntityGraph 를 정의하지 않고 @EntityGraph에서 바로 정의할 수도 있다.
```
@EntityGraph(attributePaths = "post")
    Optional<Comment> getById(Long id);
```

---
#### Projection
엔티티의 일부 데이터만 가져오기.

인터페이스 기반 프로젝션

 - Closed Projection
 : 가져오려는 애트리뷰트만 가져오기 때문에 쿼리 최적화 가능, default 메소드 사용 가능.
```
public interface CommentSummary {

    String getContent();
    int getUp();
    int getDown();

}
```
```
    List<CommentSummary> findByPost_Id(Long id);
```

 - Open Projection
 : @Value(SpEL)을 사용해서 연산 가능, SpEL이 엔티티 대상이기 때문에 쿼리 최적화 불가능.
```
public interface CommentSummary {

    @Value("#{target.up + ' ' + target.down}")
    String getVotes();

}
```

만약 연산을 Closed Projection에서 하고 싶다면?  
 => 인터페이스의 default 메서드 사용.
 
```
public interface CommentSummary {

    String getContent();
    int getUp();
    int getDown();

    default String getVotes() {
        return getUp() + " " + getDown();
    }
}
```

클래스 기반 프로젝션

```
public class CommentSummary {

    private String content;
    private int up;
    private int down;

    public CommentSummary(String content, int up, int down) {
        this.content = content;
        this.up = up;
        this.down = down;
    }

    public String getContent() {
        return content;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public String getVotes() {
        return getUp() + " " + getDown();
    }
}

```
인터페이스 기반과 별다른 차이는 없지만 롬복을 사용할 수 있다는 장점이 있다.

다이나믹 프로젝션 :
프로젝션 용 메서드 하나에서 어떤 프로젝션을 사용할지 인자로 받을 수 있다.
```
    <T> List<T> findByPost_Id(Long id, Class<T> type);
```

---
#### Specifications
조건절을 Predicate와 유사한 Spec으로 정의할 수 있다.
 
설정 방법
https://docs.jboss.org/hibernate/stable/jpamodelgen/reference/en-US/html_single/

의존성
```
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-jpamodelgen</artifactId>
        </dependency>
```

메이븐 플러그인 설정
```
            <plugin>
                <groupId>org.bsc.maven</groupId>
                <artifactId>maven-processor-plugin</artifactId>
                <version>2.0.5</version>
                <executions>
                    <execution>
                        <id>process</id>
                        <goals>
                            <goal>process</goal>
                        </goals>
                        <phase>generate-sources</phase>
                        <configuration>
                            <processors>
                                <processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
                            </processors>
                        </configuration>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>org.hibernate</groupId>
                        <artifactId>hibernate-jpamodelgen</artifactId>
                        <version>${hibernate.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
```

Repository 상속 추가 
```
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
}
```

Spec 정의하기 
```
public class CommentSpecs {

    public static Specification<Comment> isBest() {
        return new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root
                    , CriteriaQuery<?> criteriaQuery
                    , CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isTrue(root.get(Comment_.best));
            }
        };
    }

    public static Specification<Comment> isGood() {
        return new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root
                    , CriteriaQuery<?> criteriaQuery
                    , CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThan(root.get(Comment_.up), 10);
            }
        };
    }
}
```

Spec 사용하기
```
    commentRepository.findAll(CommentSpecs.isBest().and(CommentSpecs.isGood()));
```
=> Spec 구현에 따라서 사용되는 쪽의 쿼리 가독성을 제공한다.

---
#### 트랜젝션
스프링 데이터 JPA가 제공하는 Repository의 모든 메소드에는 기본적으로 @Transactional이 적용되어 있다.

@Transactional
 : 클래스, 인터페이스, 메소드에서 사용할 수 있으며, 메소드에 가장 가까운 애노테이션이 우선 순위가 높다.  
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html
```
    @Transactional(readOnly = true)
    List<CommentSummary> findByPost_Id(Long id);
```

JPA의 구현체로 Hibernate를 사용할 때 트랜잭션을 readOnly로 설정했을 때 장점
 : readOnly는 Flush 모드를 NEVER로 설정하여 Dirty Checking을 하지 않도록 함. => 성능적 이점

---
#### Auditing
엔티티 변경 시점에 언제, 누가 변경했는지에 대한 정보를 기록하는 기능이다.  
스프링 부트 자동 설정 없음.

메인 어플리케이션에서 @EnableJpaAuditing 추가
```
@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

Auditing 기능을 사용할 엔티티에 @EntityListeners(AuditingEntityListener.class) 추가
```
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    ~
}
```

AuditorAware 추가
```
@Component
public class AccountAuditorAware implements AuditorAware<Account> {

    @Override
    public Optional<Account> getCurrentAuditor() {
        //시큐리티를 통해 현재 user를 꺼내오는 로직 필요
        return Optional.empty();
    }
}
```

```
@EnableJpaAuditing(auditorAwareRef = "accountAuditorAware")
```

엔티티 프로퍼티 설정
```
    @CreatedDate
    private Date created;

    @CreatedBy
    @ManyToOne
    private Account createdBy;

    @LastModifiedDate
    private Date updated;

    @LastModifiedBy
    @ManyToOne
    private Account updatedBy;
```

Auditing 대신 @PrePersist와 @PreUpdate등 JPA 라이프 사이클 이벤트를 활용할 수도 있다. 

엔티티 클래스에서 콜백 정의
```
    @PrePersist
    public void prePersist() {
        System.out.println("Pre Persist is called");
        //엔티티가 영속화 되기 전 실행 될 로직을 추가하여 사용
    }
```

