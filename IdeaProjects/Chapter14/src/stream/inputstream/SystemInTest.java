package stream.inputstream;

import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInTest {
    public static void main(String[] args) {

        System.out.println("입력");

        try {
            int i;
            while ((i = System.in.read()) != '\n') {
                System.out.print((char)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("입력 후 '끝' 이라고 쓰세요.");

        try {
            int i;
            InputStreamReader isr = new InputStreamReader(System.in); //byte -> 문자, 다른 스트림을 생성자의 매개변수로 받음
            while ((i = isr.read()) != '끝') { //'끝'은 2byte 이기 때문에 보조 스트림 필요
                System.out.print((char)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}