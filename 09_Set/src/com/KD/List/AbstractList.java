package com.KD.List;

public abstract class AbstractList<E> implements List<E> {
	/**
	 * the number of elements
	 */
	protected int size;
	
	/** 
	 * check the size
	 * @return size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * check if the list contains the element
	 * @return True : is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * check if the list contains the element
	 * @param element
	 * @return True : contains
	 */
	public boolean contains(E elememt) {
		return indexOf(elememt) != ELEMENT_NOT_FOUND;
	}
	
	/**
	 * add element at the last position
	 */
	public void add(E element) {
		add(size, element);
	}
	
	/**
	 * deal index out of bound 
	 */
	protected void outOfBound(int index) {
		throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
	}
	
	/**
	 * check if index is illegal
	 */
	protected void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			// throw exception
			outOfBound(index);
		}
	}
	
	/**
	 * check if index is illegal for add
	 * in add index can equal to size
	 */
	protected void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			// throw exception
			outOfBound(index);
		}
	}
}
