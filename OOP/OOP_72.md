# 객체 지향 프로그래밍

#### 72. 그 외 입출력 클래스와 데코레이터 패턴     

 - File 클래스    
파일 개념을 추상화한 클래스  
입출력 기능은 없고 파일의 속성, 경로, 이름 등을 알 수 있음     

 - RandomAccessFile 클래스    
입출력 클래스 중 유일하게 파일 입출력을 동시에 할 수 있는 클래스   
파일 포인터가 있어서 읽고 쓰는 위치의 이동이 가능함   
다양한 자료형에 대한 메서드가 제공됨    

```
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
```

* 데코레이터 패턴  
자바의 입출력 스트림은 데코레이터 패턴을 사용   
실제 입출력 기능을 가진 객체(컴포넌트)와 그 외 다양한 기능을 제공하는 데코레이터(보조스트림)을 사용하여 다양한 입출력 기능을 구현  
상속보다 유연한 확장성을 가짐    
지속적인 서비스의 증가와 제거가 용이함   
 
```
package stream.coffee;

public class CoffeeTest {
    public static void main(String[] args) {

        Coffee americano = new KenyaAmericano();
        americano.brewing();

        System.out.println();

        Coffee kenyaLatte = new Latte(new KenyaAmericano());
        kenyaLatte.brewing();

        System.out.println();

        Coffee mocha = new Mocha(new Latte(new KenyaAmericano()));
        mocha.brewing();

        System.out.println();

        Coffee etiopiaLatte = new Latte(new EtiopiaAmericano());
        etiopiaLatte.brewing();

    }
}
```
    
    