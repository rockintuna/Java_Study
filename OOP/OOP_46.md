# 객체 지향 프로그래밍

#### 46. Class 클래스 

* Class 클래스     
자바의 모든 클래스와 인터페이스는 컴파일 후 class 파일로 생성됨  
class 파일에는 객체의 정보(멤버변수, 메서드, 생성자 등)가 포함되어 있음    
Class 클래스는 컴파일된 class 파일에서 객체의 정보를 가져올 수 있음     
동적 로딩에 많이 사용    

Class 클래스 가져오기  
```
String s = new String();
Class c = s.getClass();
```
```
Class c = String.Class;
```
```
Class c = Class.forName("java.lang.String"); //동적 로딩 : 컴파일 타임이 아닌 런타임에서 로딩
```

* reflection 프로그래밍  
Class 클래스로부터 객체의 정보를 가져와 프로그래밍하는 방식     
로컬에 객체가 없고 자료형을 알 수 없는 경우 유용한 프로그래밍     
java.lang.reflect 패키지에 있는 클래스 활용    

String 클래스 정보 확인
```
package classex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class StringClassTest {
    public static void main(String[] args) throws ClassNotFoundException {

        Class c1 = String.class;

        String str = new String();
        Class c2 = str.getClass();

        Class c3 = Class.forName("java.lang.String");

        Constructor[] cons = c3.getConstructors();
        for (Constructor con : cons) { //String 클래스의 모든 생성자 출력
            System.out.println(con);
        } //보통 로컬에 이 클래스가 없을 때 사용
        System.out.println();

        Method[] methods = c3.getMethods();
        for (Method method : methods) { //String 클래스의 모든 메서드 선언부 출력
            System.out.println(method);
        }

    }
}
```

Class 클래스를 이용한 사용자 클래스 인스턴스 생성
```
package classex;

public class Person {
    private String name;
    private int age;

    public Person() {};

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return name;
    }
}
```

```
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
```

* forName() 메서드와 동적 로딩  
Class 클래스 static 메서드    
동적 로딩 : 컴파일시 데이터 타입이 모두 biding되어 자료형이 로딩되는 것이 아닌,  
실행 중에 데이터 타입을 알고 biding 되는 방식   
실행 시에 로딩되므로 경우에 따라 다른 클래스가 사용될 수 있어 유용함     
컴파일 타임에 체크할 수 없으므로 해당 문자열에 대한 클래스가 없는 경우 예외 발생  
    
    