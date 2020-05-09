# Java 기초

#### 13. 제어문 if

if (조건식) {  
    수행문;    
}   
조건식이 참인 경우 수행. 거짓인 경우 조건문 종료.   

if (조건식) {  
    수행문1;    
} else {
    수행문2;
}   

조건식이 참인 경우 수행. 거짓인 경우 else.     

```
package ifexample;

public class IfExample1 {
    public static void main(String[] args) {
        char gender = 'M';

        if ( gender == 'F' ) { // 블럭의 시작
            System.out.println("여성입니다."); // 블럭 내부에서는 들여쓰기
        } // 블럭 끝
        else {
            System.out.println("남성입니다.");
        }
    }
}
```

조건이 여러개일 때는..   

if (조건식) {  
    수행문1;    
} else if (조건식) {
    수행문2;
} else if (조건식) {
    수행문3;    
} else {
    수행문4;
}  

나이별 요금 계산

```
package ifexample;

import java.util.Scanner;

public class IfExample2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int age = scanner.nextInt();
        int charge = 0;

        if ( age < 8 ) {
            charge = 1000;
        }
        else if ( age < 14 ) {
            charge = 1500;
        }
        else if ( age < 20 ) {
            charge = 2000;
        }
        else {
            charge = 3000;
        }

        System.out.println("나이 : " + age);
        System.out.println("요금 : " + charge);
    }
}
```

점수별 학점 계산
```
package ifexample;

import java.util.Scanner;

public class IfExample3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int score = scanner.nextInt();
        char grade;

        if ( score>=90 ) {
            grade = 'A';
        }
        else if ( score>=80 ) {
            grade = 'B';
        }
        else if ( score>=70 ) {
            grade = 'C';
        }
        else if ( score>=60 ) {
            grade = 'D';
        }
        else {
            grade = 'F';
        }

        System.out.println("점수 : " + score);
        System.out.println("학점 : " + grade);
    }
}

```

* 조건문과 조건 연산자

```
package ifexample;

public class IfExample3 {
    public static void main(String[] args) {

        int a = 10;
        int b =20;
        int max;

        max = (a>b)? a:b;

        System.out.println(max);

// 위와 아래는 동일한 결과.

        if (a>b) {
            max = a;
        }
        else {
            max = b;
        }
        System.out.println(max);
    }
}
```