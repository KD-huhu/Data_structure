package com.KD;

public class Main {

	public static void main(String[] args) {
		int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4 };
		System.out.println(maxSubArray3(nums));
	}
	
	static int maxSubArray1(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int max = Integer.MIN_VALUE;
		for (int begin = 0; begin < nums.length; begin++) {
			for (int end = begin; end < nums.length; end++) {
				int sum = 0;
				for (int i = begin; i <= end; i++) {
					sum += nums[i];
				}
				max = Math.max(max, sum);
			}
		}
		return max;
	}
	
	static int maxSubArray2(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int max = Integer.MIN_VALUE;
		for (int begin = 0; begin < nums.length; begin++) {
			int sum = 0;
			for (int end = begin; end < nums.length; end++) {
				sum += nums[end];
				max = Math.max(max, sum);
			}
		}
		return max;
	}
	
	static int maxSubArray3(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		return maxSubArray3(nums, 0, nums.length);
	}
	
	static int maxSubArray3(int[] nums, int begin, int end) {
		if (end - begin < 2) return nums[begin];
		int mid = (begin + end) >> 1;
		int leftMax = nums[mid - 1];
		int leftSum = leftMax;
		for (int i = mid - 2; i >= begin; i--) {
			leftSum += nums[i];
			leftMax = Math.max(leftMax, leftSum);
		}
		int rightMax = nums[mid];
		int rightSum = rightMax;
		for (int i = mid + 1; i < end; i++) {
			rightSum += nums[i];
			rightMax = Math.max(rightMax, rightSum);
		}
		return Math.max(leftMax + rightMax, 
					Math.max(
					maxSubArray3(nums, begin, mid), 
					maxSubArray3(nums, mid, end))
				);
	}
}
