package com.KD.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import com.KD.printer.BinaryTreeInfo;
import com.KD.printer.BinaryTrees;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HashMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	private int size;
	private Node<K, V>[] table;
	private static final int DEFAULT_CAPACITY = 1 << 4;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	public HashMap() {
		table = new Node[DEFAULT_CAPACITY];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		if (size == 0) return;
		size = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	@Override
	public V put(K key, V value) {
		// check if set need enlarge
		resize();
		// get index by 'key'
		int index = index(key);
		// get node at table[index]
		Node<K, V> root = table[index];
		if (root == null) {
			root = createNode(key, value, null);
			table[index] = root;
			size++;
			fixAfterPut(root);
			return null;
		}
		// add new node to the red black tree
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		K k1 = key;
		int h1 = hash(k1);
		Node<K, V> result = null;
		// this parameter is a flag, if we have searched the whole tree at the root
		boolean searched = false;
		do {
			parent = node;
			K k2 = node.key;
			int h2 = node.hash;
			if (h1 > h2) {
				cmp = 1;
			} else if (h1 < h2) {
				cmp = -1;
			} else if (Objects.equals(k1, k2)) {
				cmp = 0;
			} else if (k1 != null && k2 != null 
					&& k1 instanceof Comparable
					&& k1.getClass() == k2.getClass()
					&& (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
			} else if (searched) { 
				// the tree has been searched
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			} else { 
				// searched == false; 
				// the tree has not been searched
				// search the tree first and decide which side by address
				if ((node.left != null && (result = node(node.left, k1)) != null)
						|| (node.right != null && (result = node(node.right, k1)) != null)) {
					// this 'key' is already exists
					node = result;
					cmp = 0;
				} else { 
					// this 'key' does not exist
					searched = true;
					cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
				}
			}
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else { 
				// compare outcome is equal
				V oldValue = node.value;
				node.key = key;
				node.value = value;
				node.hash = h1;
				return oldValue;
			}
		} while (node != null);
		// find which side of its parent to add
		Node<K, V> newNode = createNode(key, value, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;
		// after add, restore balance
		fixAfterPut(newNode);
		return null;
	}
	
	/**
	 * get node by key
	 */
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
	 * check if the 'value' is in the tree
	 * we need traverse each bucket in table and then traverse R-B-tree in this bucket
	 * check each node of the tree, if node.value equals value, return true
	 */
	@Override
	public boolean containsValue(V value) {
		if (size == 0) return false;
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			// use level order to traverse the tree
			if (table[i] == null) continue;	
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (Objects.equals(value, node.value)) return true;			
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (size == 0 || visitor == null) return;	
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) continue;	
			// use level order to traverse the tree
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (visitor.visit(node.key, node.value)) return;				
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
	}
	
	public void print() {
		if (size == 0) return;
		for (int i = 0; i < table.length; i++) {
			final Node<K, V> root = table[i];
			System.out.println("【index = " + i + "】");
			BinaryTrees.println(new BinaryTreeInfo() {
				@Override
				public Object string(Object node) {
					return node;
				}		
				@Override
				public Object root() {
					return root;
				}				
				@Override
				public Object right(Object node) {
					return ((Node<K, V>)node).right;
				}				
				@Override
				public Object left(Object node) {
					return ((Node<K, V>)node).left;
				}
			});
			System.out.println("---------------------------------------------------");
		}
	}
	
	protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
		return new Node<>(key, value, parent);
	}
	
	
	
	/**
	 * check if we need enlarge the table
	 */
	private void resize() {
		// load factor <= 0.75
		if (size / table.length <= DEFAULT_LOAD_FACTOR) return;
		// enlarge
		Node<K, V>[] oldTable = table;
		table = new Node[oldTable.length << 1];
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] == null) continue;			
			queue.offer(oldTable[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}				
				// move node to new table
				// this must be put at last
				// in this function we clear node`s left, right and parent
				moveNode(node);
			}
		}
	}
	
	private void moveNode(Node<K, V> newNode) {
		// reset / clear the node
		newNode.parent = null;
		newNode.left = null;
		newNode.right = null;
		newNode.color = RED;
		// get node`s new index
		int index = index(newNode);
		// get node at table[index]
		Node<K, V> root = table[index];
		if (root == null) {
			root = newNode;
			table[index] = root;
			fixAfterPut(root);
			return;
		}
		// root is not null
		// need add new node to the R-B-tree
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		K k1 = newNode.key;
		int h1 = newNode.hash;
		do {
			parent = node;
			K k2 = node.key;
			int h2 = node.hash;
			if (h1 > h2) {
				cmp = 1;
			} else if (h1 < h2) {
				cmp = -1;
			} else if (k1 != null && k2 != null 
					&& k1 instanceof Comparable
					&& k1.getClass() == k2.getClass()
					&& (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
			} else {
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			}			
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			}
		} while (node != null);
		// check which side of its parent to insert
		newNode.parent = parent;
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		// after add, restore balance
		fixAfterPut(newNode);
	}
	
	protected V remove(Node<K, V> node) {
		if (node == null) return null;	
		Node<K, V> willNode = node;		
		size--;	
		V oldValue = node.value;		
		if (node.hasTwoChildren()) { 
			// degree of the node is 2
			// find its successor
			Node<K, V> s = successor(node);
			//  use successor to replace node
			node.key = s.key;
			node.value = s.value;
			node.hash = s.hash;
			// remove successor
			node = s;
		}
		// the degree of the remove node is 1 / 0
		Node<K, V> replacement = node.left != null ? node.left : node.right;
		int index = index(node);
		if (replacement != null) { 
			// the degree of the remove node is 1 
			// change parent
			replacement.parent = node.parent;
			// change parent`s left and right
			if (node.parent == null) { 
				// the degree of the remove node is 1
				// the node is root
				table[index] = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else { 
				// node == node.parent.right
				node.parent.right = replacement;
			}
			// after remove, restore balance
			fixAfterRemove(replacement);
		} else if (node.parent == null) { 
			// node is leaf
			// node is root
			table[index] = null;
		} else { 
			// node is just leaf
			if (node == node.parent.left) {
				node.parent.left = null;
			} else { 
				// node == node.parent.right
				node.parent.right = null;
			}
			// after remove, restore balance
			fixAfterRemove(node);
		}
		// an interface for child class
		afterRemove(willNode, node);
		return oldValue;
	}
	
	protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) { }
	
	private Node<K, V> successor(Node<K, V> node) {
		if (node == null) return null;
		// predecessor is in the left_child.right.right.right...
		Node<K, V> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		// predecessor is in parent/parent.parent.parent.parent... 
		// node must in its right subtree
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}
		return node.parent;
	}
	
	private Node<K, V> node(K key) {
		Node<K, V> root = table[index(key)];
		return root == null ? null : node(root, key);
	}
	
	private Node<K, V> node(Node<K, V> node, K k1) {
		int h1 = hash(k1);
		// restore check result
		Node<K, V> result = null;
		int cmp = 0;
		while (node != null) {
			K k2 = node.key;
			int h2 = node.hash;
			// compare hash code first
			if (h1 > h2) {
				node = node.right;
			} else if (h1 < h2) {
				node = node.left;
			} else if (Objects.equals(k1, k2)) {
				// compare if k1 equals k2
				return node;
			} else if (k1 != null && k2 != null 
					&& k1 instanceof Comparable
					&& k1.getClass() == k2.getClass()
					&& (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
				node = cmp > 0 ? node.right : node.left;
			} else if (node.right != null && (result = node(node.right, k1)) != null) { 
				return result;
			} else { 
				// key does not exit in the right subtree
				node = node.left;
			}
		}
		return null;
	}
	
	/**
	 * use 'key' calculate the index in table
	 */
	private int index(K key) {
		return hash(key) & (table.length - 1);
	}
	
	private int hash(K key) {
		if (key == null) return 0;
		int hash = key.hashCode();
		return hash ^ (hash >>> 16);
	}
	
	private int index(Node<K, V> node) {
		return node.hash & (table.length - 1);
	}

	private void fixAfterRemove(Node<K, V> node) {
		// if remove node is Red
		// or the replacement node is Red
		if (isRed(node)) {
			black(node);
			return;
		}
		Node<K, V> parent = node.parent;
		// remove root, just return
		if (parent == null) return;
		// remove leaf node and color is black
		// this will cause underflow
		// charge the remove node is left child or right child
		boolean left = parent.left == null || node.isLeftChild();
		// get sibling node
		Node<K, V> sibling = left ? parent.right : parent.left;
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
					fixAfterRemove(parent);
				}
			} else { 
				// sibling has at least 1 red child
				// can borrow one node from sibling
				// if sibling is black, rotate first
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
			// remove node is on the right
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
					fixAfterRemove(parent);
				}
			} else {
				// sibling has at least 1 red child
				// can borrow one node from sibling
				// if sibling is black, rotate first
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

	private void fixAfterPut(Node<K, V> node) {
		Node<K, V> parent = node.parent;
		// if add root node
		// or overflow to root
		if (parent == null) {
			black(node);
			return;
		}
		// if parent is black just return
		if (isBlack(parent)) return;
		// get uncle node
		Node<K, V> uncle = parent.sibling();
		// get grand node
		// and set grand color as red
		Node<K, V> grand = red(parent.parent);
		if (isRed(uncle)) { 
			// uncle is red means overflow
			black(parent);
			black(uncle);
			// take grand as a new remove node
			fixAfterPut(grand);
			return;
		}
		// uncle is not red 
		// need do rotation
		if (parent.isLeftChild()) { 		// L
			if (node.isLeftChild()) { 		// LL
				black(parent);
			} else { 						// LR
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else { 							// R
			if (node.isLeftChild()) { 		// RL
				black(node);
				rotateRight(parent);
			} else { 						// RR
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
		// change parent as the root of the subtree
		parent.parent = grand.parent;
		if (grand.isLeftChild()) {
			grand.parent.left = parent;
		} else if (grand.isRightChild()) {
			grand.parent.right = parent;
		} else { 
			// grand is root
			table[index(grand)] = parent;
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
	
	protected static class Node<K, V> {
		int hash; 	// the hash code for 'key'
		K key;
		V value;
		boolean color = RED;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;
		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			int hash = key == null ? 0 : key.hashCode();
			this.hash = hash ^ (hash >>> 16);
			this.value = value;
			this.parent = parent;
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
		
		@Override
		public String toString() {
			return "Node_" + key + "_" + value;
		}
	}
}
