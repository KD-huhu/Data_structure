package com.KD.UnionFind;

public abstract class UnionFind {
	protected int[] parents;
	
	/**
	 * Constructor
	 */
	public UnionFind(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("capacity must be >= 1");
		}
		parents = new int[capacity];
		for (int i = 0; i < parents.length; i++) {
			parents[i] = i;
		}
	}
	
	/**
	 * find the set V belongings
	 */
	public abstract int find(int v);

	/**
	 * merge V1 and V2 sets
	 */
	public abstract void union(int v1, int v2);
	
	/**
	 * check if V1 and V2 belonging the same set
	 */
	public boolean isSame(int v1, int v2) {
		return find(v1) == find(v2);
	}
	
	protected void rangeCheck(int v) {
		if (v < 0 || v >= parents.length) {
			throw new IllegalArgumentException("v is out of bounds");
		}
	}
}

