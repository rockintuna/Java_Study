## PSA (Portable Service Abstraction)

이미 잘 만들어진 인터페이스를 사용하는 것.
확장성이 좋지 못한 코드 또는 기술에 특화되어 있는 코드에 사용하면 좋다.

Spring Framework이 제공하는 API는 대부분이 추상화되어있다.  

---
####추상화 예제

- 스프링 트랜잭션  
@Transactional 어노테이션의 Aspect는 PlatformTransactionManager 인터페이스를 사용하여 코딩되어 있는데,
그렇기 때문에 PlatformTransactionManager에 대한 구현체
(JpaTransactionManager, DatasourceTransactionManager, HibernateTransactionManager 등)가 바뀌더라도 
@Transactional Aspect의 코드가 변경될 필요가 없다.  

- 스프링 캐시  
@Cacheable / @CacheEvict 등 스프링 캐시 관련 어노테이션의 Aspect는
CacheManager 인터페이스를 사용하여 코딩되어있으며,  
위와 동일하게 구현체가 바뀌더라도 Aspect 코드는 변경될 필요가 없다.

- 스프링 웹 MVC  
@Controller / @RequestMapping 등 스프링 웹 MVC 관련 어노테이션은
의존성에 따라 Servlet을 사용할 수도 있고 Reactive를 사용할 수도 있다.  
고로, 해당 어노테이션을 사용한 나의 코드는 변경하기 쉽거나 변경되지 않아도 된다.  
    
    