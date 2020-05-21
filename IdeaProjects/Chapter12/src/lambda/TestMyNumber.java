package lambda;

public class TestMyNumber {
    public static void main(String[] args) {

        MyMaxNumber max = (x,y) -> (x>=y)?x:y; //MyMaxNumber 인터페이스의 메서드가 호출되면
                                               //x,y 변수를 통해 이렇게 구현한다
        System.out.println(max.getMaxNumber(10,30));
    }
}
