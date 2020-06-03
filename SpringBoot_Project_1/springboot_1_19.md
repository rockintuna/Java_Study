# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 19. Lombok     

Annotation Processor 를 이용해서 코딩 자동화

@Setter     
@Getter     
@Builder    

```
@Entity
@Getter //모든 속성들에 대한 getter 가 알아서 만들어 진다
@NoArgsConstructor //디폴트 생성자
@AllArgsConstructor //모든 속성 조합을 사용하는 생성자들
@Builder //Builder 패턴을 사용
public class Restaurant {
    @Id
    @Setter
    @GeneratedValue
    private Long id;
    @Setter //name에 대한 setter 가 알아서 만들어 진다
    private String name;
    private String address;

    @Transient  
    private List<MenuItem> menuItems;

    public String getInformation() {
        return name+" in "+address;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<MenuItem>(menuItems);
    }

    public void setInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
```

Builder로 인스턴스 생성시
```
    @Test
    public void creation() {
        Restaurant restaurant = Restaurant.builder() //@Builder 패턴을 사용, 가독이 좋고 순서가 없다는 장점이 있다.  
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getName(), is("Bob zip"));
        assertThat(restaurant.getAddress(), is("Seoul"));
    }
```
    
    