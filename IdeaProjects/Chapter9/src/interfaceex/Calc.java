package interfaceex;

public interface Calc {

    double PI = 3.14; //이텔릭체 : 상수
    int Error = -999999999;

    int add(int num1,int num2);
    int substract(int num1,int num2);
    int times(int num1,int num2);
    int divide(int num1,int num2);

    default void description() { //디폴트 메서드
        System.out.println("정수 계산기를 구현합니다.");
        myMethod();
    }

    static int total(int[] arr) { //정적 메서드, 인스턴스를 생성하지 않고도 쓸 수 있다.
        int total=0;

        for(int i:arr) {
            total += i;
        }
        myStaticMethod();
        return total;
    }

    private void myMethod() { //일반 private 메서드
        System.out.println("private method");
    }

    private static void myStaticMethod() { //정적 private 메서드
        System.out.println("private static method");
    }
}
