package string;

public class StringTest {

    public static void main(String[] args) {

        String str1 = new String("abc"); //힙메모리 영역
        String str2 = "abc";                     //상수 풀
        String str3 = "abc";

        System.out.println(str1 == str2); //false, 메모리 위치가 다르기 때문
        System.out.println(str2 == str3); //true, 둘다 상수풀이기 때문

    }
}
