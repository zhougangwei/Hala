package chat.hala;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest4 {
    @Test
    public void addition_isCorrect() {
        List<Long> datas = new ArrayList<>();
        List<Long> ass = new ArrayList<>();
        datas.add(1L);
        datas.add(2L);
        datas.add(3L);
        datas.add(4L);
        ass.add(1L);
        ass.add(4L);
        List<Long> followIds = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            Long aLong = datas.get(i);
            if (!ass.contains(aLong)) {
                followIds.add(aLong);
            }
        }
        System.out.println(followIds.size());


    }



}


