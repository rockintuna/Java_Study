package codingexample;

import java.io.IOException;

public class SortTest {
    public static void main(String[] args) throws IOException {

        int ch;
        int[] arr = new int[5];

        System.out.println("정렬 방식을 선택하세요.");
        System.out.println("B : BubbleSort");
        System.out.println("Q : QuickSort");
        System.out.println("H : HeapSort");

        ch = System.in.read();
        Sort sort;

        if (ch == 'B' || ch =='b') {
            sort = new BubbleSort();
        } else if (ch == 'Q' || ch =='q') {
            sort = new QuickSort();
        } else if (ch == 'H' || ch =='h') {
            sort = new HeapSort();
        } else {
            System.out.println("Error");
            return;
        }

        System.out.println(ch);

        sort.ascending(arr);
        sort.descending(arr);
        sort.description();

    }
}
