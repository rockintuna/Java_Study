package abstractex;

public class ComputerTest {
    public static void main(String[] args) {
        //Computer computer = new Computer(); //추상 클래스이기 때문에 인스턴스화 불가능
        Computer desktop = new Desktop();
        desktop.display();
        desktop.turnOn();

        Computer mynotebook = new MyNoteBook(); //NoteBook type이나 MyNoteBook type도 가능

    }
}
