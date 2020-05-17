package cooperation;

public class TakeTransTest {
    public static void main(String[] args) {

        Student studentJ = new Student("James",5000);
        Student studentT = new Student("Tomas",10000);
        Student studentE = new Student("Edward",20000);

        Bus bus100 = new Bus(100);
        Bus bus500 = new Bus(500);

        Subway subwayGreen = new Subway(2);
        Subway subwayBlue = new Subway(4);

        Taxi taxi7979 = new Taxi(7979);

        studentJ.takeBus(bus100);
        studentT.takeSubway(subwayGreen);
        studentE.takeTaxi(taxi7979);

        studentJ.showInfo();
        studentT.showInfo();
        studentE.showInfo();

        bus100.showBusInfo();
        bus500.showBusInfo();
        subwayBlue.showBusInfo();
        subwayGreen.showBusInfo();
        taxi7979.showTaxiInfo();
    }
}

