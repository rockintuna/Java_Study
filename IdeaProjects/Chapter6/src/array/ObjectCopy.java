package array;

public class ObjectCopy {
    public static void main(String[] args) {

        Book[] library = new Book[5];
        Book[] copylibrary = new Book[5];

        library[0] = new Book("태백산맥1","조정래");
        library[1] = new Book("태백산맥2","조정래");
        library[2] = new Book("태백산맥3","조정래");
        library[3] = new Book("태백산맥4","조정래");
        library[4] = new Book("태백산맥5","조정래");

        System.arraycopy(library,0,copylibrary,0,5); //기본자료형과 동일하게 사용하면 얕은 복사 (인스턴스 추가 생성 x)

        /*for (int i=0; i<copylibrary.length; i++) {
            copylibrary[i].showBookInfo();
        }*/

        for ( Book book : copylibrary ) { //향상된 for문  for ( 변수 선언 : array ) array의 모든 요소를 var에 순차적으로 입력
            book.showBookInfo();
        }
        library[0].setTitle("나목");
        library[0].setAuthor("박완선");

        library[0].showBookInfo();
        copylibrary[0].showBookInfo(); //동일하게 변경됨 (주소만 복사된 것이므로 동일한 인스턴스이다.) == 얕은 복사

    }
}
