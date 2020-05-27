# 객체 지향 프로그래밍

#### 33. 추상 클래스란?

* 추상 클래스    
추상 메서드를 포함한 클래스     
추상 메서드 : 구현코드 없이 선언부만 있는 메서드    
abstract 예약어 사용     
new(인스턴스화) 할 수 없음   

추상 클래스는 상속의 상위 클래스로 사용된다.   
추상 클래스의 추상 메서드는 하위 클래스가 구현해야 한다. 


```
package abstractex;

public abstract class Computer { //추상 메서드가 있으면 클래스는 항상 추상 클래스이다.

    public abstract void display(); //이 메서드는 구현하지 않겠다는 의미드 (추상 메서드)
    public abstract void typing();  //추상 메서드들은 하위 클래스에서 구현된다.

    public void turnOn() {
        System.out.println("전원을 켭니다.");
    }

    public void turnOff() {
        System.out.println("전원을 끕니다.");
    }

}
```
```
package abstractex;

public class Desktop extends Computer {

    @Override
    public void display() { //상위 클래스의 모든 추상 메서드들을 구현해주지 않으면 에러가 발생한다.
                            //만약 구현하지 않거나 부분만 구현해야 할 경우 abstract 클래스가 되어야 한다.
        System.out.println("Desktop display");
    }

    @Override
    public void typing() {
        System.out.println("Desktop typing");
    }

}
```

```
package abstractex;

public abstract class Notebook extends Computer{
    @Override
    public void typing() {
        System.out.println("Notebook typing");
    }
}
```

```
package abstractex;

public class MyNoteBook extends Notebook {
    @Override
    public void display() {
        System.out.println("MyNoteBook display");
    }
}
```

```
package abstractex;

public class ComputerTest {
    public static void main(String[] args) {
        //Computer computer = new Computer(); //추상 클래스이기 때문에 인스턴스화 불가능
        Computer desktop = new Desktop();
        desktop.display();
        desktop.turnOn();

        Computer mynotebook = new MyNoteBook(); //NoteBook type이나 MyNoteBook type도 가능

    }
}
```
    
    
