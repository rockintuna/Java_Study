# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 11. 레이어 분리     

UI Layer는 interfaces 패키지로 분리하여 Controller를 저장했다.    
Domain Layer는 domain 패키지로 분리하여 도메인 모델과 저장소를 저장했다.   

Application Layer를 중간에 추가할 것이다.     
application 패키지 추가 및 MenuItem 도메인 모델 추가    

application이 없을때 menu를 사용하려면    

MenuItem Repository
```
@Component
public class MenuItemRepositoryImpl implements MenuItemRepository{

    private List<MenuItem> menuitems = new ArrayList<MenuItem>();
    
    public MenuItemRepositoryImpl() {
        menuitems.add(new MenuItem("Kimchi"));
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long restaurantId) {
        return this.menuitems;
    }
}
```

Controller
```
    @Autowired //Spring이 알아서 객체를 생성해서 멤버변수에 넣어준다.
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/restaurants/{id}") //{}로 바뀌는 부분을 매핑가능
    public Restaurant detail(@PathVariable("id") Long id) { //주소의 파라미터를 활용할 수 있음

        Restaurant restaurant = restaurantRepository.findById(id); //저장소에서 기능을 생성하여 사용

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        return restaurant;
    }
```  
위처럼 Controller가 복잡해진다...
때문에 application layer를 추가해서 기능적인 부분을 분리한다.  
하려고 하는것은 다음과 같다     
application layer 역할을 해줄 RestaurantService 객체 생성    
Service에서 구현된 기능을 Controller에서 사용   
repository에 대한 의존성을 Service로 이관     

RestaurantService
```
@Service //Spring에서 application으로 정의
public class RestaurantService {
    //의존성 연결을 Service에서 미리
    @Autowired 
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    //Controller에서 사용될 기능 구현    
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    public Restaurant getRestaurant(Long id) {
        //가게 정보 
        Restaurant restaurant =  restaurantRepository.findById(id);
        //메뉴 정보 
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        return restaurant;
    }
}
```

Controller 처럼 Service 또한 Test를 하자   
Service Test    
```
public class RestaurantServiceTest {

    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;

    @Before //모든 Test가 실행되기 전에 수행 됨
    public void setUp() {
        restaurantRepository = new RestaurantRepositoryImpl();
        menuItemRepository = new MenuItemRepositoryImpl();
        restaurantService = new RestaurantService(restaurantRepository,menuItemRepository);
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void getRestaurant() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);

        assertThat(menuItem.getName(), is("Kimchi"));

    }
}
```

Controller가 단순화 된다.     
```
@RestController
public class RestaurantController {

    @Autowired //Service만 의존성 연결
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {

        List<Restaurant> restaurants = restaurantService.getRestaurants();
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {

        Restaurant restaurant = restaurantService.getRestaurant(id); //기본 정보 + 메뉴 정보를 가져올 새로운 메서드
        //repository가 저장소 역할을 했다면, 복잡한 처리들을 저장할 application layer

        return restaurant;
    }

}
```
