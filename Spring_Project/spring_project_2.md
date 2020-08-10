# 스프링 프로젝트
### 스프링 프레임워크 실습 및 프로젝트    

#### 02. IOC (Inversion of Control)

Spring의 대표적인 특징 중 하나.

여러가지 의미를 내포하며,
주로 의존성에 대한 제어권을 외부에서(DI를 통해) 가지고 있다는 특징이다.  

ApplicationContext (Bean factory)
 : IoC 컨테이너의 핵심적인 인터페이스.
 IoC 컨테이너에 객체를 생성하고 생성된 객체들(Bean)의 의존성을 관리해준다.  
 이해만 해둘뿐, 딱히 이 인터페이스에 대해 직접 보거나 사용할 일은 없는 듯.
 
Bean
 : Spring IoC 컨테이너가 관리하는 객체.  
Class를 Bean으로 등록하려면 Component Scan을 사용하거나
또는 직접 XML이나 자바 설정 파일(@Configuration)에 등록해야 한다.

Component Scanning 
 : Annotation 처리 프로세서 역할을 하는 @ComponentScan에 의하여
 모든 @Component Annotation이 붙은 Class들을 Bean으로 등록한다.
 
```
@Component
    @Repository
    @Service
    @Controller
```
  
직접 등록하기
```
@Configuration
public class Config() {
    @Bean
    public String test() {
        return "test";
    }   
}
```

Bean을 꺼내어 쓰는 방법 
 : @Autowired 또는 @Inject 또는 ApplicationContext.getBean()으로 직접 꺼내기     
Bean으로 등록된 Class만 Bean을 꺼내어 쓸 수 있다.


의존성 주입(Dependency Injection) 
 : @Autowired 또는 @Inject를 사용하는데,
 예외로 생성자가 오직 하나이면서 Bean을 파라미터로 받는다면 @Autowired를 생략하고도 DI를 자동으로 해준다.  
 @Autowired/@Inject를 붙인다면 생성자/필드/Setter에 붙이게 된다.
   
   