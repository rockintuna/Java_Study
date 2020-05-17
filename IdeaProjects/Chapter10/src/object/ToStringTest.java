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
