package collection.set.treeset;

import java.util.Comparator;

public class Member implements Comparator<Member> {

    private int memberId;
    private String memberName;

    public Member() {}
    public Member(int memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "memberId=" + memberId +
                ", memberName=" + memberName;
    }

    @Override
    public int compare(Member o1, Member o2) { //o1이 this, o2가 매개변
        return (o1.memberId-o2.memberId);
    }

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

}
