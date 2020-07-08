package com.KD.tree;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class BST<E> extends BinaryTree<E> {
	private Comparator<E> comparator;
	
	public void add(E element) {
		elementNotNullCheck(element);
		// if add first item
		if (root == null) {
			root = createNode(element, null);
			size++;
			afterAdd(root);
			return;
		}		
		// if not add first item
		// find parent node
		Node<E> parent = root;
		Node<E> node = root;
		int cmp = 0;
		do {
			cmp = compare(element, node.element);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else { // element == node.element
				node.element = element;
				return;
			}
		} while (node != null);
		// find which side of parent to add
		Node<E> newNode = createNode(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;
		// for child to realize keeping balance
		afterAdd(newNode);
	}
	
	/**
	 * this is an interface for child
	 * child can override this function to keep balance
	 * @param node
	 */
	protected void afterAdd(Node<E> node) { }

	public boolean contains(E element) {
		return node(element) != null;
	}
	
	/**
	 * public remove: give an element and remove it
	 * use private remove
	 * private remove is remove a node
	 * @param element
	 */
	public void remove(E element) {
		remove(node(element));
	}
	
	private void remove(Node<E> node) {
		if (node == null) return;		
		size--;		
		if (node.hasTwoChildren()) { // the degree of the node is 2
			// find successor
			Node<E> s = successor(node);
			// use the successor to replace it 
			node.element = s.element;
			// remove the successor
			// in this way, the next step is remove the node which degree must be 1 / 0
			node = s;
		}		
		// the degree of the node is 1 / 0
		Node<E> replacement = node.left != null ? node.left : node.right;
		
		if (replacement != null) { // degree == 1
			// change parent
			replacement.parent = node.parent;
			// change parent`s left / right
			if (node.parent == null) { // degree == 1 and node == root
				root = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else { // node == node.parent.right
				node.parent.right = replacement;
			}
			// for child to realize keeping balance
			afterRemove(replacement);
		} else if (node.parent == null) { // node is leaf and node == root
			root = null;
			afterRemove(node);
		} else { // node is just a leaf
			if (node == node.parent.left) {
				node.parent.left = null;
			} else { // node == node.parent.right
				node.parent.right = null;
			}
			afterRemove(node);
		}
	}
	
	/**
	 * this is an interface for child
	 * child can override this function to keep balance
	 * @param node
	 */
	protected void afterRemove(Node<E> node) { }

	
	/**
	 * find node by element
	 * @param element
	 * @return node(node.element == element)
	 */
	private Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			int cmp = compare(element, node.element);
			if (cmp == 0) return node;
			if (cmp > 0) {
				node = node.right;
			} else { // compare outcome < 0
				node = node.left;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(root, sb, "");
		return sb.toString();
	}
	
	private void toString(Node<E> node, StringBuilder sb, String prefix) {
		if (node == null) return;
		toString(node.left, sb, prefix + "L---");
		sb.append(prefix).append(node.element).append("\n");
		toString(node.right, sb, prefix + "R---");
	}
	
	/**
	 * constructor
	 */
	public BST() {
		this(null);
	}
	
	public BST(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * @return 0:e1==e2, >0:e1>e2, <0:e1<e2
	 */
	private int compare(E e1, E e2) {
		// if comparator is not null
		// use the comparator
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		// if comparator is null
		// use comparable attribute
		return ((Comparable<E>)e1).compareTo(e2);
	}
	
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
