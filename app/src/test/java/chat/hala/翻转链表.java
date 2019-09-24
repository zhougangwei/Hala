package chat.hala;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author wjy on 2019/9/16/016.
 */
public class 翻转链表 {
    @Test
    public void addition_isCorrect() {


        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        ListNode f = new ListNode(6);
        a.next=b;
        b.next=c;
        c.next=d;
        d.next=e;
        e.next=f;
        ListNode listNode = ReverseList(a);
        System.out.println(":111111");
    }

    class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
        public ListNode ReverseList(ListNode head) {

            ArrayList<ListNode> list = new ArrayList();

            while (head != null) {
                list.add(head);
                head = head.next;
            }
            list.get(0).next=null;
            for (int i = list.size() - 1; i > 0; i--) {
                ListNode node = list.get(i);
                node.next = list.get(i - 1);
            }

            return list.get(list.size() - 1);

        }
    }
