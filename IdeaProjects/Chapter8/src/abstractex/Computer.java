package abstractex;

public abstract class Computer { //추상 메서드가 있으면 클래스는 항상 추상 클래스이다.

    public abstract void display(); //이 메서드는 구현하지 않겠다는 의미드 (추상 메서드)
    public abstract void typing();  //추상 메서드들은 하위 클래스에서 구현된다.

    public void turnOn() {
        System.out.println("전원을 켭니다.");
    }

    public void turnOff() {
        System.out.println("전원을 끕니다.");
    }

}
