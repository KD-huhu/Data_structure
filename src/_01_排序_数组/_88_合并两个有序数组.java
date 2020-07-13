package _01_排序_数组;

/**
 * https://leetcode-cn.com/problems/merge-sorted-array/
 * @author DELL
 * 思路：对于两个有序数组的合并，需要从右向左实现
 * 因为对于数组1，其最后部分是空白，所以可以在不干扰实际使用的情况下进行复制
 * 需要使用到三个指针，分别指向数组1、数组2有数据的位置，以及当前可以插入的位置
 * 注意在实际中数组1和数组2指针为0，也就是数据全部遍历完成后的操作
 */
public class _88_合并两个有序数组 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
    	int i1 = m-1;
    	int i2 = n-1;
    	int cur = nums1.length - 1;
    	while (i2 >= 0) {
			if (i1 >= 0 && nums2[i2] < nums1[i1]) {
				nums1[cur--] = nums1[i1--];
			} else {
				nums1[cur--] = nums2[i2--];
			}
		}
    }
}
