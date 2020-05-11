# 객체 지향 프로그래밍

#### 08. 참조 자료형

- 기본 자료형     
int, long, float, double, ...
- 참조 자료형     
String, Date, Student, ...  

참조 자료형 직접 생성하기
```
package reference;

public class Subject { //과목 class 생성

    String subjectName;
    int score;
    int subjectID;

}
```

```
package reference;

public class Student {

    int studentID;
    String studentName;

    Subject korea; //과목 type(참조 자료형)으로 변수 선언
    Subject math;

    public Student(int id, String name) {
        studentID = id;
        studentName = name;

        korea = new Subject(); //생성 단계가 필요하다
        math = new Subject();
    }

    public void setKoreaSubject(String name,int score) {
        korea.subjectName = name; //과목 class의 속성 참조
        korea.score = score;
    }

    public void setMathSubject(String name,int score) {
        math.subjectName = name;
        math.score = score;
    }

    public void showStudentScore() {
        int total = korea.score + math.score;
        System.out.println(studentName+"의 총점은 "+total+"점 입니다.");
    }
}
```
String과는 다르게    
```
korea = new Subject();
```
처럼 생성 단계가 필요한데, 보통 생성자에 넣어둔다.   

실행
```
package reference;

public class StudentTest {
    public static void main(String[] args) {

        Student studentLee = new Student(101,"Lee");

        studentLee.setKoreaSubject("국어",100);
        studentLee.setMathSubject("수학",95);

        Student studentKim = new Student(102,"Kim");

        studentKim.setKoreaSubject("국어",80);
        studentKim.setMathSubject("수학",99);

        studentLee.showStudentScore();
        studentKim.showStudentScore();
    }
}
```