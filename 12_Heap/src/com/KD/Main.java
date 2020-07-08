package com.KD;

import java.util.Comparator;

import com.KD.heap.BinaryTreeHeap;
import com.KD.printer.BinaryTrees;

public class Main {

	static void test1() {
		BinaryTreeHeap<Integer> heap = new BinaryTreeHeap<>();
		heap.add(68);
		heap.add(72);
		heap.add(43);
		heap.add(50);
		heap.add(38);
		heap.add(10);
		heap.add(90);
		heap.add(65);
		BinaryTrees.println(heap);
		// heap.remove();
		// BinaryTrees.println(heap);
		
		System.out.println(heap.replace(70));
		BinaryTrees.println(heap);
	}
	
	static void test2() {
		Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
		BinaryTreeHeap<Integer> heap = new BinaryTreeHeap<>(data);
		BinaryTrees.println(heap);
		
		data[0] = 10;
		data[1] = 20;
		BinaryTrees.println(heap);
	}
	
	static void test3() {
		Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
		BinaryTreeHeap<Integer> heap = new BinaryTreeHeap<>(data, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
		BinaryTrees.println(heap);
	}
	
	static void test4() {
		// build a small top heap
		BinaryTreeHeap<Integer> heap = new BinaryTreeHeap<>(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
		// find top k
		int k = 3;
		Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93, 
				91, 19, 54, 47, 73, 62, 76, 63, 35, 18, 
				90, 6, 65, 49, 3, 26, 61, 21, 48};
		for (int i = 0; i < data.length; i++) {
			if (heap.size() < k) { 
				// add former k elements 
				heap.add(data[i]); 
			} else if (data[i] > heap.get()) { 
				// if is k+1 element, and is bigger than the top element
				heap.replace(data[i]); 
			}
		}
		BinaryTrees.println(heap);
	}
	
	public static void main(String[] args) {
//		test1();
//		test2();
//		test3();
		test4();
	}

}
