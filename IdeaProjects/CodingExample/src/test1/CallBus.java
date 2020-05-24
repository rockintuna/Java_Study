package test1;

import java.util.Scanner;

public class CallBus {
    final int[] hoursOfDay = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};

    public boolean isServiceTime(int day, int hourOfDay) {
        isWorkingDay(1);
        return false;
    }

    private boolean isWorkingDay(int day) {
        return false;
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
        isService=callBus.isServiceTime(day,hourOfDay);
        System.out.println(isService);

    }
}
