## Null-safety
Spring 5부터 추가된 Null 관련 어노테이션.  
org.springframework.lang

컴파일 시점에 최대한 NullPointerException을 방지할 수 있다.  

@NonNull : IntelliJ에서 null이면 미리 경고받을 수 있다.  
```
    @NonNull //return이 Null인지 확인
    public String greeting(@NonNull String name) { //매개변수가 Null인지 확인
        return "Hi! "+name;
    }
```
@Nullable  

패키지 레벨 설정 (설정된 패키지 이하로 모두 설정)  
@NonNullApi  
@NonNullFields   