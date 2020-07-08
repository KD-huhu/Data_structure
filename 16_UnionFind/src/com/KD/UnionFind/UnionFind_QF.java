package com.KD.UnionFind;

/**
 * Quick Find
 * @author KD_huhu
 */
public class UnionFind_QF extends UnionFind {
	
	public UnionFind_QF(int capacity) {
		super(capacity);
	}
	/**
	 * parent is root
	 */
	public int find(int v) {
		rangeCheck(v);
		return parents[v];
	}
	/**
	 * set all the element in V1 parents as V2
	 */
	public void union(int v1, int v2) {
		int p1 = find(v1);
		int p2 = find(v2);
		if (p1 == p2) return;
		for (int i = 0; i < parents.length; i++) {
			if (parents[i] == p1) {
				parents[i] = p2;
			}
		}
	}
}

