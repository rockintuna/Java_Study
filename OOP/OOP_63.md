# 객체 지향 프로그래밍

#### 63. 예외와 예외 처리  

컴파일 오류 : 프로그램 코드 작성 중 발생하는 문법적 오류   
실행 오류 : 실행중인 프로그램이 의도치 않은 동작을 하거나(bug) 프로그램이 중단되는 오류(runtime error)     
자바는 예외 처리를 통하여 프로그램의 비정상 종료를 막고 log를 남길 수 있음    

시스템 오류(error) : 가상 머신에서 발생, 프로그래머가 처리할 수 없음    
동적 메모리를 다 사용한 경우, stack over flow 등  
예외(Exception) : 프로그램에서 제어할 수 있는 오류     
읽으려는 파일이 없는 경우, 네트웍이나 소켓 연결 오류 등    
자바 프로그램에서는 예외에 대한 처리를 수행 함

Exception 클레스 : 모든 예외 클래스의 최상위 클래스  
IOException, RuntimeException 등     

try~catch 문으로 예외처리하기    
try {   
예외가 발생할 수 있는 동작   
} catch(처리할 예외 타입 e) {  
try블록 안에서 예외가 발생했을 때 수행되는 부분    
}  

try {   
예외가 발생할 수 있는 동작   
} catch(처리할 예외 타입 e) {  
try블록 안에서 예외가 발생했을 때 수행되는 부분    
} finally {     
예외 발생 여부와 상관 없이 항상 수행되는 부분
리소스를 정리하는 코드를 주로 쓴다       
}  

```
package exception;

public class ArrayExceptionTest {
    public static void main(String[] args) {

        int[] arr = new int[5];

        try {
            for (int i = 0; i <= 5; i++) {
                System.out.println(arr[i]);
            }
        }catch(ArrayIndexOutOfBoundsException e) { //IDE가 잡아주지 않으므로 직접 써야함
            System.out.println(e);
            System.out.println("예외 처리");
        } //예외만 처리되고 프로그램은 계속 수행됨
        System.out.println("프로그램 종료");
    }
}
```

+ finally
```
package exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExceptionTest {
    public static void main(String[] args) {

        FileInputStream fis = null;
        System.out.println(new File("").getAbsolutePath()); //working directory 확인

        try {
            fis = new FileInputStream("IdeaProjects/Chapter13/a.txt");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            //return;
        } finally {
            try {
                fis.close(); //stream을 close하는 작업을 진행해야되므로
                System.out.println("finally");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("end");
    }
}
```

try~with~resources 문 (java 7)    
리소스를 자동으로 해제하도록 제공해주는 구문     
해당 리소스가 AutoCloseable(인터페이스)을 구현한 경우 close()를 명시적으로 호출하지 않아도   
try{}블록에서 오픈된 리소스는 정상적인 경우나 예외가 발생한 경우 모두 자동으로 close()가 호출 됨    

FileInputStream의 경우 AutoCloseable이 구현되어 있음  

AutoCloseable를 구현한 클래스 생성
```
package exception;

public class AutoCloseObject implements AutoCloseable{

    @Override
    public void close() throws Exception {
        System.out.println("close()가 호출되었습니다.");
    }
}
```
해당 클래스 예외 처리
```
package exception;

public class AutoCloseTest {
    public static void main(String[] args) {

        try(AutoCloseObject obj = new AutoCloseObject()) {
            throw new Exception(); //exception 강제 발생
        } catch (Exception e) {
            System.out.println(e);
        } //close() 를 명시적으로 호출하지 않지만 내부적으로는 호출 됨
    }
}
```

향상된 try-with-resources 문 (java 9)   
```
package exception;

public class AutoCloseTest {
    public static void main(String[] args) {

        AutoCloseObject obj = new AutoCloseObject();
        
        try(obj) { //선언할 필요 없이 미리 생성된 객체 사용가능 
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
}
```
    
    