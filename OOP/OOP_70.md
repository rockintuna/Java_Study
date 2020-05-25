# 객체 지향 프로그래밍

#### 70. 보조 스트림     

실제 읽고 쓰는 스트림이 아닌 보조적인 기능을 추가하는 스트림
데코레이터라고도 불린다    
상위 클래스 FilterInputStream, FilterOutputStream    
생성자의 매개변수로 다른 스트림을 가진다
  
데코레이터 패턴(Decorator Pattern)     
기반 스트림 > 보조 스트림1 > 보조 스트림2  

Buffered 스트림 : 내부에 8192바이트 배열을 가지고 있음, 읽거나 쓸때 속도가 빠름    
```
private static int DEFAULT_BUFFER_SIZE = 8192;
```

Buffered 스트림을 이용하여 파일 복사
```
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
```

DataInputStream/DataOutputStream : 자료가 저장된 상태 그대로 자료형을 유지하며 읽거나 쓰는 기능을 제공하는 스트림    

```
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
```
    
    