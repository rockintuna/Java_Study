package ifexample;

public class IfExample1 {
    public static void main(String[] args) {
        char gender = 'M';

        if ( gender == 'F' ) { // 블럭의 시작
            System.out.println("여성입니다."); // 블럭 내부에서는 들여쓰기
        } // 블럭 끝
        else {
            System.out.println("남성입니다.");
        }
    }
}
