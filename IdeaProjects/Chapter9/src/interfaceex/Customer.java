package interfaceex;

public class Customer implements Buy,Sell{
    @Override
    public void buy() {
        System.out.println("customer buy");
    }

    @Override
    public void sell() {
        System.out.println("customer sell");
    }

    @Override
    public void order() { //디폴트 메서드는 원래 재정의하지 않아도 되지만,
                          // 동일한 이름의 디폴트 메서드가 중복된다면 재정의 해야한다.
        System.out.println("customer order");
    }

    public void sayHello() {
        System.out.println("hello");
    }

}
