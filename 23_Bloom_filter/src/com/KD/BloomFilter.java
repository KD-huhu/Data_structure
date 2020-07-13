package com.KD;

public class BloomFilter<T> {
	/**
	 * length of binary vector
	 */
	private int bitSize;
	/**
	 * binary vector
	 */
	private long[] bits;
	/**
	 * number of hash functions
	 */
	private int hashSize;
	
	/**
	 * calculate the parameters of the 'Bloom Filter'
	 * @param n
	 * @param p
	 */
	public BloomFilter(int n, double p) {
		if (n <= 0 || p <= 0 || p >= 1) {
			throw new IllegalArgumentException("wrong n or p");
		}
		double ln2 = Math.log(2);
		// calculate the length of the binary vector
		bitSize = (int) (- (n * Math.log(p)) / (ln2 * ln2));
		// calculate the number of hash functions
		hashSize = (int) (bitSize * ln2 / n);
		// the length of the bits array
		bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
	}
	
	/**
	 * add elements
	 */
	public boolean put(T value) {
		nullCheck(value);
		// using value to generate two integers
		int hash1 = value.hashCode();
		int hash2 = hash1 >>> 16;
		boolean result = false;
		for (int i = 1; i <= hashSize; i++) {
			int combinedHash = hash1 + (i * hash2);
			if (combinedHash < 0) {
				// make sure the build index value is greater than 0
				combinedHash = ~combinedHash;
			} 
			// generate a right index
			int index = combinedHash % bitSize;
			// set the binary of the index position to 1
			if (set(index)) result = true;
		}
		return result;
	}
	
	/**
	 * determine whether an element exists
	 */
	public boolean contains(T value) {
		nullCheck(value);
		// using value to generate two integers
		int hash1 = value.hashCode();
		int hash2 = hash1 >>> 16;
		for (int i = 1; i <= hashSize; i++) {
			int combinedHash = hash1 + (i * hash2);
			if (combinedHash < 0) {
				combinedHash = ~combinedHash;
			} 
			// generate a binary index
			int index = combinedHash % bitSize;
			// query whether the binary of the index position is 0
			if (!get(index)) return false;
		}
		return true;
	}
	
	/**
	 * set the binary of the index position to 1
	 */
	private boolean set(int index) {
		long value = bits[index / Long.SIZE];
		int bitValue = 1 << (index % Long.SIZE);
		bits[index / Long.SIZE] = value | bitValue;
		return (value & bitValue) == 0;
	}
	
	/**
	 * look at the binary value of the index position
	 * @return true means 1, false means 0
	 */
	private boolean get(int index) {
		long value = bits[index / Long.SIZE];
		return (value & (1 << (index % Long.SIZE))) != 0;
	}
	
	private void nullCheck(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Value must not be null.");
		}
	}
}
