package cooperation;

public class Student {

    String studentName;
    int grade;
    int money;

    public Student(String studentName,int money) {

        this.studentName = studentName;
        this.money = money;
    }

    public void takeBus(Bus bus) {  //버스 객체와의 협업
        bus.take(1000);
        this.money -= 1000;
    }

    public void takeSubway(Subway subway) {  //지하철 객체와의 협업
        subway.take(1200);
        this.money -= 1200;
    }

    public void takeTaxi(Taxi taxi) {
        taxi.take(10000);
        this.money -= 10000;
    }

    public void showInfo() {

        System.out.println(studentName+"님의 남은 돈은 "+money+"원 입니다.");
    }
}
