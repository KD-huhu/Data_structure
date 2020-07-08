package com.KD.NoCompareSort;

import com.KD.Sort;

public class CountingSort extends Sort<Integer> {

	@Override
	protected void sort() {
		// find max
		int max = array[0];
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}
		// create new array save times
		int[] counts = new int[max - min + 1];
		// count times
		for (int i = 0; i < array.length; i++) {
			counts[array[i] - min]++;
		}
		// add
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}
		// traverse from right to left
		int[] newArray = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--) {
			newArray[--counts[array[i] - min]] = array[i];
		}
		// copy ordered array to old array
		for (int i = 0; i < newArray.length; i++) {
			array[i] = newArray[i];
		}
	}
	
	protected void sort0() {
		// find max element
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		} 
		// create new array
		int[] counts = new int[1 + max];
		// count times of element
		for (int i = 0; i < array.length; i++) {
			counts[array[i]]++;
		} 
		// sort by times
		int index = 0;
		for (int i = 0; i < counts.length; i++) {
			while (counts[i]-- > 0) {
				array[index++] = i;
			}
		} 
	}	
}
