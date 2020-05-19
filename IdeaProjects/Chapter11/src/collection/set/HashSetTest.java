package collection.set;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetTest {
    public static void main(String[] args) {

        HashSet<String> set = new HashSet<String>();

        set.add("이순신");
        set.add("김유신");
        set.add("강감찬");
        set.add("이순신");

        System.out.println(set); //순서가 없음, 중복도 없음

        Iterator<String> ir = set.iterator(); //모든 Collection 객체에서 사용가능

        while(ir.hasNext()) {
            String str = ir.next(); //순회 선언시 String으로 선언하였기 때문에 반환값은 String이 된다.
            System.out.println(str);
        }

    }
}
