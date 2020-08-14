## AOP (Aspect Oriented Programming)

흩어진 Concern을 Aspect로 모듈화 할 수 있는 프로그래밍 기법이다.  
AOP의 핵심은 흩어진 코드(여러 곳에서 반복되는 코드)를 한 곳으로 모으는 것.  
AOP의 구현체로 AspectJ와 스프링 AOP가 있다.  

Concern(여러 경로에 흩어져 있는 비슷한 코드)의 변경이 있을때 
Concern들을 Aspect로 모아서 Aspect만 변경함으로서 
모든 경로에서 각각 따로 변경해줘야 하는 번거로움을 방지해준다.  

###AOP 주요 개념
Aspect : 모아진 코드와 정보 집합 모듈  
Advice : 구현될 기능  
PointCut : 어디에 적용되는지의 정보  
Target : 적용이 되는 대상  
Join Point : 생성자 호출 직전/직후, 메서드 호출시 등등 적용 가능한 시점

###AOP 적용 방법
AspectJ  
컴파일 타임 : 자바파일을 class 파일로 만들 때, 추가 컴파일링 필요  
로딩 타임 : class 로딩 시점, java agent 로드타임위버 설정 필요  
Spring AOP (쉽고 현실적임)  
런 타임 : A 빈이 만들어질때 A 타입의 proxy 빈을 만들어서 proxy 빈이 Advice를 먼저 호출  

대표적으로 @Transactional 어노테이션이 있는데,
트랜잭션 처리(트랜잭션 시작, 커밋/롤백)에 대한 동일한 부분을 어노테이션 하나로 처리한다.  
참고로 JPA Repository의 모든 메서드에는 @Transactional이 생략되어 적용되어있다.  

###스프링 AOP
프록시 기반의 AOP 구현체이며 스프링 빈에만 적용할 수 있다.  

프록시 패턴은 주로 접근 제어 또는 부가기능을 추가하기 위해 사용한다.  

1. 프록시는 Real Subject와 같은 인터페이스를 구현하고(같은 타입) Real Subject를 의존한다.    
1. 프록시에만 부가기능을 추가하고 Real Subject는 변경하지 않는다.  
1. 사용자는 부가기능이 구현된 프록시를 사용한다.

이때, 프록시를 작성하는 어려움이나 반복적인 코드, 그리고 객체들 사이의 복잡한 관계 등 문제가 발생하는데  

스프링 AOP는 스프링 IoC 컨테이너가 제공하는 기반시설과 
런 타임 시점에 동적으로 프록시를 만들어 주는 기능(Dynamic Proxy)을 사용하여 이런 문제를 해결한다.  

스프링 IoC 컨테이너의 AbstractAutoProxyCreator(implements BeanPostProcessor)에 의하여
기존 빈을 대체하는 프록시를 동적으로 생성하고 프록시를 기존 빈 대신에 컨테이너에 등록한다.  

Aspect 정의   
Pointcut과 Advice의 정보가 필요하다.  
```
@Component //Aspect Bean 이어야 한다.
@Aspect
public class PerfAspect {

    //Advice 생성
    @Around("execution(* kr.co.testproject..*.EventService.*(..))") 
    //Advice는 @Around 어노테이션이 붙어야 하고 값으로 pointcut을 이름으로 적용하거나 또는 직접 정의할 수 있다.
    //표현식을 사용할 수 있다. 
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable { 
    //ProceedingJoinPoint는 적용될 메소드라고 보면 된다.
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed(); //메소드 호출
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }
}
```

또는 어노테이션으로 pointcut을 정의할 수 있는데
먼저 어노테이션을 생성하고
```
@Target(ElementType.METHOD) // 이 어노테이션은 메서드에 붙일것이다라고 명시.  
@Retention(RetentionPolicy.CLASS) // 어노테이션 정보를 유지할 기간, AOP는 CLASS 이상으로 사용해야 한다.  
public @interface PerfLogging {
}
```

Aspect의 @Around에서 execution 대신 어노테이션 표현식을 사용한다.
```
    @Around("@annotation(PerfLogging)") 
    //@PerfLogging 어노테이션이 붙은 메서드가 pointcut이 된다.  
```

또는 특정 Bean에 적용
```
    @Around("bean(simpleEventService)") 
    //@PerfLogging 어노테이션이 붙은 메서드가 pointcut이 된다.  
```

단순히 메서드 실행 기준 특정 시점에만 부가기능이 있는 경우에는
@Around 대신 @Before, @AfterReturning, @AfterThrowing를 사용해도 된다.
```
    @Before("bean(simpleEventService)")
```


###스프링 AOP 활용하기 예시  
Annotation 생성
```
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface LogExecutionTime {
}
```

실제 Aspect 생성
```
@Component
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
    
    