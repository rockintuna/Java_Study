# 객체 지향 프로그래밍

#### 40. 인터페이스의 요소들     

상수 : 선언된 모든 변수는 상수로 처리 됨    
메서드 : 모든 메서드는 추상 메서드    
java8   
디폴트 메서드 : 기본 구현을 가지는 메서드, 클래스에서 재정의 가능  
정적 메서드 : 인스턴스 생성과 상관없이 인터페이스 타입으로 호출하는 메서드  
java9   
private 메서드 : 인터페이스 내에서 사용하기 위한 메서드, 클래스에서 재정의 불가능  

추상 클래스와 다른점은 하나의 클래스에서 여러 인터페이스를 구현할 수 있다.  
이떄, 디폴트 메서드의 이름이 중복된다면, 재정의 해야한다.   

인터페이스 간에 상속할 수도 있다.(type inheritance) 이때 implements 대신 extends를 사용한다.     

요소들     
```
    default void description() { //디폴트 메서드
        System.out.println("정수 계산기를 구현합니다.");
        myMethod();
    }

    static int total(int[] arr) { //정적 메서드, 인스턴스를 생성하지 않고도 쓸 수 있다.
        int total=0;

        for(int i:arr) {
            total += i;
        }
        myStaticMethod();
        return total;
    }

    private void myMethod() { //일반 private 메서드
        System.out.println("private method");
    }

    private static void myStaticMethod() { //정적 private 메서드
        System.out.println("private static method");
    }
```

동시에 여러 인터페이스 상속, 인터페이스 간의 상속    

```
package interfaceex;

public interface X {
    void x();
}

```
```
package interfaceex;

public interface Y {
    void y();
}
```

```
package interfaceex;

public interface MyInterface extends X,Y{
    void myMythod();

}
```
```
package interfaceex;

public class MyClass implements MyInterface{
    // MyClass는 MyInterface 타입이기도 하지만, X 타입이기도 하고 Y 타입이기도 하다.
    @Override
    public void myMythod() {

    }

    @Override
    public void x() {

    }

    @Override
    public void y() {

    }
}
```

인터페이스 상속과 동시에 클래스 상속    
```
package bookshelf;

public interface Queue {

    void enQueue(String title);
    String deQueue();

    int getSize();
}
```

```
package bookshelf;

import java.util.ArrayList;

public class Shelf {

    protected ArrayList<String> shelf;

    public Shelf() {
        shelf = new ArrayList<String>();
    }

    public ArrayList<String> getShelf() {
        return shelf;
    }

    public int getCount() {
        return shelf.size();
    }
}
```

```
package bookshelf;

public class BookShelf extends Shelf implements Queue{ //extends 먼저
    @Override
    public void enQueue(String title) {
        shelf.add(title);
    }

    @Override
    public String deQueue() {
        return shelf.remove(0);
    }

    @Override
    public int getSize() {
        return getCount();
    }
}
```

```
package bookshelf;

public class BookShelfTest {
    public static void main(String[] args) {

        Queue bookQueue = new BookShelf(); //기능적인것을 우선한다면 보통 인터페이스 타입으로 선언
        bookQueue.enQueue("태백산맥1");
        bookQueue.enQueue("태백산맥2");
        bookQueue.enQueue("태백산맥3");

        System.out.println(bookQueue.deQueue());
        System.out.println(bookQueue.deQueue());
        System.out.println(bookQueue.deQueue());
    }
}
```
    
    
