package com.KD.tree;

import java.util.LinkedList;
import java.util.Queue;

import com.KD.printer.BinaryTreeInfo;

@SuppressWarnings("unchecked")
public class BinaryTree<E> implements BinaryTreeInfo {
	protected int size;
	protected Node<E> root;
	
	protected static class Node<E> {
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
	
	/**
	 * visitor.stop is a flag about traverse
	 * visit is a method to help print outcome
	 * and can help implement just print part of the tree
	 * and can save stack by stop recurrence
	 * @return true:stop traverse
	 */
	public static abstract class Visitor<E> {
		boolean stop;
		abstract boolean visit(E element);
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
	
	protected Node<E> predecessor(Node<E> node) {
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
	
	@SuppressWarnings("unused")
	protected Node<E> successor(Node<E> node) {
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
