package classex;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Person person = new Person("james");
        System.out.println(person);

        Class c1 = Class.forName("classex.Person"); //동적 로딩
        Person person1 = (Person)c1.newInstance(); //기본생성자 사용하여 인스턴스 생성, 리턴타입이 Object이므로 다운캐스팅
        System.out.println(person1);

        //기본생성자가 아닌 다른 생성자를 사용하여 인스턴스를 생성하려는 경우..
        Class[] parameterTypes = {String.class}; //String을 사용하는 생성자를 사용할 것
        Constructor cons = c1.getConstructor(parameterTypes); //생성자 매개변수 타입을 요소로 가지는 Class 타입 배열 필요

        System.out.println(cons.toString()); //선택된 생성자 출력

        Object[] initargs = {"김유신"}; //생성자의 매개변수로 사용될 값들을 요소로 가지는 배열(여러개일 수 있으므로 배열 사용)
        Person personKim = (Person) cons.newInstance(initargs); //매개변수 값을 요소로 가지는 Object 타입 배열 필요
        System.out.println(personKim);
    }
}
