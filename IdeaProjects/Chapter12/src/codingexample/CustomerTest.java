package codingexample;

import java.util.ArrayList;
import java.util.List;

public class CustomerTest {
    public static void main(String[] args) {

        List<Customer> customerList = new ArrayList<Customer>();
        Customer Lee = new Customer(1,"이순신",40,100);
        Customer Kim = new Customer(2,"김유신",20,100);
        Customer Hong = new Customer(3,"홍길동",13,50);

        customerList.add(Lee);
        customerList.add(Kim);
        customerList.add(Hong);

        customerList.stream().forEach(c->System.out.println(c.customerName+" "));
        int total = customerList.stream().mapToInt(c->c.cost).sum();
        System.out.println("총 금액 :"+total);
        customerList.stream().filter(c->c.age >= 20).map(c->c.customerName).sorted().forEach(s -> System.out.println(s));
    }
}
