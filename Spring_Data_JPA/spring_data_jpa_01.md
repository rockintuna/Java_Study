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

#### 인터페이스 정의하기

#### Null 처리

#### 쿼리 만들기

#### 비동기 쿼리 메소드

#### 커스텀 리포지토리

#### 기본 리포지토리 커스터마이징

#### 도메인 이벤트

#### QueryDSL 연동

#### 웹 기능


### Spring Data JPA
 
