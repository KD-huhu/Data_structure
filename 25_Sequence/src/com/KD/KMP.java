package com.KD;

public class KMP {
	public static int indexOf1(String text, String pattern) {
		if (text == null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		
		int[] next = next2(pattern);
		int pi = 0;
		int ti = 0;
		int tmax = tlen - plen;
		while (pi < plen && ti <= tmax) {
			if (pi < 0 || text.charAt(ti) == pattern.charAt(pi)) {
				ti++;
				pi++;
			} else {
				pi = next[pi];
			}
		}
		return pi == plen ? ti - pi : -1;
	}
	
	@SuppressWarnings("unused")
	private static int[] next1(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		int i = 0;
		int n = next[i] = -1;
		int imax = len -1;
		while (i < imax) {
			if (n < 0 || pattern.charAt(i) == pattern.charAt(n)) {
				next[++i] = ++n;
			} else {
				n = next[n];
			}
		}
		return next;
	}
	
	private static int[] next2(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		int i = 0;
		int n = next[i] = -1;
		int imax = len -1;
		while (i < imax) {
			if (n < 0 || pattern.charAt(i) == pattern.charAt(n)) {
				i++;
				n++;
				if (pattern.charAt(i) == pattern.charAt(n)) {
					next[i] = next[n];
				} else {
					next[i] = n;
				}
			} else {
				n = next[n];
			}
		}
		return next;
	}
}
