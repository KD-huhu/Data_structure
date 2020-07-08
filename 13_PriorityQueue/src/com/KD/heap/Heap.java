package com.KD.heap;

public interface Heap<E> {
	
	int size();	// get size
	
	boolean isEmpty();	// check if is empty
	
	void clear();	// clear
	
	void add(E element);	 // add element
	
	E get();	// get the top element
	
	E remove(); // remove the top element
	
	E replace(E element); 
	// remove the top element, add a new element
}
