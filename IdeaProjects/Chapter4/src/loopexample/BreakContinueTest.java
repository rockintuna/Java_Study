package loopexample;

public class BreakContinueTest {
    public static void main(String[] args) {

        int dan;
        int num;
        int result;

        for (dan=2; dan<10; dan++) {
            if (dan%2==1) {
                continue;
            }
            for (num=1; num<10; num++) {
                if (num>dan) {
                    break;
                }
                result=dan*num;
                System.out.println(dan+"X"+num+"="+result);
            }
        }
    }
}
