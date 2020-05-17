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
