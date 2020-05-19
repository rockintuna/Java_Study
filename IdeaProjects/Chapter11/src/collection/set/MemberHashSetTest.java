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
        manager.addMember(memberPark2);

        manager.showAllMember();

        manager.removeMember(100);
        manager.showAllMember();

    }
}
