package com.KD;

import java.util.HashMap;

public class Trie<V> {
	private int size;
	private Node<V> root;
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		size = 0;
		root = null;
	}

	public V get(String key) {
		Node<V> node = node(key);
		return node != null && node.word ? node.value : null;
	}

	public boolean contains(String key) {
		Node<V> node = node(key);
		return node != null && node.word;
	}

	public V add(String key, V value) {
		keyCheck(key);
		// create new node
		if (root == null) {
			root = new Node<>(null);
		}
		Node<V> node = root;
		int len = key.length();
		for (int i = 0; i < len; i++) {
			char c = key.charAt(i); 
			boolean emptyChildren = node.children == null;
			Node<V> childNode = emptyChildren ? null : node.children.get(c);
			if (childNode == null) {
				childNode = new Node<>(node);
				childNode.character = c;
				node.children = emptyChildren ? new HashMap<>() : node.children;
				// add new child
				node.children.put(c, childNode);
			}
			node = childNode;
		}
		if (node.word) { 
			// this word is already exist
			V oldValue = node.value;
			node.value = value;
			return oldValue;
		}
		// add new word
		node.word = true;
		node.value = value;
		size++;
		return null;
	}

	public V remove(String key) {
		// find last node of the word
		Node<V> node = node(key);
		// if this node is not an end
		// do nothing
		if (node == null || !node.word) return null;
		size--;
		V oldValue = node.value;
		// if node has other child
		if (node.children != null && !node.children.isEmpty()) {
			node.word = false;
			node.value = null;
			return oldValue;
		}
		// if node has no child
		Node<V> parent = null;
		while ((parent = node.parent) != null) {
			parent.children.remove(node.character);
			if (parent.word || !parent.children.isEmpty()) break;
			node = parent;
		}
		return oldValue;
	}

	public boolean startsWith(String prefix) {
		return node(prefix) != null;
	}
	
	/**
	 * get a node by key
	 * @param key
	 * @return
	 */
	private Node<V> node(String key) {
		keyCheck(key);
		Node<V> node = root;
		int len = key.length();
		for (int i = 0; i < len; i++) {
			if (node == null || node.children == null || node.children.isEmpty()) return null;
			char c = key.charAt(i); 
			node = node.children.get(c);
		}
		return node;
	}
	
	private void keyCheck(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("key must not be empty");
		}
	}
	
	private static class Node<V> {
		Node<V> parent;
		HashMap<Character, Node<V>> children;
		Character character;
		V value;
		boolean word;  	// word is a flag for check if it is an end for a word
		public Node(Node<V> parent) {
			this.parent = parent;
		}
	}
}
