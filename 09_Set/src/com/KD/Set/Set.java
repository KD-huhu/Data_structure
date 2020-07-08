package com.KD.Set;

public interface Set<E> {
	
	/**
	 * All Set interfaces
	 */
	
	int size();
	
	boolean isEmpty();
	
	void clear();
	
	boolean contains(E element);
	
	void add(E element);
	
	void remove(E element);
	
	void traversal(Visitor<E> visitor);
	
	/**
	 * define visitor function
	 * @param <E>
	 */
	public static abstract class Visitor<E> {
		boolean stop;
		public abstract boolean visit(E element);
	}
}
