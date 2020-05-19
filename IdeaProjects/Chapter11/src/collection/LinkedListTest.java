package collection;

import java.util.LinkedList;

public class LinkedListTest {
    public static void main(String[] args) {

        LinkedList<String> myList = new LinkedList<String>();

        myList.add("A");
        myList.add("B");
        myList.add("C");

        System.out.println(myList);
        myList.add(1,"D");

        System.out.println(myList);

        for(int i=0;i<myList.size();i++) {
            String s = myList.get(i);  //set 인터페이스에는 get메서드가 없다.
            System.out.println(s);
        }
    }
}
