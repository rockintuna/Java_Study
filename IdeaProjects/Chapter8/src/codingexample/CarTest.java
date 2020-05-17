package codingexample;

import java.util.ArrayList;

public class CarTest {
    public static void main(String[] args) {

        ArrayList<Car> carlist = new ArrayList<Car>();

        carlist.add(new Sonata());
        carlist.add(new Avante());
        carlist.add(new Genesis());

        for (Car car : carlist) {
            car.run();
            System.out.println("=================");
        }

    }
}
