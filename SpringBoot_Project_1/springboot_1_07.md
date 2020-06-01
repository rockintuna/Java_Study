# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 07. 가게 목록, 가게 상세  

가게 목록 Demo    
Controller  
```
package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController //Rest API 를 사용하는 컨트롤러 임을 명시
public class RestaurantController {

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant = new Restaurant(1004l, "Bob zip","Seoul");
        restaurants.add(restaurant);
        //웹에서 출력은 아래와 같다    
        //[{"name":"Bob zip","address":"Seoul","id":1004,"information":"Bob zip in Seoul"}]
        return restaurants;
    }

}
```

Controller TEST
```
package kr.co.fastcampus.eatgo.interfaces;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //spring을 이용하여 테스트 실행
@WebMvcTest(RestaurantController.class) //특정 컨트롤러를 테스트한다고 명시
public class RestaurantControllerTest {

    @Autowired //spring에서 알아서 넣어줄 수 있도록
    private MockMvc mvc; //spring mvc 테스트를 위한 객체    

    @Test
    public void list() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));

    }
}
```

가게 상세 Demo  
가게 저장소 생성 in domain   
```
package kr.co.fastcampus.eatgo.domain;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    List<Restaurant> restaurants = new ArrayList<>(); //가게마다 if문을 쓰지 않기 위해 List로 가게 관리
                                                      //여러 메서드에서 사용될 수 있도록 멤버변수로    

    public RestaurantRepository() {
        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));
        restaurants.add(new Restaurant(2020L,"Cyber Food","Seoul"));
    }

    public List<Restaurant> findAll() {
        return restaurants;
    }

    public Restaurant findById(Long id) {   //찾는 기능을 UI에 구현하지 않고 저장소에서 구현    
        return restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
```

Controller
```
package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Rest API 를 사용하는 컨트롤러 임을 명시
public class RestaurantController {

    private RestaurantRepository repository = new RestaurantRepository(); //저장소 객체 선언

    //UI 레이어는 사용자와 내부의 비즈니스 로직, 도메인 모델들이 서로 상관 없도록 중간 다리 역할만 하는 것이 좋다

    @GetMapping("/restaurants")
    public List<Restaurant> list() {

        List<Restaurant> restaurants = repository.findAll(); //API들의 중복제거를 위해 가게 저장소 사용
        return restaurants;
    }

    @GetMapping("/restaurants/{id}") //{}로 바뀌는 부분을 매핑가능
    public Restaurant detail(@PathVariable("id") Long id) { //주소의 파라미터를 활용할 수 있음

        Restaurant restaurant = repository.findById(id); //저장소에서 기능을 생성하여 사용
        return restaurant;
    }

}
```

Controller Test
```
package kr.co.fastcampus.eatgo.interfaces;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //spring을 이용하여 테스트 실행
@WebMvcTest(RestaurantController.class) //특정 컨트롤러를 테스트한다고 명시
public class RestaurantControllerTest {

    @Autowired //spring에서 알아서 넣어줄 수 있도록
    private MockMvc mvc;

    @Test
    public void list() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
        mvc.perform(MockMvcRequestBuilders.get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2020")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Cyber Food\"")
                ));

    }
```
    
    