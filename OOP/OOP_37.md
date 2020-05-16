# 객체 지향 프로그래밍

#### 37. 인터페이스  

* 인터페이스의 요소     
추상 메서드 : 인터페이스는 모두 추상 메서드로만 구현되어 있다.     
상수 : new가 될 수 없기때문에 모든 변수는 상수이다.     
디폴트 메서드 : 하위 클래스들의 중복 구현 방지.    
정적 메서드  
private 메서드     

* 인터페이스의 선언과 구현
```
public interface Calc {

int iNum = 3; //컴파일 과정에서 상수로 변환 

int add(int num1,int num2); //컴파일 과정에서 추상 메서드로 변환 

}
```

```
package interfaceex;

public class CalcTest {
    public static void main(String[] args) {

        Calc calc = new CompleteCalc();
        int n1 =10;
        int n2 = 2;

        System.out.println(calc.add(n1,n2));
        System.out.println(calc.substract(n1,n2));
        System.out.println(calc.times(n1,n2));
        System.out.println(calc.divide(n1,n2));

        CompleteCalc ccalc = (CompleteCalc)calc;
        ccalc.showInfo();

    }
}
```

```
package interfaceex;

public abstract class Calculator implements Calc{ //인터페이스는 implements

    @Override
    public int add(int num1, int num2) {
        return num1+num2;
    }

    @Override
    public int substract(int num1, int num2) {
        return num1-num2;
    }

}
```

```
package interfaceex;

public class CompleteCalc extends Calculator{
    @Override
    public int times(int num1, int num2) {
        return num1*num2;
    }

    @Override
    public int divide(int num1, int num2) {
        if (num2==0) {
            return Error;
        } else {
            return num1 / num2;
        }
    }

    public void showInfo() {
        System.out.println("모두 구현하였습니다.");
    }
}
```