# Java 기초

#### 09. 상수와 리터럴, 형변환

상수 : 변하지 않는 수 (final 키워드)   

리터럴 : 프로그램에서 사용하는 모든 숫자, 값, 논리 값    
모든 리터럴은 상수 풀(constant pool)에 저장되어 있다.
저장될 때 정수는 int, 실수는 double로 저장 된다.   

형 변환    

1. 묵시적 형 변환     
작은 수 -> 큰 수,
덜 정밀한 수 -> 더 정밀한 수
로 대입되는 경우

2. 명시적 형 변환     
자료 형을 명시하여 변환, 자료 손실 발생 가능.

```
package variable;

public class ImplicitConversion {
    public static void main(String[] args) {

        byte bNum = 10;
        int iNum = bNum; //더 큰수로 묵시적 형 변환

        System.out.println(bNum);
        System.out.println(iNum);

        int iNum2 = 20;
        float fNum = iNum2; //더 정밀한 수로 형 변환

        System.out.println(fNum);

        double dNum;
        dNum = fNum + iNum; //더 정밀한 수로 형 변환 2회 i->f->d
        System.out.println(dNum);

    }

}
```
```
package variable;

public class ExplicitConversion {
    public static void main(String[] args) {

        int i = 1000;
        //byte bNum = i;
        byte bNum = (byte)i; //명시적 형 변환
        System.out.println(bNum); //데이터 유실

        double dNum = 1.2;
        float fNum = 0.9F;

        //int iNum1 = dNum + fNum;
        int iNum1 = (int)(dNum + fNum);
        int iNum2 = (int)dNum + (int)fNum;
        System.out.println(iNum1); //데이터 유실 2.1 -> 2
        System.out.println(iNum2); //데이터 유실 1.2+0.9 -> 1+0

    }
}

```


10진수를 2,8,16진수로 쓰는 법
* 2진수(0B) : 0B1010;
* 8진수(0) : 012;
* 16진수(0X) : 0XA;

```
package variable;

public class BinaryTest {
    public static void main(String[] args) {

        int num = 10;
        int bNum = 0B1010;
        int oNum = 012;
        int xNum = 0XA;

        System.out.println(num);
        System.out.println(bNum);
        System.out.println(oNum);
        System.out.println(xNum);
    }
}

``` 
    
    