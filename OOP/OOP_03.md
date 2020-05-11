# 객체 지향 프로그래밍

#### 03. 함수와 메서드

* 함수(function)  
하나의 기능을 수행하는 일련의 코드     
함수는 호출하여 사용하 기능이 수행된 후 값을 반환할 수 있음     
함수로 구현된 기능은 여러 곳에서 호출되어 사용될 수 있음 (코드의 재사용)  
가독성, 유지보수에 좋다.
    
함수는 이름, 매개변수, 반환 값, 몸체로 구성된다.


```
package classpart;

public class FunctionTest {
    //            반환type 이름(매개변수)
    public static int addNum(int num1, int num2) { //2개의 매개변수 및 int 반환 값이 필요하다.
        int result;
        result = num1+num2;
        return result; //반환 값 result
    }

    public static void sayHello(String greeting) { //반환 값이 없는 함수 (void)
        System.out.println(greeting);
    }

    public static int calcSum() { //매개변수가 없고 반환 값만 있는 함수

        int sum=0;
        int i;

        for (i=0;i<=100;i++) {
            sum += i;
        }

        return sum;
    }

    public static void main(String[] args) {
        int n1 = 10;
        int n2 = 20;

        int total = addNum(n1,n2); //매개변수를 통해 함수의 반환 값을 int 변수로 받음.
        sayHello("안녕"); //반환 값 없이 매개변수 출력
        int num = calcSum(); //매개변수 없이 반환 값을 int 변수로 받음.

        System.out.println(total);
        System.out.println(num);
    }

}
```

* 메서드   
객체의 기능을 구현하기 위해 클래스 내에서 구현되는 함수  
메서드를 구현함으로써 객체의 기능이 구현됨     
메서드 이름은 사용하는 쪽에 맞게 명명   
ex) getStudentName() (camel notation)   
지역변수 : 함수 내에서 사용되는 변수   

* 메모리
stack : 함수 호출에 사용되는 memory, 함수의 호출이 끝나면 반환된다.   
점유된 순서의 반대로 반환된다. (main부터 점유, main을 끝으로 반환.)
    
    