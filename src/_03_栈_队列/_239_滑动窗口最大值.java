package _03_栈_队列;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode-cn.com/problems/sliding-window-maximum/
 * @author DELL
 * 思路：返回数组的长度：num.length - k + 1
 * 		因为有k-1个数据不能作为滑动窗口的头
 *  	使用双端队列，保证队列内的数据从头到尾递减
 *  	指针i指向滑动窗口的尾部，w指向滑动窗口头部
 *  	i遍历整个数组，将i指向的元素和队列尾部元素比较：
 *  		如果i较小，入队；
 *  		如果i较大，删除队尾元素，再次比较；
 *  	w值为合法时，先判断对头元素是否在滑动窗口内部：
 *  		（判断依据：对头索引大于w--合法）
 *  		不在，直接删除；
 *  		在，对头元素的值为滑动窗口中的最大值；
 * 		注意：队列中存放的只是元素对应的索引（提高查询效率）
 */
public class _239_滑动窗口最大值 {
	// 方法1：双端队列实现
	// peek: 取值（偷偷瞥一眼）
	// poll: 删除（削）
	// offer: 添加（入队）
    public int[] maxSlidingWindow(int[] nums, int k) {
    	if (nums == null || nums.length == 0 || k < 1) return new int[0];
    	if (k == 1) return nums;
    	int[] maxes = new int[nums.length - k + 1];
    	Deque<Integer> deque = new LinkedList<>();
    	for (int ri = 0; ri < nums.length; ri++) {
			// 只要nums[队尾] <= nums[i]，就删除队尾
    		while (!deque.isEmpty() && nums[ri] >= nums[deque.peekLast()]) {
				deque.pollLast();
			}
    		// 将i加到队尾
    		deque.offerLast(ri);
    		// 检查窗口的索引是否合法
    		int li = ri - k + 1;
    		if (li < 0) continue;
    		// 检查队头的合法性
    		if (deque.peekFirst() < li) {
    			// 队头不合法（失效，不在滑动窗口索引范围内）
    			deque.pollFirst();
			}
    		// 设置窗口的最大值
    		maxes[li] = nums[deque.peekFirst()];
		}
    	return maxes;
    }
    
    // 方法2：使用扫描，通过保存最大值来减少时间复杂度
    public int[] maxSlidingWindow1(int[] nums, int k) {
    	if (nums == null || nums.length == 0 || k < 1) return new int[0];
    	if (k == 1) return nums;
    	int[] maxes = new int[nums.length - k + 1];
    	// 当前滑动窗口的最大值索引
    	int maxIdx = 0;
    	// 求出前k个元素的最大值索引
    	for (int i = 1; i < k; i++) {
			if (nums[i] > nums[maxIdx]) maxIdx = i;
		}
    	// li是滑动窗口的最左索引
    	for (int li = 0; li < maxes.length; li++) {
    		// ri是滑动窗口的最右索引
			int ri = li + k - 1;
			if (maxIdx < li) { // 最大值的索引不在滑动窗口的合理范围内
				// 求出[li, ri]范围内最大值的索引
				maxIdx = li;
				for (int i = li + 1; i <= ri; i++) {
					if (nums[i] > nums[maxIdx]) maxIdx = i;
				}
			} else if (nums[ri] >= nums[maxIdx]) { // 最大值的索引在滑动窗口的合理范围内
				maxIdx = ri;
			}
			maxes[li] = nums[maxIdx];
		}
    	return maxes;
    }
}
