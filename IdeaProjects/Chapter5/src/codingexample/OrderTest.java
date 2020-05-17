package codingexample;

public class OrderTest {

    public static void main(String[] args) {

        Order order = new Order();

        order.orderNum=201907210001L; //형변환
        order.customerId="abc123";
        order.orderdate="2019년 07월 21일";
        order.customerName="홍길순";
        order.stockNum="PD0345-12";
        order.address="서울시 영등포구 여의도동 20번지";

        order.showOrderInfo();
    }
}
