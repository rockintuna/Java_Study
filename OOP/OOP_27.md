# 객체 지향 프로그래밍

#### 27. 상속에서 클래스 생성 과정과 형변환

하위 클래스 생성시 항상 상위 클래스가 먼저 생성되어야 한다.     
```
package inheritance;

public class VIPCustomer extends Customer{ //상위 클래스(Customer) 지정

    double salesRatio;
    private int agentID;

    public VIPCustomer() {
        //super(); //아무런 상위 클래스 생성자 호출 명령이 없으면 pre-compile 단계에서 이 함수가 들어감
                   //super : 상위 클래스의 메모리 위치 함수, super() : 상위 클래스의 기본 생성자 호출
        //super(0, null); //만약 상위 클래스의 기본 생성자가 없으면, 명시적으로 상위 클래스의 생성자를 호출해야 한다.
        customerGrade = "VIP"; //만약 상위 클래스에서 private인 경우 사용할 수 없음
        bonusRatio = 0.05;
        salesRatio = 0.1;

        System.out.println("VIPCustomer() 생성자 호출");
    }
    /*public VIPCustomer(int customerID, String customerName) {
    //만약 상위 클래스의 기본 생성자가 없으면, 명시적으로 상위 클래스의 생성자를 호출해야 한다. 방법2
        super(customerID, customerName);
        customerGrade = "VIP";
        bonusRatio = 0.05;
        salesRatio = 0.1;
    }*/
}
```
    
* 상위 클래스로의 묵시적 형변환 (업캐스팅)   
상속관계에서 모든 하위 클래스는 상위 클래스로 묵시적 형변환이 된다.  
(하위 클래스는 상위 클래스의 타입을 내포하고 있다.)  
그 역은 성립하지 않는다.  
```
Customer Lee = new VIPCustomer();
//or
VIPCustomer Lee = new VIPCustomer();
```
둘다 VIPCustomer 인스턴스를 생성하는 코드이지만..   
위의 코드(업캐스팅 사용한)는 type이 Customer이므로,     
Customer의 변수/메서드만 접근할 수 있다.     
다만 VIPCustomer관련 멤버변수 메모리는 힙 영역에 생성되긴 한다.    
    
    