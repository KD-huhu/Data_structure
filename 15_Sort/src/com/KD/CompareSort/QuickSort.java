package com.KD.CompareSort;

import com.KD.Sort;

public class QuickSort<T extends Comparable<T>> extends Sort<T> {

	@Override
	protected void sort() {
		sort(0, array.length);
	}

	/**
	 * do quick sort for index =  [begin, end) element
	 * @param begin
	 * @param end
	 */
	private void sort(int begin, int end) { 
		if (end - begin < 2) return;		
		// get pivot index
		int mid = pivotIndex(begin, end);
		// do quick sort for sub-array
		sort(begin, mid); 
		sort(mid + 1, end); 
	} 
	
	/**
	 * find pivot index
	 * @return pivot index
	 */
	private int pivotIndex(int begin, int end) {
		// random chose an element to do exchange with first
		swap(begin, begin + (int)(Math.random() * (end - begin)));
		// copy element at begin
		T pivot = array[begin];
		// end is the last element
		end--;
		while (begin < end) {
			while (begin < end) {
				if (cmp(pivot, array[end]) < 0) { // 右边元素 > 轴点元素
					end--;
				} else { 
					// right element <= pivot element
					array[begin++] = array[end];
					break;
				}
			}
			while (begin < end) {
				if (cmp(pivot, array[begin]) > 0) { // 左边元素 < 轴点元素
					begin++;
				} else { 
					// left element >= pivot element
					array[end--] = array[begin];
					break;
				}
			}
		}
		// put pivot element at pivot index
		array[begin] = pivot;
		// return pivot index
		return begin;
	}
}
