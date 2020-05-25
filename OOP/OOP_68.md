# 객체 지향 프로그래밍

#### 68. 바이트 단위, 문자 단위 입출력 스트림

* 바이트 단위 스트림    
InputStream : 바이트 단위 입력 스트림 최상위 클래스     
OutputStrem : 바이트 단위 출력 스트림 최상위 클래스     

추상 메서드를 포함한 추상클래스로 하위 클래스가 구현하여 사용  
주요 하위 클래스   
FileInputStream, ByteArrayInputStream, FilterInputStream, etc
FileOutputStream, ByteArrayOutputStream, FilterOutputStream, etc

* FileInputStream, FileOutputStream 사용하기    
파일에 한 바이트 씩 자료를 읽고 쓰는데 사용   
입력 스트림은 파일이 없을 때 예외 발생  
출력 스트림은 파일이 없는 경우 새로 파일을 생성하여 출력   

FileInputStream
```
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
```

FileInputStream & try-with-resources
```
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
```

FileInputStream & byte[]로 읽기
```
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
```

FileOutputStream
```
package stream.outputstream;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputTest1 {
    public static void main(String[] args) {

        try(FileOutputStream fos = new FileOutputStream("output.txt",true)) { //append : 이어쓰기
            fos.write(65); //ABC 숫자를 넣었지만 문자로 출력
            fos.write(66);
            fos.write(67);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
```

FileOutputStream & FileInputStream 
```
package stream.outputstream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputTest2 {
    public static void main(String[] args) {

        byte[] bs = new byte[26];
        byte data = 65;
        for (int i = 0; i<bs.length; i++) {
            bs[i] = data;
            data++;
        }

        try(FileOutputStream fos = new FileOutputStream("alpha.txt",true);
            FileInputStream fis = new FileInputStream("alpha.txt")) {

            fos.write(bs);
            int ch;
            while ((ch = fis.read()) != -1) {
                System.out.print((char)ch);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
```
    
* 문자 단위 스트림     
Reader : 문자 단위로 읽는 최상위 스트림  
Writer : 문자 단위로 쓰는 최상위 스트림  
추상 메서드를 포함한 추상클래스로 하위 클래스가 구현하여 사용  
주요 하위 클래스   
FileReader, InputStreamReader, BufferedReader, etc  
FileWriter, OutputStreamWriter, BufferedWriter, etc     

* FileReader와 FileWriter    
파일에 문자를 읽고 쓸때 가장 많이 사용하는 클래스    
문자의 인코딩 방식을 지정할 수 있음    

바이트 단위 스트림과의 비교
```
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
```

FileWriter & FileReader
```
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
```