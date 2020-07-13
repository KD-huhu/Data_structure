package _02_链表;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 * @author DELL
 * 思路：从头节点开始，同时取值相加，直至两个链表完全为空
 * 注意在加法中要加上进位标志位
 * 在对两个链表的遍历结束后，要注意再次检查标志位，因为最高位的加法可能进位
 */
public class _2_两数相加 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	if (l1 == null) return l1;
    	if (l2 == null) return l2;
    	// 使用虚拟头节点优化
		ListNode dummyHead = new ListNode(0);
		ListNode last = dummyHead;
		// 进位标志位
		int carry = 0;
		while (l1 != null || l2 != null) {
			int v1 = 0;
			if (l1 != null) {
				v1 = l1.val;
				l1 = l1.next;
			}
			int v2 = 0;
			if (l2 != null) {
				v2 = l2.val;
				l2 = l2.next;
			}
			int sum = v1 + v2 + carry;
			// 设置进位值
			carry = sum / 10;
			// sum的个位数作为新节点的值
			last.next = new ListNode(sum % 10);
			last = last.next;
		}
		if (carry > 0) {
			// carry == 1
			last.next = new ListNode(carry);
		}
    	return dummyHead.next;
    }
}
