package com.KD.UnionFind;

public class UnionFind_QU_Rank extends UnionFind_QU {
	private int[] ranks;

	public UnionFind_QU_Rank(int capacity) {
		super(capacity);
		
		ranks = new int[capacity];
		for (int i = 0; i < ranks.length; i++) {
			ranks[i] = 1;
		}
	}
	
	@Override
	public void union(int v1, int v2) {
		int p1 = find(v1);
		int p2 = find(v2);
		if (p1 == p2) return;
		
		if (ranks[p1] < ranks[p2]) {
			parents[p1] = p2;
		} else if (ranks[p1] > ranks[p2]) {
			parents[p2] = p1;
		} else {
			parents[p1] = p2;
			// update the height
			ranks[p2] += 1;
		}
	}
}
