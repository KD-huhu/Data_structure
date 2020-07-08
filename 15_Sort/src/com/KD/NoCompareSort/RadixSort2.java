package com.KD.NoCompareSort;

import com.KD.Sort;

public class RadixSort2 extends Sort<Integer> {

	@Override
	protected void sort() {
		// find max
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		
		int[][] buckets = new int[10][array.length];
		int[] bucketSize = new int[buckets.length];
		for (int divider = 1; divider <max; divider *= 10) {
			for (int i = 0; i < array.length; i++) {
				int no = array[i] / divider % 10;
				buckets[no][bucketSize[no]++] = array[i];
			}
			int index = 0;
			for (int i = 0; i < buckets.length; i++) {
				for (int j = 0; j < bucketSize[i]; j++) {
					array[index++] = buckets[i][j];
				}
				bucketSize[i] = 0;
			}
		}
	}
}
