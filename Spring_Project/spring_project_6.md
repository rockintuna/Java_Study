## 데이터 바인딩

프로퍼티 값을 타겟 객체에 설정하는 기능.  
즉, 사용자 입력값(문자열)을 객체가 가지고 있는 int, Date, Boolean 또는 도메인 타입 등으로 변환해 넣어주는 기능이다.  

### PropertyEditor
DataBinder가 변환 작업에 사용하는 인터페이스.  

문자열을 객체로 변환하는 Editor 만들기
```
public class EventEditor extends PropertyEditorSupport {
    
    @Override
    public String setAsText(String text) throws IllegalArgumentException {
        setValue(new Event(Integer.parseInt(text)));
    }
}
```
PropertyEditorSupport는 PropertyEditor의 구현체이다.
참고로 PropertyEditor는 쓰레드 세이프 하지 않기 때문에 절대로 싱글톤 빈으로 등록하지 않는다.  
그리고 Object / String 간 변환만 지원한다.  
사용할 때는 @InitBinder로 사용할 PropertyEditor를 등록할 수 있다.  
```
    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Event.class, new EventEditor());
    }
```

### Converter와 Formatter

#### Converter
Source 타입을 Target 타입으로 변환, 상태 정보가 없기 때문에(stateless) 쓰레드 세이프하다.

```
public class StringToEventConverter implements Converter<String, Event> {
        
    @Override
    public Event convert(String source) {
        return new Event(Integer.parseInt(source));
    }
}
``` 

WebMvcConfigurer 에서 설정, FormatterRegistry 등록하여 사용한다.
```
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEventConverter());
    }
}
```

#### Formatter
보다 Web에 특화된 인터페이스
String과 Object 변환, Locale에 따른 i18n 기능.

```
@Component
public class EventFormatter implements Formatter<Event> {
    @Override
    public Event parse(String text, Locale locale) throws ParseException {
        return new Event(Integer.parseInt(text));
    }
    
    @Override
    public Event print(Event object, Locale locale) {
        return object.getId().toString();
    }
}
```

WebMvcConfigurer 에서 설정, FormatterRegistry에 등록하여 사용한다.
```
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new EventFormatter());
    }
}
```

#### ConversionService
Converter/Formatter는 DataBinder 대신에 ConversionService에서 쓰레드 세이프하게 실제 변환 작업이 수행된다.  
스프링 MVC, 빈(value) 설정, SpEL에서 사용한다.  
DefaultFormattingConversionService
 : ConversionService와 FormatterRegistry를 구현하고 그 외 여러 기본 컨버터와 포매터가 등록되어 있다.  
 
conversionService를 toString()으로 확인하면 등록된 모든 Converter, Formatter를 확인할 수 있다.  

참고로 스프링 부트에서는 DefaultFormattingConversionService를 상속받는 WebConversionService가 빈으로 등록되고
WebMvcConfigurer 설정 없이 Formatter와 Converter 빈을 찾아서 자동으로 등록해준다.  
    
    