# 객체 지향 프로그래밍

#### 58. 람다식    

* 람다식이란? (java 8 or newer)  
자바에서 함수형 프로그래밍을 구현하는 방식     
클래스를 생성하지 않고 함수의 호출만으로 기능 수행 (내부적으로 익명 객체 사용)    
함수형 인터페이스 선언    

* 함수형 프로그래밍이란?  
순수 함수를 구현하고 호출 
매개변수만 사용하기 때문에 외부에 사이드 이펙트를 주지 않고 병렬 처리 가능
안정적인 확장성있는 프로그래밍 방식    

문법
```
InterFace itfc = str -> {System.out.println(str);};
InterFace2 itfc2 = (x,y) -> x+y //구현부가 return 문 하나라면 return, 중괄호 생략 가능
```

```
package lambda;

public class TestStringConcat {
    public static void main(String[] args) {

        StringConImpl impl = new StringConImpl(); //객체지향에서는 인터페이스를 구현한 클래스를 사용
        impl.makeString("hello","world");

        StringConcat concat = (s,v) -> System.out.println(s+" "+v); //함수형 프로그래밍, 람다식 사용
        concat.makeString("hello","world"); //클래스 구현이 필요없고, 메서드 구현을 따로 만들 필요도 없음.
                                            //실제로는 아래처럼 익명 내부클래스로 동작한다.

        StringConcat concat2 = new StringConcat() {
            @Override
            public void makeString(String str1, String str2) {
                System.out.println(str1+" "+str2);
            }
        };
        concat2.makeString("hello","world");
    }
}
```

람다식은 프로그램내에서 변수처럼 사용할 수 있다.     

```
package lambda;

interface PrintString{
    void showString(String str);
}

public class TestLambda {
    public static void main(String[] args) {

        PrintString lambdaStr = str -> System.out.println(str); //함수의 구현부가 변수로 대입
        lambdaStr.showString("Test1");

        showMyString(lambdaStr); //매개변수로 활용
        PrintString lambdaStr2 = returnString(); //반환된 구현부를 변수에 대입
        lambdaStr2.showString("Test3");
    }

    public static void showMyString(PrintString p) {
        p.showString("Test2");
    }

    public static PrintString returnString() {
        return str->System.out.println(str+"!!!"); //함수의 구현부를 반환
    }
}
```

