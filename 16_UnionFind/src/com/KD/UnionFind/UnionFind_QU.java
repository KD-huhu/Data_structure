package com.KD.UnionFind;

/**
 * Quick Union
 * @author KD_huhu
 *
 */
public class UnionFind_QU extends UnionFind {

	public UnionFind_QU(int capacity) {
		super(capacity);
	}
	/**
	 * though parent find root
	 */
	public int find(int v) {
		rangeCheck(v);
		while (v != parents[v]) {
			v = parents[v];
		}
		return v;
	}
	/**
	 * set V1 root as V2 root
	 */
	public void union(int v1, int v2) {
		int p1 = find(v1);
		int p2 = find(v2);
		if (p1 == p2) return;
		parents[p1] = p2;
	}
}
