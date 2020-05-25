package stream.decorator;

import java.io.*;

public class DataStreamTest {
    public static void main(String[] args) {

        try(FileOutputStream fos = new FileOutputStream("IdeaProjects/Chapter14/data.txt");
            DataOutputStream dos = new DataOutputStream(fos);
            FileInputStream fis = new FileInputStream("IdeaProjects/Chapter14/data.txt");
            DataInputStream dis = new DataInputStream(fis)) {

            dos.writeByte(100); //한 바이트로 씀
            dos.write(100); //4 바이트로 씀
            dos.writeChar('A');
            dos.writeUTF("안녕하세요");

            System.out.println(dis.readByte()); //쓴 방식대로 읽어야 한다
            System.out.println(dis.read());
            System.out.println(dis.readChar());
            System.out.println(dis.readUTF());

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
