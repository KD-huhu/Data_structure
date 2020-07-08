package com.KD;

import com.KD.file.Files;
import com.KD.printer.*;
import com.KD.tree.BST;
import com.KD.tree.BinaryTree;
import com.KD.tree.BinaryTree.Visitor;
import com.KD.tree.RBTree;

@SuppressWarnings("unused")
public class Main {

	static void test1() {
		Integer data[] = new Integer[] {
				55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
		};
		RBTree<Integer> rb = new RBTree<>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(rb);
			System.out.println("---------------------------------------");
		}
	}
	
	static void test2() {
		Integer data[] = new Integer[] {
				55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
		};
		
		RBTree<Integer> rb = new RBTree<>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
		}

		BinaryTrees.println(rb);

		for (int i = 0; i < data.length; i++) {
			rb.remove(data[i]);
			System.out.println("---------------------------------------");
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(rb);
		}
	}
	
	public static void main(String[] args) {
		test2();
	}
}