package com.KD.Set;

import java.util.Comparator;

import com.KD.RBTree.BinaryTree;
import com.KD.RBTree.RBTree;

public class RBTreeSet<E> implements Set<E> {
	private RBTree<E> tree;
	
	public RBTreeSet() {
		this(null);
	}
	
	public RBTreeSet(Comparator<E> comparator) {
		tree = new RBTree<>(comparator);
	}
	
	@Override
	public int size() {
		return tree.size();
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	@Override
	public void clear() {
		tree.clear();
	}

	@Override
	public boolean contains(E element) {
		return tree.contains(element);
	}

	@Override
	public void add(E element) {
		// we use Red-Black-Tree 
		// it has already request element not repeat
		// we can add element directly
		tree.add(element);
	}

	@Override
	public void remove(E element) {
		tree.remove(element);
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		// we use Red-Black-Tree in_order traverse
		// but the parameter in in_order traverse is a Visitor class
		// we need new a Visitor class and use visitor parameter
		tree.inorder(new BinaryTree.Visitor<E>() {
			public boolean visit(E element) {
				return visitor.visit(element);
			}
		});
	}

}
