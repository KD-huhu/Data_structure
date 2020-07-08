package com.KD.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings({"unchecked", "unused"})
public class TreeMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	private int size;
	private Node<K, V> root;
	private Comparator<K> comparator;
	
	public TreeMap() {
		this(null);
	}
	
	public TreeMap(Comparator<K> comparator) {
		this.comparator = comparator;
	}
	
	private static class Node<K, V> {
		K key;
		V value;
		boolean color = RED;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;
		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
		
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}
		
		public boolean isRightChild() {
			return parent != null && this == parent.right;
		}
		
		public Node<K, V> sibling() {
			if (isLeftChild()) {
				return parent.right;
			}			
			if (isRightChild()) {
				return parent.left;
			}			
			return null;
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
	 * add new node 
	 */
	@Override
	public V put(K key, V value) {
		keyNotNullCheck(key);
		// if it is the fist node
		if (root == null) {
			root = new Node<>(key, value, null);
			size++;
			// after add check balance
			afterPut(root);
			return null;
		}
		// if is not the first node
		// find parent
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		do {
			cmp = compare(key, node.key);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else { 			// if key is equal, overwrite
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			}
		} while (node != null);
		// find the add side
		Node<K, V> newNode = new Node<>(key, value, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;
		// after add check balance
		afterPut(newNode);
		return null;
	}

	@Override
	public V get(K key) {
		Node<K, V> node = node(key);
		return node != null ? node.value : null;
	}

	@Override
	public V remove(K key) {
		return remove(node(key));
	}

	@Override
	public boolean containsKey(K key) {
		return node(key) != null;
	}
	
	/**
	 * check if the value in the map value
	 */
	@Override
	public boolean containsValue(V value) {
		if (root == null) return false;
		// use level order traversal
		// compare every value 
		Queue<Node<K, V>> queue = new LinkedList<>();
		queue.offer(root);		
		while (!queue.isEmpty()) {
			Node<K, V> node = queue.poll();
			if (valEquals(value, node.value)) return true;			
			if (node.left != null) {
				queue.offer(node.left);
			}			
			if (node.right != null) {
				queue.offer(node.right);
			}
		}		
		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (visitor == null) return;
		traversal(root, visitor);
	}
	
	private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
		if (node == null || visitor.stop) return;
		// use in_order traversal
		traversal(node.left, visitor);
		if (visitor.stop) return;
		visitor.visit(node.key, node.value);
		traversal(node.right, visitor);
	}
	
	private boolean valEquals(V v1, V v2) {
		return v1 == null ? v2 == null : v1.equals(v2);
	}
	
	private V remove(Node<K, V> node) {
		if (node == null) return null;
		size--;		
		V oldValue = node.value;		
		// the degree of the node is 2
		if (node.hasTwoChildren()) { 
			// find successor
			Node<K, V> s = successor(node);
			// use the successor`s key and value cover the remove node
			node.key = s.key;
			node.value = s.value;
			// remove the successor
			node = s;
		}
		// the degree of the node is 1 / 0
		// get replacement ode
		Node<K, V> replacement = node.left != null ? node.left : node.right;
		// the degree of the node is 1
		if (replacement != null) { 
			// change parent
			replacement.parent = node.parent;
			// change parent`s left and right
			// the degree of the node is 1
			// the node is root
			if (node.parent == null) { 
				root = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else { 
				// node == node.parent.right
				node.parent.right = replacement;
			}
			// after remove, restore balance
			// pay attention the node in parameter is the replaced node
			afterRemove(replacement);
		} else if (node.parent == null) { 
			// node is leaf and root
			root = null;
		} else { 
			// node is leaf, not root
			if (node == node.parent.left) {
				node.parent.left = null;
			} else { 
				// node == node.parent.right
				node.parent.right = null;
			}
			// after remove, restore balance
			afterRemove(node);
		}		
		return oldValue;
	}
	
	private void afterRemove(Node<K, V> node) {
		// if the color of remove node is red(has no replaced node)
		// or the color of replaced node is red
		// color the replaced node black
		if (isRed(node)) {
			black(node);
			return;
		}
		Node<K, V> parent = node.parent;
		if (parent == null) return;	
		// remove node is black
		// underflow happened
		// charge the node is left child or right child
		boolean left = parent.left == null || node.isLeftChild();
		Node<K, V> sibling = left ? parent.right : parent.left;
		if (left) { 
			// remove node is on the left
			// sibling is on the right
			if (isRed(sibling)) { 
				// sibling is red
				black(sibling);
				red(parent);
				// parent do rotation 
				rotateLeft(parent);
				//  sibling`s child becomes new sibling
				sibling = parent.right;
			}		
			// sibling is black
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// sibling has no red child
				// parent need merge down
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else { 
				// sibling has at least 1 red child
				// borrow a child
				// if sibling`s left is black, rotate first
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}
				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);
			}
		} else { 
			// remove node is on the left
			// change all right--left; left--right
			if (isRed(sibling)) { 
				black(sibling);
				red(parent);
				rotateRight(parent);
				sibling = parent.left;
			}
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else { 
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left;
				}
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
		}
	}

	private Node<K, V> predecessor(Node<K, V> node) {
		if (node == null) return null;
		// predecessor is in the left child`s right child
		// left.right.right.right....
		Node<K, V> p = node.left;
		if (p != null) {
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		// find predecessor in parent and grand
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		// node.parent == null
		// or node == node.parent.right
		return node.parent;
	}
	
	private Node<K, V> successor(Node<K, V> node) {
		if (node == null) return null;
		// successor is in the right child`s left child
		// right.left.left.left....
		Node<K, V> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		// find successor in parent and grand
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}
		// node.parent == null
		// or node == node.parent.right
		return node.parent;
	}
	
	private Node<K, V> node(K key) {
		Node<K, V> node = root;
		while (node != null) {
			int cmp = compare(key, node.key);
			if (cmp == 0) return node;
			if (cmp > 0) {
				node = node.right;
			} else { 
				// compare outcome < 0
				node = node.left;
			}
		}
		return null;
	}
	
	private void afterPut(Node<K, V> node) {
		Node<K, V> parent = node.parent;
		// add root
		// or overflow to root
		if (parent == null) {
			black(node);
			return;
		}
		// parent is black, just return
		if (isBlack(parent)) return;
		// find uncle
		Node<K, V> uncle = parent.sibling();
		// find grand
		Node<K, V> grand = red(parent.parent);
		if (isRed(uncle)) { 
			// uncle is red, overflow
			black(parent);
			black(uncle);
			// mark uncle node as new add node
			afterPut(grand);
			return;
		}
		
		// 叔父节点不是红色
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				black(parent);
			} else { // LR
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else { // R
			if (node.isLeftChild()) { // RL
				black(node);
				rotateRight(parent);
			} else { // RR
				black(parent);
			}
			rotateLeft(grand);
		}
	}
	
	private void rotateLeft(Node<K, V> grand) {
		Node<K, V> parent = grand.right;
		Node<K, V> child = parent.left;
		grand.right = child;
		parent.left = grand;
		afterRotate(grand, parent, child);
	}
	
	private void rotateRight(Node<K, V> grand) {
		Node<K, V> parent = grand.left;
		Node<K, V> child = parent.right;
		grand.left = child;
		parent.right = grand;
		afterRotate(grand, parent, child);
	}
	
	private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
		// make parent as new root for the subtree
		parent.parent = grand.parent;
		if (grand.isLeftChild()) {
			grand.parent.left = parent;
		} else if (grand.isRightChild()) {
			grand.parent.right = parent;
		} else { 
			// grand is root
			root = parent;
		}
		// update child`s parent
		if (child != null) {
			child.parent = grand;
		}
		// update grand`s parent
		grand.parent = parent;
	}

	private Node<K, V> color(Node<K, V> node, boolean color) {
		if (node == null) return node;
		node.color = color;
		return node;
	}
	
	private Node<K, V> red(Node<K, V> node) {
		return color(node, RED);
	}
	
	private Node<K, V> black(Node<K, V> node) {
		return color(node, BLACK);
	}
	
	private boolean colorOf(Node<K, V> node) {
		return node == null ? BLACK : node.color;
	}
	
	private boolean isBlack(Node<K, V> node) {
		return colorOf(node) == BLACK;
	}
	
	private boolean isRed(Node<K, V> node) {
		return colorOf(node) == RED;
	}
	
	private int compare(K e1, K e2) {
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<K>)e1).compareTo(e2);
	}
	
	private void keyNotNullCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null");
		}
	}
}
