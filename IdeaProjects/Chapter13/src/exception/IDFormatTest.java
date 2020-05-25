package exception;

public class IDFormatTest {

    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) throws IDFormatException {
        if (userID == null) {
            throw new IDFormatException("아이디는 null일 수 없습니다."); //throws는 미루기 throw는 발생
        } else if (userID.length() <8 || userID.length() > 20) {
            throw new IDFormatException("아이디는 8자 이상 20자 이하로 쓰세요.");
        } else {
            this.userID = userID;
        }
    }

    public static void main(String[] args) {
        IDFormatTest idTest = new IDFormatTest();

        String myID = "tuna";

        try {
            idTest.setUserID(myID);
        } catch (IDFormatException e) {
            System.out.println(e);
        }

        myID = null;

        try {
            idTest.setUserID(myID);
        } catch (IDFormatException e) {
            System.out.println(e);
        }

    }
}
