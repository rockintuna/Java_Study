package coffee;

public class CoffeeTest {

    public static void main(String[] args) {

        Person Kim = new Person("김졸려",40000);
        Person Lee = new Person("이피곤",20000);

        BeanCoffee beanCoffee = new BeanCoffee();
        StarCoffee starCoffee = new StarCoffee();

        Kim.buyStarCoffee(starCoffee,Menu.STARLATTE);
        Lee.buyBeanCoffee(beanCoffee,Menu.BEANLATTE);

    }
}
