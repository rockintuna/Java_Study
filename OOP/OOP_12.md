# 객체 지향 프로그래밍

#### 12. 객체 간 협력

객체 지향 프로그램은 객체를 정의 하고 객체 간의 '협력'을 구현한 프로그램     

학생, 버스, 지하철 객체 간 협력 프로그램

- 학생
```
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

    public void showInfo() {

        System.out.println(studentName+"님의 남은 돈은 "+money+"원 입니다.");
    }
}
```

- 버스
```
package cooperation;

public class Bus {

    int busNumber;
    int passengerCount;
    int money;

    public Bus(int busNumber) {
        this.busNumber = busNumber;
    }

    public void take(int money) { //승차 메서드
        this.money += money;
        this.passengerCount++;
    }

    public void showBusInfo() {
        System.out.println(busNumber+"번 버스의 승객은 "+passengerCount+"명 이고, 수입은 "+money+"원 입니다.");
    }

}
```

- 지하철
```
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
        System.out.println(lineNumber+"번 버스의 승객은 "+passengerCount+"명 이고, 수입은 "+money+"원 입니다.");
    }
}

```

