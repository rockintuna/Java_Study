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
