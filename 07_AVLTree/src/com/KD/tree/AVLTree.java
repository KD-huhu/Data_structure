package com.KD.tree;

import java.util.Comparator;

public class AVLTree<E> extends BST<E>{
	
	public AVLTree() {
		this(null);
	}
	
	public AVLTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	public static class AVLNode<E> extends Node<E> {
		int height = 1;

		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}
		
		/**
		 * calculate the node`s balance factor
		 * balance factor == left subtree height - right subtree height
		 * @return
		 */
		public int balanceFactor() {
			// get left subtree height
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			// get right subtree height
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			return leftHeight - rightHeight;
		}
		
		/**
		 * update the height of the node
		 */
		public void updataHeight() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			// height = max(leftHeight, rightHeight) + 1
			height = Math.max(leftHeight, rightHeight) + 1;
		}
		
		public Node<E> tallerChild(){
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			if (leftHeight > rightHeight) {
				return left;
			}
			if (leftHeight < rightHeight) {
				return right;
			}
			return isLeftChild() ? left : right;
		}
		
		@Override
		public String toString() {
			String parentString = "null";
			if (parent != null) {
				parentString = parent.element.toString();
			}
			return element + "_p(" + parentString + ")_h(" + height + ")";
		}
	}
	
	/**
	 * after add a new node
	 * keep balance
	 */
	@Override
	protected void afterAdd(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// if the tree is balanced
				// update the height of the node
				updateHeight(node);
			} else {
				// if the tree is not balanced
				// restore balance
				rebalance(node);
				// after add we only need once restore balance
				break;
			}
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// if the tree is balanced
				// update the height of the node
				updateHeight(node);
			} else {
				// if the tree is not balanced
				// restore balance
				rebalance(node); 
				// after remove and first restore node balance
				// it may affect all the parent and grandparent node
			}
		}
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<>(element,parent);
	}
	
	/**
	 * method 1 for each different conditions use different ways
	 * restore balance 
	 * @param grand the unbalanced node has minimum height
	 */
	private void rebalance(Node<E> grand) {
		Node<E> parent = ((AVLNode<E>)grand).tallerChild();
		Node<E> node = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) {		// L
			if (node.isLeftChild()) {	// LL condition
				rotateRight(grand);		// grand do right rotation
			} else {					// LR condition
				rotateLeft(parent);		// parent do left rotation
				rotateRight(grand);		// grand do right rotation
			}
		} else {						// R
			if (node.isLeftChild()) {	// RL
				rotateRight(parent); 	// parent do right rotation
				rotateLeft(grand); 		// grand do left rotation
			} else {					// RR
				rotateLeft(grand); 		// grand do left rotation
			}
		}
	}

	private void rotateLeft(Node<E> grand) {
		Node<E> parent = grand.right;
		Node<E> child = parent.left;
		// change child
		grand.right = child;
		parent.left = grand;
		// change parent and update height
		afterRotate(grand, parent, child);
	}

	private void rotateRight(Node<E> grand) {
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		// change child
		grand.left = child;
		parent.right = grand;
		// change parent and update height
		afterRotate(grand, parent, child);
	}
	
	private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
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
		// update height
		// must update grand height first
		// course grand now is parent`s child node
		updateHeight(grand);
		updateHeight(parent);
	}
	
	/**
	 * method 2 use one universal way to restore balance
	 * @param grand
	 */
	@SuppressWarnings("unused")
	private void rebalance1(Node<E> grand) {
		// get taller child
		Node<E> parent = ((AVLNode<E>)grand).tallerChild();
		Node<E> node = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) {		// L
			if (node.isLeftChild()) {	// LL
				rotate(grand, node, node.right, parent, parent.right, grand);
			} else {					// LR
				rotate(grand, parent, node.left, node, node.right, grand);
			}
		} else {						// R
			if (node.isLeftChild()) {	// RL
				rotate(grand, grand, node.left, node, node.right, parent);	
			} else {					// RR
				rotate(grand, grand, parent.left, parent, node.left, node);
			}
		}
	}

	private void rotate(Node<E> r, 		// root of the subtree
			Node<E> b, Node<E> c, 
			Node<E> d, 
			Node<E> e, Node<E> f) {
		// make d as the root of subtree
		d.parent = r.parent;
		if (r.isLeftChild()) {
			r.parent.left = d;
		} else if (r.isRightChild()) {
			r.parent.right = d;
		} else {		// r is root
			root = d;
		}
		
		// change b, c
		b.right = c;
		if (c != null) {
			c.parent = b;
		}
		updateHeight(b);
		
		// change e, f
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		updateHeight(f);
		
		// change d, b, f
		d.left = b;
		d.right = f;
		b.parent = d;
		f.parent = d;
		updateHeight(d);
	}

	private void updateHeight(Node<E> node) {
		((AVLNode<E>)node).updataHeight();
	}

	/**
	 * if the AVL node is balanced
	 * the absolute value of the node balance factor is <= 1
	 * @param node
	 * @return
	 */
	private boolean isBalanced(Node<E> node) {
		return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
	}
}
