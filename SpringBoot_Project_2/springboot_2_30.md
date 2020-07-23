# 스프링 부트 프로젝트
### 지인 정보 관리 시스템 만들기  

#### 30. Exception Handling     

Custom Exception 활용하기   
프로젝트 내에서 의도적으로 발생시키는 예외는 별도의 Custom Exception으로 처리하는것이 좋다.  

```
@Slf4j
public class PersonNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Person Entity가 존재하지 않습니다.";
    public PersonNotFoundException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
```
```
@Slf4j
public class RenameNotPermittedException extends RuntimeException{
    private static final String MESSAGE = "이름 변경이 허용되지 않았습니다.";
    public RenameNotPermittedException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
```

Exception의 정보 전달을 위한 DTO 생성
```
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private int code;
    private String message;

    public static ErrorResponse of(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.value(), message);
    };

    public static ErrorResponse of(HttpStatus httpStatus, FieldError fieldError) {
        if (fieldError == null) {
            return new ErrorResponse(httpStatus.value(), "invalid params");
        } else {
            return new ErrorResponse(httpStatus.value(), fieldError.getDefaultMessage());
        }
    }
}
```


Custom Exception이 아니더라도 응답은 일관성있게 나가는 것이 좋다.
=> Exception Handler 사용
특정 클래스에서만 사용하지 않고 API 전역적으로 사용하려고 한다.
=> RestControllerAdvice

서버의 오류메세지를 클라이언트로 보내는것은 보안적으로 위험하다.  
=> 실제 error 메세지 대신 일반적인 문구를 사용

```
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RenameNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRenameNotPermittedException(RenameNotPermittedException ex) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePersonNotFoundException(PersonNotFoundException ex) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getBindingResult().getFieldError());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error("서버 오류 : {}", ex.getMessage(), ex);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 서버 오류가 발생하였습니다.");
    }
}
```   
    
Parameter Validator
```
public class PersonDto {
    //null, empty, blank 예외처리, 디폴트 메시지 설정
    @NotBlank(message = "이름은 필수값입니다.")
    private String name;
```
    
    