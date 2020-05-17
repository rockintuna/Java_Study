package operator;

public class OperatorEx3 {
    public static void main(String[] args) {

        int num1 = 10;
        int i = 2;

        boolean value = ((num1+=10)<10)&&((i+=2)<10);
        System.out.println(value); //false
        System.out.println(num1);  //20
        System.out.println(i);     //2

    }
}
