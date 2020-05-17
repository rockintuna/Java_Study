package interfaceex;

public class CustomerTest {
    public static void main(String[] args) {

        Customer customer = new Customer();

        customer.buy();
        customer.sell();
        customer.order();
        customer.sayHello();

        Buy buyer = customer;  //모두 인스턴스의 메서드가 실행된다.
        buyer.buy();
        buyer.order();

        Sell seller = customer;
        seller.sell();
        seller.order();


    }
}
