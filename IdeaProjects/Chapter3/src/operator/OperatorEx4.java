package operator;

public class OperatorEx4 {
    public static void main(String[] args) {

        int num1 = 0B00001010; //10
        int num2 = 0B00000101; //5

        System.out.println(num1 & num2); //00000000 0
        System.out.println(num1 | num2); //00001111 15
        System.out.println(num1 ^ num2); //00001111 15
        System.out.println(num2 << 1); //(x2) 00001010 10
        System.out.println(num2 << 2); //(x2^2) 00010100 20
        System.out.println(num2 << 3); //(x2^3) 00010100 40
        System.out.println(num2 >> 2); //(/2^2) 00000001 1
        System.out.println(num2 <<= 3); //복합 대입 연산자 40
        System.out.println(num2); //40

    }
}
