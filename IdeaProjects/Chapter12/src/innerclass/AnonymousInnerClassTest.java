package innerclass;

class Outer2 {

    int outNum = 100;
    static int sNum =200;

    Runnable getRunnable(int i) { //Runnable 한 type의 객체를 반환하는 메서드

        int num = 100; //지역변수 i, num
        return new Runnable() { //익명 내부 클래스, 메서드 내부

            @Override
            public void run() {
                //num += 10; 메서드의 지역변수 변경할 수 없음.
                // i = 200;  지역 내부 클래스를 포함하는 메서드의 지역변수는 내부적으로 final이 된다..

                System.out.println(num);
                System.out.println(i);
                System.out.println(outNum);
                System.out.println(sNum);
            }
        }; //익명 내부 클래스 구현 끝 세미콜론
    }

    Runnable runner = new Runnable() {  //익명 내부 클래스2, 메서드 외부
                                        //하나의 인터페이스나 추상클래스에 대해서 상속 또는 구현하는 별개의 클래스를 생성하지 않아도
                                        //바로 이름 없이 생성할 수 있다.
        @Override
        public void run() {
            System.out.println("test");
        }
    };
}

public class AnonymousInnerClassTest {
    public static void main(String[] args) {
        Outer2 outer = new Outer2();
        Runnable runnable = outer.getRunnable(30);

        runnable.run();
        outer.runner.run();
    }
}
