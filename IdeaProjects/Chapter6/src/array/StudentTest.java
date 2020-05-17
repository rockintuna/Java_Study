package array;

public class StudentTest {
    public static void main(String[] args) {

        Student Lee = new Student(101,"Lee");
        Student Kim = new Student(102,"Kim");

        Lee.addSubject("국어",100);
        Lee.addSubject("수학",90);
        Kim.addSubject("국어",100);
        Kim.addSubject("수학",90);
        Kim.addSubject("영어",80);

        Lee.showStudentInfo();
        Kim.showStudentInfo();
    }
}
