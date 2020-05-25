# 객체 지향 프로그래밍

#### 66. 자바 입출력 스트림     

* 입출력 스트림 이란    
네트웍에서 자료의 흐름이 물과 같다는 의미에서 유래    
다양한 입출력 장치에 독립적으로 일관성 있는 입출력 방식 제공  
입출력이 구현되는 곳에서는 모두 I/O 스트림을 사용   
:키보드, 파일, 디스크, 메모리 등  

구분  
I/O 대상 기준 : 입력 스트림, 출력 스트림    
자료의 종류 : 바이트 스트림, 문자 스트림    
스트림의 기능 : 기반 스트림, 보조 스트림    

* 입력 or 출력 스트림  
입력 스트림 : 대상으로 부터 자료를 읽어 들이는 스트림  
ex) FileInputStream(바이트), FileReader(문자), BufferedInputStream(보조), BufferedReader, etc   
출력 스트림 : 대상으로 자료를 출력하는 스트림  
ex) FileOutputStream, FileWriter, BufferedOutputStream, BufferedWriter, etc   

* 바이트 or 문자 단위 스트림  
바이트 단위 스트림 : 바이트 단위로 자료를 읽고 씀 (동영상, 음악 등)   
ex) ~Stream
문자 단위 스트림 : 문자는 2byte이상씩 처리해야 함   
ex) ~Reader, ~Writer

* 기반 or 보조 스트림  
기반 스트림 : 대상에 직접 자료를 읽고 쓰는 기능의 스트림   
ex) File~
보조 스트림 : 직접 읽고 쓰는 기능은 없고 추가적인 기능을 제공해 주는 스트림    
기반 스트림이나 또 다른 보조 스트림을 생성자의 매개변수로 포함한다   
ex) Buffered~, InputStreamReader, OutputStreamWriter

* 표준 입출력    
System 클래스의 표준 입출력 멤버
```
public Class System {   
    public static PrintStream out; //표준 출력 스트림
    public static InputStream in;  //표준 입력 스트림
    public static PrintStream err; //표준 에러 스트림
}
```

* System.in 사용하여 입력 받기  
한 바이트 씩 읽어 들임    
한글과 같은 여러 바이트로 된 문자를 읽기 위해서는    
InPutStreamReader와 같은 보조 스트림을 사용해야 함    

```
package stream.inputstream;

import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInTest {
    public static void main(String[] args) {

        System.out.println("입력");

        try {
            int i;
            while ((i = System.in.read()) != '\n') {
                System.out.print((char)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("입력 후 '끝' 이라고 쓰세요.");

        try {
            int i;
            InputStreamReader isr = new InputStreamReader(System.in); //byte -> 문자, 다른 스트림을 생성자의 매개변수로 받음
            while ((i = isr.read()) != '끝') { //'끝'은 2byte 이기 때문에 보조 스트림 필요
                System.out.print((char)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

* Scanner 클래스   
java.util 패키지에 있는 입력 클래스 
문자뿐 아니라 정수, 실수 등 다양한 자료형을 읽을 수 있음   
생성자가 다양하여 여러 소스로부터 자료를 읽을 수 있음  

Scanner(File source) : 파일을 매개변수로 받아 Scanner 생성  
Scanner(InputStream source) : 바이트 스트림을 매개변수로 받아 Scanner 생성  
Scanner(String source) : String을 매개변수로 받아 Scanner 생성    

* Console 클래스   
System.in 을 사용하지 않고 콘솔에서(cmd,terminal) 표준 입출력이 가능     
readLine() : 문자열을 읽습니다.     
readPassword() : 사용자에게 문자열을 보여주지 않고 읽습니다    
reader() : Reader 클래스를 반환   
writer() : PrintWriter 클래스를 반환  
    
```
package stream.inputstream;

import java.io.Console;

public class ConsoleTest {
    public static void main(String[] args) {

        Console console = System.console();

        System.out.println("이름");
        String name = console.readLine();
        System.out.println("비밀 번호");
        char[] password = console.readPassword();

        System.out.println(name);
        System.out.println(password);
    }
}
```
    
    