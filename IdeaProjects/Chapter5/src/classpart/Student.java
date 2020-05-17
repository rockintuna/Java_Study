package classpart;

public class Student {  //객체를 코드로 표현 -> class
    //public class는 java file내에 하나만 존재하며 java file이름과 동일해야 한다.

    //객체의 속성을 변수로 표현 (멤버 변수, 속성)
    public int studentId; //public = 접근제어자
    public String studentName; //문자열을 사용하기 위해 java에서 제공되는 class (java.lang package)
    public String address;

    public Student(String name) {
        studentName = name;
    }

    public Student(int id, String name) {
        studentId = id;
        studentName = name;
        address = "주소 없음";
    }
    //기능은 메서드로 구현
    public void showStudentInfo() { //void:반환하는 값의 자료형을 나타냄(없음)
                                    //메서드 명 다음의 괄호 안에 매개변수가 있을수 도 있다.
        System.out.println(studentName+","+address);
    }
}
