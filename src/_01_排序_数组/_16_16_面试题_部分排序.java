package _01_排序_数组;

/**
 * https://leetcode-cn.com/problems/sub-sort-lcci/
 * @author DELL
 * 思路：题目意思是找出数组中最短的无序的区间，返回值的区间开始的索引和结束的索引
 * 区间最右端是自左向右最右侧的逆序对
 * 区间最左端是自右向左最左侧的逆序对
 */
public class _16_16_面试题_部分排序 {
    public int[] subSort(int[] array) {
    	if (array.length == 0) return new int[] { -1, -1 };
    	int max = array[0];
    	int r = -1;
    	for (int i = 1; i < array.length; i++) {
			if (array[i] >= max) {
				max = array[i];
			} else {
				r = i;
			}
		}
    	if (r == -1) return new int[] { -1, -1 };
    	int min = array[array.length - 1];
    	int l = -1;
    	for (int i = array.length - 2; i >= 0; i--) {
			if (array[i] <= min) {
				min = array[i];
			} else {
				l = i;
			}
		}
    	return new int[] { l, r };
    }
}
