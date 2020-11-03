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

#### Null 처리
 - Spring Data 2.0부터 자바 8의 Optional을 지원한다.
 => 반환 값이 없을 때 Null과는 다르며 Optional 객체에서 사용할 수 있는 메소드를 사용할 수 있다.

 - 콜렉션은 Null을 리턴하지 않고 비어있는 콜렉션을 리턴한다.
 => 콜렉션 Null 체크 하지 말자.

 - Null 애노테이션(@NonNullApi, @NonNull, @Nullable)을 지원한다.
 => 런타임 시에 Null여부를 체크할 수 있다.

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


#### 비동기 쿼리 메소드

#### 커스텀 리포지토리

#### 기본 리포지토리 커스터마이징

#### 도메인 이벤트

#### QueryDSL 연동

#### 웹 기능


### Spring Data JPA
 
