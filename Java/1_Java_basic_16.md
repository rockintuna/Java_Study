# Java 기초

#### 16. 제어문 while, do-while

동일한 수행문을 조건이 맞는 동안 반복 수행.   

while(조건식) {    
    수행문1;   
    ...     
}   
    수행문2;   
    ...     
    
while 내 조건문이 참인 경우 수행문1을 반복 수행한다.   

```
package loopexample;

public class WhileExample {
    public static void main(String[] args) {

        int num = 1;
        int sum = 0;

        while ( num <= 10 ) {
            sum += num;
            num++;
        }

        System.out.println(sum);

    }
}

```

do {    
    수행문1;   
    ...     
} while(조건식);   
    수행문2;
    ...
 
반복문 중 가장 안씀.    
먼저 수행문1을 수행한 뒤 조건 체크.   
최소 한번은 수행문이 수행되어야 하는 경우에 사용.    

```
package loopexample;

import java.util.Scanner;

public class DoWhileExample1 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int num;
        int sum=0;

        do {
            num = scanner.nextInt();
            sum += num;
            System.out.println(sum);
        } while ( num != 0 );

    }
}
```


