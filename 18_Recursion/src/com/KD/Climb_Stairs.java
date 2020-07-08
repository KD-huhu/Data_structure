package com.KD;

public class Climb_Stairs {
	int climbStairs(int n) {
		if (n <= 2) return n;
		return climbStairs(n - 1) + climbStairs(n - 2);
	}
	
	int climbStairs1(int n) {
		if (n <= 2) return n;
		int first = 1;
		int second = 2;
		for (int i = 3; i <= n; i++) {
			second = first + second;
			first = second - first;
		}
		return second;
	}
}
