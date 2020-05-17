package codingexample;

public class Order {

    long orderNum; //숫자만 있으므로 long type
    String customerId;
    String orderdate; //날짜는 보통 Calendar 클래스 사용.
    String customerName;
    String stockNum;
    String address;

    void showOrderInfo() {
        System.out.println("주문 번호 :"+orderNum);
        System.out.println("주문자 아이디 :"+customerId);
        System.out.println("주문 날짜 :"+orderdate);
        System.out.println("주문자 이름 :"+customerName);
        System.out.println("주문 상품 번호 :"+stockNum);
        System.out.println("배송 주소 :"+address);
    }
}
