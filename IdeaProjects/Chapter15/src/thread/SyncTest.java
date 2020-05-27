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
