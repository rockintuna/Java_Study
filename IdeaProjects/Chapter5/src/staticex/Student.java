package staticex;

public class Student {

    private static int serialNum = 1000; //인스턴스 들이 공유함. 그렇기 때문에 외부에서 변경을 피하기 위하여 보통 private로 만
    private int studentId;
    public String studentName;
    public String address;

    public Student(String name) {
        studentName = name;
        serialNum++;   //객체 생성시 static 변수 증가하도록
        studentId = serialNum;    //증가된 serialNum을 학번으로
    }

    public int getStudentId() {
        return studentId;
    }

    public void showStudentInfo() {
        System.out.println(studentName+","+address);
    }

    public static int getSerialNum() {   //static 메서드
        int i = 0;   // 지역변수 사용가능
        //studentName = "Lee"; //인스턴스 변수 사용할 수 없음
        return serialNum;
    }

    public static void setSerialNum(int serialNum) {
        Student.serialNum = serialNum;
    }
}
