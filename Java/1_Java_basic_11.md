# Java 기초

#### 11. 관계, 논리, 조건 비트 연산자

* 관계자 연산자     
 = 비교연산자    
결과가 true/false로 반환 됨.  
'>', '<', '>=', '<=', '==', '!='

* 논리 연산자    
결과가 true/false로 반환 됨.  
&& 논리 곱, 두 항 모두 참이어야 true   
|| 논리 합, 두 항 중 하나만 참이면 true     
! 부정, 항의 논리 결과 변경 (true -> false, false -> true)    

단락 회로 평가     
앞 항 결과만으로 논리 연산자 결과가 나온다면 뒷 항은 평가되지 않음.
-> 프로그램에서 예상하지 못한 결과가 발생할 수 있으므로 유의

```
package operator;

public class OperatorEx3 {
    public static void main(String[] args) {

        int num1 = 10;
        int i = 2;

        boolean value = ((num1+=10)<10)&&((i+=2)<10);
        System.out.println(value); //false
        System.out.println(num1);  //20
        System.out.println(i);     //2

    }
}
```
논리 곱에서 왼쪽 항은 연산이 되었으나 오른쪽 항은 연산되지 않았음을 확인할 수 있다.    

* 조건 연산자    
조건식? 결과1: 결과2;  
```
int num = (5>3)> 10: 20;
```

* 비트 연산자    
~ : 비트 반전 (1의 보수)   
& : 비트 단위 AND, 1&1 만 1   
| : 비트 단위 OR, 0|0 만 0   
^ : 비트 단위 XOR   두 비트가 서로 다른 경우 1    
<< : 왼쪽 shift   a << 2 a를 2bit 만큼 왼쪽으로 이동, 0으로 채움  
\>> : 오른쪽 shift, 부호비트로 채움       
\>>> : 오른쪽 shift, 0으로 채움
왼쪽 : 곱하기, 오른쪽 : 나누기     
    
어디에 사용하나    
마스크 : 몇개의 비트 값만 사용할 떄   
비트켜기 : &00001111 하위 4bit중 1인 비트만 꺼내기    
비트끄기 : |11110000 하위 4bit중 0인 비트만 0으로 만들기     
비트토글 : 모든 비트들을 0은 1로, 1은 0으로 바꾸고 싶을 때   
shift를 통해 곱하기를 빠르게      
    
```
package operator;

public class OperatorEx4 {
    public static void main(String[] args) {

        int num1 = 0B00001010; //10
        int num2 = 0B00000101; //5

        System.out.println(num1 & num2); //00000000 0
        System.out.println(num1 | num2); //00001111 15
        System.out.println(num1 ^ num2); //00001111 15
        System.out.println(num2 << 1); //(x2) 00001010 10
        System.out.println(num2 << 2); //(x2^2) 00010100 20
        System.out.println(num2 << 3); //(x2^3) 00010100 40
        System.out.println(num2 >> 2); //(/2^2) 00000001 1
        System.out.println(num2 <<= 3); //복합 대입 연산자 40
        System.out.println(num2); //40

    }
}
```    
    
    