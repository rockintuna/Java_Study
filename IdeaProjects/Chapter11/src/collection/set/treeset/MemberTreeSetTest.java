package collection.set.treeset;

public class MemberTreeSetTest {
    public static void main(String[] args) {

        MemberTreeSet manager = new MemberTreeSet();

        Member memberLee = new Member(100,"Lee");
        Member memberKim = new Member(101,"Kim");
        Member memberPark = new Member(102,"Park");

        manager.addMember(memberLee);
        manager.addMember(memberKim);
        manager.addMember(memberPark);

        manager.showAllMember();

        manager.removeMember(100);
        manager.showAllMember();

    }
}
