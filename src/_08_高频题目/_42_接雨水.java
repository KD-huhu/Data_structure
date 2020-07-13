package _08_高频题目;

/**
 * https://leetcode-cn.com/problems/trapping-rain-water/
 * @author DELL
 * 思路：计算柱子上可以停留多少水
 * 头和尾部的柱子不可能停留水
 * 柱子存留水取决于左右所有柱子中的最大值
 * min(左边柱子最大值, 右边柱子最大值) - 自己的高度
 */
public class _42_接雨水 {

    /**
     * 空间复杂度O(1)，时间复杂度O(n)
     * 双指针，从0和length-1开始扫描
     */
    public int trap(int[] height) {
        if (height == null || height.length == 0) return 0;
        int l = 0, r = height.length - 1, lowerMax = 0, water = 0;
        while (l < r) {
			// height[l]、height[r]中较小的那个
        	int lower = height[height[l] <= height[r] ? l++ : r--];
        	// 到目前为止最大的lower
        	lowerMax = Math.max(lowerMax, lower);
        	water += lowerMax - lower;
		}
        return water;
    }

    public int trap1(int[] height) {
        if (height == null || height.length == 0) return 0;
        int lastIdx = height.length - 2;
        int[] rightMaxes = new int[height.length];
        for (int i = lastIdx; i >= 1; i--) {
            rightMaxes[i] = Math.max(rightMaxes[i + 1], height[i + 1]);
        }
        // 遍历每一根柱子，看看每一根柱子上能放多少水
        int water = 0, leftMax = 0;
        // 在扫描的同时计算左边的最大值，减少一次遍历和以一个数组
        for (int i = 1; i <= lastIdx; i++) {
            leftMax = Math.max(leftMax, height[i - 1]);
            // 求出左边最大、右边最大中的较小者
            int min = Math.min(leftMax, rightMaxes[i]);
            // 说明这根柱子不能放水
            if (min <= height[i]) continue;
            // 说明这根柱子能放水
            water += min - height[i];
        }
        return water;
    }

    public int trap0(int[] height) {
        if (height == null || height.length == 0) return 0;
        int lastIdx = height.length - 2;
        int[] leftMaxes = new int[height.length];
        // 动态规划求最大值
        // 仅需要比较旁边柱子的高度和其之前的最大值
        for (int i = 1; i <= lastIdx; i++) {
            leftMaxes[i] = Math.max(leftMaxes[i - 1], height[i - 1]);
        }
        int[] rightMaxes = new int[height.length];
        for (int i = lastIdx; i >= 1; i--) {
            rightMaxes[i] = Math.max(rightMaxes[i + 1], height[i + 1]);
        }
        // 遍历每一根柱子，看看每一根柱子上能放多少水
        int water = 0;
        for (int i = 1; i <= lastIdx; i++) {
            // 求出左边最大、右边最大中的较小者
            int min = Math.min(leftMaxes[i], rightMaxes[i]);
            // 说明这根柱子不能放水
            if (min <= height[i]) continue;
            // 说明这根柱子能放水
            water += min - height[i];
        }
        return water;
    }

}
