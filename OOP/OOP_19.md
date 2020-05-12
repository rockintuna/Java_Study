# 객체 지향 프로그래밍

#### 19. 객체 배열

* 객체 배열 (참조 자료형 배열)     
```
Book[] library = new Book[5]; //null이 초기화됨
                              //실제로는 객체의 주소가 들어가게 된다
```

배열로 만 클래스 생성    
```
package array;

public class Book {

    private String title;
    private String author;

    public Book() {
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void showBookInfo() {
        System.out.println(title+","+author);
    }
}
```

객체 배열 생성 및 인스턴스 생성  
```
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
```

* 기본 자료형 배열 복사
```
package array;

public class ArrayCopy {
    public static void main(String[] args) {

        int[] arr1 = {10,20,30,40,50};
        int[] arr2 = {1,2,3,4,5};

        System.arraycopy(arr1,0,arr2,1,3); //배열을 복사할때 사용하는 메서드
                        //소스,어디부터,타겟,어디부터,몇개

        for (int i=0; i<arr2.length; i++) {
            System.out.println(arr2[i]);
        }

    }
}

```

* 객체 배열 복사

얕은 복사   
```
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
```

깊은 복사   
```
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
```