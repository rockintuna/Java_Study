package codingexample;

public class Example1_2 {
    public static void main(String[] args) {

        char operator = '+';
        int num1=74;
        int num2=10;

        switch (operator) {
            case '+':
                System.out.println(num1+num2);
                break;
            case '-':
                System.out.println(num1-num2);
                break;
            case '*': System.out.println(num1*num2);
                break;
            case '/': System.out.println(num1/num2);
                break;
            default : System.out.println("Error");
        }
    }
}
