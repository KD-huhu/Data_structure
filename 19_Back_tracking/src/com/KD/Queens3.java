package com.KD;

public class Queens3 {

	public static void main(String[] args) {
		new Queens3().place8Queens();
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
	byte cols;
	
	/**
	 * mark whether there is a queen on a diagonal line
	 * form 'top left corner' to 'lower right corner'
	 */
	short leftTop;
	
	/**
	 * mark whether there is a queen on a diagonal line
	 * form 'top right corner' to 'lower left corner'
	 */
	short rightTop;
	
	void place8Queens() {
		queens = new int[8];
		place(0);
		System.out.println("There are " + ways + " solutions for '8 Queen Problem'.");
	}
	
	/**
	 * place the 'queen' from the 'row' row
	 * @param row
	 */
	void place(int row) {
		if (row == 8) {
			ways++;
			show();
			return;
		}
		
		for (int col = 0; col < 8; col++) {
			int cv = 1 << col;
			if ((cols & cv) != 0) continue;	
			int lv = 1 << (row - col + 7);
			if ((leftTop & lv) != 0) continue;	
			int rv = 1 << (row + col);
			if ((rightTop & rv) != 0) continue;
			
			queens[row] = col;
			cols |= cv;
			leftTop |= lv;
			rightTop |= rv;
			place(row + 1);
			cols &= ~cv;
			leftTop &= ~lv;
			rightTop &= ~rv;
		}
	}
	
	void show() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
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
