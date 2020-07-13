package _08_高频题目;

/**
 * https://leetcode-cn.com/problems/move-zeroes/
 * @author DELL
 * 思路：将非0元素的值和最前面的0交换
 */
public class _283_移动零 {
    public void moveZeroes(int[] nums) {
        if (nums == null) return;
        for (int i = 0, cur = 0; i < nums.length; i++) {
            if (nums[i] == 0) continue;
            if (cur != i) {
                nums[cur] = nums[i];
                nums[i] = 0;
            }
            cur++;
        }
    }
}
