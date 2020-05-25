package stream.inputstream;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputTest3 {
    public static void main(String[] args) {

        try (FileInputStream fis = new FileInputStream("IdeaProjects/Chapter14/input2.txt")){ //AutoCloseable
            int i;
            byte[] bs = new byte[10];
            while ( (i = fis.read(bs)) != -1 ) { //i:읽은 개수
                for (int k=0; k<i; k++) {
                    System.out.print((char)bs[k]);
                }
                System.out.println();
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
