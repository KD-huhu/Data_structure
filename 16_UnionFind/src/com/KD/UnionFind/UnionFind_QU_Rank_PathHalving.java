package com.KD.UnionFind;

public class UnionFind_QU_Rank_PathHalving extends UnionFind_QU_Rank{
	
	public UnionFind_QU_Rank_PathHalving(int capacity) {
		super(capacity);
	}
	
	@Override
	public int find(int v) { 
		rangeCheck(v);
		while (v != parents[v]) {
			parents[v] = parents[parents[v]];
			v = parents[v];
		}
		return v;
	}
}
