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

