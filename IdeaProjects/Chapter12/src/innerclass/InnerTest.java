package innerclass;

class OutClass {
    private int num = 10;
    private static int sNum = 20;
    private InClass inClass;

    public OutClass() {
        inClass = new InClass(); //주로 OutClass의 생성자에서 인스턴스 생성
    }

    class InClass { //인스턴스 내부 클래스
        int iNum = 100;
        //static int sInNum = 200; //static 변수는 OutClass 생성과 무관해야 하기때문에..

        void inTest() {
            System.out.println(num);
            System.out.println(sNum);
        }
    }

    public void usingInner() {
        inClass.inTest();
    }

    static class InStaticClass { //정적 내부 클래스, Outer 클래스의 생성 여부와 무관
        int inNum = 100;
        static int sInNum = 200; //static 변수 또는 메서드 선언 가능

        void inTest() {
            System.out.println(inNum);
            System.out.println(sInNum);
            System.out.println(sNum);
        }

        static void sTest() {
            //System.out.println(inNum); //static 메서드에서는 static 변수만 사용할 수 있다.
            System.out.println(sInNum);
            System.out.println(sNum);
        }
    }
}

public class InnerTest {
    public static void main(String[] args) {

        OutClass outClass = new OutClass();
        //outClass.inClass.inTest(); //inClass가 private이므로 사용할 수 없음
        outClass.usingInner();

        OutClass.InClass myInClass = outClass.new InClass(); //생성된 Outer class의 참조변수로부터 Inner class 인스턴스 생성
        myInClass.inTest();

        System.out.println();

        OutClass.InStaticClass sInClass = new OutClass.InStaticClass(); //Outer class 생성과 무관하게 생성할 수 있다.
        sInClass.inTest(); //static 메서드가 아니기때문에 Inner class를 먼저 생성해야 한다.

        OutClass.InStaticClass.sTest(); //Outer class 또는 Inner 클래스 생성과 무관하게 생성할 수 있다.

    }
}
