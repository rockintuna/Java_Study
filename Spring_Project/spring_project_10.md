## Spring MVC 설정

### Spring MVC Bean 설정

DispatcherServlet의 기본 전략에만 의존하기에는 확장성이 저하될 수 있기 때문에 추가적으로 설정할 수 있어야 한다.  

@Configuration을 사용한 자바 파일에 스프링 MVC 구성요소를 직접 빈으로 등록할 수 있다.

```
@Configuration
public class WebConfig {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
```

### @EnableWebMvc
애노테이션 기반 스프링 MVC를 사용할 때 편리한 웹 MVC 기본 설정  
@EnableWebMvc를 사용하면 DelegatingWebMvcConfiguration.class를 import하여 
애노테이션 기반 스프링 MVC 환경에 적합한 여러가지 설정 및 전략들을 추가하고 변경한다.

```
@Configuration
@EnableWebMvc
public class WebConfig {

}
```

@EnableWebMvc를 사용할 때는 ApplicationContext에 Servlet Context가 등록되어야 한다.
```
public class WebApplication implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setServletContext(servletContext);
        context.register(WebConfig.class);
        context.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic app = servletContext.addServlet("app", dispatcherServlet);
        app.addMapping("/app/*");
    }
}
```


### WebMvcConfigurer
특히, DelegatingWebMvcConfiguration을 사용하면 Formatter나 Interceptor를 추가하는 등 추가적인 설정이 더 쉬워진다는 장점도 있다.  
WebMvcConfigurer는 @EnableWebMvc가 제공하는 빈을 커스터마이징할 수 있는 기능을 제공하는 인터페이스이다.  

```
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF",".jsp");
    }
}
```

새로운 빈을 추가할 필요 없이 @EnableWebMvc가 제공하는 빈에 추가 설정을 한다는 장점이 있다.  

### 스프링 부트의 스프링 MVC 설정
추가적인 전략이 자동으로 DispatcherServlet에 추가된다.  

HandlerMapping으로 resourceHandlerMapping 추가, 정적 resource 지원  
 
