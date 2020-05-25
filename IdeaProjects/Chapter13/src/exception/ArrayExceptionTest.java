package exception;

public class ArrayExceptionTest {
    public static void main(String[] args) {

        int[] arr = new int[5];

        try {
            for (int i = 0; i <= 5; i++) {
                System.out.println(arr[i]);
            }
        }catch(ArrayIndexOutOfBoundsException e) { //IDE가 잡아주지 않으므로 직접 써야함
            System.out.println(e);
            System.out.println("예외 처리");
        } //예외만 처리되고 프로그램은 계속 수행됨
        System.out.println("프로그램 종료");
    }
}
