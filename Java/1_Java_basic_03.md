# Java 기초

#### 03. 설치 (IntelliJ 사용)

* 아래 ORACLE URL을 통해 원하는 버전의 JDK를 설치한다.    
https://www.oracle.com/java/technologies/javase-downloads.html

* Eclipse 또는 IntelliJ에서 새로운 프로젝트를 생성한다.    
File > New > Project... > Java 선택 > Project SDK를 설치한 Java로 설정 > Next > Next > Project 이름 및 Location 설정 > Finish

* 간단한 프로그램 작성
    * src에 'hello' 패키지 생성 (Command+N Mac)
    * 'hello' 패키지에 'HelloJava' Class 생성 (Command+N Mac)     

   
*주의 :*  
패키지 이름은 소문자(hello)로 한다.    
Class 이름은 대문자(HelloJava)로 *시작*한다.  
    
main 함수는 class를 시작하는? 함수이다.
```
package hello;

public class HelloJava {

    public static void main(String[] args) {

        System.out.println("Hello, Java");
    }

}
```

* 컴파일을 위해서 소스코드를 빌드한다.  
Build > Build Project (Command+F9 [Mac])    

* 프로젝트 디렉토리에 있는 class 파일을 확인한다.     
```
$ ls out/production/First/hello/
HelloJava.class
```

* Run   
Run > Run (Control+Option+R [Mac])    

화면 확인   
Hello, Java     
    
