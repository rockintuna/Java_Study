# 객체 지향 프로그래밍

#### 50. 컬렉션 프레임워크  

* 컬렉션 프레임워크란?   
프로그램 구현에 필요한 자료구조와 알고리즘을 구현해 놓은 라이브러리   
java.util 패키지에 구현되어 있음  
개발에 소요되는 시간을 절약하고 최적화된 라이브러리를 사용할 수 있음  
Collection 인터페이스와 Map 인터페이스로 구성됨    

Collection : 하나의 객체 관리를 위해 선언된 인터페이스    
+--List : 순서가 있는 자료관리, 중복 허용          
+--Set : 순서가 정해져있지 않음, 중복을 허용하지 않음  

Map : 쌍으로 이루어진 객체 관리를 위해 선언된 인터페이스  
Map을 사용하는 객체는 key-value 쌍으로 되어있으며 이중 Key는 중복될 수 없다. 

* List 인터페이스    
Collection의 하위 인터페이스    
객체를 순서에따라 저장하고 관리하는데 필요한 메서드가 선언된 인터페이스     
배열의 기능을 구현하기 위한 메서드가 선언됨    
ArrayList, Vector, LinkedList

ArrayList와 Vector   
객체 배열 클래스    
일반적으로 ArrayList를 더 많이 사용    
Vector는 멀티 쓰레드 프로그램에서 동기화를 제공   
capacity와 size는 다른 의미   

ArrayList와 LinkedList   
둘다 자료의 순차적 구조를 구현한 클래스  
ArrayList는 물리적 순서와 논리적 순서가 동일하다. (검색용)  
LinkedList는 물리적으로는 순차적이지 않을 수 있다. (변경용)     

```
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
```

* Stack     
LIFO    
이미 구현된 클래스가 제공되고 있음     
ArrayList, LinkedList로 구현할 수 있음     
관련 용어 : pop, push, top    

* Queue
FIFO    
ArrayList, LinkedList로 구현할 수 있음     
관련용어 : front, rear, enqueue, dequeue    

```
package collection;


import java.util.ArrayList;

class MyStack {
    private ArrayList<String> arrayStack = new ArrayList<String>();

    public void push(String data) {
        arrayStack.add(data);
    }

    public String pop() {
        int len = arrayStack.size();
        if (len == 0 ) {
            System.out.println("Stack이 비어있습니다.");
            return null;
        } else {
            return arrayStack.remove(len-1);
        }
    }
}

public class StackTest {

    public static void main(String[] args) {

        MyStack myStack = new MyStack();
        myStack.push("A");
        myStack.push("B");
        myStack.push("C");

        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());

    }
}
```

* Set 인터페이스     
Collection 하위의 인터페이스    
중복을 허용하지 않는다.   
순서가 없다.     
get(i) 메서드를 제공하지 않는다.(대신 Iterator 순회)   
아이디, 주민번호, 사번 등 유일한 값이나 객체를 관리할 때 사용    
HashSet, TreeSet

Iterator로 순회하기  
Collection의 개체를 순회하는 인터페이스  

iterator() 메서드 호출
```
Iterator ir = new memberArrayList.iterator();
```
Iterator에 선언된 메서드   
hashNext() : 이후에 요소가 더 있는지 체크   
next : 다음 요소 반환     

```
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
```

HashSet
```
package collection.set;

import java.util.HashSet;
import java.util.Iterator;

public class MemberHashSet {

    private HashSet<Member> hashSet;

    public MemberHashSet() {
        hashSet = new HashSet<Member>();
    }

    public void addMember(Member member) {
        hashSet.add(member);
    }

    public boolean removeMember(int memberId) {

        Iterator<Member> ir = hashSet.iterator();
        while(ir.hasNext()) {
            Member member = ir.next();
            if (member.getMemberId() == memberId) {
                hashSet.remove(member);
                return true;
            }
        }
        System.out.println(memberId+"번호가 존재하지 않습니다.");
        return false;
    }

    public void showAllMember() {
        for(Member member : hashSet) {
            System.out.println(member);
        }
        System.out.println();
    }
}
```

```
package collection.set;

public class MemberHashSetTest {
    public static void main(String[] args) {

        MemberHashSet manager = new MemberHashSet();

        Member memberLee = new Member(100,"Lee");
        Member memberKim = new Member(101,"Kim");
        Member memberPark = new Member(102,"Park");
        Member memberPark2 = new Member(102,"Park");

        manager.addMember(memberLee);
        manager.addMember(memberKim);
        manager.addMember(memberPark);
        manager.addMember(memberPark2); //중복된 데이터이기 때문에 저장되지 않는데,
                                        //이는 Member 클래스에서 equals(), hashCode()를 
                                        //ID가 같은경우 논리적으로 동일하도록 재정의했기 때문이다.

        manager.showAllMember();

        manager.removeMember(100);
        manager.showAllMember();

    }
}
```

Member 클래스 중
```
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member) {
            Member member = (Member)obj;
            if (memberId == member.memberId) {
                return true;
            } return false;
        } return false;
    }

    @Override
    public int hashCode() {
        return memberId;
    }
```

TreeSet
객체의 정렬에 사용되는 클래스    
```
package collection.set.treeset;

import java.util.TreeSet;

public class TreeSetTest {
    public static void main(String[] args) {

        TreeSet<String> treeSet = new TreeSet<String>();

        treeSet.add("홍길동");
        treeSet.add("강감찬");
        treeSet.add("이순신");

        for (String str : treeSet) {
            System.out.println(str); //정렬이 되어있음, String 클래스에 Comparable 인터페이스가 구현되어 있음
        }
    }
}
```
중복을 허용하지 않으면서 오름차순 또는 내림차순으로 객체를 정렬     
내부적으로 이진 검색 트리로 구현되어 있음     
트리에 자료가 저장될 때 자료를 비교하게 되는데 
이때의 객체비교를 위하여 Comparable이나 Comparator 인터페이스를 구현해야 한다.

Member 클래스 중
숫자로 비교하는 경우..
```
public class Member implements Comparable<Member>{
...
    @Override
    public int compareTo(Member member) { //this와 매개변수를 비교
        return (this.memberId - member.memberId); //양수를 반환하게 되면 오름차순 정렬
    }
...
}
```

문자열로 비교하는 경우..
```
    @Override
    public int compareTo(Member member) { //this와 매개변수를 비교
        return this.memberName.compareTo(member.getMemberName()); //String은 compareTo가 이미 구현되어 있음   
    }
```

Comparator 인터페이스를 사용하는 경우...
```
    @Override
    public int compare(Member o1, Member o2) { //o1이 this, o2가 매개변
        return (o1.memberId-o2.memberId);
    }
```
```
    //TreeSet 생성자에 Comparator가 구현되어 있는 객체를 매개변수로 전달해야 한다.   
    public MemberTreeSet() {
        treeSet = new TreeSet<Member>(new Member());
    }
```

이미 Comparable이 구현되어있는 경우 Comparator를 통해 다른 정렬방식을 정의할 수 있다.  

```
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
```
    
    
