package com.KD;

public class Queens {

	public static void main(String[] args) {
		new Queens().placeQueens(8);
	}
	
	/**
	 * array index is the row number
	 * array element is the column number
	 */
	int[] cols;
	
	/**
	 * count solutions
	 */
	int ways;
	
	void placeQueens(int n) {
		if (n < 1) return;
		cols = new int[n];
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
			if (isValid(row, col)) {
				// place the 'queen' at (raw, col)
				cols[row] = col;
				place(row + 1);
			}
		}
	}
	
	/**
	 * check if the 'queen' could be placed at (raw, col)
	 * do pruning
	 */
	boolean isValid(int row, int col) {
		for (int i = 0; i < row; i++) {
			// there is a queen at this column
			if (cols[i] == col) {
				//System.out.println("[" + row + "][" + col + "]=false");
				return false;
			}
			// the queen at raw 'i' is on the same oblique line with the 'queen' on column 'col'
			if (row - i == Math.abs(col - cols[i])) {
				//System.out.println("[" + row + "][" + col + "]=false");
				return false;
			}
		}
		//System.out.println("[" + row + "][" + col + "]=true");
		return true;
	}
	
	void show() {
		for (int row = 0; row < cols.length; row++) {
			for (int col = 0; col < cols.length; col++) {
				if (cols[row] == col) {
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

