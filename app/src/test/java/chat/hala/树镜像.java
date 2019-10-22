package chat.hala;

import org.junit.Test;

/**
 * @author wjy on 2019/10/22/022.
 */
public class 树镜像 {
    @Test
    public void addition_isCorrect() {
        TreeNode treeNode = new TreeNode(8);
        TreeNode treeNode1 = new TreeNode(6);
        TreeNode treeNode2 = new TreeNode(10);
        TreeNode treeNode3 = new TreeNode(5);
        TreeNode treeNode4 = new TreeNode(7);
        TreeNode treeNode5 = new TreeNode(9);
        TreeNode treeNode6 = new TreeNode(11);

        treeNode.left = treeNode1;
        treeNode.right = treeNode2;
        treeNode1.left = treeNode3;
        treeNode1.right = treeNode4;
        treeNode2.left = treeNode5;
        treeNode2.right = treeNode6;
        Mirror(treeNode);
        System.out.println(treeNode+"1111");


    }

    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        reverse(root);
    }

    public void reverse(TreeNode a) {
        if (a.left != null && a.right != null) {
            TreeNode le = a.left;
            a.left = a.right;
            a.right = le;
            reverse(a.left);
            reverse(a.right);
        } else if (a.left == null) {
            a.right = a.left;
        } else if (a.right == null) {
            a.left = a.right;
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }


    }

}
