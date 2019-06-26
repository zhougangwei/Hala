package chat.hala;

import chat.hala.hala.utils.TimeUtil;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest2 {
    @Test
    public void addition_isCorrect() {
        String s = TimeUtil.dealDateFormat("2019-05-27T07:21:59.000+0000");
        System.out.println(s);

    }


}


