package witharraylist;

import java.util.ArrayList;

public class CustomerTest {
    public static void main(String[] args) {

        Customer Lee = new VIPCustomer(101,"Lee",1001);
        Customer Kim = new GoldCustomer(102,"Kim");
        Customer Choi = new GoldCustomer(103,"Choi");
        Customer Park = new Customer(104,"Park");
        Customer Jung  = new Customer(105,"Jung");

        ArrayList<Customer> customerList = new ArrayList<Customer>();
        customerList.add(Lee);
        customerList.add(Kim);
        customerList.add(Choi);
        customerList.add(Park);
        customerList.add(Jung);

        for (Customer customer : customerList) {
            System.out.println(customer.showCustomerInfo());
        }

        for (Customer customer : customerList) {
            System.out.println(customer.customerName+"님이 지불하신 금액은 "+customer.calcPrice(10000)+"원 입니다.");
            System.out.println("총 "+customer.bonusPoint+"점이 적립되었습니다.");
        }
    }
}
