package collection.set.treeset;

import java.util.Comparator;
import java.util.TreeSet;

class MyCompare implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2)*(-1); //문자열 역순정렬
    }
}
public class TreeSetTest {
    public static void main(String[] args) {

        TreeSet<String> treeSet = new TreeSet<String>(new MyCompare()); //String 클래스에서 구현된 정렬 방식을 무시하고
                                                                        //내가 구현한 정렬 방식을 사용할 수 있다.

        treeSet.add("홍길동");
        treeSet.add("강감찬");
        treeSet.add("이순신");

        for (String str : treeSet) {
            System.out.println(str);
        }
    }
}
