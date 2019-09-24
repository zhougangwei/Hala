package chat.hala;

import org.junit.Test;

import java.util.Stack;

/**
 * Example local unit test, which will execute on the development machine (host).
 *	重建二叉树
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class 跳楼梯 {
    @Test
    public void addition_isCorrect() {

        Solution solution = new Solution();
        int i = solution.JumpFloor(4);
        System.out.println(i+"");

    }


    public class Solution {

        public int JumpFloor(int target) {
            int count=0;

            if(target-1>0){
                count++;
                return count+JumpFloor(target-1);
            }else if(target-1==0){
                return 0;
            }

            return count;
        }
    }
}


