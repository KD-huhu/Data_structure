package com.KD.CompareSort;

import com.KD.Sort;

public class HeapSort<T extends Comparable<T>> extends Sort<T> {
	private int heapSize;

	@Override
	protected void sort() {
		// build heap in place
		heapSize = array.length;
		for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
			siftDown(i);
		}
		while (heapSize > 1) {
			// exchange top element and tail element
			swap(0, --heapSize);
			// do sift down to index = 0 element
			siftDown(0);
		}
	}
	
	private void siftDown(int index) {
		T element = array[index];
		int half = heapSize >> 1;
		while (index < half) { 
			// index must be non-leaf node
			// compare with left child first
			int childIndex = (index << 1) + 1;
			T child = array[childIndex];
			int rightIndex = childIndex + 1;
			// right child is larger than left
			if (rightIndex < heapSize && 
					cmp(array[rightIndex], child) > 0) { 
				child = array[childIndex = rightIndex];
			}
			// parent >= child
			if (cmp(element, child) >= 0) break;
			array[index] = child;
			index = childIndex;
		}
		array[index] = element;
	}
}