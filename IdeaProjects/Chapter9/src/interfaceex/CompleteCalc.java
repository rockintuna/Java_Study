package interfaceex;

public class CompleteCalc extends Calculator{
    @Override
    public int times(int num1, int num2) {
        return num1*num2;
    }

    /*@Override
    public void description() {
        System.out.println("재정의 한 description");
    }*/

    @Override
    public int divide(int num1, int num2) {
        if (num2==0) {
            return Error;
        } else {
            return num1 / num2;
        }
    }

    public void showInfo() {
        System.out.println("모두 구현하였습니다.");
    }
}
