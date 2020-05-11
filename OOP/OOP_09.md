# 객체 지향 프로그래밍

#### 09. 정보 은닉  

* 접근 제어자 : 변수, 메서드, 생성자에 대한 접근 권한 지정    
public : 완전 공개    
private : 해당 클래스 내에서만 공개    
protected : 상위 클래스의 private변수를 하위 클래스에서 public으로 쓰고 싶을 때    
default : 같은 패키지 내에서만 공개    

 * 정보은닉
클래스 내부의 정보에 접근하지 못하도록 함
(private 접근 제어자를 통한 정보 은닉)
private를 외부에서 접근하게 하려면 public 메서드 제공

```
package hiding;

public class MyDate {

    private int day;
    private int month;
    private int year;

    private boolean isValid=true; //멤버 변수 boolean은 선언과 동시에 false로 대입된다.

    public void setDay(int day) { //private 멤버 변수의 외부 입력을 위한 public 메서드 제공
        if (day<0 || day>31) {    //잘못된 사용 방지
            isValid = false;
        } else {
            this.day = day;       //멤버 변수와 매개 변수의 이름이 같은 경우 this.멤버변수
        }
    }

    public void setMonth(int month) {
        if (month<0 || month>12) {
            isValid = false;
        } else {
            this.month = month;
        }
    }

    public void setYear(int year) {
        if (year<0) {
            isValid = false;
        } else {
            this.year = year;
        }
    }

    public int getDay() { //private 멤버 변수의 외부 참조를 위한 public 메서드 제공
        return day;
    }
    public int getMonth() { 
        return month;
    }
    public int getYear() { 
        return year;
    }

    public void showDate() {

        if (isValid) { //잘못된 사용 검
            System.out.println(year+"년 "+month+"월 "+day+"일 ");
        } else {
            System.out.println("유효하지 않은 날짜 입니다.");
        }
    }
}
```
보통 위와 같은 public 메서드의 이름을 set get으로 시작하여 생성하지만,
다른 이름으로 생성도 가능하다.

외부 접근
```
package hiding;

public class MyDateTest {
    public static void main(String[] args) {

        MyDate date = new MyDate();

        date.setDay(10);
        date.setMonth(7);
        date.setYear(2019);

        date.showDate();
    }
```