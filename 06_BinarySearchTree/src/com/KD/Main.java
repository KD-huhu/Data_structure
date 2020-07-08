package com.KD;

import com.KD.file.Files;
import com.KD.printer.*;
import com.KD.BinarySearchTree.*;

public class Main {

	static void test1() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
		BinaryTrees.println(bst);
	}
	
	/**
	 * write to file
	 */
	static void test2() {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < 40; i++) {
			bst.add((int)(Math.random() * 100));
		}
		
		String str = BinaryTrees.printString(bst);
		str += "\n";
		Files.writeToFile("F:/1.txt", str, true);
		
		 BinaryTrees.println(bst);
	}
	
	static void test3() {
		BinaryTrees.println(new BinaryTreeInfo() {
			
			@Override
			public Object string(Object node) {
				return node.toString() + "_";
			}
			
			@Override
			public Object root() {
				return "A";
			}
			
			@Override
			public Object right(Object node) {
				if (node.equals("A")) return "C";
				if (node.equals("C")) return "E";
				return null;
			}
			
			@Override
			public Object left(Object node) {
				if (node.equals("A")) return "B";
				if (node.equals("C")) return "D";
				return null;
			}
		});
	}
	
	static void test4() {
		@SuppressWarnings("unused")
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5
		};
		
//		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//		for (int i = 0; i < data.length; i++) {
//			bst.add(data[i]);
//		}
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < 10; i++) {
			bst.add((int)(Math.random() * 100));
		}
		
		BinaryTrees.println(bst);
		System.out.println(bst.isComplete());
	}
	
	static void test5() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}	
		 System.out.println(bst.height());
	}
	
	static void test6() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
		BinaryTrees.println(bst);
		
		bst.remove(7);
		
		BinaryTrees.println(bst);
	}
	
	static void test7() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		BinaryTrees.println(bst);
		
		bst.preorder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 2 ? true : false;
			}
		});
		System.out.println();
		
		bst.inorder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 4 ? true : false;
			}
		});
		System.out.println();
		
		bst.postorder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 4 ? true : false;
			}
		});
		System.out.println();
		
		bst.levelOrder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 9 ? true : false;
			}
		});
		System.out.println();
	}
	
	public static void main(String[] args) {
//		test1();
//		test2();
//		test3();
//		test4();
//		test5();
//		test6();
//		test7();
	}
}
