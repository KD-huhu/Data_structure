package com.KD.UnionFind;

public class UnionFind_QU_Size extends UnionFind_QU {
	private int[] sizes;

	public UnionFind_QU_Size(int capacity) {
		super(capacity);
		
		sizes = new int[capacity];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = 1;
		}
	}
	
	/**
	 * set V1 root as V2 root
	 */
	@Override
	public void union(int v1, int v2) {
		int p1 = find(v1);
		int p2 = find(v2);
		if (p1 == p2) return;
		
		if (sizes[p1] < sizes[p2]) {
			parents[p1] = p2;
			sizes[p2] += sizes[p1];
		} else {
			parents[p2] = p1;
			sizes[p1] += sizes[p2];
		}
	}
}
