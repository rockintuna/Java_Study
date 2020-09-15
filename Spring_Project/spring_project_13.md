## 스프링 MVC 핵심 기술

### HTTP 요청 맵핑하기 

#### HTTP Method

 - GET 요청
    - 클라이언트가 서버의 리소스를 요청할 떄 사용
    - 캐싱 가능 (조건적 GET 가능)
    - 브라우저의 기록에 남고 북마크가 가능
    - URL이 다 보이므로 민감한 데이터에는 맞지 않음
    - idemponent
    
 - POST 요청
    - 클라이언트가 서버의 리소스를 수정하거나 새로만들 떄 사용
    - 서버에 보내는 데이터를 POST 요청 본문에 담는다
    - 캐싱 불가능
    - 브라우저 기록에 남지 않고 북마크가 불가능
    - 데이터 길이 제한이 없다
    
 - PUT 요청
    - URI에 해당하는 데이터를 새로 만들거나 수정할 떄 사용
    - POST와 다른 점은 "URI"에 대한 의미에 있다
        - POST의 URI는 보내는 데이터를 처리할 리소스
        - PUT의 URI는 보내는 데이터에 해당하는 리소스
    - idemponent : 동일한 GET 요청은 항상 동일한 응답을 반환해야 한다.
    
 - PATCH 요청
    - PUT과 비슷하지만, 기존 엔티티와 새 엔티티의 차이점만 보낸다는 차이가 있다.
    - idemponent
    
 - DELETE 요청
    - URI에 해당하는 리소스를 삭제할 떄 사용
    - idemponent
    
#### 스프링 웹 MVC에서 HTTP Method 맵핑하기
@RequestMapping : http 요청 맵핑 어노테이션, 메서드 종류를 지정하지 않으면 기본적으로 모든 메서드를 허용하게 된다.
즉, 메서드 종류를 지정한다는 것은 허용하는 요청의 종류를 제한하는 것과 같다.   
@ResponseBody : 리턴을 응답 본문으로 전달하는 어노테이션  
@GetMapping : @RequestMapping(method=RequestMethod.GET)  

#### URI 패턴 맵핑
@RequestMapping에서 지원하는 패턴  
 - ? : 한 글자  
 - \* : 여러 글자  
 - \** : 여러 패스
 - 정규표현식 가능 (ex: "/{name: [a-z]+}")  

만약 패턴이 중복되는 경우, 가장 구체적으로 맵핑되는 핸들러가 선택됨

#### 컨텐츠 타입 맵핑
 - 특정 타입의 데이터를 담고있는 요청만 처리하도록 핸들러 설정
@RequestMapping(consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)  
Content-Type 헤더로 필터링

 - 특정 타입의 응답을 만드는 핸들러 설정
@RequestMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
Accept 헤더로 필터링, 만약 요청 헤더에 accept가 비워진 경우에는 그냥 처리해줌

consumes/produces를 클래스의 @RequestMapping 어노테이션에서도 설정할 수 있는데,
이때 메서드(핸들러)의 @RequestMapping의 consumes/produces가 설정되면 클래스의 것은 무시된다.

#### 헤더와 파라미터 맵핑
 - @RequestMapping(headers = "key") : 특정 헤더가 있는 요청만 처리
 - @RequestMapping(headers = "!key") : 특정 헤더가 없는 요청만 처리
 - @RequestMapping(headers = "key=value") : 특정 헤더/키 쌍이 있는 요청만 처리
 - @RequestMapping(params = "a") : 특정 요청 매개변수 키를 가지고 있는 요청만 처리
 - @RequestMapping(params = "!a") : 특정 요청 매개변수가 없는 요청만 처리
 - @RequestMapping(params = "a=b") : 특정 요청 매개변수 키/값 쌍을 가지고 있는 요청만 처리
 
#### HEAD와 OPTIONS 요청 처리
HEAD와 OPTIONS http method는 스프링 웹 MVC가 기본으로 제공해주는 기능이다.  

 - HEAD : GET 요청과 동일하지만 응답 본문을 받아오지 않고 응답 헤더만 받아온다.
 - OPTIONS : 응답 헤더 ALLOW로 사용할 수 있는 HTTP Method 목록을 받아온다.
 
#### 커스텀 애노테이션

 - 메타 애노테이션  
 애노테이션에 사용할 수 있는 애노테이션  
 스프링이 제공하는 대부분의 애노테이션은 메타 애노테이션으로 사용할 수 있다
 
 - 조합 애노테이션  
 한개 혹은 여러개의 메타 애노테이션을 조합해서 만든 애노테이션  
 코드를 간결하게 줄일 수 있다  
 보다 구체적인 의미를 부여할 수 있다
 
 - @Retantion  
 해당 애노테이션 정보를 언제까지 유지할 것인가 결정
   - Source : 소스 코드까지만 유지, 컴파일하면 해당 애노테이션 정보는 사라짐
   - Class : 컴파일 한 .class 파일에도 유지, 런타임시 클래스를 메모리로 읽어오면 해당 정보는 사라짐 (default)
   - Runtime : 클래스를 메모리로 읽어왔을때까지 유지, 코드에서 이 정보를 바탕으로 특정 로직을 실행할 수 있다
  
 - @Target  
 해당 애노테이션을 어디에 사용할 수 있는지 결정
 
 - @Documented  
 해당 애노테이션을 사용한 코드의 문서에 그 애노테이션에 대한 정보를 표기할 지 결정
 
```
@RequestMapping(method = RequestMethod.GET, value = "/hello")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetHelloMapping {
}
```
   
### 핸들러 메소드 

#### 핸들러 메소드 아규먼트와 리턴 타입

 - 핸들러 메소드 아규먼트
 : 주로 요청 그 자체 또는 요청에 들어있는 정보를 받아오는데 사용
 
WebRequest, NativeWebRequest, ServletRequest, HttpServletRequest : 요청 또는 응답 자체에 접근 가능한 API

InputStream, Reader, OutputStream, Writer : 요청 본문을 읽어오거나, 응답 본문을 쓸 때 사용할 수 있는 API

PushBuilder : HTTP/2 리소스 푸쉬에 사용 (스프링 5)

HttpMethod : 요청의 Http 메서드에 대한 정보

Locale, TimeZone, ZoneId : LocaleResolver가 분석한 요청의 Locale 정보

@PathVariable : URL 템플릿 변수 읽을 때 사용

@MatrixVariable : URL 경로 중에 키/값 쌍을 읽어올 때 사용

@RequestParam : 서블릿 요청 매게변수 값을 선언한 메소드 아규먼트 타입으로 변환, 단순 타입에서는 생략 가능

@RequestHeader : 요청 해더 값을 선언한 메소드 아규먼트 타입으로 변환 

@RequestBody : 요청 본문을 HttpMessageConverter를 사용해 특정 타입으로 변환

 - 핸들러 메소드 리턴
 : 주로 응답 또는 모델을 렌더링할 뷰에 대한 정보를 제공하는데 사용
 
@ResponseBody : 리턴 값을 HttpMessageConverter를 사용해 응답 본문으로 사용한다.

HttpEntity, ResponseEntity : 응답 본문 뿐 아니라 헤더 정보까지 전체 응답을 만들 때 사용한다.

String : ViewResolver를 사용해서 뷰를 찾을 떄 사용할 뷰 이름

View : 암묵적인 모델 정보를 랜더링할 뷰 인스턴스

Map, Model : 암묵적으로 판단한 뷰를 랜더링할 때 사용할 모델 정보

@ModelAttribute : 암묵적으로 판단한 뷰를 랜더링할 때 사용할 모델 정보에 추가한다.

#### URI 패턴을 아규먼트로

 - @PathVariable  
 요청 URL 패턴의 일부를 핸들러 메소드 아규먼트로 받는 방법  
 타입 변환 지원  
 값이 반드시 있어야 한다.  
 Optional 지원  
 
 - @MatrixVariable  
 @PathVariable과 유사하나 아규먼트로 키/값쌍을 받는다.
 별도의 활성화가 필요하다.
 
```
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        //세미콜론을 제거하지 않도록 설정
        urlPathHelper.setRemoveSemicolonContent(false);
        
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
```

```
    @GetMapping("/events/{id}")
    @ResponseBody
    public Event getEvent(@PathVariable("id") Long id, @MatrixVariable String name) {
        //key = "name", value = ?  
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        return event;
    }
```

#### 요청 매개변수를 아규먼트로

 - @RequestParam  
요청 매개변수에 있는 단순 타입 데이터를 메서드 아규먼트로 받을 수 있다. ("/events?id=20" 또는 폼 데이터)  
값이 반드시 있어야 한다. (또는 required=false, Optional 사용하여 기본값 설정 등)  
String이 아닌 값들은 타입 변환을 지원한다.  
Map을 사용하여 모든 매개변수를 받아올 수 있다.  

```
    @GetMapping("/events")
    @ResponseBody
    public Event getEvent(@RequestParam("id") Long id) {
        Event event = new Event();
        event.setId(id);
        return event;
    }
```

#### @ModelAttribute
여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나 해당 객채를 새로 만들 때 사용  

```
    @GetMapping("/events")
    @ResponseBody
    public Event getEvents(@ModelAttribute Event event) {
        return event;
    }
```

복합 타입 객체의 각 데이터를 바인딩 하는 방법은 URL, 요청 매개변수, 세션 등이 있다.

값을 바인딩할 수 없는 경우 400 에러가 발생하는데, 
이 에러를 직접 다루고 싶은 경우 BindingResult 타입의 아규먼트를 추가한다.

```
    @GetMapping("/events")
    @ResponseBody
    public Event getEvents(@ModelAttribute Event event, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println("================");
            bindingResult.getAllErrors().forEach(c -> {
                System.out.println(c);
            });
        }
        return event;
    }
```

에러 발생 없이 에러정보는 BindingResult에 들어가고, 바인딩 되지 않은 값은 null이 된다.

만약 바인딩 이후에 추가적인 검증작업이 필요한 경우 @Valid 또는 @Validated 애노테이션 사용  
(ex) Event 객체의 limit 속성은 항상 1이상이어야 한다.

```
    @Min(1)
    private Integer limit;
```

```
    @GetMapping("/events")
    @ResponseBody
    public Event getEvents(@Valid @ModelAttribute Event event, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println("================");
            bindingResult.getAllErrors().forEach(c -> {
                System.out.println(c);
            });
        }
        return event;
    }
```
이때 @Valid 검증에 의해 bindingResult에 에러가 들어감에도 불구하고
해당 값은 바인딩 됨에 유의.

#### @Validated
@Valid와는 다르게 validation group이라는 힌트를 사용하여 그룹 클래스를 지정할 수 있다.  
그룹을 지정하지 않은 경우 @Valid와 동일하게 동작한다.

두개의 validation group 생성
```
    interface ValidateLimit {}
    interface ValidateName {}

    private Long id;

    @NotBlank(groups = ValidateName.class)
    private String name;

    @Min(value = 1,groups = ValidateLimit.class)
    private Integer limit;
```

특정 그룹만 검증하기
```
    @GetMapping("/events")
    @ResponseBody
    public Event getEvents(@Validated(Event.ValidateName.class) @ModelAttribute Event event, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println("================");
            bindingResult.getAllErrors().forEach(c -> {
                System.out.println(c.toString());
            });
        }
        return event;
    }
```
 
#### @SessionAttributes
모델 정보를 HTTP 세션에 저장해주는 애노테이션  
HttpSession을 직접 사용할 수도 있지만 @SessionAttributes를 사용하면
설정한 이름에 해당하는 모델 정보를 자동으로 세션에 저장해준다.  
여러 화면 또는 요청에서 사용되는 객체를 공유할 때 사용한다.  
```
@Controller
@SessionAttributes("event")
public class EventController {
```

SessionStatus를 사용해서 특정 폼 처리 완료 후에 세션 데이터를 비우도록 할 수 있다.
```
    @PostMapping("/events")
    public String createEvent(@Validated @ModelAttribute Event event,
                              BindingResult bindingResult,
                              SessionStatus sessionStatus) {
        if(bindingResult.hasErrors()) {
           return "/events/form";
        }
        //todo save
        sessionStatus.setComplete();
        return "redirect:/events/list";
    }
```

#### @SessionAttribute

HTTP 세션에 들어있는 데이터를 참조할 떄 사용한다.  
타입 컨버전을 지원, 데이터 수정을 위해서는 HttpSession 사용  

@SessionAttributes는 해당 컨트롤러 내에서만 특정 모델 객체를 공유할때 사용하는 한편,  
@SessionAttribute는 컨트롤러 밖(인터셉터 또는 필터 등)에서 만들어준 세션 데이터에 접근할 때 사용한다.  

접속 시간을 기록하여 Session에 저장하는 인터셉트를 생성하고 (등록 필요)
핸들러 메서드에서 사용하기

```
public class VisitTimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("visitTime") == null) {
            session.setAttribute("visitTime", LocalDateTime.now());
        }
        return true;
    }
}
```

```
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new VisitTimeInterceptor());
    }
```

```
    @GetMapping("/events/list")
    public String getEvents(@ModelAttribute Event event,
                            Model model,
                            SessionStatus sessionStatus,
                            @SessionAttribute LocalDateTime visitTime) {
        System.out.println(visitTime);
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        model.addAttribute("eventList",eventList);
        sessionStatus.setComplete();
        return "/events/list";
    }
```

#### RedirectAttributes
리다이렉트 할 때는 Model에 들어있는 primitive type 데이터가 URI 쿼리 매개변수에 추가된다.  
(ex localhost:8080/events/list?name=mvc&limit=50)    

스프링부트에서 기능 활성화를 위해서는 Ignore-default-model-on-redirect 프로퍼티를 사용해야 한다.  

application.properties
```
spring.mvc.ignore-default-model-on-redirect=false
```

리다이렉트할 때 원하는 데이터만 전달하고 싶을 때는 RedirectAttributes에 명시적으로 추가하여 사용할 수 있다.  
```
    @PostMapping("/events/form/limit")
    public String eventsFormLimitSubmit(@Validated @ModelAttribute Event event,
                                        BindingResult bindingResult,
                                        SessionStatus sessionStatus,
                                        RedirectAttributes attributes) {
        if(bindingResult.hasErrors()) {
            return "/events/form-limit";
        }
        //todo save
        attributes.addAttribute("name", event.getName());
        attributes.addAttribute("limit", event.getLimit());
        sessionStatus.setComplete();
        return "redirect:/events/list";
    }
```

리다이렉트 요청을 처리하는 곳에서 쿼리 매개변수를 @RequestParam 또는 @ModelAttribute로 받을 수 있다.
```
    @GetMapping("/events/list")
    public String getEvents(@ModelAttribute("newEvent") Event event,
                            Model model,
                            @SessionAttribute LocalDateTime visitTime) {
        System.out.println(visitTime);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        model.addAttribute("eventList",eventList);
        return "/events/list";
    }
```
요청 매개변수를 복합객체로 받기 위해 @ModelAttribute를 사용할 때, 
@SessionAttributes에서 세션에 저장한 이름과 동일하게 받는다면
우선적으로 객체를 세션에서 찾게 되기 때문에 에러가 발생할 수 있다. 
그러므로 SessionAttributes와는 다른 이름으로 받아야 요청 매개변수를 통해 객체로 바인딩할 수 있다.  

#### Flash Attributes
주로 리다이렉트 중에 데이터를 전달하는 목적으로 사용한다.  
데이터가 URI에 노출되지 않고 임의의 객체를 사용할 수 있다.  

RedirectAttributes의 addFlashAttribute 메서드를 사용하여 객체를 HTTP 세션에 저장하여 넘겨준다.  
리다이렉트 요청을 처리 한 다음 세션에서 제거된다.  
```
    @PostMapping("/events/form/limit")
    public String eventsFormLimitSubmit(@Validated @ModelAttribute Event event,
                                        BindingResult bindingResult,
                                        SessionStatus sessionStatus,
                                        RedirectAttributes attributes) {
        if(bindingResult.hasErrors()) {
            return "/events/form-limit";
        }
        //todo save
        attributes.addFlashAttribute("newEvent",event);
        sessionStatus.setComplete();
        return "redirect:/events/list";
    }
```

넘겨받은 객체는 Model에 자동으로 들어가기 때문에
리다이렉트 요청을 처리하는 곳에서는 @ModelAttribute로 선언하여 객체를 받을 필요 없이 
Model에서 꺼내어 쓰면 된다.  
```
    @GetMapping("/events/list")
    public String getEvents(Model model,
                            @SessionAttribute LocalDateTime visitTime) {
        System.out.println(visitTime);

        List<Event> eventList = new ArrayList<>();
        eventList.add((Event) model.asMap().get("newEvent"));

        model.addAttribute("eventList",eventList);
        return "/events/list";
    }
```

#### MultipartFile
파일 업로드에 사용하는 메소드 아규먼트  
MultipartResolver 빈이 설정되어 있어야 사용할 수 있다.
(스프링부트에서는 MultipartAutoConfiguration에 의해 자동으로 설정된다.)  
POST mulripart/form-data 요청이 들어있는 파일을 참조할 수 있다.  
List<MultipartFile> 아규먼트로 여러 파일을 참조할 수 있다.  

파일 업로드 폼 예시
```
<body>
<div th:if="${message}">
    <h2 th:text="${message}"/>
</div>

<form method="POST" enctype="multipart/form-data" action="#" th:action="@{/file}">
    File : <input type="file" name="file"/>
    <input type="submit" name="Upload"/>
</form>
```

```
    @GetMapping("/file")
    public String fileUploadForm(Model model) {
        return "files/index";
    }

    @PostMapping("/file")
    public String fileUpload(@RequestParam MultipartFile file,
                             RedirectAttributes attributes) {
        //todo file storage service
        String message = file.getOriginalFilename() + " is uploaded.";

        attributes.addFlashAttribute("message",message);
        return "redirect:/file";
    }
```

#### File Download
스프링 ResourceLoader 사용  

 - ResouceLoader
 : 리소스를 읽어오는 기능을 제공하는 인터페이스  
 Resource getResource(String location);
  
파일 다운로드 응답 헤더에 설정 할 내용
 - CONTENT_DISPOSITION : 사용자가 파일을 받을 때 사용할 파일 이름
 - CONTENT_TYPE : 파일의 미디어 타입
 - CONTENT_LENGTH : 파일의 크기
 
```
    @Autowired
    private ResourceLoader resourceLoader;    

    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:"+fileName);

        File file = resource.getFile();

        Tika tika = new Tika();
        String mediaType = tika.detect(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
                .header(HttpHeaders.CONTENT_TYPE, mediaType)
                .header(HttpHeaders.CONTENT_LENGTH, file.length()+"")
                .body(resource);
    }
```

 - 리턴 타입 ResponseEntity : 응답 본문, 응답 헤더, 응답 상태 코드를 설정할 수 있는 리턴 타입이다. <>에는 응답 본문의 타입을 넣어준다.  
 - Tika : File의 미디어 타입을 알아낼 수 있다.
```
        <dependency>
            <groupId>org.apache.tika</groupId>
            <artifactId>tika-core</artifactId>
            <version>1.20</version>
        </dependency>

```

#### @RequestBody & HttpEntity

 - @RequestBody  
 요청 본문에 들어있는 데이터를 HttpMessageConverter를 통해 변환한 객체로 받을 수 있다.  
 @Valid 또는 @Validated와 같이 사용하여 검증을 진행할 수 있다.  
 BindingResult 아규먼트를 사용해 코드로 바인딩 또는 검증 에러를 확인할 수 있다.  
 
```
@RestController
@RequestMapping("/api/events")
public class EventApi {

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return event;
    }
}
```
 
 - HttpEntity  
 @RequestBody와 비슷하지만 추가적으로 요청 헤더 정보를 사용할 수 있다.  

```
@RestController
@RequestMapping("/api/events")
public class EventApi {

    @PostMapping
    public Event createEvent(HttpEntity<Event> request) {
        System.out.println(request.getHeaders().getContentType());
        return request.getBody();
    }
}
```
 
#### @ResponseBody & ResponseEntity

 - @ResponseBody  
 핸들러 메서드의 리턴 데이터를 HttpMessageConverter를 통해 변환 후에 응답 본문 메시지로 보낼때 사용  
 @RestController를 사용하면 모든 핸들러 메서드에 적용된다.  

```
    @PostMapping
    @ResponseBody
    public Event createEvent(@RequestBody @Valid Event event) {
        return event;
    }
```
 - ResponseEntity  
 응답 헤더 상태 코드 본문을 직접 다루고 싶은 경우에 사용한다.  
 
```
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @Valid Event event,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(event);
    }
```

#### @ModelAttribute의 다른 사용법
 - 해당 컨트롤러의 모든 요청에서 공통적으로 사용하는 모델 초기화하기  

```
    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories",List.of("study", "seminar", "hobby", "social"));
    }
```

 - 메서드에 붙이면 리턴하는 객체를 모델에 넣어준다.  
 이 때는 RequestToViewNameTranslator에 의해 URL 이름으로 view 선택

```
    @GetMapping("/events/form-name")
    @ModelAttribute
    public Event newEvent() {
        return new Event();
    }
```

#### DataBinder : @InitBinder
특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용  

바인딩 설정  
webDataBinder.setDisallowedFields();

포매터 설정  
webDataBinder.addCustomFormatter();

Validator 설정  
webDataBinder.addValidators();

또는 특정 이름의 모델 객체에만 바인딩 또는 검증 설정 적용
```
    @InitBinder("event")
```

이벤트에 대한 데이터를 바인딩할때 id를 바인딩하지 않도록 설정
```
    @InitBinder("event")
    public void initEventBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }
```

#### 예외 처리 핸들러 : @ExceptionHandler
특정 예외가 발생한 요청을 처리하는 핸들러를 정의한다.  
일반적인 핸들러 메서드와 비슷하게 작성할 수 있다.  

```
    @ExceptionHandler
    public String eventErrorHandler(EventException exception, Model model) {
        model.addAttribute("message", "event error");
        return "error";
    }
```

Rest API의 경우 보통 ResponseEntity를 사용하여 예외에 대한 정보를 응답 본문으로 전달한다.  
```
    @ExceptionHandler
    public ResponseEntity errorHandler(EventException exception) {
        return ResponseEntity.badRequest().body("can't create event.");
    }
```

#### 전역 컨트롤러 : @ControllerAdvice  

@InitBinder, @ExceptionHandler, @ModelAttribute 를 모든 컨트롤러에서 사용하고 싶을 때 사용

```
@ControllerAdvice
public class GlobalController {
    @ExceptionHandler
    public String eventErrorHandler(EventException exception, Model model) {
        model.addAttribute("message", "event error");
        return "error";
    }

    @InitBinder
    public void initEventBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", List.of("study", "seminar", "hobby", "social"));
    }

}
```

적용할 범위를 지정할 수 있다.  
 - 특정 애노테이션이 걸려있는 컨트롤러에만 적용
```
@ControllerAdvice(annotations = RestController.class)
```
 - 특정 패키지 이하의 컨트롤러에만 적용
```
@ControllerAdvice("me.rockintuna.demowebmvc")
```
 - 특정 클래스 타입에만 적용 
```
@ControllerAdvice(assignableTypes = {EventController.class, EventApi.class})
```

