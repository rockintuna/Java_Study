package cooperation;

public class Subway {

    int lineNumber;
    int passengerCount;
    int money;

    public Subway(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void take(int money) {
        this.money += money;
        this.passengerCount++;
    }

    public void showBusInfo() {
        System.out.println(lineNumber+"호선 지하철 승객은 "+passengerCount+"명 이고, 수입은 "+money+"원 입니다.");
    }
}
