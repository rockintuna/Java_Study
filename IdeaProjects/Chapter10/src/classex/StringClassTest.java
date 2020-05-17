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
