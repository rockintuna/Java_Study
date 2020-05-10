# 객체 지향 프로그래밍

#### 01. 객체 지향 프로그래밍과 클래스

* 객체 (Object)   
의사나 행위가 미치는 대상 (사전적)    
구체적, 추상적 데이터의 단위
예) 사람, 자동차, 주문, 생산, 관리...   

* 객체 지향 프로그래밍   
객체를 기반으로 하는 프로그래밍   
객체를 정의하고, 객체의 기능을 구현하며, 객체간의 협력을 구현     
<> 절차 지향 프로그래밍      

* 클래스   
객체를 코드로 구현한 것   
객체 지향 프로그래밍의 가장 기본적인 요소     
객체의 청사진     

* 멤버 변수     
객체가 가지는 속성을 변수로 표현  
클래스의 멤버 변수

* 메서드   
객체의 기능을 구현  

class 생성하기.
```
package classpart;

public class Student {  //객체를 코드로 표현 -> class
    //public class는 java file내에 하나만 존재하며 java file이름과 동일해야 한다.

    //객체의 속성을 변수로 표현 (멤버 변수, 속성)
    public int studentId; //public = 접근제어자
    public String studentName; //문자열을 사용하기 위해 java에서 제공되는 class (java.lang package)
    public String address;

    //기능은 메서드로 구현
    public void showStudentInfo() { //void:반환하는 값의 자료형을 나타냄(없음)
                                    //메서드 명 다음의 괄호 안에 매개변수가 있을수 도 있다.
        System.out.println(studentName+","+address);
    }
}

```

다른 class에서 Student class 사용하기.
```
package classpart;

public class StudentTest { //객체를 사용하는 건 다른 클래스인 경우가 대부분이다.

    public static void main(String[] args) {
        Student studentLee = new Student(); //생성자로 학생 class를 생성. 

        studentLee.studentName = "이순신";  //생성된 class의 멤버 변수를 참조할 수 있다. (참조 변수)
        studentLee.address = "서울";

        studentLee.showStudentInfo(); //생성된 class의 메서드를 참할 수 있다.
    }
}

```
    
    