package generic;

public class GenericPrinter<T extends Meterial> { // T : 자료형 매개변
                                                  // 클래스가 생성될때 T에 타입을 대입
                                                  //Meterial을 상속받은 타입만 들어갈 수 있게 제한

    private T meterial; //재료는 플라스틱,파우더 등 여러가지일 수 있으므로

    public T getMeterial() { //제네릭 타입을 사용하는 메서드 : 제네릭 메서드
        return meterial;
    }

    public void setMeterial(T meterial) {
        this.meterial = meterial;
    }

    @Override
    public String toString() {
        return meterial.toString();
    }

    public void printing() {
        meterial.doPrinting(); //T가 Meterial 클래스로 제한되면서, Meterial 클래스에 정의된 메서드를 공유할 수 있다.
    }

}
