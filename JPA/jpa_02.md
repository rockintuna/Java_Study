## JPA 내부 구조

### 영속성 관리

#### 영속성 컨텍스트 
JPA를 이해하는데 가장 중요한 용어  
"엔티티를 영구 저장하는 환경"

영속성 컨텍스트는 논리적인 개념이며 눈에 보이지 않는다.  
EntityManager를 이용하여 영속성 컨텍스트에 접근한다.  

J2SE 환경은 EntityManager와 영속성 컨텍스트가 1:1  
J2EE,Spring framework 환경은 EntityManager와 영속성 컨텍스트가 N:1  

엔티티의 생명주기
 - 비영속(transient) : 영속성 컨텍스트와 전혀 관계없는 새로운 상태
 - 영속(managed) : 영속성 컨텍스트에 관리되는 상태
 - 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
 - 삭제(removed) : 삭제된 상태

```
        try {
            //transient
            Account account = new Account();
            account.setName("tester");

            //managed
            entityManager.persist(account);
            //사실 persist()에서 DB에 저장되지는 않는다.
            //영속성 컨텍스트에 들어가게 된다.
            //insert는 commit 시점에 발생한다.

            //detached
            entityManager.detach(account);

            //removed
            entityManager.remove(account);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
```

영속성 컨텍스트의 특징

 - 1차 캐시 : 
 영속 엔티티는 영속성 컨텍스트의 1차 캐시에 속하게 된다.  
 조회를 할때 DB에 접근하기 전에 이 캐시부터 조회하게 된다.  
 1차 캐시에 없어서 DB에 직접 접근하여 얻어진 엔티티도 영속 상태로 1차 캐시에 넣어둔다.

 - 영속 엔티티의 동일성 보장 : 
 1차 캐시를 통해 Repeatable Read isolation level을 DB가 아닌 Application 에서 제공한다.
 
```
        try {
            Account account1 = entityManager.find(Account.class, 1L);
            Account account2 = entityManager.find(Account.class, 1L);

            System.out.println(account1==account2); //true

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
```

 - 엔티티 등록에서 트랜잭션 지원 : 
 데이터 변경시 트랜잭션을 이용하여 쓰기 지연을 할 수 있다.
 persist()에서는 엔티티를 1차 캐시에 저장하고
 엔티티를 분석하여 Insert SQL을 생성한 뒤 쓰기 지연 SQL 저장소에 넣어둔다.  
 transaction.commit() 시점에서 쓰기 지연 SQL의 SQL들이 실행한다.  
 이를 통해 connection 한번으로 모든 처리를 모아서 한번에 할 수 있다는 장점이 있다.
   
```
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
```

 - 엔티티 수정시 변경 감지 : 
 영속 엔티티가 수정되면 JPA가 Dirty Checking 기능으로 엔티 변경을 감지하여 
 commit() 시점에 update SQL을 실행한다.  
 
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

Dirty Checking : 1차 캐시의 스냅샷(1차 캐시에 캐싱될 때의 엔티티 상태)과 현재 엔티티 상태를 비교하는 것,
변경이 있으면 update SQL을 생성하여 쓰기 지연 SQL 저장소에 저장한다.  

#### 플러시 
영속성 컨텍스트의 변경 내용을 데이터베이스에 반영하는 것.

플러시가 발생하면 어떤일이 일어나는가?  
 - Dirty Checking
 - 감지된 Update 쿼리를 쓰기 지연 SQL 저장소에 저장
 - 쓰기 지연 SQL 저장소의 SQL 쿼리들을 DB로 전송
 
플러시는 언제 발생하는가?
 - em.flush()
 - 트랜잭션 커밋
 - JPQL 쿼리 실행
 find()는 1차 캐시에서 찾으면 되지만, JPQL은 실제 SQL이 발생하게 되므로...
 
플러시 모드 옵션 (em.setFlushMode())
 - FlushModeType.Auto : 커밋 또는 쿼리 실행 시 플러시 (default)
 - FlushModeType.Commit : 커밋할 때만 플러시
 
#### 준영속 상태
detached : 영속 상태의 엔티티가 영속성 컨텍스트에서 분리됨.  
영속성 컨텍스트가 제공하는 기능들을 사용할 수 없다.  

준영속 상태로 만드는 방법?
 - em.detach(entity) : 특정 엔티티만 준영속 상태로 전환
 - em.clear() : 영속성 컨텍스트 초기화
 - em.close() : 영속성 컨텍스트 닫기

다시 영속 상태로 변환하려면?
 - em.merge(entity)

준영속 상태의 엔티티는 영속성 컨텍스트에서 관리되지 않기 때문에,
Dirty Checking이 발생하지 않는다.  
따라서, 아래 예시에서는 Update SQL이 DB로 전송되지 않는다.
```
        try {
            Account account = entityManager.find(Account.class, 1L);
            account.setName("modified_name");

            entityManager.detach(account);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
```
