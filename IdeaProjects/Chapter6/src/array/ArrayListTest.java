package array;

import java.util.ArrayList;

public class ArrayListTest {
    public static void main(String[] args) {

        //ArrayList list = new ArrayList(); //요소 자료형 미지정
        ArrayList<String> list = new ArrayList<String>(); //요소 자료형 지정

        //몇가지 메서드 가능 테스트
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        for (String str : list) { //향상된 for문 사용 가능
            System.out.println(str);
        }

        for (int i=0;i<list.size();i++) { //list의 요소 총 개수는 size
            System.out.println(list.get(i)); //get 메서드를 통해 요소 참조
        }



    }
}
