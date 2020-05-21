# 객체 지향 프로그래밍

#### 55. Map 인터페이스  

* Map 인터페이스   
쌍으로 이루어진 객체 관리를 위해 선언된 인터페이스
검색을 위한 자료구조       
Map을 사용하는 객체는 key-value 쌍으로 되어있으며 Key는 중복될 수 없다.   
Key를 이용하여 값을 저장하거나 검색, 삭제할 때 사용하면 편리하다.     
내부적으로 hash 방식으로 구현되어 있다.    
```
index = hash(key) //index는 저장 위치    
```
key가 되는 객체는 객체의 유일성 여부를 알기 위해 equals(), hashCode() 메서드를 재정의한다.  

HashMap 클래스     
Map 인터페이스를 구현한 클래스 중에서 가장 일반적으로 쓰인다.    
pair 자료를 쉽고 빠르게 관리할 수 있다.   
HashTable 클래스는 Vector 처럼 동기화를 제공한다.     
```
package collection.hashmap;

import java.util.HashMap;
import java.util.Iterator;

public class MemberHashMap {

    private HashMap<Integer,Member> hashMap;

    public MemberHashMap() {
        hashMap = new HashMap<Integer, Member>();
    }

    public void addMember(Member member) {
        hashMap.put(member.getMemberId(),member); //add가 아닌 put임에 유의, key-value pair가 들어간다.
    }

    public boolean removeMember(int memberId) { //key 값을 통해 제거
        if (hashMap.containsKey(memberId)) { //containsKey : 해당 key값의 요소가 있는지 체크
            hashMap.remove(memberId); //key값으로 제거
            return true;
        } else {
            System.out.println("회원 번호가 없습니다.");
            return false;
        }
    }

    public void showAllMember() {
        //hashMap.keySet().iterator(); //keySey() : 모든 key 객체를 set으로 반환
        //hashMap.values().iterator(); //values() : 모든 value 객체를 Collection으로 반환

        Iterator<Integer> ir = hashMap.keySet().iterator();
        while (ir.hasNext()) {
            int key = ir.next();
            Member member = hashMap.get(key); //get(key) 해당 key의 value 반환
            System.out.println(member);
        }
        System.out.println();
    }
}
```

```
package collection.hashmap;

public class MemberHashMapTest {
    public static void main(String[] args) {

        MemberHashMap manager = new MemberHashMap();

        Member memberLee = new Member(100,"Lee");
        Member memberKim = new Member(101,"Kim");
        Member memberPark = new Member(102,"Park");
        Member memberPark2 = new Member(102,"Park");

        manager.addMember(memberLee);
        manager.addMember(memberKim);
        manager.addMember(memberPark);
        manager.addMember(memberPark2); //key type인 Integer에 이미 equals()와 hashCode()가 재정의 되어있다.
                                        //그렇기 때문에 따로 재정의해주지 않아도 안들어간다.

        manager.showAllMember();

        manager.removeMember(101);
        manager.showAllMember();

    }
}
```
TreeMap 클래스     
key 객체를 정렬하여 key-value를 pair로 관리하는 클래스   
정렬을 위하여 key에 사용되는 클래스에 Comparable 또는 Comparator 인터페이스를 구현   

```
package collection.treemap;

import java.util.Iterator;
import java.util.TreeMap;

public class MemberTreeMap {

    TreeMap<Integer,Member> treeMap;

    public MemberTreeMap() {
        treeMap = new TreeMap<Integer, Member>();
    }

    public void addMember(Member member) {
        treeMap.put(member.getMemberId(),member);
    }

    public boolean removeMember(int memberId) {
        if (treeMap.containsKey(memberId)) {
            treeMap.remove(memberId);
            return true;
        } else {
            System.out.println("회원 번호가 없습니다.");
            return false;
        }
    }

    public void showAllMember() {
        Iterator<Integer> ir = treeMap.keySet().iterator();
        while (ir.hasNext()) {
            int key = ir.next();
            Member member = treeMap.get(key);
            System.out.println(member);
        }
        System.out.println();
    }
}
```

```
package collection.treemap;

public class MemberTreeMapTest {
    public static void main(String[] args) {

        MemberTreeMap manager = new MemberTreeMap();

        Member memberLee = new Member(200,"Lee");
        Member memberKim = new Member(400,"Kim");
        Member memberPark = new Member(100,"Park");

        manager.addMember(memberLee);
        manager.addMember(memberKim);
        manager.addMember(memberPark);

        manager.showAllMember(); //Integer 클래스에서 Comparable가 구현되어 있으므로
                                 //따로 구현하지 않더라도 정렬되어있다.

        manager.removeMember(200);
        manager.showAllMember();
    }
}
```