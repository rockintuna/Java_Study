package test1;

import java.util.ArrayList;

public class StringCompress {

    String compress(String any) {
        char[] sourceArr = any.toCharArray();
        ArrayList<Integer> compressedArr = new ArrayList<Integer>();


        int equal = 1;
        int length = sourceArr.length;
        System.out.println(length);

        for (int i = 1; i < length; i++) {
            if (sourceArr[i - 1] == sourceArr[i]) {
                equal++;
            } else {
                compressedArr.add(equal);
                compressedArr.add((int) sourceArr[i - 1]);
                equal = 1;
            }
        }

        return compressedArr.toString();
    }

    String decompress(String compressed) {
        return compressed;
    }

    public static void main(String[] args) {
        String str = "ZZZAAAAAAAAAABBCCQAA";

        StringCompress strcompress = new StringCompress();

        //Compress
        String compressed = strcompress.compress(str);
        System.out.println(compressed);

        //Decompress
        String decompressed = strcompress.decompress(compressed);
        System.out.println(decompressed);
    }
}
