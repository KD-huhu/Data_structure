package com.KD.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	private static class RBNode<E> extends Node<E>{
		// set tolerant color as red
		boolean color = RED;
		public RBNode(E element, Node<E> parent) {
			super(element, parent);
		}
		
		@Override
		public String toString() {
			String str = "";
			if (color == RED) {
				str = "R_";
			}
			return str + element.toString();
		}
	}
	
	public RBTree() {
		this(null);
	}
	
	public RBTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	@Override
	protected void afterAdd(Node<E> node) {
		Node<E> parent = node.parent;
		// if add root node
		// or overflow to root
		if (parent == null) {
			black(node);
			return;
		}
		// if parent is black just return
		if (isBlack(parent)) {
			return;
		}
		// get uncle node
		Node<E> uncle = parent.sibling();
		// get grand node
		// and set grand color as red
		Node<E> grand = red(parent.parent);
		// uncle is red means overflow
		if (isRed(uncle)) {	
			black(parent);
			black(uncle);
			// take grand as a new remove node
			afterAdd(grand);
			return;
		}
		// uncle is not red 
		// need do rotation
		if (parent.isLeftChild()) {		// L
			if (node.isLeftChild()) {	// LL
				black(parent);
			} else {					// LR
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else {						// R
			if (node.isLeftChild()) {	// RL
				black(node);
				rotateRight(parent);
			} else {					// RR
				black(parent);
			}
			rotateLeft(grand);
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node) {
		// if remove node color is red
		// or the replace node is red
		if (isRed(node)) {
			black(node);
			return;
		}
		// get parent
		Node<E> parent = node.parent;
		// remove root, just return
		if (parent == null) {
			return;
		}
		// remove leaf node and color is black
		// this will cause underflow
		// charge the remove node is left child or right child
		boolean left = parent.left == null || node.isLeftChild();
		// get sibling node
		Node<E> sibling = left ? parent.right : parent.left;
		// if remove node is on the left
		if (left) {
			// if sibling node is red
			if (isRed(sibling)) {
				// do left rotation on parent
				// make sibling`s child as node`s new sibling
				black(sibling);
				red(parent);
				rotateLeft(parent);
				// change sibling
				sibling = parent.right;
			}
			// if sibling is black
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// there is no red node in sibling
				// parent need merge down with sibling
				// save parent color
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else {
				// sibling has at least 1 red child
				// can borrow one node from sibling
				// if sibling is black, rotate first
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}
				// sibling color will fellow parent`s color
				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);
			}
		} else {	// remove node is on the right
			// if sibling node is red
			if (isRed(sibling)) {
				// do right rotation on parent
				// make sibling`s child as node`s new sibling
				black(sibling);
				red(parent);
				rotateRight(parent);
				// change sibling
				sibling = parent.left;
			}
			// if sibling is black
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// there is no red node in sibling
				// parent need merge down with sibling
				// save parent color
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else {
				// sibling has at least 1 red child
				// can borrow one node from sibling
				// if sibling is black, rotate first
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left;
				}
				// sibling color will fellow parent`s color
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
		}
	}
	
	/**
	 * set node color as 'color'
	 * @param node
	 * @param color
	 * @return
	 */
	private Node<E> color(Node<E> node, boolean color){
		if (node == null) {
			return node;
		}
		((RBNode<E>)node).color = color;
		return node;
	}
	
	/**
	 * set node color as red
	 * @param node
	 * @return
	 */
	private Node<E> red(Node<E> node){
		return color(node, RED);
	}
	
	/**
	 * set node color as black
	 * @param node
	 * @return
	 */
	private Node<E> black(Node<E> node){
		return color(node, BLACK);
	}
	
	/**
	 * get the color of the node
	 * @param node
	 * @return
	 */
	private boolean colorOf(Node<E> node) {
		return node == null ? BLACK : ((RBNode<E>)node).color;
	}
	
	/**
	 * charge if the node color is red 
	 * @param node
	 * @return
	 */
	private boolean isRed(Node<E> node) {
		return colorOf(node) == RED;
	}
	
	/**
	 * charge if the node color is black 
	 * @param node
	 * @return
	 */
	private boolean isBlack(Node<E> node) {
		return colorOf(node) == BLACK;
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new RBNode<>(element, parent);
	}
}
