package thread;

class MyThread implements Runnable {
    public void run() {
        int i;
        for (i=0;i<100;i++) {
            System.out.print(i + "\t");
            try {
                Thread.sleep(100); //Thread 클래스의 메서드
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class ThreadTest {
    public static void main(String[] args) {

        System.out.println("start");

        Thread t = Thread.currentThread(); //현재 수행중인 thread(main)
        System.out.println(t); //이름, priority, 그룹

        System.out.println("end");
    }
}
