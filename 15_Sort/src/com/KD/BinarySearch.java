package com.KD;

public class BinarySearch {
	
	/**
	 * find V in ordered array`s place
	 */
	public static int indexOf(int[] array, int v) {
		if (array == null || array.length == 0) return -1;
		int begin = 0;
		int end = array.length;
		while (begin < end) {
			int mid = (begin + end) >> 1;
			if (v < array[mid]) {
				end = mid;
			} else if (v > array[mid]) {
				begin = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
	
	/**
	 * find V in ordered array`s insert place
	 */
	public static int search(int[] array, int v) {
		if (array == null || array.length == 0) return -1;
		int begin = 0;
		int end = array.length;
		while (begin < end) {
			int mid = (begin + end) >> 1;
			if (v < array[mid]) {
				end = mid;
			} else {
				begin = mid + 1;
			}
		}
		return begin;
	}
}
