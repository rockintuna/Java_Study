# 객체 지향 프로그래밍

#### 25. 상속이란   

클래스에서 상속의 의미 :  
새로운 클래스를 정의할 때 이미 구현된 클래스를 상속받아서    
속성이나 기능이 확장되는 클래스를 구현 함.

```
class B extends A {
}
```

상속하는 클래스 : 상위 클래스   
상속받는 클래스 : 하위 클래스   
    
자바에서는 다중 상속이 불가능하다.     

상속을 사용하는 경우 :   
상위클래스는 더 일반적인 개념과 기능을 가짐    
하위클래스는 더 구체적인 개념과 기능을 가짐    

상위 클래스
```
package inheritance;

public class Customer {

    protected int customerID; // 하위 클래스들이 참조할수 있도록 private가 아닌 protected 사용
    protected String customerName;
    protected String customerGrade;
    int bonusPoint;
    double bonusRatio;

    public Customer() {
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
```

하위 클래스
```
package inheritance;

public class VIPCustomer extends Customer{ //상위 클래스(Customer) 지정

    double salesRatio;
    private int agentID;

    public VIPCustomer() {
        customerGrade = "VIP"; //만약 상위 클래스에서 private인 경우 사용할 수 없음
        bonusRatio = 0.05;
        salesRatio = 0.1;
    }
}
```

사용
```
package inheritance;

public class CustomerTest {
    public static void main(String[] args) {

        Customer Lee = new Customer();
        Customer Kim = new VIPCustomer();

        Lee.setCustomerID(10010);
        Lee.setCustomerName("이순신");
        Lee.bonusPoint = 1000;

        Kim.setCustomerID(10020);    //하위 클래스에서 상위 클래스의 기능 사용
        Kim.setCustomerName("김유신");
        Kim.bonusPoint = 10000;

        System.out.println(Lee.showCustomerInfo());
        System.out.println(Kim.showCustomerInfo());
    }
}
```
    
    