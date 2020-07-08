package com.KD;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.KD.printer.BinaryTreeInfo;

@SuppressWarnings("unchecked")
public class BinarySearchTree<E> implements BinaryTreeInfo {
	private int size;
	private Node<E> root;
	private Comparator<E> comparator;
	
	private static class Node<E> {
		E element;
		Node<E> left;
		Node<E> right;
		Node<E> parent;
		public Node(E element, Node<E> parent) {
			this.element = element;
			this.parent = parent; 
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		root = null;
		size = 0;
	}
	
	public void add(E element) {
		elementNotNullCheck(element);
		// if add first item
		if (root == null) {
			root = new Node<>(element, null);
			size++;
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
		Node<E> newNode = new Node<>(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;
	}

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
		} else if (node.parent == null) { // node is leaf and node == root
			root = null;
		} else { // node is just a leaf
			if (node == node.parent.left) {
				node.parent.left = null;
			} else { // node == node.parent.right
				node.parent.right = null;
			}
		}
	}
	
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
	
	/**
	 * visitor.stop is a flag about traverse
	 * visit is a method to help print outcome
	 * and can help implement just print part of the tree
	 * and can save stack by stop recurrence
	 * @return true:stop traverse
	 */
	public static abstract class Visitor<E> {
		boolean stop;
		public abstract boolean visit(E element);
	}
	
	/**
	 * pre_order traverse
	 * @param visitor
	 */
	public void preorder(Visitor<E> visitor) {
		if (visitor == null) return;
		preorder(root, visitor);
	}
	
	private void preorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) return;		
		visitor.stop = visitor.visit(node.element);
		preorder(node.left, visitor);
		preorder(node.right, visitor);
	}
	
	/**
	 * in_order traverse
	 * @param visitor
	 */
	public void inorder(Visitor<E> visitor) {
		if (visitor == null) return;
		inorder(root, visitor);
	}
	
	private void inorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) return;		
		inorder(node.left, visitor);
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
		inorder(node.right, visitor);
	}
	
	/**
	 * post_order traverse
	 * @param visitor
	 */
	public void postorder(Visitor<E> visitor) {
		if (visitor == null) return;
		postorder(root, visitor);
	}
	
	private void postorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) return;
		
		postorder(node.left, visitor);
		postorder(node.right, visitor);
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
	}
	
	/**
	 * levelOrder traverse
	 * levelOrder is different, it uses iteration
	 * @param visitor
	 */
	public void levelOrder(Visitor<E> visitor) {
		if (root == null || visitor == null) return;
		// create a queue
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		// queue.poll and offer its left_child and right_child
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (visitor.visit(node.element)) return;		
			if (node.left != null) {
				queue.offer(node.left);
			}			
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
	}
	
	/**
	 * use levelOrder to implement if tree is complete tree
	 * @return
	 */
	public boolean isComplete() {
		if (root == null) return false;		
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		boolean leaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (leaf && !node.isLeaf()) return false;			
			if (node.left != null) {	// if left != null, offer left
				queue.offer(node.left);
			} else if (node.right != null) { // node.left == null && node.right != null
				return false;
			}			
			if (node.right != null) {
				queue.offer(node.right);
			} else { // node.right == null
				leaf = true;
			}
		}		
		return true;
	}
	
	public int height() {
		if (root == null) return 0;		
		// initial height = 0
		int height = 0;
		// save the number of nodes in each level
		// the first level size = 1, only a root
		int levelSize = 1;
		// use levelOrder
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);		
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			levelSize--;			
			if (node.left != null) {
				queue.offer(node.left);
			}			
			if (node.right != null) {
				queue.offer(node.right);
			}
			if (levelSize == 0) { // mean this level has finished, will start a new level
				levelSize = queue.size();
				height++;	// one level is finished, height++
			}
		}	
		return height;
	}
	
	public int height2() {
		return height(root);
	}
	
	/**
	 * get any node`s height
	 * @param node
	 * @return
	 */
	private int height(Node<E> node) {
		if (node == null) return 0;
		return 1 + Math.max(height(node.left), height(node.right));
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
	
	public BinarySearchTree() {
		this(null);
	}
	
	public BinarySearchTree(Comparator<E> comparator) {
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

	@SuppressWarnings("unused")
	private Node<E> predecessor(Node<E> node) {
		if (node == null) return null;		
		// predecessor is in the left_child.right.right.right...
		Node<E> p = node.left;
		if (p != null) {
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}		
		// predecessor is in parent/parent.parent.parent.parent... 
		// node must in its right subtree
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		// node.parent == null, node == root
		// or
		// node == node.parent.right
		return node.parent;
	}
	
	private Node<E> successor(Node<E> node) {
		if (node == null) return null;		
		// predecessor is in the right_child.left.left.left...
		Node<E> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}		
		// predecessor is in parent/parent.parent.parent.parent... 
		// node must in its left subtree
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}
		// node.parent == null, node == root
		// or
		// node == node.parent.left
		return node.parent;
	}
	

	/**
	 * about print binary search tree
	 */
	
	@Override
	public Object root() {
		return root;
	}

	@Override
	public Object left(Object node) {
		return ((Node<E>)node).left;
	}

	@Override
	public Object right(Object node) {
		return ((Node<E>)node).right;
	}

	@Override
	public Object string(Object node) {
		Node<E> myNode = (Node<E>)node;
		String parentString = "null";
		if (myNode.parent != null) {
			parentString = myNode.parent.element.toString();
		}
		return myNode.element + "_p(" + parentString + ")";
	}
}
