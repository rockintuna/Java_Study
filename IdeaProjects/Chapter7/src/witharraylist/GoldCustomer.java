package witharraylist;

public class GoldCustomer extends Customer{

    double salesRatio;

    public GoldCustomer(int customerID, String customerName) {
        super(customerID, customerName);
        customerGrade = "Gold";
        bonusRatio = 0.2;
        salesRatio = 0.1;
    }

    @Override
    public int calcPrice(int price) {
        bonusPoint += price*bonusRatio;
        return price - (int)(price*salesRatio);
    }
}
