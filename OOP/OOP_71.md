# 객체 지향 프로그래밍

#### 71. 직렬화    

인스턴스의 상태를 그대로 저장하거나 네트웍으로 전송하고 이를 다시 복원하는 방식    
ObjectInputStream, ObjectOutputStream 보조스트림 사용  

Serializable 인터페이스  
직렬화는 인스턴스의 내용이 외부로 유출되는 것이므로 프로그래머가 객체의 직렬화 가능 여부를 명시함  
구현 코드가 없는 마크 인터페이스  

```
package stream.serialization;

import java.io.*;

// class Person implements Externalizable { //직렬화가능하며 구현해야할 기능이 있음  
class Person implements Serializable { //직렬화가능하다는 마크 인터페이스 
    String name;
    transient String job; //직렬화 제외 키워드

    public Person(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String toString() {
        return name + "," + job;
    }

}

public class SerializationTest {
    public static void main(String[] args) {

        Person personLee = new Person("이순신","엔지니어");
        Person personKim = new Person("김유신","선생님");

        //객체 파일 생성
        try (FileOutputStream fos = new FileOutputStream("IdeaProjects/Chapter14/serial.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(personLee);
            oos.writeObject(personKim);
        } catch (IOException e) {
            System.out.println(e);
        }

        //객체 파일 읽기
        try (FileInputStream fis = new FileInputStream("IdeaProjects/Chapter14/serial.dat");
        ObjectInputStream ois = new ObjectInputStream(fis)) {
            Person p1 = (Person) ois.readObject();
            Person p2 = (Person) ois.readObject();

            System.out.println(p1);
            System.out.println(p2);

        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }


    }
}
```
    
    