package loopexample;

import java.util.Scanner;

public class DoWhileExample1 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int num;
        int sum=0;

        do {
            num = scanner.nextInt();
            sum += num;
            System.out.println(sum);
        } while ( num != 0 );

    }
}
