package inheritance;

public class CustomerTest {
    public static void main(String[] args) {

        //Customer Lee = new Customer();
        Customer Kim = new VIPCustomer();

        //Lee.setCustomerID(10010);
        //Lee.setCustomerName("이순신");
        //Lee.bonusPoint = 1000;

        Kim.setCustomerID(10020);    //하위 클래스에서 상위 클래스의 기능 사용
        Kim.setCustomerName("김유신");
        Kim.bonusPoint = 10000;

        //System.out.println(Lee.showCustomerInfo());
        System.out.println(Kim.showCustomerInfo());
    }
}
