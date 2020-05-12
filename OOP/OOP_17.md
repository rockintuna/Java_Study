# 객체 지향 프로그래밍

#### 17. 배열

* 배열이란  
동일한 자료형의 순차적 자료 구조  
ex) 학생 100명에 대한 학번 변수

배열 선언
```
int[] arr = new int[10]; //int 10개 = 40byte length=10, 0~9
int arr[] = new int[10];
```
배열은 fixed length이기 때문에  
만약 배열의 길이를 늘리고 싶다면, 
더 긴 배열을 선언한 뒤 값을 복사해야 한다.   

배열은 연속적이어야 한다.  (중간에 비어있으면 안된다.)    
데이터가 들어가거나 빠질때 추가적인 작업이 필요하다.   

배열을 사용하는 가장 큰 이유 : 인덱스 연산자  
```
arr[4] //추출이 편하고 속도가 빠름
```

ArrayList를 쓰면 편하게 배열을 사용할 수 있다.     
    
```
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
```
    
    