package template;

public class PlayerTest {
    public static void main(String[] args) {

        Player player1 = new Player();
        player1.play(1);

        AdvancedLevel aLevel = new AdvancedLevel();

        player1.upgradeLevel(aLevel);
        player1.play(2);

        SuperLevel sLevel = new SuperLevel();

        player1.upgradeLevel(sLevel);
        player1.play(3);
    }
}
