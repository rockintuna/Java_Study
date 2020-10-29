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

