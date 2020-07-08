package com.KD;

import java.util.Arrays;

import com.KD.CompareSort.BubbleSort1;
import com.KD.CompareSort.BubbleSort2;
import com.KD.CompareSort.BubbleSort3;
import com.KD.CompareSort.HeapSort;
import com.KD.CompareSort.InsertionSort1;
import com.KD.CompareSort.InsertionSort2;
import com.KD.CompareSort.InsertionSort3;
import com.KD.CompareSort.MergeSort;
import com.KD.CompareSort.QuickSort;
import com.KD.CompareSort.SelectionSort;
import com.KD.CompareSort.ShellSort;
import com.KD.NoCompareSort.CountingSort;
import com.KD.NoCompareSort.RadixSort;
import com.KD.NoCompareSort.RadixSort2;
import com.KD.tools.Asserts;
import com.KD.tools.Integers;

@SuppressWarnings({"rawtypes", "unchecked","unused"})
public class Main {

	public static void main(String[] args) {
		Integer[] array = {7, 3, 5, 8, 6, 7, 4, 5};
		Integer[] array1 = new Integers().random(10000, 1, 10000);
		Integer[] array2 = new Integers().tailAscOrder(0, 10000, 1000);
		
		testSorts(array, 
				new RadixSort(),
				new RadixSort2(),
				new CountingSort(),
//				new SelectionSort(), 
//				new HeapSort(), 
//				new MergeSort(),
//				new QuickSort(),
//				new ShellSort(),
//				new BubbleSort1(),
//				new BubbleSort2(),
//				new BubbleSort3(),
//				new InsertionSort1(),
//				new InsertionSort2(),
				new InsertionSort3()
				);
	}

	static void testSorts(Integer[] array, Sort... sorts) {
		for (Sort sort : sorts) {
			Integer[] newArray = Integers.copy(array);
			sort.sort(newArray);
			Asserts.test(Integers.isAscOrder(newArray));
		}
		Arrays.sort(sorts);
		
		for (Sort sort : sorts) {
			System.out.println(sort);
		}
	}
}
