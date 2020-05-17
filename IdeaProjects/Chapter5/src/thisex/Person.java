package thisex;

public class Person {

    String name;
    int age;

    public Person() {
        this("이름 없음",10);
    }

    public Person(String name, int age) {

        this.name = name;
        this.age = age;

    }

    public void showInfo() {
        System.out.println(name+"은 "+age+"살 입니다.");
    }

    public Person getSelf() {
        return this;
    }
}
