package codingexample;

import java.util.ArrayList;

public class Student {

    private String studentName;
    private ArrayList<Book> booklist;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public ArrayList<Book> getBooklist() {
        return booklist;
    }

    public void setBooklist(ArrayList<Book> booklist) {
        this.booklist = booklist;
    }

    public Student(String studentName) {
        this.studentName = studentName;
        booklist = new ArrayList<Book>();
    }

    public void addBook(String title) {
        Book book = new Book(title);
        booklist.add(book);
    }

    public void showStudentInfo() {
        System.out.print(studentName+" 학생이 읽은 책은 :");
        for (int i=0; i<booklist.size(); i++) {
            System.out.print(booklist.get(i).title+" ");
        }
        System.out.println("입니다.");
    }


}
