# 객체 지향 프로그래밍

#### 49. 제네릭 프로그래밍  

* 제네릭 프로그래밍이란   
변수의 선언이나 메서드의 매개변수를 하나의 참조 자료형이 아닌 여러 자료형으로 변환될 수 있도록 프로그래밍 하는 방식   
실제 사용되는 참조 자료형으로의 변환은 컴파일러가 검증하므로 안정적인 프로그래밍 방식이다.  

```
package generic;

public class GenericPrinter<T extends Meterial> { // T : 자료형 매개변
                                                  // 클래스가 생성될때 T에 타입을 대입
                                                  //Meterial을 상속받은 타입만 들어갈 수 있게 제한

    private T meterial; //재료는 플라스틱,파우더 등 여러가지일 수 있으므로

    public T getMeterial() { //제네릭 타입을 사용하는 메서드 : 제네릭 메서드
        return meterial;
    }

    public void setMeterial(T meterial) {
        this.meterial = meterial;
    }

    @Override
    public String toString() {
        return meterial.toString();
    }

    public void printing() {
        meterial.doPrinting(); //T가 Meterial 클래스로 제한되면서, Meterial 클래스에 정의된 메서드를 공유할 수 있다.
    }

}
```

```
package generic;

public abstract class Meterial {

    public abstract void doPrinting();
}
```


```
package generic;

public class Plastic extends Meterial{

    @Override
    public String toString() {
        return "재료는 플라스틱입니다.";
    }

    @Override
    public void doPrinting() {
        System.out.println("플라스틱 프린팅합니다.");
    }
}
```

```
package generic;

public class Powder extends Meterial{

    @Override
    public String toString() {
        return "재료는 파우더입니다";
    }

    @Override
    public void doPrinting() {
        System.out.println("파우더 프린팅합니다.");
    }
}
```

```
package generic;

public class GenericPrinterTest {
    public static void main(String[] args) {

        GenericPrinter<Powder> powderPrinter = new GenericPrinter<>();
        Powder powder = new Powder();
        powderPrinter.setMeterial(powder);
        System.out.println(powderPrinter.toString());

        GenericPrinter<Plastic> plasticPrinter = new GenericPrinter<>();
        Plastic plastic = new Plastic();
        plasticPrinter.setMeterial(plastic);
        System.out.println(plasticPrinter.toString());

        //GenericPrinter<Water> waterPrinter; //Water는 Meterial 클래스를 상속받지 않았으므로 에러 발생

        plasticPrinter.printing();
        powderPrinter.printing();
    }
}
```

일반 클래스에서도 제네릭 메서드를 사용할 수 있다.
```
public <T,V> double makeRectangle(Point<T,V> p1, Point<T,V> p2) {
...
}
```
제네릭 메서드의 자료형 매개변수<T,V>는 메서드 내에서만 유효하다.(지역변수와 같은 개념)  
    
    