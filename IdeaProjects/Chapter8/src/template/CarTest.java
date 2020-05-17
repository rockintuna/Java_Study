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
