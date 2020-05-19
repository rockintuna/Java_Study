package codingexample;

public class MyDate {

    int day;
    int month;
    int year;

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyDate) {
            MyDate date = (MyDate) obj;
            if (day == date.day && month == date.month && year == date.year) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return year*10000+month*100+day;
    }

    public static void main(String[] args) {

        MyDate date1 = new MyDate(30,1,1992);
        MyDate date2 = new MyDate(30,1,1992);

        System.out.println(date1.equals(date2));

        System.out.println(date1.hashCode());
        System.out.println(date2.hashCode());
    }
}
