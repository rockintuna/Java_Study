package template;

public class Player {
    private PlayerLevel level;

    public Player() {
        level = new BegginerLevel();
        level.showLevelMassage();
    }

    public PlayerLevel getLevel() {
        return level;
    }

    public void upgradeLevel(PlayerLevel level) {
        this.level = level;
        level.showLevelMassage();
    }

    public void play(int count) {
        level.go(count);
    }
}
