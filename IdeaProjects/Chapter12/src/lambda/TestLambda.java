package lambda;

interface PrintString{
    void showString(String str);
}

public class TestLambda {
    public static void main(String[] args) {

        PrintString lambdaStr = str -> System.out.println(str); //함수의 구현부가 변수로 대입
        lambdaStr.showString("Test1");

        showMyString(lambdaStr); //매개변수로 활용
        PrintString lambdaStr2 = returnString(); //반환된 구현부를 변수에 대입
        lambdaStr2.showString("Test3");
    }

    public static void showMyString(PrintString p) {
        p.showString("Test2");
    }

    public static PrintString returnString() {
        return str->System.out.println(str+"!!!"); //함수의 구현부를 반환
    }
}
