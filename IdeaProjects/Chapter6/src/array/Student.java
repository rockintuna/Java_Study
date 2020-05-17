package array;

import java.util.ArrayList;

public class Student {

    int studentID;
    String studentName;
    ArrayList<Subject> subjectlist; //ArrayList 변수 선언

    public Student(int studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;

        subjectlist = new ArrayList<Subject>(); //참조형 변수이므로 초기화를 생성자에 넣어주자

    }

    public void addSubject(String name, int score) {
        Subject subject = new Subject(name,score); //배열이 비어있으므로 subject 객체 생성
        subjectlist.add(subject);
    }

    public void showStudentInfo() {

        int total=0;

        for ( Subject subject : subjectlist ) {
            total += subject.getScore();
            System.out.println(studentName+"의 "+subject.getName()+"과목 성적은 "+subject.getScore()+"점 입니다.");
        }
        System.out.println("총점은 "+total+"점 입니다.");
    }
}
