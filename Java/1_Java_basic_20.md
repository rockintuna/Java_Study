# Java 기초

#### 20. break, continue 문

* break     
감싸고 있는 블럭을 빠져나오는 기능.    
주로 반복문, 조건문, switch-case문과 같이 사용됨.  
반복문에서 특정 조건에서 반복 중지.    

```
package loopexample;

public class BreakExample {
    public static void main(String[] args) {

        int num;
        int sum=0;

        for (num=1; ; num++) {
            sum += num;
            if (sum>=100) {
                break; //sum이 100을 넘어가는 경우 반복 중지
            }
        }

        System.out.println(sum);
        System.out.println(num);

    }
}
```

* continue      
반복문에서 특정 조건에서 블럭 내부의 다른 수행문을 수행하지 않음.   

```
package loopexample;

public class ContinueExample {
    public static void main(String[] args) {

        int num;

        for (num=1; num<=100; num++) {
            if (num%3!=0) { //3의 배수가 아닌 경우 출력 X
                continue;
            }
            System.out.println(num);

        }
    }
}
```

구구단 짝수단만 출력하면서 곱하는수가 단과 같거나 작은 경우만 출력
```
package loopexample;

public class BreakContinueTest {
    public static void main(String[] args) {

        int dan;
        int num;
        int result;

        for (dan=2; dan<10; dan++) {
            if (dan%2==1) {
                continue;
            }
            for (num=1; num<10; num++) {
                if (num>dan) {
                    break; //내부 반복문만 중지된다.
                }
                result=dan*num;
                System.out.println(dan+"X"+num+"="+result);
            }
        }
    }
}
```
    
    