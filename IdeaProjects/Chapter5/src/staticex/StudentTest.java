package staticex;

public class StudentTest {
    public static void main(String[] args) {

        Student studentLee = new Student("Lee");
        System.out.println(Student.getSerialNum());
        Student studentKim = new Student("Kim");

        System.out.println(Student.getSerialNum());

        System.out.println(studentLee.getStudentId());
        System.out.println(studentKim.getStudentId());

        System.out.println(Student.getSerialNum());//실제 static 변수 또는 메서드 사용은 이것 처럼 참조 변수가 아닌 클래스 이름에서 참조

   }
}
