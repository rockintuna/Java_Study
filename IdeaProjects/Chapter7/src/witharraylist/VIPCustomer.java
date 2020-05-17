package witharraylist;

public class VIPCustomer extends Customer { //상위 클래스(Customer) 지정

    double salesRatio;
    private int agentID;

    public VIPCustomer(int customerID, String customerName, int agentID) {
        super(customerID,customerName);
        customerGrade = "VIP";
        bonusRatio = 0.05;
        salesRatio = 0.1;
        this.agentID = agentID;
    }

    @Override //에너테이션.. 컴파일러에게 오버라이딩 사실 전달
    public int calcPrice(int price) { //오버라이딩 소스 메서드와 선언부가 동일해야 한다
        bonusPoint += price*bonusRatio;
        return price - (int)(price*salesRatio);
    }

    @Override
    public String showCustomerInfo() {
        return super.showCustomerInfo()+" 담당 상담원 번호는 "+agentID+"입니다.";
    }
}
