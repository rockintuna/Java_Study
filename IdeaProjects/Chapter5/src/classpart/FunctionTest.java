package classpart;

public class FunctionTest {

    public static int addNum(int num1, int num2) { //2개의 매개변수 및 int 반환 값이 필요하다.
        int result;
        result = num1+num2;
        return result; //반환 값 result
    }

    public static void sayHello(String greeting) { //반환 값이 없는 함수 (void)
        System.out.println(greeting);
    }

    public static int calcSum() { //매개변수가 없고 반환 값만 있는 함수

        int sum=0;
        int i;

        for (i=0;i<=100;i++) {
            sum += i;
        }

        return sum;
    }

    public static void main(String[] args) {
        int n1 = 10;
        int n2 = 20;

        int total = addNum(n1,n2); //매개변수를 통해 함수의 반환 값을 int 변수로 받음.
        sayHello("안녕"); //반환 값 없이 매개변수 출력
        int num = calcSum(); //매개변수 없이 반환 값을 int 변수로 받음.

        System.out.println(total);
        System.out.println(num);
    }

}
