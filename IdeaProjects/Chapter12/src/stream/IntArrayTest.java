package stream;

import java.util.Arrays;

public class IntArrayTest {
    public static void main(String[] args) {

        int[] arr = {1,2,3,4,5};

        int sum = Arrays.stream(arr).sum();
        int count = (int)Arrays.stream(arr).count(); //썼으니 재생성

        System.out.println(sum);
        System.out.println(count);

    }
}
