package cooperation;

public class Taxi {

    int taxiNumber;
    int money;
    int passengerCount;

    public Taxi(int taxiNumber) {
        this.taxiNumber = taxiNumber;
    };

    public void take(int money) {
        this.money += money;
        this.passengerCount++;
    }

    public void showTaxiInfo() {
        System.out.println(taxiNumber+"번 택시의 승객은 "+passengerCount+"명 이고, 수입은 "+money+"원 입니다.");
    }

}
