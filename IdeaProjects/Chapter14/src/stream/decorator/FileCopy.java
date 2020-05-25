package stream.decorator;

import java.io.*;
//import java.net.Socket;
//import java.nio.Buffer;

public class FileCopy {
    public static void main(String[] args) throws IOException {

        long milliseconds = 0;

        try(FileInputStream fis = new FileInputStream("IdeaProjects/Chapter14/alpha.txt");
            FileOutputStream fos = new FileOutputStream("IdeaProjects/Chapter14/copy.txt");
            BufferedInputStream bis = new BufferedInputStream(fis); //더 빠르게 처리하기 위해 데코레이터 사용
            BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            milliseconds = System.currentTimeMillis(); //현재시간

            int i;
            while ((i=bis.read()) != -1) {
                bos.write(i); //while 동안 한 바이트씩 읽어서 바로 쓰기
            }

            milliseconds = System.currentTimeMillis() - milliseconds; //시간차 계산
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println(milliseconds);

        //Socket socket = new Socket();

        //BufferedReader isr = new BufferedReader(new InputStreamReader(socket.getInputStream())); //byte->문자 보조에 추가로 버퍼기능까지
        //isr.readLine();//BufferedReader에만 있는 메서드, 한줄만 읽는 기능(/n 까지)

    }
}
