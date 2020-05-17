package loopexample;

public class ForExample1 {
    public static void main(String[] args) {

        int num;
        int count;
        int result;

        for(num=2; num<10; num++) {

            for (count = 1; count < 10; count++) {
                result = num*count;
                System.out.println(num+"x"+count+"="+result);
            }
            System.out.println();
        }

    }
}
