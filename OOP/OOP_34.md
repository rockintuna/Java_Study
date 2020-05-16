# 객체 지향 프로그래밍

#### 34. 추상 클래스 응용: 템플릿 메서드 

템플릿 : 틀이나 견본    
템플릿 메서드 : 추상 메서드나 구현된 메서드를 활용하여 전체의 흐름을 정의 해 놓은 메서드     
            final로 선언하여 재정의할 수 없게함  
템플릿 메서드 패턴 : 디자인 패턴의 일종     
프레임 워크에서 많이 사용되는 설계 패턴  
추상 클래스로 선언된 상의 클래스에서 추상 메서드를 이용하여 전체 구현의 흐름을 정의하고   
구체적인 각 메서드 구현은 하위 클래스에 위임함  
하위 클래스가 어떤 구현을 하든 템플릿 메서드에 정의된 시나리오 대로 수행됨   

```
package template;

public abstract class Car {

    public abstract void drive();
    public abstract void stop();

    public void startCar() {
        System.out.println("시동을 켭니다.");
    }
    public void turnOff() {
        System.out.println("시동을 끕니다.");
    }

    public void washCar() {}; //훅메서드, 하위 클래스에서 재정의 용으로 생성

    final public void run() { //템플릿 메서드, 재정의 할 수 없도록 final 키워드 사용
                              //시나리오가 정의되어있다.
        startCar();
        drive();
        stop();
        turnOff();
        washCar();
    }
}
```

```
package template;

public class AICar extends Car {
    @Override
    public void drive() {
        System.out.println("자율 주행합니다.");
        System.out.println("자동차가 스스로 방향을 바꿉니다.");
    }

    @Override
    public void stop() {
        System.out.println("스스로 멈춥니다.");
    }

    @Override
    public void washCar() { //훅메서드 재정의
        System.out.println("자동 세차 합니다.");
    }
}
```

```
package template;

public class ManualCar extends Car {
    @Override
    public void drive() {
        System.out.println("사람이 운전합니다.");
        System.out.println("사람이 핸들을 조작합니다.");
    }

    @Override
    public void stop() {
        System.out.println("브레이크를 밟아서 정지합니다.");
    }
}
```

```
package template;

public class CarTest {
    public static void main(String[] args) {

        Car aicar = new AICar();
        aicar.run();

        System.out.println("=======================");

        Car manualcar = new ManualCar();
        manualcar.run();

    }
}
```

* final 예약어     
final 변수 : 변경될 수 없는 상수  
final 메서드 : 하위 클래스에서 재정의 할 수 없음     
final 클래스 : 더 이상 상속될 수 없음  
    
     