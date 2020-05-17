package array;

public class ObjectCopy2 {
    public static void main(String[] args) {

        Book[] library = new Book[5];
        Book[] copylibrary = new Book[5];

        library[0] = new Book("태백산맥1","조정래");
        library[1] = new Book("태백산맥2","조정래");
        library[2] = new Book("태백산맥3","조정래");
        library[3] = new Book("태백산맥4","조정래");
        library[4] = new Book("태백산맥5","조정래");

        copylibrary[0] = new Book(); //인스턴스 따 생성
        copylibrary[1] = new Book();
        copylibrary[2] = new Book();
        copylibrary[3] = new Book();
        copylibrary[4] = new Book();

        for (int i=0; i<library.length; i++) { //깊은 복사
            copylibrary[i].setTitle(library[i].getTitle());
            copylibrary[i].setAuthor(library[i].getAuthor());
        }

        for ( Book book : copylibrary ) {
            book.showBookInfo();
        }
        library[0].setTitle("나목");
        library[0].setAuthor("박완선");

        library[0].showBookInfo();
        copylibrary[0].showBookInfo(); //복사본은 변경되지 않음 (서로 다른 인스턴스) == 깊은 복사

    }
}
