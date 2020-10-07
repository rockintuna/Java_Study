## JPA (Java Persistence API)

### JPA
자바 진영의 ORM 기술 표준

ORM? 
(Object-Relational Mapping)   
객체와 관계형 DB 사이에서 매핑하는 역할을 담당한다.  
ORM을 통해 객체는 객체대로 DB는 DB대로 설계할 수 있다.  

 - JPA의 핵심  
    - SQL 생성 : 개발자의 SQL 작성에 대한 고충을 덜어준다.
    - JDBC 사용 : JPA도 결국은 JDBC API를 통해서 DB에 접근한다.
    - 패러다임 불일치 해결 : 객체지향 프로그래밍과 관계형 데이터베이스 사이의 페러다임 불일치를 해결해준다.
    
JPA는 인터페이스의 모음이다.  
개발자는 JPA 표준 인터페이스의 구현체를 사용하게 되는데,
특히 Hibernate를 사용하게 된다.  

JPA를 사용하면 일반적인 JDBC 프로그래밍에 비하여
생산성 향상, 유지보수성 향상, 패러다임 불일치 해결, 성능 향상을 기대할 수 있다.  

### 체험하기

#### 의존성

JPA 하이버네이트
```
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.4.21.Final</version>
        </dependency>
```
H2 데이터베이스
```
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.200</version>
        </dependency>
```

#### JPA 설정하기
persistence.xml 파일을 통해 JPA를 설정할 수 있다.

JPA 버전 설정
```
<persistence version="2.2"
```

unit 이름으로 그룹화하여 각 속성을 설정할 수 있다.
```
    <persistence-unit name="hello">
```

필수 : 
JDBC 드라이버 설정,
DB USER/PASSWORD/URL 설정,
dialect 설정 (표준 SQL을 벗어난 특정 DB 문법)

옵션 : 
SQL 보기,
SQL 포메팅,
SQL 주석 넣기, ...

/META-INF/persistence.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_2.xsd">

    <persistence-unit name="hello">
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:~/data/test"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
            
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.use_sql_comments" value="true"/>
        </properties>
    </persistence-unit>

</persistence>
```

#### 코드에서 JPA 사용하기
EntityManagerFactory는 Application 구동시에 한번만 생성되도록 하고
EntityManager는 클라이언트의 요청마다 생성되도록 한다.  
주의) EntityManager는 쓰레드간에 공유하면 안됨.
```
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        //todo

        entityManager.close();
        entityManagerFactory.close();
    }
}
```

#### 객체와 테이블 생성 및 맵핑

테이블 생성
```
create table Account (
id bigint not null,
name varchar(255),
primary key(id)
);
```

Account 객체 생성  
@Entity : JPA가 관리할 객체  
@Id : 데이터베이스의 PK와 맵핑  
```
@Entity
public class Account {

    @Id
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
만약 테이블 이름이나 컬럼 이름이 객체의 것과 다르다면
@Table 또는 @Column 어노테이션으로 직접 맵핑할 수 있다.  

#### Data 변경 해보기  
Data 변경 작업은 Transaction 생성과 시작, 종료(커밋 또는 롤백)가 필요하다.

Insert
```
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Account account = new Account();
            account.setId(1L);
            account.setName("tester");

            entityManager.persist(account);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

       entityManagerFactory.close();
    }
}
```

Update
```
        try {
            Account account = entityManager.find(Account.class, 1L);
            account.setName("modified_name");

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

```
Update 같은 경우 find로 불러온 객체를 변경한 후 다시 persist()를 호출해서 저장하지 않아도 되는데,
이는 JPA가 트랜잭션 commit 시점에 변경된 객체를 감지하고 알아서 update 쿼리를 보내기 때문이다.  

#### JPQL
객체지향 쿼리로서 SQL과 유사하지만 같지는 않다.  
JPQL은 테이블이 아닌 엔티티 객체를 대상으로 쿼리를 작성하게 된다.  
JPQL의 from 절은 테이블이 아닌 객체가 온다.  
```
        try {
            List<Account> accountList = entityManager.createQuery("select a from Account as a", Account.class)
                    .getResultList();

            for(Account account : accountList) {
                System.out.println(account.getId());
                System.out.println(account.getName());
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
```