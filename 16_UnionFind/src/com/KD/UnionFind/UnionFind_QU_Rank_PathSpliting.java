package com.KD.UnionFind;

public class UnionFind_QU_Rank_PathSpliting extends UnionFind_QU_Rank{
	
	public UnionFind_QU_Rank_PathSpliting(int capacity) {
		super(capacity);
	}
	
	@Override
	public int find(int v) { 
		rangeCheck(v);
		while (v != parents[v]) {
			int p = parents[v];
			parents[v] = parents[parents[v]];
			v = p;
		}
		return v;
	}
}
