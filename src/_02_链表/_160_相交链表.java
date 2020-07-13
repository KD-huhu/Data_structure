package _02_链表;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 * @author DELL
 * 思路：相交节点是两个指针指向同一个节点，所以内存地址相同
 * 由于两个链表的长度不同，所以在第一次遍历时无法得到相同的节点。
 * 解决办法：将两个链表互相拼接在对方的尾部，从而使得两个链表的长度一致
 * 在第二段中可以找点相交的节点
 */
public class _160_相交链表 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode curA = headA;
        ListNode curB = headB;
        while (curA != curB) {
			curA = (curA == null) ? headB : curA.next;
			curB = (curB == null) ? headA : curB.next;
		}
        return curA;
    }
}
