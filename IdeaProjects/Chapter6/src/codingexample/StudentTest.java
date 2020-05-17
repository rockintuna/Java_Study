package codingexample;

public class StudentTest {
    public static void main(String[] args) {

        Student Lee = new Student("Lee");
        Lee.addBook("태백산맥1");
        Lee.addBook("태백산맥2");

        Student Kim = new Student("Kim");
        Kim.addBook("토지1");
        Kim.addBook("토지2");
        Kim.addBook("토지3");

        Student Cho = new Student("Cho");
        Cho.addBook("해리포터1");
        Cho.addBook("해리포터2");
        Cho.addBook("해리포터3");
        Cho.addBook("해리포터4");
        Cho.addBook("해리포터5");
        Cho.addBook("해리포터6");

        Lee.showStudentInfo();
        Kim.showStudentInfo();
        Cho.showStudentInfo();

    }
}
