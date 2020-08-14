## SpEL (스프링 Expression Language)

###스프링 EL이란? (upto spring3.0)  
1. 객체 그래프를 조회하고 조작하는 기능 제공
1. Unified EL과 비슷하지만, 메소드 호출이나 문자열 템플릿 기능을 지원한다.
1. OGNL, MVEL, JBoss EL 등 여러 EL이 있지만 SpEL은 모든 스프링 프로젝트 전반에 걸쳐 사용하도록 만들어져있다.

###문법  

\#{"표현식"}  
```
    @Value("#{100 + 100}")
    int value;
```
${"프로퍼티"}
```
    @Value("${my.value}")
    int value;
```
참고로 표현식 내부에 프로퍼티를 사용할 수 있지만 반대는 안된다.
```
    @Value("#{${my.value} eq 100}")
    boolean isValue100;
```  
Sample Bean 참조
```
    @Value(#{sample.data})
    int value;
```

SpEL의 해석은 SpelExpressionParser에 의해 이루어진다.  
```
ExpressionParser parser = new SpelExpressionParser();
Expression expression = parser.parseExpression("2 + 100");
Integer value = expression.getValue(Integer.class);
```

###SpEL이 사용되는 곳  
@Value  
@ConditionalOnExpression (EL을 이용하여 선별적으로 Bean 등록)  
스프링 시큐리티   
스프링 데이터 @Query  
Thymeleaf  
    
        