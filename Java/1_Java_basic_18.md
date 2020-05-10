# Java 기초

#### 18. 제어문 for문, 중첩반복문

* for문  
반복문 중 가장 많이 사용됨.    
일정횟수 반복 구현에 효율적.    

for (초기화식; 조건식; 증감식;)   
{   
    수행문;   
    ...     
}   

```
package loopexample;

public class ForExample {
    public static void main(String[] args) {

        int num=0;
        int total = 0;

        while ( num < 10) {
            total += num;
            num++;
        }
        System.out.println(total);
        
        //위와 아래는 같은 로직이다.        

        int count;
        int sum=0;

        for(count=0; count<10; count++) { //횟수의 의미가 있는경우 0부터 시작하는 것에 익숙해지자.
            sum += count;
        }
        System.out.println(sum);

    }
}
```

같은 로직이지만 for문이 더 편하고 가독성이 좋다.   

while, do-while은 조건식의 결과 또는 변수가 true,false인 경우 주로 사용.   
for문은 특정 수의 범위, 횟수와 관련하여 반복되는 경우 사용. 배열과 같이 사용.     

* 무한루프  
while (true) {  
...     
}   
or
for(;;) {
...     
}   

* 중첩 반복문    
반복문 내부에 또 다른 반복문 사용.    
외부반복문 / 내부반복문 간의 변 값 변화에 유의.    

중첩반복문을 이용한 구구단
```
package loopexample;

public class ForExample1 {
    public static void main(String[] args) {

        int num;
        int count;
        int result;

        for(num=2; num<10; num++) {
            for (count = 1; count < 10; count++) {
                result = num*count;
                System.out.println(num+"x"+count+"="+result);
            }
            System.out.println();
        }

    }
}
```
    
    