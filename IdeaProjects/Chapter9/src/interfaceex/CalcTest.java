package interfaceex;

public class CalcTest {
    public static void main(String[] args) {

        Calc calc = new CompleteCalc();
        int n1 =10;
        int n2 = 2;

        System.out.println(calc.add(n1,n2));
        System.out.println(calc.substract(n1,n2));
        System.out.println(calc.times(n1,n2));
        System.out.println(calc.divide(n1,n2));

        CompleteCalc ccalc = (CompleteCalc)calc;
        ccalc.showInfo();

        calc.description();

        int[] arr = {1,2,3,4,5};
        System.out.println(Calc.total(arr)); //참조변수가 아닌 인터페이스 이름으로 참조한다.

    }
}
