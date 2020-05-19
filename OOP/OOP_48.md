# 객체 지향 프로그래밍

#### 48. String, Wrapper 클래스

* String    

String 클래스 선언하기
```
String str1 = new String("abc"); //인스턴스로 생성
String str2 = "abc"              //상수풀에 있는 문자
```

```
package string;

public class StringTest {

    public static void main(String[] args) {

        String str1 = new String("abc"); //힙메모리 영역
        String str2 = "abc";                     //상수 풀
        String str3 = "abc";

        System.out.println(str1 == str2); //false, 메모리 위치가 다르기 때문
        System.out.println(str2 == str3); //true, 둘다 상수풀이기 때문
        
    }
}
```

한번 선언되거나 생성된 문자열은 변경할 수 없다.   
가변적으로 문자열을 사용하려면 StringBuilder 또는 StringBuffer를 사용한다.   
StringBuilder는 단일 쓰레드 프로그래밍에서,  
StringBuffer는 멀티 쓰레드 프로그래밍에서 사용된다.  

```
package string;

public class StringTest2 {
    public static void main(String[] args) {

        String java = new String("java");
        String android = new String("android");

        System.out.println(System.identityHashCode(java));
        java = java.concat(android);

        System.out.println(java);
        System.out.println(System.identityHashCode(java)); //메모리 주소가 변경됨,즉 새로운 메모리 영역 사용됨

    }
}
```

```
package string;

public class StringBuilderTest {
    public static void main(String[] args) {

        String java = new String("java");
        String android = new String("android");

        StringBuilder buffer = new StringBuilder(java);
        System.out.println(System.identityHashCode(buffer));
        buffer.append("android");
        System.out.println(System.identityHashCode(buffer)); //변경되어도 메모리 주소는 변하지 않음

        java = buffer.toString(); //String 타입으로 형변환 
        System.out.println(java);
    }
}
```

* Wrapper 클래스   
기본 자료형에 대한 클래스  
Boolean, Integer, 등등    
    
    