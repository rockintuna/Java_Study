# 객체 지향 프로그래밍

#### 65. 다양한 예외 처리  

* 예외 처리 미루기     
throws를 이용하여 예외처리 미루기   
try{} 블록으로 예외처리 하지 않고, 메서드 선언부에 throws를 추가  
예외가 발생한 메서드에서 예외처리를 하지 않고 메서드를 호출한 곳에서 예외처리함    
main()에서 throws를 사용하면 가상 머신에서 처리 됨  

```
package exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ThrowsException {

    public Class loadClass(String fileName, String className) throws FileNotFoundException, ClassNotFoundException { //throws로 미루기

        FileInputStream fis = new FileInputStream(fileName);
        Class c = Class.forName(className);
        return c;
    }
    public static void main(String[] args) {

        ThrowsException test = new ThrowsException();
        try {
            test.loadClass("IdeaProjects/Chapter13/a.txt","java.lang.String"); //loadClass에 대한 try~catch문 필요
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        System.out.println("end");
    }
}
```

* 다중 예외 처리  
하나의 try{} 블록에서 여러 예외가 발생하는 경우   
하나의 catch{} 블록에서 예외 처리 하거나  
여러 catch{} 블록으로 나누어 처리할 수 있음    
이때, 최상위 예외 클래스인 Exception 클래스는 가장 마지막 블록에 위치해야 함  

하나의 catch{} 블록에서 처리     
```
package exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ThrowsException {

    public Class loadClass(String fileName, String className) throws FileNotFoundException, ClassNotFoundException { //throws로 미루기

        FileInputStream fis = new FileInputStream(fileName);
        Class c = Class.forName(className);
        return c;
    }
    public static void main(String[] args) {

        ThrowsException test = new ThrowsException();
        try {
            test.loadClass("IdeaProjects/Chapter13/a.txt","java.lang.string");
        } catch (FileNotFoundException | ClassNotFoundException e) { //두개의 예외를 같이 처리
            System.out.println(e);
        }
        System.out.println("end");
    }
}
```
여러 catch{}블록에서 처리 및 디폴트 Exception   
```
package exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ThrowsException {

    public Class loadClass(String fileName, String className) throws FileNotFoundException, ClassNotFoundException { //throws로 미루기

        FileInputStream fis = new FileInputStream(fileName);
        Class c = Class.forName(className);
        return c;
    }
    public static void main(String[] args) {

        ThrowsException test = new ThrowsException();
        try {
            test.loadClass("IdeaProjects/Chapter13/a.txt","java.lang.String");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (Exception e) { //그 외 다른 예외 처리(디폴트 익셉션), 항상 마지막 catch{} 블록에서..
            System.out.println(e);
        }
        System.out.println("end");
    }
}
```

* 사용자 정의 예외     
JDK에서 제공하는 예외 클래스 외에 사용자의 필요에 의해 예외 클래스를 정의하여 사용    
기존 JDK클래스에서 상속받아 예외 클래스 생성  

사용자 정의 예외 연습    
(id가 null 이거나 8자이하 20자 이상인 경우 예외 처리)    


예외 클래스 생성
```
public class IDFormatException extends Exception {
    public IDFormatException(String message) { //생성자의 매개변수로 예외 상황 메세지 받음    
    super(massage);
    }
}
```
사용자 정의 예외 사용 
```
package exception;

public class IDFormatTest {

    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) throws IDFormatException { //예외처리 미루기 
        if (userID == null) {
            throw new IDFormatException("아이디는 null일 수 없습니다."); //throws는 미루기 throw는 발생
        } else if (userID.length() <8 || userID.length() > 20) {
            throw new IDFormatException("아이디는 8자 이상 20자 이하로 쓰세요.");
        } else {
            this.userID = userID;
        }
    }

    public static void main(String[] args) {
        IDFormatTest idTest = new IDFormatTest();

        String myID = "tuna";

        try { //사용하는 곳에서 예외 처리  
            idTest.setUserID(myID);
        } catch (IDFormatException e) {
            System.out.println(e);
        }

        myID = null;

        try {
            idTest.setUserID(myID);
        } catch (IDFormatException e) {
            System.out.println(e);
        }

    }
}
```
    
    