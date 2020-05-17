package codingexample;

import java.util.Scanner;

public class Example2 {
    public static void main(String[] args) {

        System.out.println("홀수 값(최대 별의 개수)을 임력하세요.");

        Scanner scanner = new Scanner(System.in);

        int max = scanner.nextInt();

        for (int i=1; i<=max; i++) {
            if (i%2==0) { //1,3,5,7,9,11
                continue;
            }
            for (int blank = (max-i)/2; blank > 0; blank--) { //5,4,3,2,1,0
                System.out.print(" ");
            }
            for (int num = 0; num < i; num++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i=max-2; i>0; i--) {
            if (i%2==0) { //9,7,5,3,1
                continue;
            }
            for (int blank = 0; blank < (max-i)/2; blank++) { //1,2,3,4,5
                System.out.print(" ");
            }
            for (int num = i; num > 0; num--) { //9,7,5,3,1
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
