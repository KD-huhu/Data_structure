package com.KD;

import com.KD.file.Files;
import com.KD.printer.*;
import com.KD.tree.AVLTree;
import com.KD.tree.BST;
import com.KD.tree.BinaryTree;
import com.KD.tree.BinaryTree.Visitor;

@SuppressWarnings("unused")
public class Main {

	static void test1() {
		Integer data[] = new Integer[] {
			67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39					
		};
		AVLTree<Integer> avl = new AVLTree<>();
		for (int i = 0; i < data.length; i++) {
			avl.add(data[i]);
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(avl);
			System.out.println("-------------------------------------------");
		}
	}
	
	public static void main(String[] args) {
		test1();
	}
}