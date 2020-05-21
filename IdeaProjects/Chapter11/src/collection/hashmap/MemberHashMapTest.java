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
