## 객체지향 쿼리 언어 (JPQL)
JPA는 다양한 쿼리 방법을 지원한다.  
(JPQL, JPA Criteria, QueryDSL, 네이티브 SQL, JDBC API 직접 사용)  

JPA를 사용하면 엔티티 객체를 중심으로 개발하게 된다.  
검색을 할 때도 테이블이 아닌 객체를 대상으로 검색을 할 필요가 있다.  
이때, 모든 DB를 객체로 변환하는 것은 사실상 불가능하기 때문에 
애플리케이션에서 필요로 하는 데이터만 객체로 변환하기 위해 결국 검색 조건이 포함된 SQL이 필요하게 된다. 

JPQL : 
SQL을 추상화한 객체 지향 쿼리 언어이다.  
JPQL을 통해서 객체를 대상으로 SQL과 유사한 문법으로 쿼리할 수 있다.
SQL을 추상화했기 때문에 특정 데이터베이스 SQL에 의존하지 않는다.

JPA Criteria : 
문자가 아닌 자바 코드로 JPQL을 작성할 수 있다.  
동적 쿼리에 용이하지만 너무 복잡하고 실용성이 없다는 단점이 있다.

QueryDSL : 
문자가 아닌 자바 코드로 JPQL을 작성할 수 있다.  
동적 쿼리 작성이 편리하고 컴파일 시점에 문법 오류를 찾을 수 있다.  
단순하고 쉬워서 실무 사용에 권장된다.  

네이티브 SQL : 
SQL을 직접 사용하는 기능.  
JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능이나 문법을 요구할 때 사용한다.

### 기본 문법

#### 기본 문법과 쿼리 API

"select m from Member as m where m.age > 18"  

 - 엔티티와 속성은 대소문자를 구분한다. (Member, age)  
 - JPQL 키워드는 대소문자를 구분하지 않는다. (SELECT, FROM)  
 - 테이블이 아닌 엔티티 이름을 사용한다. (Member)  
 - 별칭이 필수이다.(m)

쿼리의 타입  
TypedQuery : 반환 타입이 명확할 때 사용  
```
TypedQuery<Movie> query = em.createQuery("select m from Movie m", Movie.class);
```
Query : 반환 타입이 명확하지 않을 때 사용  
```
Query query = em.createQuery("select m.name, m.age from Movie m");
```

결과 조회 API  
query.getResultList() : 결과가 하나 이상일 때, 리스트를 반환, 결과가 없으면 빈 리스트를 반환한다.  
query.getSingleResult() : 결과가 정확히 하나일 때, 단일 객체를 반환, 결과가 없거나 두 개 이상이면 예외 발생

파라미터 바인딩
 - 이름 기준  
```
TypedQuery query = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class);
query.setParameter("username", usernameParam);
```
 - 위치 기준
```
TypedQuery query = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class);
query.setParameter(1, usernameParam);
```

#### 프로젝션
SELECT 절에 조회할 대상(엔티티, 임베디드 타입, 스칼라 타입)을 지정하는 것.  
이 대상들은 영속성 컨텍스트에서 관리되게 된다.

SELECT m FROM Member m : 엔티티 프로젝션
SELECT m.team FROM Member m : 엔티티 프로젝션
SELECT m.address FROM Member m : 임베디드 타입 프로젝션
SELECT m.username, m.age FROM Member m : 스칼라 타입 프로젝션

여러 값을 프로젝션으로 가지는 쿼리 결과 조회하는 방법
 - Query 타입으로 조회
```
Query query = em.createQuery("select m.name, m.age from Movie m");
List resultList = query.getResultList();

Object o = resultList.get(0);
Object[] result = (Object[]) o;
System.out.println("name = "+result[0]);
System.out.println("age = "+result[1]);
```

 - Object[] 타입으로 조회
```
TypedQuery<Object[]> query = em.createQuery("select m.name, m.age from Movie m");
List<Object[]> resultList = query.getResultList();

Object[] result = resultList.get(0);
System.out.println("name = "+result[0]);
System.out.println("age = "+result[1]);
```
 
 - new 명령어로 조회  
단순값을 DTO로 바로 조회할 수 있다.  
패키지명을 포함한 전체 클래스명을 입력해야 하고 순서와 타입이 일치하는 생성자가 필요하다.
```
List<MemberDTO> result = em.createQuery("select new mypack.MemberDTO(m.name, m.age) from Member m", MemberDto.class)
        .getResultList();

MemberDTO memberDTO = result.get(0);
System.out.println("name = "+memberDTO.getName());
System.out.println("age = "+memberDTO.getAge());
```

#### 페이징
JPA는 페이징을 다음 두 API로 추상화하였다.  

 - setFirstResult(int startPosition) : 조회 시작 위치(0 부터)
 - setMaxResult(int maxResult) : 조회 할 데이터 수
```
    em.createQuery("select m from Member m order by m.age desc", Member.class);
        .setFirstResult(0)
        .setMaxResult(10)
        .getResultList();
```

#### 조인
JOIN 절에도 테이블이 아닌 엔티티가 와야한다. (ex) m.team t

JOIN : 내부 조인  
LEFT JOIN : 외부 조인  

세타 조인도 가능은 하다. (FROM에 두 엔티티)
```
em.createQuery("SELECT m,t FROM Member m, Team t WHERE m.username = t.name");
```

ON 절 : 조인 대상에서 필터링 할 때 사용한다.
```
em.createQuery("SELECT m,t FROM Member m LEFT JOIN m.team t ON t.name = 'A'"); 
```
또는 연관관계 없는 엔티티와의 외부 조인에서 사용한다.
```
em.createQuery("SELECT m,t FROM Member m LEFT JOIN m.team t ON m.username = t.name"); 
```

#### 서브 쿼리
쿼리 내부에 또 한번 쿼리를 작성하여 그 결과를 변수처럼 사용하는 방법.

 - 평균 나이보다 많은 회원
```
em.createQuery("SELECT m FROM Member m where m.age > (SELECT avg(m2.age) FROM Member m2)");
```

서브 쿼리 지원 합수  
EXISTS (sub query): 서브쿼리에 결과가 존재하면 참 (<> NOT EXISTS)

ALL|ANY|SOME (sub query)  
ALL : 조건이 모두 만족하면 참
ANY : 조건이 하나라도 만족하면 참 (== SOME)

IN (sub query) : 서브 쿼리의 결과 중 하나라도 같은 것이 있다면 참 (<> NOT IN)

JPA 서브 쿼리 한계  
 - JPA는 WHERE, HAVING 절에서만 서브 쿼리를 사용할 수 있다.(하이버네이트에서는 SELECT 절에서도 사용가능하다.)
 - JPQL은 FROM 절에서 서브 쿼리가 불가능하다.
 
#### JPQL 타입 표현

문자 : 'Hello', 'She''s'  
숫자 : 10L, 10D, 10F  
Boolean : TRUE, FALSE  
ENUM : example.MemberType.ADMIN (패키지명을 포함해야한다, 길기 때문에 보통은 파라미터 바운딩 사용)  
엔티티 타입 : TYPE(i) = Book (상속 관계에서 사용, SQL에서는 DTYPE으로 필터링)
```
"select i from Item i where type(i) = Book"
```

#### CASE 식
SELECT 절에 조건을 추가하여 결과를 조건에 따라 다르게 받을 수 있다.
 - 기본 CASE 식
```
SELECT 
    CASE WHEN m.age <= 10 THEN '학생요금'
         WHEN m.age >= 60 THEN '경로요금'
         ELSE '일반요금'
    END
FROM Member m
```

 - 단순 CASE 식
```
SELECT 
    CASE t.name
         WHEN 'A' THEN '100명'
         WHEN 'B' THEN '200명'
         ELSE '인원제한없음'
    END
FROM Team t
```

 - COALESCE : 첫번째 값이 null이 아니면 두번째 주어진 값을 반환
```
SELECT COALESCE(m.username, '이름 없는 회원') FROM Member m
```

 - NULLIF : 두 값이 같으면 null을 반환, 다르면 첫번째 값 반환 
```
SELECT NULLIF(m.username, '관리자') FROM Member m
```

#### JPQL 사용자 정의 함수
사용하기 전에 방언 설정에서 먼저 추가해야 한다.
```
public class MyH2Dialect extends H2Dialect {
    public MyH2Dialect() {
        registerFunction("group_concat", new StandardSQLFunction("group_concat",StandardBasicTypes.STRING));
    }
}
```

hibernate.dialect 변경
```
<property name="hibernate.dialect" value="dialect.MyH2Dialect"/>
```

사용 방법
```
em.createQuery("select function('group_concat',m.username) from Member m", String.class);
```
```
em.createQuery("select group_concat(m.username) from Member m", String.class);
```

### 중급 문법

#### 경로 표현식
.(점)을 찍어서 객체 그래프를 탐색하는 것

경로 표현식 용어 정리
 - 상태 필드 : 단순히 값을 저장하기 위한 필드
 - 연관 필드 : 연관 관계를 위한 필드
    - 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티
    - 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬렉션
    
경로 표현식의 특징
 - 상태 필드 : 경로 탐색의 끝이며 더 이상 탐색할 수 없다.
 - 단일 값 연관 필드 : 묵시적 내부 조인 발생, 추가적으로 탐색할 수 있다.
 - 컬렉션 값 연관 필드 : 묵시적 내부 조인 발생, 더 이상 탐색할 수 없다. 

묵시적 조인의 주의사항
 - 항상 내부 조인
 - 컬렉션은 경로 탐색의 끝이며 추가 탐색을 하려면 명시적 조인을 통해 별칭을 얻어야 한다.
 - 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM절에 영향을 준다.

=> 가급적 묵시적 조인 대신 명시적 조인 사용.
 
#### 페치 조인 (fetch join)
SQL의 조인 종류가 아니고 JPQL에서 성능 최적화를 위해 제공하는 기능이다.  
연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회(즉시 로딩)하는 기능이다.  
일반 조인과 다른 점은 연관된 엔티티를 함께 조회하느냐 마느냐의 차이이다.  

JPQL
```
"select m from Member m join fetch m.team"
```
SQL 
```
select M.*, T.* from MEMBER M inner join TEAM T on M.TEAM_ID = T.ID;
```
JPQL에서는 select절에 Member만 있지만 SQL에서는 TEAM 까지 같이 조회한다.  
이것은 지연로딩으로 설정하더라도 프록시로 조회하지 않고 실제 데이터를 조회하는데,
엔티티에 직접 적용하는 글로벌 로딩 전략보다 페치 조인이 우선하기 때문이다.

한편, 컬렉션 페치 조인에서는 중복된 결과가 나오게 될 수도 있다.  
ex) TEAM A에 MEMBER가 2명인 경우  
```
    List<Team> resultList = em.createQuery("select t from Team t join fetch t.members", Team.class)
            .getResultList();

    for (Team team : resultList) {
        System.out.println("teamname = "+team.getName());
    }
```
결과)  
teamname = A  
teamname = A  

이때, 중복 제거를 위해서 DISTINCT를 사용할 수 있다.  
JPQL의 DISTINCT는 SQL에 DISTINCT를 추가하는 기능 외에
애플리케이션에서 엔티티 중복을 제거하는 기능도 가지고 있다.(같은 식별자를 가진 엔티티 제거)

ex)   
```
    List<Team> resultList = em.createQuery("select distinct t from Team t join fetch t.members", Team.class)
            .getResultList();

    for (Team team : resultList) {
        System.out.println("teamname = "+team.getName());
    }
```
결과)  
teamname = A  

페치 조인의 특징과 한계
 - 페치 조인 대상에는 별칭을 사용할 수 없다. (Hibernate에서는 가능하지만 가급적 사용하지 않는다.)
 - 둘 이상의 컬렉션은 페치 조인할 수 없다.
 - 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없다. (Hibernate는 경고 후 메모리에서 페이징하지만 위험하다.)
 
페이징 API를 써야한다면...  
 => 컬렉션 페치 조인 대신 단일 값 연관 필드를 페치 조인 한다.  
 또는 @BatchSize(size) 애노테이션을 이용하여 페치 조인 대신 N+1 문제를 해결한다.
 
#### 다형성 쿼리

TYPE  
조회 대상을 특정 자식으로 한정할 수 있다.
```
"select i from Item i where type(i) in (Book, Movie)"
```

TREAT  
부모 타입을 특정 자식 타입으로 다룰때 사용한다.(자바 다운 캐스팅과 유사하다.)
```
"select i from Item i where treat(i as Book).auther = 'kim'"
```

#### 엔티티 직접 사용
JPQL에서 엔티티를 사용하면 엔티티의 기본 값(PK)이 사용된다.
이것은 파라미터로 전달하거나 식별자를 직접 전달해도 동일하다.  
ex)  
JPQL
```
"select count(m) from Member m"
```
SQL
```
select count(m.id) from MEMBER m;
```

또는 연관된 엔티티를 사용하면 엔티티의 외래 키 값(FK)이 사용된다.
```
    List<Member> resultList = em.createQuery("select m from Member m where m.team = :team", Member.class)
        .setParameter("team",team1).getResultList();

    for (Member member : resultList) {
        System.out.println("member = " + member.getName());
    }
```
SQL
```
Hibernate: 
    /* select
        m 
    from
        Member m 
    where
        m.team = :team */ select
            member0_.MEMBER_ID as member_i1_4_,
            member0_.createdDate as createdd2_4_,
            member0_.lastModifiedDate as lastmodi3_4_,
            member0_.city as city4_4_,
            member0_.street as street5_4_,
            member0_.zipcode as zipcode6_4_,
            member0_.name as name7_4_,
            member0_.TEAM_ID as team_id8_4_ 
        from
            Member member0_ 
        where
            member0_.TEAM_ID=?
```
JPQL에서는 where 조건에 Team을 주었는데, SQL에서는 FK(TEAM_ID)로 변환되어 실행 됨.

#### Named 쿼리
미리 정의해서 이름을 부여해두고 사용하는 JPQL이다.  
 - 정적 쿼리만 가능하다.  
 - 어노테이션이나 XML에 정의하여 사용한다.
 - 애플리케이션 로딩 시점에 초기화 후 재사용된다.
 - 애플리케이션 로딩 시점에 쿼리를 검증한다.(중요)
 
정의하기
```
@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.name = :name"
)
public class Member {
    ~
}
```

사용하기
```
List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
    .setParameter("name", "member1")
    .getResultList();
```

#### 벌크 연산
쿼리 한번으로 여러 로우(엔티티)를 변경할 수 있다.

 - UPDATE, DELETE 지원
 - excuteUpdate()의 결과는 영향받은 엔티티의 수를 반환한다.
 - INSERT 지원 (insert into .. select, Hibernate)

```
int resultCount = em.createQuery("update Member m set m.createdDate = :createdDate")
    .setParameter("createdDate", LocalDateTime.now())
    .executeUpdate();
```

벌크 연산은 영속성 컨텍스트를 무시하고 DB에 직접 쿼리한다.  
=> 꼬임 방지를 위해 영속성 컨텍스트 작업보다 먼저 벌크 연산을 수행 하거나,
 벌크 연산 후 영속성 컨텍스트를 초기화하자.

