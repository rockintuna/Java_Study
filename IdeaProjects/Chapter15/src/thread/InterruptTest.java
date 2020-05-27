package thread;

public class InterruptTest extends Thread{

    public void run() {
        int i;
        for (i=0;i<100;i++) {
            System.out.println(i);
        }
        try {
            sleep(5000); //5초 not runnable
        } catch (InterruptedException e) {
            System.out.println(e);
            System.out.println("Wake!!!");
        }
    }
    public static void main(String[] args) {

        InterruptTest test = new InterruptTest();
        test.start();
        test.interrupt(); //Interrupt 예외 발생

        System.out.println("end");

    }
}
