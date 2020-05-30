package test1;

import java.util.Arrays;
import java.util.Scanner;

public class CallBus {
    final static int[] days = {0,1,2,3,4,5,6};
    final static int[] hoursOfDay = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};


    public boolean isServiceTime(int day, int hourOfDay) {
        return isWorkingDay(day);
    }

    private boolean isWorkingDay(int day) {
        return Arrays.asList(days).contains(day);
    }


    public static void main(String[] args) {
        int day;
        int hourOfDay;
        boolean isService;

        Scanner scanner = new Scanner(System.in);

        System.out.println("요일을 입력하세요.");
        day = scanner.nextInt();
        System.out.println("시간을 입력하세요.");
        hourOfDay = scanner.nextInt();

        CallBus callBus = new CallBus();
        System.out.println(Arrays.asList(days));
        isService=callBus.isServiceTime(day,hourOfDay);
        System.out.println(isService);

    }
}
