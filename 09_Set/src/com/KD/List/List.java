package com.KD.List;

public interface List<E> {
	static final int ELEMENT_NOT_FOUND = -1;
	/**
	 * clear all the elements
	 */
	void clear();
	
	/**
	 * check the size
	 * @return size
	 */
	int size();
	
	/**
	 * check if the list is empty
	 * @return True : is empty
	 */
	boolean isEmpty();
	
	/**
	 * check if the list contains the element
	 * @param element
	 * @return True : contains
	 */
	boolean contains(E element);
	
	/**
	 * add element at the last position
	 * @param element
	 */
	void add(E element);
	
	/**
	 * add element at index position
	 * @param index
	 */
	void add(int index, E element);
	
	/**
	 * get the element on index position 
	 * @param index
	 * @return element
	 */
	E get(int index);
	
	/**
	 * set element at index position
	 * @param index
	 * @param element
	 * @return the old element on index position
	 */
	E set(int index, E element);
	
	/**
	 * remove the element on index position 
	 * @param index
	 * @return the element removed
	 */
	E remove(int index);
	
	/**
	 * check the element`s index
	 * @param element
	 * @return the element`s index
	 */
	int indexOf(E element);
}
