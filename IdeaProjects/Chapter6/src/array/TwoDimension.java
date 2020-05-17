package array;

public class TwoDimension {
    public static void main(String[] args) {

        //int[][] arr = new int[2][3];
        int[][] arr = {{1,2,3},{4,5,6,7}};

        System.out.println(arr.length); //2차원 배열에서 length는 행의 개수
        System.out.println(arr[0].length); //0번째 행의 길이
        System.out.println(arr[1].length);

        //행을 기준으로 열을 돌린다.(2중 for문)

        for (int i=0; i<arr.length; i++) { //행 for문
            for (int j=0; j<arr[i].length; j++) { //열 for문
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
}
