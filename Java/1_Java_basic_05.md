# Java 기초

#### 05. 변수란 무엇인가

변수 : 변하는 수   
상수 : 변하지 않는 수
    
변수는 *선언*이 필요하다.   
    
선언 방법 :     
자료형 변수이름;  
int age; 
    
```
package variable;

public class VariableTest {

    public static void main(String[] args) {
        int age, count;
        int age_2 = 20;
        age = 30;
        System.out.println(age);
        System.out.println(age_2);

        age = 10;
        System.out.println(age);
        count = 1;
        System.out.println(count);

    }
}
```

'='표시는 같다는 의미가 아니고 대입의 의미이다. (l-value=r-value : r-value를 l-value에 대입)   
그렇기 때문에 언제든지 변수는 바뀔 수 있다.   
    
선언은 여러 변수를 한번에 할 수 도 있고 선언과 동시에 초기화(대입)할 수도 있다.  

선언은 해당 변수형의 크기 만큼 메모리를 사용한다.     
(ex int age; -> 4byte 메모리 점유)   
    
    
변수이름은...
1. 숫자로 시작할 수 없다.
2. 특수문자는 '_'와 '$'만 가능하다.
3. 예약어는 쓸 수 없다. (ex : while, for, int, etc...)
4. 쓰임에 맞게 명명해야 가독성이 좋다. (줄여서 약어로 쓰지 말자.)

되도록 소문자로 시작하고 단어가 바뀔때 대문자 (camel notation)      
    
    