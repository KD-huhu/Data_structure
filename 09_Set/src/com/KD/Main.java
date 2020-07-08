package com.KD;

import com.KD.Set.ListSet;
import com.KD.Set.RBTreeSet;
import com.KD.Set.Set;
import com.KD.Set.Set.Visitor;

public class Main {

	static void test1() {
		Set<Integer> listSet = new ListSet<>();
		// add elements
		listSet.add(10);
		listSet.add(11);
		listSet.add(11);
		listSet.add(12);
		listSet.add(10);
		
		listSet.traversal(new Visitor<Integer>() {			
			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});
	}
	
	static void test2() {
		Set<Integer> treeSet = new RBTreeSet<>();
		// add elements
		treeSet.add(10);
		treeSet.add(11);
		treeSet.add(11);
		treeSet.add(12);
		treeSet.add(10);
		
		treeSet.traversal(new Visitor<Integer>() {			
			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});
	}
	
	public static void main(String[] args) {
		test2();
	}

}
