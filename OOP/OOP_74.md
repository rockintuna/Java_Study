# 객체 지향 프로그래밍

#### 74. Thread 구현하기    

쓰레드를 구현할 일이 많지는 않지만 안드로이드 개발에서 쓰임   
쓰레드 프로그래밍에서는 공유 자원이 중요  

* Process   
실행중인 프로그램, OS로부터 메모리를 할당 받음     

* Thread    
실제 프로그램이 수행되는 작업의 최소 단위     
하나의 프로세스는 하나 이상의 Thread를 가지게 됨  

* Thread 구현하기   
1. 자바 Thread 클래스로부터 상속받아 구현    
2. Runnable 인터페이스 구현   

상속 방식
```
package thread;

class MyThread extends Thread { 
    public void run() {
        int i;
        for (i=0;i<200;i++) {
            System.out.print(i + "\t");
            try {
                sleep(100); //Thread 클래스의 메서드
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class ThreadTest {
    public static void main(String[] args) {

        System.out.println("start");
        MyThread th1 = new MyThread(); //main이랑 별개로 수행됨
        MyThread th2 = new MyThread();

        th1.start(); //클래스의 run() 수행
        th2.start();
        System.out.println("end");
    }
}
```

구현 방식   
```
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
        MyThread runner1 = new MyThread();
        MyThread runner2 = new MyThread();
        Thread th1 = new Thread(runner1); //Runnable을 구현한 객체를 매개변수로 가능
        Thread th2 = new Thread(runner2);

        th1.start();
        th2.start();

        System.out.println("end");
    }
}
```

* Multi-thread 프로그래밍    
동시에 여러 개의 Thread가 수행되는 프로그래밍    
Thread는 각각의 작업공간(context)를 가짐   
공유 자원이 있는 경우 race condition이 발생     
critical section은 한번에 하나의 thread만 접근할 수 있으며     
critical section에 대한 동기화(synchronization)의 구현이 필요   

* Thread의 여러가지 메서드 활용   

Thread 우선 순위     
Thread.MIN_PRIORITY(=1) ~ Thread.MAX_PRIORITY(=10)  
디폴트 우선 순위 : Thread.NORM_PRIORITY(=5)    

setPriority(int newPriority)
int getPriority()

우선 순위가 높은 thread는 CPU를 배분 받을 확률이 높다     
```
public class ThreadTest {
    public static void main(String[] args) {

        System.out.println("start");

        Thread t = Thread.currentThread(); //현재 수행중인 thread(main)
        System.out.println(t); //이름, priority, 그룹
        
        System.out.println("end");
    }
}
```

join() 메서드  
```
package thread;

public class JoinTest extends Thread{

    int start;
    int end;
    int total;

    public JoinTest(int start,int end) {
        this.start = start;
        this.end = end;
    }

    public void run() {
        int i;
        for(i=start; i<=end; i++) {
            total += i;
        }
    }

    public static void main(String[] args) {

        JoinTest jt1 = new JoinTest(1,50);
        JoinTest jt2 = new JoinTest(51,100);

        jt1.start();
        jt2.start();


        try {
            jt1.join(); //main thread가 jt1, jt2 thread를 대기(not runnable)
            jt2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int total = jt1.total+jt2.total;
        System.out.println(jt1.total);
        System.out.println(jt2.total);
        System.out.println(total);

    }
}
```

interrupt() 메서드     
다른 thread에 예외를 발생시키는 interrupt를 보냄  
thread가 join(),sleep(),wait() 메서드에 의해 블럭킹 되었다면 interrupt에 의해 다시 runnable 상태가 될 수 있음     

```
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
```

Thread 종료하기     
데몬등 무한 반복하는 thread가 종료될 수 있도록 run() 메서드 내의 while문을 활용   
Thread.stop()은 사용하지 않는다     

```
package thread;

import java.io.IOException;

public class TerminateThread extends Thread{

    private boolean flag = false;
    int i;

    public TerminateThread(String name) { //thread 이름 주기
        super(name);
    }

    public void run() {
        while( !flag ) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName()+" end");
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public static void main(String[] args) throws IOException {

        TerminateThread threadA = new TerminateThread("A");
        TerminateThread threadB = new TerminateThread("B");

        threadA.start();
        threadB.start();

        int in;
        while(true) {
            in = System.in.read();
            if (in == 'A') {
                threadA.setFlag(true);
            } else if (in == 'B') {
                threadB.setFlag(true);
            } else if (in == 'M') {
                threadA.setFlag(true);
                threadB.setFlag(true);
                break;
            }
        }
        System.out.println("main end");
    }
}
```