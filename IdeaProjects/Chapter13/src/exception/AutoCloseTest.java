package exception;

public class AutoCloseTest {
    public static void main(String[] args) {

        AutoCloseObject obj = new AutoCloseObject();
        try(obj) {
            throw new Exception(); //exception 강제 발생
        } catch (Exception e) {
            System.out.println(e);
        } //close() 를 명시적으로 호출하지 않지만 내부적으로는 호출 됨
    }
}
