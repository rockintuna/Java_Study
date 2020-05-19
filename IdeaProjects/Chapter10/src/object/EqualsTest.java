package object;

class Student {
    int studentNum;
    String studentName;

    public Student(int studentNum, String studentName) {
        this.studentNum = studentNum;
        this.studentName = studentName;
    }

    public boolean equals(Object object) {
        if (object instanceof Student) {
            Student student = (Student)object;
            if (this.studentNum==student.studentNum) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return studentNum; //equals()에서 사용한 멤버변수를 활용
    }
}

public class EqualsTest {
    public static void main(String[] args) {

        Student Lee = new Student(100,"이순신");
        Student Lee2 = Lee;
        Student Shin = new Student(100,"이순신");

        System.out.println(Lee==Lee2); //true
        System.out.println(Lee==Shin); //false
        System.out.println(Lee.equals(Shin)); //true

        System.out.println(Lee.hashCode());  //100
        System.out.println(Shin.hashCode()); //100

        Integer i1 = new Integer(100);
        Integer i2 = new Integer(100);

        System.out.println(i1.equals(i2)); //true, Integer에서 equals()와 hashCode()가 재정의 되어있음
        System.out.println(i1.hashCode());
        System.out.println(i2.hashCode());

        System.out.println(System.identityHashCode(i1)); //실제 메모리 주소 값을 확인하려면
        System.out.println(System.identityHashCode(i2));
    }
}
