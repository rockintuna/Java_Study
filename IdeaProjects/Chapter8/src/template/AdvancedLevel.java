package template;

public class AdvancedLevel extends PlayerLevel{

    @Override
    public void run() {
        System.out.println("빠르게 달립니다.");
    }

    @Override
    public void jump() {
        System.out.println("점프합니다.");
    }

    @Override
    public void turn() {
        System.out.println("turn할 수 없습니다.");
    }

    public void showLevelMassage() {
        System.out.println("중급자 입니다.");
    }
}
