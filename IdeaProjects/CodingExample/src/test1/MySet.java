package test1;

import java.util.ArrayList;
import java.util.Arrays;

public class MySet extends ArrayList{

    private MySet() {
    }

    public MySet(Integer[] arr) {
        isNumberOverLimit(arr);
        for (int i = 0; i < arr.length; i++) {
            if (!this.contains(arr[i])) {
                this.add(arr[i]);
            }
        }
    }

    public static int[] solution(Integer[] list1, Integer[] list2) {
        int[] result = new int[5];

        MySet mySet1 = new MySet(list1);
        result[0] = mySet1.size();

        MySet mySet2 = new MySet(list2);
        result[1] = mySet2.size();

        MySet sumSet = sum(mySet1,mySet2);
        result[2] = sumSet.size();

        MySet complementSet = complement(mySet1,mySet2);
        result[3] = complementSet.size();

        MySet intersectSet = intersect(mySet1,mySet2);
        result[4] = intersectSet.size();

        return result;
    }

    public static MySet sum(MySet base, MySet other) {
        MySet result = new MySet();
        result.addAll(base);
        result.addAll(other);
        return deduplicate(result);
    }

    public static MySet complement(MySet base, MySet other) {
        MySet result = new MySet();
        result.addAll(base);
        result.removeAll(other);
        return result;
    }

    public static MySet intersect(MySet base, MySet other) {
        MySet result = new MySet();
        result.addAll(base);
        result.retainAll(other);
        return result;
    }

    private static MySet deduplicate(MySet mySet) {
        MySet result = new MySet();
        for (int i = 0; i < mySet.size(); i++) {
            if (!result.contains(mySet.get(i))) {
                result.add(mySet.get(i));
            }
        }
        return result;
    }

    private static void isNumberOverLimit(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 1000000) {
                throw new RuntimeException("제한된 수를 초과하였습니다.");
            }
        }
    }

    public static void main(String[] args) {
        Integer[] test1 = {2,3,4,3,5};
        Integer[] test2 = {1,6,7};

        MySet mySet1 = new MySet(test1);
        MySet mySet2 = new MySet(test2);

        System.out.println(mySet1);
        System.out.println(mySet2);
        System.out.println(sum(mySet1,mySet2));
        System.out.println(complement(mySet1,mySet2));
        System.out.println(intersect(mySet1,mySet2));

        System.out.println(Arrays.toString(solution(test1,test2)));

    }
}
