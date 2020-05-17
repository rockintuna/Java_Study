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
