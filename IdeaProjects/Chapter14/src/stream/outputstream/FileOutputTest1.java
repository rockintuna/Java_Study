package stream.outputstream;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputTest1 {
    public static void main(String[] args) {

        try(FileOutputStream fos = new FileOutputStream("IdeaProjects/Chapter14/output.txt",true)) { //append : 이어쓰기
            fos.write(65); //ABC 숫자를 넣었지만 문자로 출력
            fos.write(66);
            fos.write(67);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
