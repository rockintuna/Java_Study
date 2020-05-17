package operator;

public class OperatorEx2 {
    public static void main(String[] args) {

        int num1=10,num2=10,num3=10,num4=10,num5 = 10;
        num1 += 2; //r-value를 더한 뒤 대입
        System.out.println(num1);
        num2 -= 2; //r-value를 뺀 뒤 대입
        System.out.println(num2);
        num3 *= 2; //r-value를 곱한 뒤 대입
        System.out.println(num3);
        num4 /= 2; //r-value로 나눈 몫을 대입
        System.out.println(num4);
        num5 %= 2; //r-value를 나눈 나머지 대입
        System.out.println(num5);
    }
}
