# 객체 지향 프로그래밍

#### 77. Multi-thread 프로그래밍     

* 임계 영역(critical section)   
두 개 이상의 thread가 동시에 접근하게 되는 리소스     
critical section에 동시에 thread가 접근하게 되면 실행 결과를 보장할 수 없음   
thread간의 순서를 맞추는 동기화(synchronization)가 필요   

* 동기화   
임계 영역에 여러 thread가 접근하는 경우, 한 thread가 수행하는 동안 공유 자원을 lock하여 다른 thread의 접근을 막음    
동기화를 잘못 구현하면 deadlock에 빠질 수 있음      

* 자바에서 동기화 구현   
synchronized 수행문과 synchronized 메서드를 이용  

synchronized 수행문
```
synchronized(참조형 수식) {
} //참조형 수식에 해당되는 객체에 lock을 건다
```
```
package thread;

class Bank { //critical section
    private int money = 10000;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void saveMoney(int save) {
        synchronized (this) { //Bank가 shared resource 이므로
            int m = this.getMoney();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setMoney(m+save);
        }
    }

    public void minusMoney(int minus) {
        synchronized (this) {
            int m = this.getMoney();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setMoney(m - minus);
        }
    }
}
class Park extends Thread {
    public void run() {
        System.out.println("start save");
        SyncTest.myBank.saveMoney(3000);
        System.out.println("save money:" + SyncTest.myBank.getMoney());
    }
}

class ParkWife extends Thread {
    public void run() {
        System.out.println("start minus");
        SyncTest.myBank.minusMoney(1000);
        System.out.println("minus money:" + SyncTest.myBank.getMoney());
    }

}

public class SyncTest {
    public static Bank myBank = new Bank();

    public static void main(String[] args) throws InterruptedException {
        Park park = new Park();
        ParkWife parkwife = new ParkWife();

        park.start();
        Thread.sleep(200);
        parkwife.start();
    }
}
```

synchronized 메서드    
현재 이 메서드가 속한 객체에 lock을 건다
deadlock 방지를 위해 synchronized 메서드 내에서 다른 synchronized 메서드를 호출하지 않는다  
```
package thread;

class Bank { //critical section
    private int money = 10000;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public synchronized void saveMoney(int save) { //synchronized 키워드로 lock
        int m = this.getMoney();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMoney(m+save);
    }

    public synchronized void minusMoney(int minus) {
        int m = this.getMoney();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMoney(m-minus);
    }
}
class Park extends Thread {
    public void run() {
        System.out.println("start save");
        SyncTest.myBank.saveMoney(3000);
        System.out.println("save money:" + SyncTest.myBank.getMoney());
    }
}

class ParkWife extends Thread {
    public void run() {
        System.out.println("start minus");
        SyncTest.myBank.minusMoney(1000);
        System.out.println("minus money:" + SyncTest.myBank.getMoney());
    }

}

public class SyncTest {
    public static Bank myBank = new Bank();

    public static void main(String[] args) throws InterruptedException {
        Park park = new Park();
        ParkWife parkwife = new ParkWife();

        park.start();
        Thread.sleep(200);
        parkwife.start();
    }
}
```

* wait(),notify() 메소드   

wait() : 리소스가 더 이상 유효하지 않은 경우 리소스가 사용 가능할 때 까지 기다리기 위해 thread를 non-runnable 상태로 전환   
wait() 상태가 된 thread는 notify()가 호출 될 때까지 기다린다    

notify() : wait()하고 있는 thread중 한 thread를 runnable한 상태로 깨움   

nofifyAll() : wait()하고 있는 모든 thread가 runnable한 상태가 되도록 함    
notify()보다 notifyAll()을 사용하길 권장됨    
특정 thread가 통지를 받도록 제어할 수 없으므로 모두 깨운 후 scheduler에 CPU를 점유하는 것이 좀더 공평     

```
package thread;

import java.util.ArrayList;

class FastLibrary{

    public ArrayList<String> books = new ArrayList<String>();

    public FastLibrary() {
        books.add("태백산맥1");
        books.add("태백산맥2");
        books.add("태백산맥3");
    }

    public synchronized String lendBook() throws InterruptedException {
        Thread t = Thread.currentThread();
        while (books.size() == 0) {
            System.out.println(t.getName() + " waiting start");
            wait();
            System.out.println(t.getName() + " waiting end");
        }
        String title = books.remove(0);
        System.out.println(t.getName() +":"+ title + " lend");
        return title;
    }

    public synchronized void returnBook(String title) {
        Thread t = Thread.currentThread();
        books.add(title);
        notifyAll(); //모든 wait 스레드를 깨운다     
        System.out.println(t.getName() +":"+ title + " return");
    }

}

class Student extends Thread{

    public void run() {
        try {
            String title = LibraryMain.library.lendBook();
            if (title == null) return;
            sleep(5000);
            LibraryMain.library.returnBook(title);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

public class LibraryMain {

    public static FastLibrary library = new FastLibrary();
    public static void main(String[] args) {

        Student std1 = new Student();
        Student std2 = new Student();
        Student std3 = new Student();
        Student std4 = new Student();
        Student std5 = new Student();
        Student std6 = new Student();

        std1.start();
        std2.start();
        std3.start();
        std4.start();
        std5.start();
        std6.start();

    }
}
```
    
    