package com.KD.NoCompareSort;

import java.util.LinkedList;
import java.util.List;

public class BucketsSort {

	public static void main(String[] args) {
		double[] array = {0.34, 0.47, 0.29, 0.84, 0.45, 0.38, 0.35, 0.76};
		List<Double>[] buckets = new List[array.length];
		for (int i = 0; i < array.length; i++) {
			int bucketIndex = (int)(array[i]*array.length);
			List<Double> bucket = buckets[bucketIndex];
			if (bucket == null) {
				bucket = new LinkedList<>();
				buckets[bucketIndex] = bucket;
			}
			bucket.add(array[i]);
		}
		int index = 0;
		
		for (int n = 0; n < buckets.length; n++) {
			if (buckets[n] == null) {
				continue;
			}
			buckets[n].sort(null);
			for (double d : buckets[n]) {
				array[index++] = d;
			}
		}
		
		for (int m = 0; m < array.length; m++) {
			System.out.println(array[m]);
		}
	}
}
