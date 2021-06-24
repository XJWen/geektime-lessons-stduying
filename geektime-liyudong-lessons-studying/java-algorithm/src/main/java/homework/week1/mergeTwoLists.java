package homework.week1;

/**
 *   Definition for singly-linked list.
 * */
class Solution {

    public static void main(String[] args) {
        ListNode listNode1 =new ListNode(1,null);
        ListNode listNode2 =new ListNode(2,listNode1);
        ListNode listNode3 =new ListNode(3,null);
        mergeTwoLists(listNode2,listNode3);
    }

     static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1==null){
            return l2;
        }
        if (l2==null){
            return l1;
        }
        if (l1.val<l2.val){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
        else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}