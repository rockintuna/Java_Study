# 객체 지향 프로그래밍

#### 28. 메서드 오버라이딩  

* 오버라이딩     
상위 클래스에 정의된 메서드의 구현 내용이 하위 클래스에서 구현할 내용과 맞지 않는 경우 하위 클래스에서 동일한 이름의 메서드를 재정의할 수 있다.  

상위 클래스 Customer
```
    public int calcPrice(int price) {
        bonusPoint += price*bonusRatio;
        return price;
    }
```

하위 클래스 VIPCustomer (VIP 할인 추가를 위한 메서드 재정의)
```
    @Override //애노테이션.. 컴파일러에게 특정한 정보를 제공해주는 역할     
              //@Override는 오버라이딩 정보 제공  
    public int calcPrice(int price) { //오버라이딩 소스 메서드와 선언부가 동일해야 한다
        bonusPoint += price*bonusRatio;
        return price - (int)(price*salesRatio);
    }
```
   
만약 객체 상속의 묵시적 형변환(업캐스팅)이 있는 경우에는..
```
Customer Lee = new VIPCustomer();
Lee.calcPrice(1000);
```
위의 calcPrice 메서드는 인스턴스의 메서드를 사용한다.  
즉, VIPCustomer 클래스에서 재정의된 calcPrice 메서드를 사용한다.(가상함수)  
    
```
package inheritance;

public class OverridingTest {
    public static void main(String[] args) {

        VIPCustomer Lee = new VIPCustomer();
        Lee.setCustomerID(10010);
        Lee.setCustomerName("이순신");
        Lee.bonusPoint = 20000;

        Customer Kim = new VIPCustomer(); //업캐스팅 
        Kim.setCustomerID(10020);
        Kim.setCustomerName("김유신");
        Kim.bonusPoint = 10000;

        System.out.println(Lee.showCustomerInfo());
        System.out.println(Kim.showCustomerInfo());
        System.out.println(Lee.calcPrice(1000)); //1000-1000*0.1 = 900
        System.out.println(Kim.calcPrice(1000)); //1000-1000*0.1 = 900 가상 메서드 사용
        //동일하게 인스턴스의 메서드를 사용한다.
    }
}
```
    
    