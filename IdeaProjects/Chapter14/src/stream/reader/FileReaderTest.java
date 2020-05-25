package stream.reader;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReaderTest {
    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream("IdeaProjects/Chapter14/reader.txt"); //byte단위 스트림
        int i;
        while ((i = fis.read()) != -1) {
            System.out.print((char)i); //byte단위이기 때문에 한글 깨짐
        }
        fis.close();

        System.out.println();
        fis = new FileInputStream("IdeaProjects/Chapter14/reader.txt");
        InputStreamReader isr = new InputStreamReader(fis); //보조 스트림 사용 (byte -> 문자)
        int k;
        while ((k = isr.read()) != -1) {
            System.out.print((char)k);
        }
        isr.close();

        System.out.println();

        FileReader fr = new FileReader("IdeaProjects/Chapter14/reader.txt"); //문자 단위 스트림
        int j;
        while ((j = fr.read()) != -1) {
            System.out.print((char)j);
        }
        fr.close();

    }
}