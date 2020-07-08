package com.KD.CompareSort;

import com.KD.Sort;

@SuppressWarnings("unchecked")
public class MergeSort<T extends Comparable<T>> extends Sort<T> {
	private T[] leftArray;

	@Override
	protected void sort() {
		leftArray = (T[]) new Comparable[array.length >> 1];
		sort(0, array.length);
	}
	
	/**
	 * do merge sort for index = [begin, end) element
	 */
	private void sort(int begin, int end) {
		if (end - begin < 2) return;	
		int mid = (begin + end) >> 1;
		sort(begin, mid);
		sort(mid, end);
		merge(begin, mid, end);
	}
	
	/**
	 * merge index = [begin, mid) and [mid, end)
	 */
	private void merge(int begin, int mid, int end) {
		int li = 0, le = mid - begin;
		int ri = mid, re = end;
		int ai = begin;
		// copy left array
		for (int i = li; i < le; i++) {
			leftArray[i] = array[begin + i];
		}
		// if left does not stop
		// when left stop, break
		while (li < le) { 
			// if right stops, just do left
			if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
				array[ai++] = array[ri++];
			} else {
				array[ai++] = leftArray[li++];
			}
		}
	}
}
