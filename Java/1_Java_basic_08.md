# Java 기초

#### 08. 실수와 논리 자료형

* 실수    

double이 기본 float 사용시 f,F 식별자 사용       
정수와 표현 방법이 다르다. (부동 소수점 방식)     
지수부 + 가수부
0을 포현할 수 없으며 약간의 오차가 발생할 수 있다. (부동 소수점 방식의 오류)

1.0 x 10^-1     
가수   밑수 지수      

float
MSB+지수부(8bit)+가수부(23bit)    
double
MSB+지수부(11bit)+가수부(52bit)    

* 논리 자료형    

boolean     
true,false 표현   
    
```
package variable;

public class DoubleTest {
    public static void main(String[] args) {

        double dNum = 3.14;

        //float fNum = 3.14;
        float fNum = 3.14F;

        System.out.println(dNum);
        System.out.println(fNum);

    }
}
```

3.14는 8byte (double)이므로 float에 그냥 대입하면 error 발생.

```
package variable;

public class DoubleTest {
    public static void main(String[] args) {

        double dNum = 1;
        for( int i = 0; i < 10000; i++) {
            dNum = dNum + 0.1;
        }
        System.out.println(dNum);

    }
}
```
결과는 1001.000000000159으로 약간의 오차가 발생한다.

```
package variable;

public class BooleanTest {
    public static void main(String[] args)  {

        boolean isMarried = false;
        System.out.println(isMarried);
    }
}

```

false 또는 true 두가지 값 만 가능.

    

* 자료형 없이 변수 사용하기 (var)

Local variable type inference (java 10 이상)      
지역변수에 한하여 컴파일러가 대입되는 값을 보고 변수형을 추론.

```
package variable;

public class BooleanTest {
    public static void main(String[] args)  {
        var iVar = 10;
        var cVar = "Char";

        System.out.println(iVar);
        System.out.println(cVar);
    }
}
```
    
    