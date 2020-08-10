# 스프링 프로젝트
### 스프링 프레임워크 실습 및 프로젝트    

#### 03. AOP (Aspect Oriented Programming)

AOP의 핵심은 흩어진 코드(여러 곳에서 반복되는 코드)를 한 곳으로 모으는 것.

대표적으로 @Transactional 어노테이션이 있는데,
트랜잭션 처리(트랜잭션 시작, 커밋/롤백)에 대한 동일한 부분을 어노테이션 하나로 처리한다.  
참고로 JPA Repository의 모든 메서드에는 @Transactional이 생략되어 적용되어있다.  


AOP 직접 활용하기  
Annotation 생성
```
@Target(ElementType.METHOD) // 이 어노테이션은 메서드에 붙일것.  
@Retention(RetentionPolicy.RUNTIME) // 코드의 유지기간  
public interface LogExecutionTime {
}
```

실제 Aspect 생성
```
@Component //Aspect는 Bean이어야 한다.
@Aspect
public class LogAspect {
	Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object proceed = joinPoint.proceed();

		stopWatch.stop();
		logger.info(stopWatch.prettyPrint());

		return proceed;
	}
}
```

Annotation 달기
```
	@LogExecutionTime
	@GetMapping("/owners/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwners";
	}
```

결과적으로, AOP를 통하여 하나의 클래스(Aspect)로 
Annotation이 달려있는 모든 메서드는 수행 시간이 계산된다.  
    
    