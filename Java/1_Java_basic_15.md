# Java 기초

#### 15. 제어문 switch ~ case

if ~ else if 문의 조건이 정수 또는 문자열일 경우 switch ~ case 로 사용할 수 있다.     

switch(변수){     
    case 조건1: 수행문1;     
    case 조건2: 수행문2:     
    ...     
    default : 수행문n;     
}       
    
package ifexample;

import java.util.Scanner;

public class SwitchCaseEx {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int rank = scanner.nextInt();
        char medalColor;

        switch(rank) {
            case 1: medalColor='G';
                    break; //break가 없으면 다음 case 문이 조건과 상관 없이 수행된다.
            case 2: medalColor='S';
                    break;
            case 3: medalColor='B';
                    break;
            default : medalColor='A'; //default는 break이 필요 없다.
        }
        System.out.println(rank + "등의 메달 색은 " + medalColor + "입니다.");
    }
}


자바 7부터 조건으로 문자열을 사용할 수 있다.  
```
package ifexample;

public class SwitchCaseEx {
    public static void main(String[] args) {

        String medalColorT="Gold";
        int rank;

        switch(medalColorT) {
            case "Gold":
                rank = 1;
                break; //break가 없으면 다음 case 문이 조건과 상관 없이 수행된다.
            case "Silver":
                rank = 2;
                break;
            case "Bronze":
                rank = 3;
                break;
            default :
                rank = 0;
                System.out.println("Error");
        }
        System.out.println(medalColorT + "메달은 " + rank + "등 입니다.");

    }
}
```

서로 다른 조건에 대한 수행문이 같은 경우 case를 병합할 수 있다.     
```
package ifexample;

import java.util.Scanner;

public class SwitchCaseEx1 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int month = scanner.nextInt();
        int day;

        switch(month) {
            case 1: case 5: case 7: case 8: case 10: case 12:
                    day=31;
                    break;
            case 2: day=28;
                    break;
            case 3: case 4: case 6: case 9: case 11:
                    day=30;
                    break;
            default :
                    System.out.println("Error");
                    day = 0;
        }
        System.out.println(month + "월의 날짜 수는 " + day + "일 입니다.");
    }
}
```
    
    