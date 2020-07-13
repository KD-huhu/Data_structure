package _08_高频题目;

/**
 * https://leetcode-cn.com/problems/container-with-most-water/
 * @author DELL
 * 思路：使用双指针优化，首先计算宽度最宽时的成水量
 * 计算之后，左右指针分别向中间逼近，
 * 只令左右指针中较小的指针移动
 */
public class _11_盛最多水的容器 {
	public int maxArea(int[] height) {
        if (height == null || height.length == 0) return 0;
        int l = 0, r = height.length - 1, water = 0;
        while (l < r) {
            int minH = Math.min(height[l], height[r]);
            water = Math.max(water, minH * (r - l));
            while (l < r && height[l] <= minH) l++;
            while (l < r && height[r] <= minH) r--;
        }
        return water;
    }

    public int maxArea2(int[] height) {
        if (height == null || height.length == 0) return 0;
        int l = 0, r = height.length - 1, water = 0;
        while (l < r) {
            if (height[l] <= height[r]) {
                int minH = height[l];
                water = Math.max(water, (r - l) * minH);
                while (l < r && height[l] <= minH) l++;
            } else {
                int minH = height[r];
                water = Math.max(water, (r - l) * minH);
                while (l < r && height[r] <= minH) r--;
            }
        }
        return water;
    }

    public int maxArea1(int[] height) {
        if (height == null || height.length == 0) return 0;
        int l = 0, r = height.length - 1, water = 0;
        while (l < r) {
            int minH = (height[l] <= height[r]) ? height[l++] : height[r--];
            water = Math.max(water, minH * (r - l + 1));
        }
        return water;
    }

    public int maxArea0(int[] height) {
        if (height == null || height.length == 0) return 0;
        int l = 0, r = height.length - 1, water = 0;
        while (l < r) {
            if (height[l] <= height[r]) {
                water = Math.max(water, (r - l) * height[l]);
                l++;
            } else {
                water = Math.max(water, (r - l) * height[r]);
                r--;
            }
        }
        return water;
    }

}
