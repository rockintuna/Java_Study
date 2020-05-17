package ifexample;

import java.util.Scanner;

public class IfExample3 {
    public static void main(String[] args) {
        int a = 10;
        int b =20;
        int max;

        max = (a>b)? a:b;

        System.out.println(max);

        if (a>b) {
            max = a;
        }
        else {
            max = b;
        }
        System.out.println(max);
    }
}
