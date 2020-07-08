package com.KD.CompareSort;

import com.KD.Sort;

public class BubbleSort2<T extends Comparable<T>> extends Sort<T> {

	@Override
	protected void sort() {
		boolean sorted = true;
		for (int end = array.length - 1; end > 0; end--) {
			for (int begin = 1; begin <= end; begin++) {
				if (cmp(begin, begin - 1) < 0) {
					swap(begin, begin - 1);
					sorted = false;
				}
			}
			if (sorted) {
				break;
			}
		}
	}

}
