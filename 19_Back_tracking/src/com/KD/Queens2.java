package com.KD;

public class Queens2 {

	public static void main(String[] args) {
		new Queens2().placeQueens(8);
	}
	
	/**
	 * array index is the row number
	 * array element is the column number
	 * use for final print
	 */
	int[] queens;
	
	/**
	 * count solutions
	 */
	int ways;
	
	/**
	 * mark whether a column has a 'queen'
	 */
	boolean[] cols;
	
	/**
	 * mark whether there is a queen on a diagonal line
	 * form 'top left corner' to 'lower right corner'
	 */
	boolean[] leftTop;
	
	/**
	 * mark whether there is a queen on a diagonal line
	 * form 'top right corner' to 'lower left corner'
	 */
	boolean[] rightTop;

	
	void placeQueens(int n) {
		if (n < 1) return;
		queens = new int[n];
		cols = new boolean[n];
		leftTop = new boolean[(n << 1) - 1];
		rightTop = new boolean[leftTop.length];
		place(0);
		System.out.println("There are " + ways + " solutions for '" + n + " Queen Problem'.");
	}
	
	/**
	 * place the 'queen' from the 'row' row
	 * @param row
	 */
	void place(int row) {
		if (row == cols.length) {
			ways++;
			show();
			return;
		}
		for (int col = 0; col < cols.length; col++) {
			// do pruning
			if (cols[col]) continue;
			int leftIndex = row - col + cols.length - 1;
			if (leftTop[leftIndex]) continue;
			int rirhtIndex = row +col;
			if (rightTop[rirhtIndex]) continue;
			
			// place a 'queen'
			queens[row] = col;
			cols[col] = true;
			leftTop[leftIndex] = true;
			rightTop[rirhtIndex] = true;
			place(row + 1);
			// if the 'back tracking' happens, need restore site
			cols[col] = false;
			leftTop[leftIndex] = false;
			rightTop[rirhtIndex] = false;
		}
	}
	
	void show() {
		for (int row = 0; row < cols.length; row++) {
			for (int col = 0; col < cols.length; col++) {
				if (queens[row] == col) {
					System.out.print("1 ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		System.out.println("------------------------------");
	}
}

