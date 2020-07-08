package com.KD;

public class Hanoi {
	
	public static void main(String[] args) {
		new Hanoi().hanoi(10, "A", "B", "C");
	}
	
	/**
	 * Move n plates from P1 to P3
	 * @param p2 the pillar in the middle
	 */
	void hanoi(int n, String p1, String p2, String p3) {
		if (n == 1) {
			move(n, p1, p3);
			return;
		}
		hanoi(n - 1, p1, p3, p2);
		move(n, p1, p3);
		hanoi(n - 1, p2, p1, p3);
	} 

	/**
	 * Print move 'no' plate from 'from' to 'to'
	 * @param no: the number of the plate
	 * @param from: from pillar
	 * @param to: to pillar
	 */
	void move(int no, String from, String to) {
		System.out.println("Move " + no + " plate form " + from + "to " + to);
	}
}	
