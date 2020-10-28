# 객체 지향 프로그래밍

#### 43. Object 클래스     

* Object 클래스    
모든 클래스의 최상위 클래스 (java.lang.Object)  
(java.lang 패키지 위치 : /Library/Java/JavaVirtualMachines/jdk-11.0.7.jdk/Contents/Home/lib/src.zip
, java.lang 패키지는 컴파일러가 자동으로 import 해준다.)    
모든 클래스는 Object 클래스에서 상속 받음  
모든 클래스는 Object 클래스의 메서드를 사용할 수 있음   
모든 클래스는 Object 클래스의 일부 메서드를 재정의하여 사용할 수 있음  
(final로 정의된 메서드들은 재정의 할 수 없음)   

```
package object;

class Book{
    String title;
    String author;

    public Book(String title,String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' + ", author='" + author + '\'';
    }
}

public class ToStringTest {
    public static void main(String[] args) {
        Book book = new Book("토지","박경리");

        System.out.println(book); //Object의 toString 메서드는 메모리 주소를 리턴한다

        String str = new String("토지"); //String 클래스는 Object로부터 toString 메서드가 재정의되어 있음
        System.out.println(str); //실제로는 str.toString()

    }
}
```

* toString() 메서드    
원형 : 
```
getClass().getName()+'@'+Integer.toHexString(hashCode());
```
객체의 정보를 String으로 바꾸어 사용할 때 유용함  
자바 클래스 중에는 이미 정의된 클래스가 많다   
(String,Integer,Calendar,etc...)    
그 외에 많은 클래서에서 재정의하여 사용한다    

* equals() 메서드  
두 객체의 동일함을 논리적으로 재정의 할 수 있음     
물리적 동일함 : 같은 메모리 주소     
논리적 동일함 : 같은 학번의 학생, 같은 주문 번호의 주문  
논리적으로 동일함을 구현하기 위해 사용   

```
Student studentLee = new Student(100,"이상원");
Student studentSang = new Student(100,"이상원");
```

studentLee와 studentSang은 물리적으로 동일한 객체가 아니지만 논리적으로는 동일한 객체라고 할 수 있다.     
논리적 동일함을 구현하기 위하여...    
```
public class EqualsTest {
    public static void main(String[] args) {

        String str1 = new String("abc");
        String str2 = new String("abc");

        System.out.println(str1==str2); //false, 메모리 주소가 같은지 확인
        System.out.println(str1.equals(str2)); //String에 재정의된 equals()는 두 문자열이 같은지 확인

    }
}
```

* hashCode() 메서드    
반환 값 : 인스턴스가 저장된 가상머신의 주소를 10진수로 반환     
두 개의 서로 다른 메모리에 위치한 인스턴스가 동일하다는 것은?     
논리적으로 동일 : equals()의 반환값이 true  
&&
동일한 hashCode 값을 가짐 : hashCode()의 반환 값이 동일   
    
```
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
```

* clone() 메서드   
객체의 복사본을 만듦     
기본 틀으로 부터 같은 속성 값을 가진 객체의 복사본을 생성할 수 있음     

객체지향 프로그래밍의 정보은닉에 위배될 가능성이 있으므로 복제할 객체는 cloneable 인터페이스를 명시해야 함     

```
package object;

class Book implements Cloneable{ //마크인터페이스 Cloneable : 이 클래스는 복제 가능하다는 것을 명시
    String title;
    String author;

    public Book(String title,String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' + ", author='" + author + '\'';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

public class ToStringTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Book book = new Book("토지","박경리");
        System.out.println(book);
        Book book2 = (Book)book.clone(); //Object로 반환되기 때문에 다운캐스팅
        System.out.println(book2);
    }
}
```

* finalize() 메서드    
직접 호출하는 메서드는 아니다.   
인스턴스가 힙메모리에서 해제될 때 가비지 콜렉터에서 호출되는 메서드     
주로 리소스 해제 또는 소켓 닫기 등에 사용    
    
    