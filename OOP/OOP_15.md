# 객체 지향 프로그래밍

#### 15. static 응용

* singleton pattern     
단 하나만 존재하는 인스턴스 (ex 학교 객체)     
생성자는 private로 생성    
클래스 내에서 static으로 유일한 객체 생성  
외부에서 유일한 객체를 참조할 수 있는 public static get() 메서드 구현    

```
package staticex;

public class Company {

    private static Company instance = new Company(); //내부적으로 인스턴스 생성

    private Company() { //생성자 private로 생성
    }

    public static Company getInstance() {  //외부에서 인스턴스 생성과 무관하게 사용하기 위해 static으로 생성
        if (instance==null) {
            instance = new Company();
        }
        return instance;
    }

}
```

```
package staticex;

import java.util.Calendar;

public class CompanyTest {
    public static void main(String[] args) {

        //Company company = new Company(); //private 생성자 이므로 인스턴스 생성할 수 없음
        Company company1=Company.getInstance();
        Company company2=Company.getInstance();

        System.out.println(company1);
        System.out.println(company2); //두 인스턴스의 메모리 주소가 동일하다

        Calendar calendar = Calendar.getInstance(); //Calendar 클래스는 싱글톤 패턴으로 구현되어 있다.
    }
}
```
    
    