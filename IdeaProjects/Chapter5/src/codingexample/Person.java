package codingexample;

public class Person {

    int age;
    String name;
    boolean isMarried;
    int numberOfChildren;

    void showPersonInfo() {
        System.out.println("나이 :"+age);
        System.out.println("이름 :"+name);
        System.out.println("결혼여부 :"+isMarried);
        System.out.println("자녀 수 :"+numberOfChildren);
    }
}
