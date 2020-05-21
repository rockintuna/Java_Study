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
