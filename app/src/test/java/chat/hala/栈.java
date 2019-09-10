package chat.hala;

import org.junit.Test;

import java.util.Stack;

/**
 * Example local unit test, which will execute on the development machine (host).
 *	重建二叉树
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class 栈 {
    @Test
    public void addition_isCorrect() {

        Solution solution = new Solution();
        solution.push(1);
        solution.push(2);
        solution.push(3);
        solution.pop();
        solution.pop();
        solution.push(4);
        solution.pop();
        solution.push(1);
        solution.push(3);

    }


    public class Solution {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();

        public void push(int node) {
            stack1.push(node);
        }

        public int pop() {
            while(!stack2.isEmpty()){
                return stack2.pop();
            }
            while(!stack1.isEmpty()){
                Integer a=stack1.pop();
                stack2.push(a);
            }
            return stack2.pop();

        }

}
}


