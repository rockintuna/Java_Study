# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 10. 의존성 주입 (DI)    

의존성 주입 (Dependency injection)   

의존성 : 의존 관계, 둘 이상의 객체가 서로 협력하는 방법에 대한 것     
ex1) A는 B에 의존 => A가 B를 사용한다 => B의 변화가 A에 영향을 끼친다 => 효과적인 관리가 필요하다    
ex2) RestaurantController는 RestaurantRepository에 의존한다.  

레스토랑 컨트롤러에서 저장소 객체를 생성하고 멤버변수로 사용했었는데, 
이런 작업을 다른 곳에서 할수 있다.    
=> Spring IOC Container

DI는 이런 객체간의 의존 관계를 Spring이 직접 관리해주도록 하는 것   
DI를 위해 두가지 어노테이션을 지원한다.
@Component  
@Autowired  

Demo    
Repository  
```
@Component //Spring이 직접 관리하도록
public class RestaurantRepository {
~
}
```
Controller  
```
@RestController 
public class RestaurantController {

    @Autowired //Spring이 알아서 객체를 생성해서 멤버변수에 넣어준다.
    private RestaurantRepository repository;

~
}
``` 

주의해야 할 것은 WebMvcTest에서 제대로 사용되지 못하기 때문에
직접 의존성 주입을 해야할 필요가 있다   

Controller Test     
```
@RunWith(SpringRunner.class) 
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean //WebMvcTest에서는 제대로된 저장소를 사용할수 없기때문에 직접 의존성 주입
    private RestaurantRepository restaurantRepository;

~
}
```

의존성 주입을 통해 얻을 수 있는 이점은 
객체 간의 의존성을 좀더 유연하게 해준다.     
예를 들어 저장소를 interface로 만들어 의존성 주입을 하면
여러 구현체를 만들수 있고 얼마든지 다른 형태로 변경할 수 있다.
(Controller가 직접적으로 저장소에 의존하고 있던 것을 분리할 수 있다.)     

저장소  
```
package kr.co.fastcampus.eatgo.domain;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> findAll();

    Restaurant findById(Long id);
}
```

구현체
```
package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component //Spring이 직접 관리하도록
public class RestaurantRepositoryImpl implements RestaurantRepository {

~
}
```
Controller
```
@RestController
public class RestaurantController {

    @Autowired 
    private RestaurantRepository repository; //구현체가 아닌 인터페이스를 사용할 수 있다  

~
}
```

주의해야 할 것은 SpyBean에서 실질적인 구현이 없는것은 사용할 수 없으므로
어떤 구현을 사용할 것인지 입력해주어야 한다.   
```
@RunWith(SpringRunner.class) 
@WebMvcTest(RestaurantController.class) 
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean(RestaurantRepositoryImpl.class) //어떤 구현체를 사용할 것인지 입력해줘야 한다     
    private RestaurantRepository restaurantRepository;
```
    
    