# 객체 지향 프로그래밍

#### 14. static 변수, 메서드     

여러 인스턴스가 같은 변수 및 메서드를 공유해야할 필요가 있을때   

* static 변수
```
    private static int serialNum = 1000;
```
처음 프로그램이 로드될 때 데이터 영역에 생성됨     
클래스 이름으로 참조     
```
Student.serialNum = 100;
```

* static 메서드    
static 변수를 위한 기능을 제공하는 메서드  
인스턴스 변수를 사용할 수 없음   

```
    public static int getSerialNum() {   //static 메서드
        int i = 0;   // 지역변수 사용가능
        //studentName = "Lee"; //인스턴스 변수 사용할 수 없음
        return serialNum;
    }
```
클래스 이름으로 참조
```
        System.out.println(Student.getSerialNum());
```

```
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

```

```
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
```
    
    