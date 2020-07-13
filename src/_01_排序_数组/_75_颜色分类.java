package _01_排序_数组;

/**
 * https://leetcode-cn.com/problems/sort-colors/
 * @author DELL
 * 注意：题目要求扫描一段得出结果的，一般是使用双指针或三指针
 * 思路：首先在题目中要求可以发现：该排序算法的空间复杂度为O（1），时间复杂度为O（n）
 * 使用三个指针，一个指向头部可用空间，一个指向尾部可用空间，一个指向当前遍历的位置
 * 当遍历指针遇到2，和尾部指针交换；交换后尾部指针--
 *  之后再次对交换过来的元素进行判断
 * 当遍历指针遇到0，和头部指针交换；交换后头部指针++
 * 	之后遍历指针++
 * 	之所以可以直接++，是因为之前部分是已经全部扫描过的区域，不会出现2
 * 当遍历指针遇到1，遍历指针直接++
 * 当遍历指针 > 尾部指针时，结束；一定是大于时可以退出
 */
public class _75_颜色分类 {
    public void sortColors(int[] nums) {
    	int i = 0;
    	int l = 0;
    	int r = nums.length -1;
    	while (i <= r) {
			if (nums[i] == 0) {
				swap(nums, i++, l++);
			} else if (nums[i] == 1) {
				i++;
			} else {
				swap(nums, i, r--);
			}
		}
    }

    private void swap(int[] nums, int i, int j) {
    	int tmp = nums[i];
    	nums[i] = nums[j];
    	nums[j] = tmp;
    }
}
