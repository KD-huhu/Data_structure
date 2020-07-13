package _02_链表;

/**
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 * @author DELL
 * 思路：将删除看做是创建一个全新的链表
 * 创建一个新的头节点，是新链表的头节点
 * 使用原始的头节点来遍历原来的链表
 * 使用一个cur指针指向新链表的最后一个节点
 * 注意：当原始链表的头部节点是要删除的节点时，新的头节点要是空值
 */
public class _203_移除链表元素 {
    public ListNode removeElements(ListNode head, int val) {
    	if (head == null) return null;
		// 使用虚拟头节点来简化代码
    	// 新链表的头结点
    	ListNode newhead = new ListNode(0);
    	// 链表的尾结点
    	ListNode newTail = newhead;
    	while (head != null) {
			if (head.val != val) {
				newTail.next = head;
				newTail = head;
			}
			head = head.next;
		}
    	newTail.next = null;
    	return newhead.next;
    }
}
