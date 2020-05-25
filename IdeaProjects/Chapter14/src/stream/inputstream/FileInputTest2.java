package stream.inputstream;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputTest2 {
    public static void main(String[] args) {

        try (FileInputStream fis = new FileInputStream("IdeaProjects/Chapter14/input.txt")){ //AutoCloseable
            int i;
            while ( (i = fis.read()) != -1 ) {
                System.out.print((char)i);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
