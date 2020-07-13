package com.KD;

public class BruteForce {
	public static int indexOf1(String text, String pattern) {
		if (text == null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		
		int pi = 0;
		int ti = 0;
		while (pi < plen && ti < tlen) {
			if (text.charAt(ti) == pattern.charAt(pi)) {
				ti++;
				pi++;
			} else {
				ti -= pi - 1;
				pi = 0;
			}
		}
		return pi == plen ? ti - pi : -1;
	}
	
	// optimize
	// reduce the number of comparison
	public static int indexOf2(String text, String pattern) {
		if (text == null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		
		int pi = 0;
		int ti = 0;
		int tmax = tlen - plen;
		while (pi < plen && ti - pi <= tmax) {
			if (text.charAt(ti) == pattern.charAt(pi)) {
				ti++;
				pi++;
			} else {
				ti -= pi - 1;
				pi = 0;
			}
		}
		return pi == plen ? ti - pi : -1;
	}
	
	public static int indexOf3(String text, String pattern) {
		if (text == null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		
		int tmax = tlen - plen;
		for (int ti = 0; ti <= tmax; ti++) {
			int pi = 0;
			for (; pi < plen; pi++) {
				if (text.charAt(ti + pi) != pattern.charAt(pi)) break;
			}
			if (pi == plen) return ti;
		}
		return -1;
	}
}
