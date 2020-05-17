package inheritance;

public class OverridingTest {
    public static void main(String[] args) {

        VIPCustomer Lee = new VIPCustomer();
        Lee.setCustomerID(10010);
        Lee.setCustomerName("이순신");
        Lee.bonusPoint = 20000;

        Customer Kim = new VIPCustomer();
        Kim.setCustomerID(10020);
        Kim.setCustomerName("김유신");
        Kim.bonusPoint = 10000;

        System.out.println(Lee.showCustomerInfo());
        System.out.println(Kim.showCustomerInfo());
        System.out.println(Lee.calcPrice(1000)); //1000-1000*0.1 = 900
        System.out.println(Kim.calcPrice(1000)); //1000-1000*0.1 = 900
    }
}
