package loopexample;

public class ForExample {
    public static void main(String[] args) {

        int num=0;
        int total = 0;

        while ( num < 10) {
            total += num;
            num++;
        }
        System.out.println(total);

        int count;
        int sum=0;

        for(count=0; count<10; count++) {
            sum += count;
        }
        System.out.println(sum);

    }
}
