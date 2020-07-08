package com.KD;

import java.util.Arrays;

public class Coin_change {

	public static void main(String[] args) {
//		coinChange2(new Integer[] {25, 10, 5, 1}, 41);
//		coinChange1();
		coinChange(new Integer[] {25, 20, 5, 1}, 41);
	}
	
	static void coinChange(Integer[] faces, int money) {
		Arrays.sort(faces);
		int coins = 0, idx = faces.length - 1;
		while (idx >= 0) {
			while (money >= faces[idx]) {
				System.out.println(faces[idx]);
				money -= faces[idx];
				coins++;
			}
			idx--;
		}
		System.out.println(coins + " coins are needed.");
	}
	
	static void coinChange2(Integer[] faces, int money) {
		Arrays.sort(faces, (Integer f1, Integer f2) -> f2 - f1); 
		int coins = 0, i = 0;
		while (i < faces.length) {
			if (money < faces[i]) {
				i++;
				continue;
			}
			System.out.println(faces[i]);
			money -= faces[i];
			coins++;
		}
		System.out.println(coins + " coins are needed.");
	}
	
	static void coinChange1() {
		int[] faces = {25, 5, 10, 1};
		Arrays.sort(faces); 
		int money = 41;
		int coins = 0;
		for (int i = faces.length - 1; i >= 0; i--) {
			if (money < faces[i]) {
				continue;
			}
			System.out.println(faces[i]);
			money -= faces[i];
			coins++;
			i = faces.length;
		}
		System.out.println(coins + " coins are needed.");
	}
}
