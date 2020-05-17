package classpart;

public class StudentTest { //객체를 사용하는 건 다른 클래스인 경우가 대부분이다.

    public static void main(String[] args) {


        Student studentLee = new Student("이순신"); //인스턴스 생성

        //참조 변수 사용 studentLee

        //studentLee.studentName = "이순신";  //생성된 인스턴스(객체)의 멤버 변수를 사용할 수 있다.
        studentLee.address = "서울";

        studentLee.showStudentInfo(); //생성된 인스턴스(객체)의 메서드를 사용할 수 있다.

        Student studentKim = new Student(101,"김유신"); //new 키워드를 통해 인스턴스 생성
                                            //생성 될 때 Student class의 멤버 변수 만큼 Heap 메모리 생성
        //studentKim.studentName="김유신";
        //studentKim.address="경주";

        studentKim.showStudentInfo();

        System.out.println(studentLee); //참조변수 출력 > classpart.Student@60f82f98 참조값 package.class@heap memory address
        System.out.println(studentKim);

    }
}
