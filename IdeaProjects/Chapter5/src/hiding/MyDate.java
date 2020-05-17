package hiding;

public class MyDate {

    private int day;
    private int month;
    private int year;

    private boolean isValid=true;

    public void setDay(int day) { //private 멤버 변수의 외부 입력을 위한 public 메서드 제공
        if (day<0 || day>31) {
            isValid = false;
        } else {
            this.day = day;           //멤버 변수와 매개 변수의 이름이 같은 경우 this.멤버변수
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
    public int getMonth() { //private 멤버 변수의 외부 참조를 위한 public 메서드 제공
        return month;
    }
    public int getYear() { //private 멤버 변수의 외부 참조를 위한 public 메서드 제공
        return year;
    }

    public void showDate() {

        if (isValid) {
            System.out.println(year+"년 "+month+"월 "+day+"일 ");
        } else {
            System.out.println("유효하지 않은 날짜 입니다.");
        }
    }
}
