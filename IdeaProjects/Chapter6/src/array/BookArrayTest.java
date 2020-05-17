package array;

public class BookArrayTest {
    public static void main(String[] args) {

        Book[] library = new Book[5]; //객체 배열이 생긴 거지 객체 5개가 생긴건 아님

        /*for (int i=0; i<library.length; i++) { //인스턴스 생성1
            library[i] = new Book();
        }*/

        library[0] = new Book("태백산맥1","조정래"); //인스턴스 생성2
        library[1] = new Book("태백산맥2","조정래");
        library[2] = new Book("태백산맥3","조정래");
        library[3] = new Book("태백산맥4","조정래");
        library[4] = new Book("태백산맥5","조정래");


        for (int i=0; i<library.length; i++) {
            System.out.println(library[i]); //각 인스턴스의 메모리 주소 출력 32bit x 5
            library[i].showBookInfo();
        }


    }
}
