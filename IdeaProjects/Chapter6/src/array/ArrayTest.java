package array;

public class ArrayTest {

    public static void main(String[] args) {

        int[] arr = new int[10]; //기본자료형 Array
        //int[] arr = new int[] {1,2,3}; //선언과 동시에 초기화 가능
        //int[] arr = {1,2,3}; //위와 동일
        int total = 0;

        for (int i=0;i<arr.length;i++) {
            System.out.println(arr[i]); //0으로 10개가 초기화되어 있음
        }

        for (int i=0,num=1;i<arr.length;i++,num++) { //배열 arr의 모든 요소를 1씩 증가하도록 변경
            arr[i]=num;
        }

        for (int i=0;i<arr.length;i++) {
            System.out.println(arr[i]);
        }

        for (int i=0;i<arr.length;i++) { //배열 요소 합 구하기
            total += arr[i];
        }

        System.out.println(total);

        double[] dArr = new double[5];
        int count = 0;
        dArr[0] = 1.1; count++;
        dArr[1] = 2.1; count++;
        dArr[2] = 3.1; count++;

        double mtotal = 1;
        for (int i=0;i<count;i++) { //count를 통해 직접 초기화 한 값만 곱
            mtotal *= dArr[i];
            System.out.println(dArr[i]);
        }
        System.out.println(mtotal);

    }
}
