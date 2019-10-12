package chat.hala.hala.utils;

import java.util.Random;

/**
 * @author wjy on 2019/10/10/010.
 */
public class RandomUtils {

   static String arr=new String( "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM");

    public static String getRandomString() {
        String result = "";
        for (int i = 0; i < 6; i++) {
            result = result + arr.charAt(new Random().nextInt(arr.length()));
        }
        return result;
    }
}
