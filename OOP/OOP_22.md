# 객체 지향 프로그래밍

#### 22. ArrayList 사용하기

* ArrayList     
자바에서 제공하는 객체 배열이 구현된 클래스    
객체 배열을 사용하는데 필요한 여러 메서드들이 구현되어 있음   

```
package array;

import java.util.ArrayList;

public class ArrayListTest {
    public static void main(String[] args) {

        //ArrayList list = new ArrayList(); //요소 자료형 미지정
        ArrayList<String> list = new ArrayList<String>(); //요소 자료형 지정

        //몇가지 메서드 가능 테스트
        list.add("aaa"); //add 메서드를 통해 요소 추가
        list.add("bbb");
        list.add("ccc");

        for (String str : list) { //향상된 for문 사용 가능
            System.out.println(str);
        }

        for (int i=0;i<list.size();i++) { //list의 요소 총 개수는 size
            System.out.println(list.get(i)); //get 메서드를 통해 요소 참조
        }



    }
}
```

연습 (학생의 과목 별 점수 확인)     
```
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
```

```
package array;

public class Subject {

    private String name;
    private int score;

    public Subject(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
```

```
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
```