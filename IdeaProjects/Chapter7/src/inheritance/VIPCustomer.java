package inheritance;

public class VIPCustomer extends Customer{ //상위 클래스(Customer) 지정

    double salesRatio;
    private int agentID;

    public VIPCustomer() {
        //super(); //아무런 상위 클래스 생성자 호출 명령이 없으면 pre-compile 단계에서 이 함수가 들어감
                   //super : 상위 클래스의 메모리 위치 함수, super() : 상위 클래스의 기본 생성자 호출
        //super(0, null); //만약 상위 클래스의 기본 생성자가 없으면, 명시적으로 상위 클래스의 생성자를 호출해야 한다.
        customerGrade = "VIP"; //만약 상위 클래스에서 private인 경우 사용할 수 없음
        bonusRatio = 0.05;
        salesRatio = 0.1;

        System.out.println("VIPCustomer() 생성자 호출");
    }
    /*public VIPCustomer(int customerID, String customerName) {
    //만약 상위 클래스의 기본 생성자가 없으면, 명시적으로 상위 클래스의 생성자를 호출해야 한다. 방법2
        super(customerID, customerName);
        customerGrade = "VIP";
        bonusRatio = 0.05;
        salesRatio = 0.1;
    }*/

    @Override //에너테이션.. 컴파일러에게 오버라이딩 사실 전달
    public int calcPrice(int price) { //오버라이딩 소스 메서드와 선언부가 동일해야 한다
        bonusPoint += price*bonusRatio;
        return price - (int)(price*salesRatio);
    }
}
