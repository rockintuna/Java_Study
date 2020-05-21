package test1;

import java.io.IOException;
import java.util.Scanner;

public class CallBus {

    public boolean isServiceTime (int day, int hourOfDay) {
            if (hourOfDay < 0 || hourOfDay > 23) {
                System.out.println("Error : 유효하지 않은 요일 또는 시간입니다.");
                return false;
            } else {
                switch (day) {
                    case 1: case 3: case 4: case 5:
                        if (hourOfDay <= 3 || hourOfDay >= 23) {
                            return true;
                        } else {
                            return false;
                        }
                    case 0:
                        if (hourOfDay == 23) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        if (hourOfDay <= 3) {
                            return true;
                        } else {
                            return false;
                        }
                    case 2:
                        if (hourOfDay <=3 || hourOfDay >= 22) {
                            return true;
                        } else {
                            return false;
                        }
                    default:
                        System.out.println("Error : 유효하지 않은 요일 또는 시간입니다.");
                        return false;
                }
            }
    }

    public static void main(String[] args) throws IOException {
        int day;
        int hourOfDay;
        boolean isService;

        Scanner scanner = new Scanner(System.in);

        System.out.println("요일을 입력하세요.");
        day = scanner.nextInt();
        System.out.println("시간을 입력하세요.");
        hourOfDay = scanner.nextInt();

        CallBus callBus = new CallBus();
        isService=callBus.isServiceTime(day,hourOfDay);
        System.out.println(isService);
    }
}
