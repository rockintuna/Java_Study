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

