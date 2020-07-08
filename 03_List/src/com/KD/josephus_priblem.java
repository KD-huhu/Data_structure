package com.KD;

import circle_list.CircleLinkedList;

public class josephus_priblem {
	
	public void josephus(int n, int m) {
		CircleLinkedList<Integer> list = new CircleLinkedList<>();
		for (int i = 1; i <= n; i++) {
			list.add(i);
		}
		list.reset();		
		while (!list.isEmpty()) {
			for (int i = 1; i < m; i++) {
				list.next();
			}
			System.out.println(list.remove());
		}
	}
}
