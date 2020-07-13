package com.KD;

public class LIS {

	public static void main(String[] args) {
//		System.out.println(lengthOfLIS1(new int[] {10, 2, 2, 5, 1, 7, 101, 18}));
//		System.out.println(lengthOfLIS2(new int[] {10, 2, 2, 5, 1, 7, 101, 18}));
		System.out.println(lengthOfLIS3(new int[] {10, 2, 2, 5, 1, 7, 101, 18}));
	}

	static int lengthOfLIS1(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int[] dp = new int[nums.length];
		int max = dp[0] = 1;
		for (int i = 1; i < dp.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (nums[i] <= nums[j]) continue;
				dp[i] = Math.max(dp[i], dp[j] + 1);
			}
			max = Math.max(dp[i], max);
		}
		return max;
	}
	
	static int lengthOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		// heaps
		int len = 0;
		// top poker array
		int[] top = new int[nums.length];
		for (int num : nums) {
			int j = 0;
			while (j < len) {
				// find the top
				if (top[j] >= num) {
					top[j] = num;
					break;
				}
				j++;
			}
			if (j == len) { // create new top
				len++;
				top[j] = num;
			}
		}
		return len;
	}
	
	static int lengthOfLIS3(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int len = 0;
		int[] top = new int[nums.length];
		for (int num : nums) {
			int begin = 0;
			int end = len;
			while (begin < end) {
				int mid = (begin + end) >> 1;
				if (num <= top[mid]) {
					end = mid;
				} else {
					begin = mid + 1;
				}
			}
			top[begin] = num;
			if (begin == len) len++;
		}
		return len;
	}
}
