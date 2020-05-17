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
