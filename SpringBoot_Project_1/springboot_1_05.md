# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 05. Test Driven Development(TDD)   

테스트 주도 개발   
목표 주도 개발    
사용자 중심 개발   
인터페이스 중심 개발     

TDD를 하는 이유는 올바르게 작동하는 깔끔한 코드 작성을 위함     
Refactoring : 작동은 그대로 둔 상태로 코드만 바꾸는 것
Refactoring을 위해서는 올바르게 작동한다는 것을 보장해 줄 Test 코드가 필요하다.    
TDD의 핵심은 Test 코드를 먼저 작성하는 것이다.  

TDD 사이클     
Red : 실패하는 테스트  
Green : 성공하는 테스트    
Refactoring : 테스트는 그대로인 상태에서 내부 구현을 변경  

Demo    
레스토랑 모델 생성
```
package kr.co.fastcampus.eatgo.domain;

public class Restourant {
    public String getName() {
        return "";
    }
}

```

레스토랑 테스트 생성
```
package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RestaurantTests {

    @Test
    public void creation() {
        Restourant restaurant = new Restaurant();
        assertThat(restaurant.getName(), is("Bob zip"));
    }

}
```

테스트 실패 내역이 출력된다.(Red)    
("Bob zip"을 기대했지만 값은 ""이다.)
```
Expected: is "Bob zip"
     but: was ""
Expected :Bob zip
Actual   :
<Click to see difference>
```

=> 성공 하도록 코딩 (Green)
```
package kr.co.fastcampus.eatgo.domain;

public class Restourant {
    public String getName() {
        return "Bob zip";
    }
}
```

Red,Green에서 이 모델을 어떻게 사용할 것인지 먼저 정의  
실제 구현은 그 다음 Refactoring

=> 실제 구현 (Refactoring)
```
package kr.co.fastcampus.eatgo.domain;

public class Restourant {
    private final String name;
    private final String address;

    public Restourant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation() {
        return name+" in "+address;
    }
}
```
    
    
