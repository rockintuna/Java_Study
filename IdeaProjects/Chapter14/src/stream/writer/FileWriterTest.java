package stream.writer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterTest {
    public static void main(String[] args) throws IOException {

        FileWriter fw = new FileWriter("IdeaProjects/Chapter14/writer.txt");

        fw.write('A');

        char[] cArr = {'B','C','D','E','F'};
        fw.write(cArr);
        fw.write(cArr,2,2); //DE

        String str = "안녕하세요.";
        fw.write(str);
        fw.close();

        FileReader fr = new FileReader("IdeaProjects/Chapter14/writer.txt");
        int i;
        while ((i=fr.read()) != -1) {
            System.out.print((char)i);
        }
        fr.close();

    }
}
