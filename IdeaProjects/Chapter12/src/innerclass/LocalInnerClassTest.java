package innerclass;

class Outer {

    int outNum = 100;
    static int sNum =200;

    Runnable getRunnable(int i) { //Runnable 한 type의 객체를 반환하는 메서드

        int num = 100; //지역변수 i, num
        class MyRunnable implements Runnable { //지역 내부 클래스, 메서드 내에 구현되어 있음
            @Override
            public void run() {
                //num += 10; 메서드의 지역변수 변경할 수 없음.
                // i = 200;  지역 내부 클래스를 포함하는 메서드의 지역변수는 내부적으로 final이 된다..

                System.out.println(num);
                System.out.println(i);
                System.out.println(outNum);
                System.out.println(sNum);
            }
        }
        return new MyRunnable();
    }
}
public class LocalInnerClassTest {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Runnable runnable = outer.getRunnable(30);

        runnable.run(); //getRunnable 메서드는 이미 종료되어 지역변수는 없어져있다.
                        //지역 내부 클래스의 메서드(run)은 언제든지 호출될 수 있다.
                        //그렇기 때문에 지역 내부 클래스를 포함하는 메서드의 지역변수는 상수(final)가 된다.
    }
}
