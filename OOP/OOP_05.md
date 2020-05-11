# 객체 지향 프로그래밍

#### 05. 인스턴스, 힙메모리

* 인스턴스  
클래스로부터 생성된 객체   
힙 메모리에 멤버 변수의 크기에 따라 메모리가 생성    
new 키워드를 이용하여 여러 개의 인스턴스를 생성    
가비지 콜렉터 스레드에 의해 반환됨(프로그래머가 직접 free하지 않아도 된다.)   
참조 변수 : 생성된 인스턴스를 가리키는 변수
참조 값 : 생성된 인스턴스의 메모리 주소 값

멤버변수 사용 : 참조변수.멤버변수     
메서드 사용 : 참조변수.메서드();    

```
package classpart;

public class StudentTest { //객체를 사용하는 건 다른 클래스인 경우가 대부분이다.

    public static void main(String[] args) {
        Student studentLee = new Student(); //인스턴스 생성

        //참조 변수 사용 studentLee
        studentLee.studentName = "이순신";  //생성된 인스턴스(객체)의 멤버 변수를 사용할 수 있다.
        studentLee.address = "서울";

        studentLee.showStudentInfo(); //생성된 인스턴스(객체)의 메서드를 사용할 수 있다.

        Student studentKim = new Student(); //new 키워드를 통해 인스턴스 생성
                                            //생성 될 때 Student class의 멤버 변수 만큼 Heap 메모리 생성
        studentKim.studentName="김유신";
        studentKim.address="경주";

        studentKim.showStudentInfo();

        System.out.println(studentLee); //참조 변수 출력>참조 값 classpart.Student@60f82f98 package.class@heap memory address
        System.out.println(studentKim);

    }
}
```
    
    