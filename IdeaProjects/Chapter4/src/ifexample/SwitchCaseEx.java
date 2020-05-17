package ifexample;

public class SwitchCaseEx {
    public static void main(String[] args) {

        String medalColorT="Gold";
        int rank;

        switch(medalColorT) {
            case "Gold":
                rank = 1;
                break; //break가 없으면 다음 case 문이 조건과 상관 없이 수행된다.
            case "Silver":
                rank = 2;
                break;
            case "Bronze":
                rank = 3;
                break;
            default :
                rank = 0;
                System.out.println("Error");
        }
        System.out.println(medalColorT + "메달은 " + rank + "등 입니다.");

    }
}
