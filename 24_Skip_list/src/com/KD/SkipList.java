package com.KD;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class SkipList<K, V> {
	private static final int MAX_LEVEL = 32;
	private static final double P = 0.25;
	private int size;
	private Comparator<K> comparator;
	// the real level
	private int level;
	// first node does not store any 'key' and 'value'
	private Node<K, V> first;
	
	public SkipList(Comparator<K> comparator) {
		this.comparator = comparator;
		first = new Node<>(null, null, MAX_LEVEL);
	}
	
	public SkipList() {
		this(null);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public V get(K key) {
		keyCheck(key);
		Node<K, V> node = first;
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null 
					&& (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			// node.nexts[i].key >= key
			if (cmp == 0) return node.nexts[i].value;
		}
		return null;
	}
	
	public V put(K key, V value) {
		keyCheck(key);
		Node<K, V> node = first;
		Node<K, V>[] prevs = new Node[level];
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null 
					&& (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			if (cmp == 0) { 
				// key exists
				V oldV = node.nexts[i].value;
				node.nexts[i].value = value;
				return oldV;
			}
			prevs[i] = node;
		}
		// new node level
		int newLevel = randomLevel();
		// add new level
		Node<K, V> newNode = new Node<>(key, value, newLevel);
		// set the antecedent and successor
		for (int i = 0; i < newLevel; i++) {
			if (i >= level) {
				first.nexts[i] = newNode;
			} else {
				newNode.nexts[i] = prevs[i].nexts[i];
				prevs[i].nexts[i] = newNode;
			}
		}
		// node number increases
		size++;
		// calculate final level
		level = Math.max(level, newLevel);
		return null;
	}
	
	public V remove(K key) {
		keyCheck(key);
		Node<K, V> node = first;
		Node<K, V>[] prevs = new Node[level];
		boolean exist = false;
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null 
					&& (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			prevs[i] = node;
			if (cmp == 0) exist = true;
		}
		if (!exist) return null;
		// the remove node
		Node<K, V> removedNode = node.nexts[0];
		// decrease the size
		size--;
		// set successor
		for (int i = 0; i < removedNode.nexts.length; i++) {
			prevs[i].nexts[i] = removedNode.nexts[i];
		}
		// update skip list level
		int newLevel = level;
		while (--newLevel >= 0 && first.nexts[newLevel] == null) {
			level = newLevel;
		}
		return removedNode.value;
	}
	
	private int randomLevel() {
		int level = 1;
		while (Math.random() < P && level < MAX_LEVEL) {
			level++;
		}
		return level;
	}
	
	private void keyCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null.");
		}
	}
	
	private int compare(K k1, K k2) {
		return comparator != null 
				? comparator.compare(k1, k2)
				: ((Comparable<K>)k1).compareTo(k2);
	}
	
	private static class Node<K, V> {
		K key;
		V value;
		Node<K, V>[] nexts;
		public Node(K key, V value, int level) {
			this.key = key;
			this.value = value;
			nexts = new Node[level];
		}
		@Override
		public String toString() {
			return key + ":" + value + "_" + nexts.length;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Totally " + level + " levels.").append("\n");
		for (int i = level - 1; i >= 0; i--) {
			Node<K, V> node = first;
			while (node.nexts[i] != null) {
				sb.append(node.nexts[i]);
				sb.append(" ");
				node = node.nexts[i];
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
