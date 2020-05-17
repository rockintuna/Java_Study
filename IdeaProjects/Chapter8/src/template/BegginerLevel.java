package template;

public class BegginerLevel extends PlayerLevel {
    @Override
    public void run() {
        System.out.println("천천히 달립니다.");
    }

    @Override
    public void jump() {
        System.out.println("jump할 수 없습니다.");
    }

    @Override
    public void turn() {
        System.out.println("turn할 수 없습니다.");
    }

    @Override
    public void showLevelMassage() {
        System.out.println("초보자 입니다.");
    }
}
