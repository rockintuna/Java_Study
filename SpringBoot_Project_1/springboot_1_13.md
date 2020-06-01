# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 13. 가짜 객체  

의존성 주입이 너무 많이 된 경우,  
테스트 하고자 하는 대상 외의 것들에 대한 의존성이 너무 커져서 테스트가 어려워지고     
테스트를 진행하기 위해 의존하는 부분들을 모두 만들어줘야 한다.     

이때 사용할만한 것이 Mock Object.    
Mock Object를 만들기 위해서 spring에서는 Mockito라는 framework 사용    

Spring은 기본적으로 POJO(Plain Old Java Object),  
전통적인 자바 오브젝트를 사용할 것을 권장하고 지원한다. (=>Mockito)  

Controller Test, MockBean
```
@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    //Test하려는 대상은 RestaurantController 이므로 그 외의 대상을 가짜로 배치하려고 함
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {

        //가짜 객체 생성, 가짜 처리 정의(willReturn)
        //실제 서비스, 저장소와는 무관하게 동작
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L,"No Bob zip","Seoul"));
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"No Bob zip\"")
                ));
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1 = new Restaurant(1004L,"No Bob zip","Seoul");
        restaurant1.addMenuItem(new MenuItem("Kimchi"));
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);

        Restaurant restaurant2 = new Restaurant(2020L,"Cyber Food","Seoul");
        restaurant2.addMenuItem(new MenuItem("Kimchi"));
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"No Bob zip\"")
                ))
                .andExpect(content().string(
                        containsString("Kimchi")
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
}
```

Service Test, Mock, Mockito
```
public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock //저장소를 가짜로 주입
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;

    @Before //모든 Test가 실행되기 전에 수행 됨
    public void setUp() {
        MockitoAnnotations.initMocks(this); //현재 Class의 Mock annotation이 붙어있는 곳에 객체 설정

        mockRestaurantRepository();
        mockMenuItemRepository();

        restaurantService = new RestaurantService(restaurantRepository,menuItemRepository);
    }

    private void mockRestaurantRepository() {
        //가짜 restaurant 생성
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant(1004L,"Bob zip","Seoul");
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(restaurant);

    }

    private void mockMenuItemRepository() {
        //가짜 Menu Item 생성   
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = new MenuItem("Kimchi");
        menuItems.add(menuItem);

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);

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
    
    