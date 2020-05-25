package stream.inputstream;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputTest1 {
    public static void main(String[] args) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("IdeaProjects/Chapter14/input.txt");
            int i;
            while ( (i = fis.read()) != -1 ) { //-1이 return 될 경우 끝이므로
                System.out.print((char)i);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }
}
