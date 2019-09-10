package chat.hala;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Example local unit test, which will execute on the development machine (host).
 *	重建二叉树
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest3 {
    @Test
    public void addition_isCorrect() {

        int[] a ={1,2,3,4,5,6,7};
        int[] b ={3,2,4,1,6,5,7};

        TreeNode treeNode = reConstructBinaryTree(a, b);
        System.out.println("1111111");
    }

    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if (pre.length!=0){

        TreeNode root=new TreeNode(pre[0]);
        int a = pre[0];

        for(int i=0;i<in.length;i++){
            if(in[i]==a){
                int[] bb=new int[i];
                int[] dd=new int[i];

                int[] cc=new int[in.length-i-1];
                int[] ee=new int[in.length-i-1];
                for(int j=0;j<i;j++){
                    bb[j]=in[j];
                    dd[j]=pre[j+1];
                }
                for(int k=i+1;k<in.length;k++){
                    cc[k-i-1]=in[k];
                    ee[k-i-1]=pre[k];
                }
                root.left=reConstructBinaryTree(dd,bb);
                root.right=reConstructBinaryTree(ee,cc);

            }
        }
        return root;
        }
        return null;



    }
     class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
}


