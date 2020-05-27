package stream.others;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {
    public static void main(String[] args) throws IOException {

        RandomAccessFile rf = new RandomAccessFile("IdeaProjects/Chapter14/random.txt","rw");

        rf.writeInt(100);
        System.out.println(rf.getFilePointer()); //현재 포인터 위치
        rf.writeDouble(3.14);
        rf.writeUTF("안녕하세요");

        rf.seek(0); //포인터 위치 변경
        int i = rf.readInt();
        double d = rf.readDouble();
        String str = rf.readUTF();

        System.out.println(i);
        System.out.println(d);
        System.out.println(str);

        rf.close();

    }
}
