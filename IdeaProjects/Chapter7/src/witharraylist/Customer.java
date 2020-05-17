package witharraylist;

public class Customer {

    protected int customerID; // 하위 클래들이 참조할수 있도록 private가 아닌 protected 사용
    protected String customerName;
    protected String customerGrade;
    int bonusPoint;
    double bonusRatio;

    public Customer(int customerID,String customerName) {
        this.customerID = customerID;
        this.customerName = customerName;
        customerGrade="SILVER";
        bonusRatio=0.01;
    }

    public int calcPrice(int price) {
        bonusPoint += price*bonusRatio;
        return price;
    }

    public String showCustomerInfo() {
        return customerName+"님의 등금은 "+customerGrade+"이며, 적립된 포인트는 "+bonusPoint+"점 입니다.";
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerGrade() {
        return customerGrade;
    }

    public void setCustomerGrade(String customerGrade) {
        this.customerGrade = customerGrade;
    }

}
