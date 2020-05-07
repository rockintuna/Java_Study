# Java 기초

#### 06. 정수 자료형

자료형 종류 : 
1. 기본형 : 자바에서 기본 제공 (정수형, 문자형, 실수형, 논리형)
2. 참조형 : 클래스 형식의 자료형

정수형     
byte < short < int(보통) < long   
문자형     
char    
실수형     
float < double(보통)  
논리형     
boolean     

* int   
4byte(32bit)    
MSB를 제외한 31bit를 사용하여 -2^31 ~ 2^31-1(2147483647)까지 표현 가능     

package variable;
```
public class IntegerTest {
    public static void main(String[] args) {

        byte bs1 = -128;
        //byte bs2 = 128;
        System.out.println(bs1);
        //System.out.println(bs2);
        //int iVal = 12345678900;
        //long lVal = 12345678900;
        long lVal = 12345678900L;

    }
}
```

해당 정수형의 한계를 초과하면 error 발생   
-> 더 큰 정수형 사용   
    
long type을 사용할 때 4byte 이상의 수를 대입할 경우 숫자를 그냥 쓰면 error 발생     
-> 리터럴은 기본적으로 4byte이므로 숫자 마지막에 'L'을 입력하여(long으로 취급) 8byte로 사용      
        
