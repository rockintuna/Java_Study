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

