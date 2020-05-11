# 객체 지향 프로그래밍

#### 07. 생성자, 생성자 오버로딩  

* 생성자   
객체를 생성할 때 new 키워드와 함께 호출 (객체 생성때만 호출)   
인스턴스를 초기화 하는 코드가 구현 됨 (주로 멤버 변수 초기화)    
반환 값이 없음, 상속되지 않음   
생성자는 클래스 이름과 동일     

기본 생성자 (디폴트 생성자)
```
    public Student() {};
```
클래스에 생성자를 구현하지 않은 경우 프리 컴파일 단계에서 컴파일러가 넣어줌.     
매개 변수가 없고 구현부가 없다.   
만약 클래스에 다른 생성자가 있는 경우 기본 생성자는 제공되지 않는다.     

생성자 구현
```
    public Student(int id, String name) {
        //body = 구현부
        studentId = id;
        studentName = name;
    }
```
위의 생성자는 인스턴스 생성과 동시에 초기화

사용할 때는 아래와 같이 매개 변수 입력  
```
package classpart;

public class StudentTest { //객체를 사용하는 건 다른 클래스인 경우가 대부분이다.

    public static void main(String[] args) {


        Student studentLee = new Student("이순신");
        studentLee.address = "서울";

        studentLee.showStudentInfo(); 

        Student studentKim = new Student(101,"김유신"); 

        studentKim.showStudentInfo();

    }
}
```

* 오버로딩  
이름이 같지만 다른 매개 변수를 가지는 생성자 또는 메서드를 사용할 수 있다.     
사용자는 여러 생성자 중 선택하여 사용할 수 있음     
private 변수도 생성자를 이용하여 초기화 할 수 있음    

```
    public Student(String name) {
        studentName = name;
    }

    public Student(int id, String name) {
        studentId = id;
        studentName = name;
        address = "주소 없음";

```

* private
```
private int name;
```
위의 멤버 변수는 클래스 외부에서 참조할 수 없다.    
생성자를 구현하여 초기화 할 수는 있다.  
    
    