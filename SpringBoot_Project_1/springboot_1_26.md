# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 26. 리뷰 작성  

POST /restaurants/{id}/reviews

Review Controller Test
```
@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValidAttributes() throws Exception {
        given(reviewService.addReview(any(),eq(1L))).willReturn(
                Review.builder().id(1004L).build()
        );

        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON) //json 타입이고
                .content("{\"name\":\"jilee\",\"score\":3,\"description\":\"Mat-it-da\"}")) //이렇게 넣을거야
                .andExpect(status().isCreated()) //잘 만들어지나?(status 201?)
                .andExpect(header().string("location", "/restaurants/1/reviews/1004")); //header의 location 확인

        verify(reviewService).addReview(any(),eq(1L)); //addReview가 호출되니?
    }

    @Test
    public void createWithInValidAttributes() throws Exception {
        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON) //json 타입이고
                .content("{}")) //이렇게 넣을거야
                .andExpect(status().isBadRequest()); //status 404?

        verify(reviewService,never()).addReview(any(),eq(1L)); //addReview가 호출되지 않니?
    }

}
```

Review Controller
```
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {

        Review review = reviewService.addReview(resource,restaurantId);

        String url = "/restaurants/"+restaurantId+"/reviews/"+review.getId();
        return ResponseEntity.created(new URI(url))
                .body("{}");

    }
}
```

Service Test
```
public class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    public void addReview() {
        Review review = Review.builder().name("jilee").score(3).description("Mat-it-da").build(); //Review 객체 생성
        reviewService.addReview(review,1004L); //기능 실행할때

        verify(reviewRepository).save(any()); //save가 호출되니?
    }

}
```

Service
```
@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review,Long restaurantId) {
        review.setRestaurantId(restaurantId);
        return reviewRepository.save(review);
    }
}
```

Review Repository
```
public interface ReviewRepository extends CrudRepository<Review, Long> {

    Review save(Review review);

    List<Review> findAllByRestaurantId(Long restaurantId);
}
```
    
    