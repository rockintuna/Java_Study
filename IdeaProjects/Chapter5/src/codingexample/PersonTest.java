package codingexample;

public class PersonTest {
    public static void main(String[] args) {

        Person person = new Person();

        person.age=40;
        person.name="James";
        person.isMarried=false;
        person.numberOfChildren=3;

        person.showPersonInfo();
    }
}
