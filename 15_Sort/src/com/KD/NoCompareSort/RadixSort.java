package com.KD.NoCompareSort;

import com.KD.Sort;

public class RadixSort extends Sort<Integer> {

	@Override
	protected void sort() {
		// find max
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		// example: 593
		// unit: array[i] / 1 % 10 = 3
		// ten: array[i] / 10 % 10 = 9
		// hundred: array[i] / 100 % 10 = 5
		// thousand: array[i] / 1000 % 10 = ...

		for (int divider = 1; divider <= max; divider *= 10) {
			countingSort(divider);
		}
	}
	
	protected void countingSort(int divider) {
		int[] counts = new int[10];
		for (int i = 0; i < array.length; i++) {
			counts[array[i] / divider % 10]++;
		}
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}
		int[] newArray = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--) {
			newArray[--counts[array[i] / divider % 10]] = array[i];
		}
		for (int i = 0; i < newArray.length; i++) {
			array[i] = newArray[i];
		}
	}
}
