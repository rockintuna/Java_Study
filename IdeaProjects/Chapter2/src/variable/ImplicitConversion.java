package variable;

public class ImplicitConversion {
    public static void main(String[] args) {

        byte bNum = 10;
        int iNum = bNum; //더 큰수로 묵시적 형 변환

        System.out.println(bNum);
        System.out.println(iNum);

        int iNum2 = 20;
        float fNum = iNum2; //더 정밀한 수로 형 변환

        System.out.println(fNum);

        double dNum;
        dNum = fNum + iNum; //더 정밀한 수로 형 변환 2회 i->f->d
        System.out.println(dNum);

    }

}
