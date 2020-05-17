package coffee;

public class Person {

    String name;
    int money;

    public Person(String name,int money) {
        this.name = name;
        this.money = money;
    }

    public void buyBeanCoffee(BeanCoffee beanCoffee,int money) {
        beanCoffee.buy(money);
        String comment=beanCoffee.comment(money);
        if (comment != null) {
            this.money -= money;
            System.out.println(name+"님이 "+money+"원으로"+comment);
        }
        System.out.println(name+"님의 잔금은 "+this.money+"입니다.");
    }

    public void buyStarCoffee(StarCoffee starCoffee,int money) {
        starCoffee.buy(money);
        String comment=starCoffee.comment(money);
        if (comment != null) {
            this.money -= money;
            System.out.println(name+"님이 "+money+"원으로"+comment);
        }
        System.out.println(name+"님의 잔금은 "+this.money+"입니다.");
    }

}
