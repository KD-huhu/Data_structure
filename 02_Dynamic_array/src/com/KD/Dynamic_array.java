package com.KD;

@SuppressWarnings("unchecked")
public class Dynamic_array<E> {
	
	// The number of elements
	private int size;
	// Elements
	private E[] elements;
	// Minimum number of array elements
	private static final int DEFAULT_CAPACITY = 10;
	
	/**
	 * Declare function with parameter object
	 * capaticy:Number of array elements
	 * if capacity < DEFAULT_CAPACITY
	 * 		capacity = DEFAULT_CAPACITY
	 */
	public Dynamic_array(int capacity) {
		capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
		// Create array
		elements = (E[]) new Object[capacity];
	}
	
	/**
	 * Declare function without parameter object
	 * capacity = DEFAULT_CAPACITY
	 */
	public Dynamic_array() {
		// Call itself
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Empty the array
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		// remember set size = 0
		size = 0;
	}

	/**
	 * View array size
	 * @return array size
	 */
	public int size() {
		return size;
	}

	/**
	 * Judge whether the array is empty
	 * @return True-empty
	 */
	public boolean isEmpty() {
		 return size == 0;
	}
	
	/**
	 * Check whether an element exists in the array
	 * @param element
	 * @return True-contain
	 */
	public boolean contains(E element) {
		return indexOf(element) != -1;
	}
	
	/**
	 * Add element at the end
	 * @param element
	 */
	public void add(E element) {
		// Add element at the end
		// Equivalent to add element at size
		add(size,element);
	}
	
	/**
	 * Get content with index as index
	 * @param index
	 * @return content
	 */
	public E get(int index) {
		rangeCheck(index);
		return elements[index];
	}

	/**
	 * Replace the element with index
	 * @param index
	 * @param element
	 * @return the element replaced
	 */
	public E set(int index, E element) {
		rangeCheck(index);
		E old_element = elements[index];
		elements[index] = element;
		return old_element;
	}

	/**
	 * Add element at index
	 * @param index
	 * @param element
	 */
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		// Make sure the array still has space
		ensureCapacity(size + 1);
		// Add element
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		elements[index] = element;
		// remember increase the size
		size++;
	}
	
	/**
	 * Remove the element, index = index
	 * @param index
	 * @return the element removed
	 */
	public E remove(int index) {
		rangeCheck(index);
		E move_element = elements[index];
		for (int i = index+1; i < size; i++) {
			elements[i - 1] = elements[i]; 
		}
		// remember decrease the size
		size--;
		// Release the space
		elements[size] = null;
		return move_element;
	}

	/**
	 * Find element corresponding index
	 * @param element
	 * @return index or -1-element is not in the array
	 */
	public int indexOf(E element) {
		// element = null
		if (element == null) {
			for (int i = 0; i < size; i++) {
				if (elements[i] == null) return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				// use element.equal:at this stage element is not null
				// but element[i] may be null
				if (element.equals(elements[i])) return i;
			}
		}
		return -1;
	}
	
	/**
	 * Make sure the array still has space
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		// Get old capacity
		int old_capacity = elements.length;
		// the array still has space
		if (old_capacity > capacity) return ;
		// the array dose not have space
		// new_capacity = 1.5 * old_capacity
		// but bitwise operation is faster
		int new_capacity = old_capacity + (old_capacity >> 1);
		E[] newElements = (E[]) new Object[new_capacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
		
		System.out.println(old_capacity + " is expanded to " + new_capacity);
	}
	
	/**
	 * Throw an IndexOutOfBoundsException
	 * @param index
	 */
	private void outOfBounds(int index) {
		throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
	}
	
	/**
	 * Check whether the index is legal
	 * @param index
	 */
	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			outOfBounds(index);
		}
	}
	
	/**
	 * Check whether the index is legal
	 * We allow to add element at index = size
	 * @param index
	 */
	private void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			outOfBounds(index);
		}
	}
	
	@Override
	/**
	 * Override toString
	 */
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(", [");
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(", ");
			}
			string.append(elements[i]);
		}
		string.append("]");
		return string.toString();
	}
}

