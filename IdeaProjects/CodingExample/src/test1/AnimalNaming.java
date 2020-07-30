package test1;

import java.util.ArrayList;
import java.util.List;

public class AnimalNaming {

    public static boolean solution(List<String> name_list) {
        isNameListOverLength(name_list);

        for ( int i = 0; i < name_list.size(); i++ ) {
            isNameOverLength(name_list.get(i));
        }

        for ( int i = 0; i < name_list.size(); i++ ) {
            if (isNameContaining(name_list, i)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isNameContaining(List<String> name_list, int i) {
        for (int j = 0; j < name_list.size(); j++ ) {
            if (i != j) {
                if ( name_list.get(i).contains(name_list.get(j)) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void isNameListOverLength(List<String> name_list) {
        if ( name_list.size() > 10000 ) {
            throw new RuntimeException("너무 많은 이름목록입니다.");
        }
    }

    public static void isNameOverLength(String name) {
        if ( name.length() > 16 ) {
            throw new RuntimeException("너무 긴 이름을 포함하고 있습니다.");
        }
    }

    public static void main(String[] args) {
        List<String> test_list = new ArrayList<>();
        test_list.add("가을");
        test_list.add("우주");
        test_list.add("여름우주");
        //test_list.add("봄봄봄봄봄봄봄봄봄봄봄봄봄봄봄봄봄봄봄");

        System.out.println(solution(test_list));
    }
}
