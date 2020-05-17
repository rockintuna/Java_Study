package abstractex;

public class Desktop extends Computer {

    @Override
    public void display() { //상위 클래스의 모든 추상 메서드들을 구현해주지 않으면 에러가 발생한다.
                            //만약 구현하지 않거나 부분만 구현해야 할 경우 abstract 클래가 되어야 한다.
        System.out.println("Desktop display");
    }

    @Override
    public void typing() {
        System.out.println("Desktop typing");
    }

}
