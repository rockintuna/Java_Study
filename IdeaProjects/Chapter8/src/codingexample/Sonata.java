package codingexample;

public class Sonata extends Car {
    @Override
    public void start() {
        System.out.println("Sonata 시동을 켭니다.");
    }

    @Override
    public void drive() {
        System.out.println("Sonata 운행합니다.");
    }

    @Override
    public void stop() {
        System.out.println("Sonata 멈춥니다.");
    }

    @Override
    public void turnOff() {
        System.out.println("Sonata 시동을 끕니다.");
    }
}
