package com.KD.tree;

import java.util.Comparator;

public class BBST<E> extends BST<E> {
	
	/**
	 * constructor
	 */
	public BBST() {
		this(null);
	}
	
	public BBST(Comparator<E> comparator) {
		super(comparator);
	}
	
	protected void rotateLeft(Node<E> grand) {
		Node<E> parent = grand.right;
		Node<E> child = parent.left;
		// change child
		grand.right = child;
		parent.left = grand;
		// change parent and update height
		afterRotate(grand, parent, child);
	}

	protected void rotateRight(Node<E> grand) {
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		// change child
		grand.left = child;
		parent.right = grand;
		// change parent and update height
		afterRotate(grand, parent, child);
	}
	
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		// change root of the subtree
		parent.parent = grand.parent;
		if (grand.isLeftChild()) {			// grand is the left node of its parent
			grand.parent.left = parent;
		} else if (grand.isRightChild()) {	// grand is the right node of its parent
			grand.parent.right = parent;
		} else {							// grand is root
			root = parent;
		}
		// update the child`s parent
		if (child != null) {
			child.parent = grand;
		}
		// update the grand`s parent
		grand.parent = parent;
	}
}
