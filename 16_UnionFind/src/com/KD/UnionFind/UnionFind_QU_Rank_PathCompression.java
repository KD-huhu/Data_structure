package com.KD.UnionFind;

public class UnionFind_QU_Rank_PathCompression extends UnionFind_QU_Rank{
	
	public UnionFind_QU_Rank_PathCompression(int capacity) {
		super(capacity);
	}
	
	@Override
	public int find(int v) { // v == 1, parents[v] == 2
		rangeCheck(v);
		if (parents[v] != v) {
			parents[v] = find(parents[v]);
		}
		return parents[v];
	}
}
