package chat.hala;

import org.junit.Test;

/**
 * @author wjy on 2019/10/22/022.
 */
public class 合并Node {

    @Test
    public void addition_isCorrect() {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(3);
        ListNode c = new ListNode(4);
        ListNode d = new ListNode(5);
        ListNode e = new ListNode(8);
        ListNode f = new ListNode(11);
        a.next=b;
        b.next=c;
        c.next=d;
        d.next=e;
        e.next=f;

        ListNode j = new ListNode(2);
        ListNode k = new ListNode(3);
        ListNode l = new ListNode(4);
        ListNode m = new ListNode(6);
        ListNode n = new ListNode(7);
        ListNode o = new ListNode(12);
        j.next=k;
        k.next=l;
        l.next=m;
        m.next=n;
        n.next=o;

        ListNode merge = Merge(a, j);
        System.out.println(merge+"1111");




    }


     class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

        public ListNode Merge(ListNode list1,ListNode list2) {
            ListNode a=new ListNode(0);
            if(list1==null&&list2==null){
                return null;
            }
            ListNode head =a;
            while(list1!=null||list2!=null){
                if(list1==null){
                    a.val=list2.val;
                    list2=list2.next;
                    if (list2!=null){
                        a.next=new ListNode(0);
                        a=a.next;
                    }
                    continue;
                }
                if(list2==null){
                    a.val=list1.val;
                    list1=list1.next;
                    if(list1!=null){
                        a.next=new ListNode(0);
                        a=a.next;
                    }
                    continue;
                }
                if(list1.val<list2.val){
                    a.val=list1.val;
                    list1=list1.next;
                    a.next=new ListNode(0);
                    a=a.next;
                }else{
                    a.val=list2.val;
                    list2=list2.next;
                    a.next=new ListNode(0);
                    a=a.next;
                }

            }
            a=null;

            return head;
        }

}
