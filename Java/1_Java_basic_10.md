# Java 기초

#### 10. 대입, 부호, 산술, 복합대입, 증감 연산자

항 : 연산에 사용되는 값
연산자 : 항을 이용하여 연산하는 기호

* 대입 연산자     
int age = 20;   
우선순위 가장 낮음.

* 단항 연산자    
부호 유지 또는 변경 (+,-)
```
package operator;

public class OperatorEx1 {
    public static void main(String[] args) {

        int num1 = -10;
        int num2 = 20;

        System.out.println(+num1);
        System.out.println(+num2);
        System.out.println(-num1);
        System.out.println(-num2);

    }
}

```
\+ 부호 유지    
\- 부호 변경    
실제 변수 값의 부호를 바꾸려면 대입 연산자와 같이 사용.    
```
int num1 = -10;     
num1 = -num1;   
```
* 산술 연산자    
사칙연산, %(나머지)    

* 복합 대입 연산자     
대입 연산자 앞의 연산자의 결과를 l-value에 대입
```
package operator;

public class OperatorEx2 {
    public static void main(String[] args) {

        int num1=10,num2=10,num3=10,num4=10,num5 = 10;
        num1 += 2; //r-value를 더한 뒤 대입
        System.out.println(num1);
        num2 -= 2; //r-value를 뺀 뒤 대입
        System.out.println(num2);
        num3 *= 2; //r-value를 곱한 뒤 대입
        System.out.println(num3);
        num4 /= 2; //r-value로 나눈 몫을 대입
        System.out.println(num4);
        num5 %= 2; //r-value를 나눈 나머지 대입
        System.out.println(num5);
    }
}
```

* 증가 감소 연산자     
변수의 값을 1 더하거나 뺄때 사용     
```
val = ++num; num 1 증가 후 대입
val = num++; 대입 후 num 1 증가
val = --num;
val = num--;

```

```
package operator;

public class OperatorEx3 {
    public static void main(String[] args) {

        int score = 100;

        System.out.println(++score); //101
        //++score 는 score = score+1 과 같은 의미이다.
        System.out.println(score++); //101
        System.out.println(score); //102

    }
}
```
증감 연산자는 대입을 내포한다.   
    
    