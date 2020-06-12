# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 23. 메뉴 관리  

메뉴 관리 : 추가 수정 삭제 기능 추가

한번에 여러게 업데이트 : Bulk Update
Patch "/restaurants/{id}/menuitems"
http status : 200

restaurant은 추가할때 save(), 수정할때 @Transactional으로 직접 수정했었는데,  
메뉴는 Bulk Update를 할거고 save()와 deleteById()를 동시에 사용할 것임.  

MenuItem Controller Test
```
    @Test
    public void bulkUpdate() throws Exception {
        mvc.perform(patch("/restaurants/1/menuitems") //1번 가게의 menuitem을 bulk로 수정할껀데
                .contentType(MediaType.APPLICATION_JSON) //헤더에 content-Type이 Application/json 이고
                .content("[\n" +
                        "  {\n" +
                        "    \"name\": \"Kimchi\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"name\": \"Gukbob\"\n" +
                        "  }\n" +
                        "]")) //이런 array를 입력할꺼야
                .andExpect(status().isOk()); //http status 200이야?
                
        verify(menuItemServices).bulkUpdate(eq(1L), any()); //서비스에서 bulkUpdate()가 올바른 id(1L)로 호출되나?
    }
```

Controller에 PatchMapping 추가
```
    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public void bulkUpdate(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody List<MenuItem> menuItems //MenuItem List를 사용자로부터 입력받는다. 
    ) {
        menuItemServices.bulkUpdate(restaurantId, menuItems);
    }
```


Service 테스트
```
    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        menuItemServices = new MenuItemService(menuItemRepository);
    }


    @Test
    public void bulkUpdate() {
        
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        
        menuItems.add(MenuItem.builder().name("Kimchi").build()); //add
        menuItems.add(MenuItem.builder().id(12L).name("Gukbob").build()); //add
        menuItems.add(MenuItem.builder().id(1004L).destroy(true).build()); //delete
        menuItems.add(MenuItem.builder().id(12L).name("Guk Bob").build()); //update

        menuItemServices.bulkUpdate(1L,menuItems);

        verify(menuItemRepository, times(3)).save(any()); //save 함수가 3회 호출되나?
        verify(menuItemRepository, times(1)).deleteById(1004L); //deleteById 함수가 1회 호출되나?

    }
```

Restaurant Model의 menuItem이 안보이게 수정 
```
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) //Null이 아닐때만 json에 넣어줘라
    private List<MenuItem> menuItems;
```

MenuItem Model에 destory라는 속성 추가
```
    @Transient
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) //json에 포함 X
    private boolean destroy;
```

Service
```
@Service
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository=menuItemRepository;
    }


    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {

        for(MenuItem menuItem : menuItems) {
            update(restaurantId, menuItem);
        }

    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if (menuItem.isDestroy()) {
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurantId);
        menuItemRepository.save(menuItem);
    }

}
```
    
    