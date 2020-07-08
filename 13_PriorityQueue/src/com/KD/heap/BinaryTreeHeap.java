package com.KD.heap;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class BinaryTreeHeap<E> extends AbstractHeap<E> {
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	/**
	 * constructor
	 * @param elements
	 * @param comparator
	 */
	public BinaryTreeHeap(E[] elements, Comparator<E> comparator)  {
		super(comparator);
		
		if (elements == null || elements.length == 0) {
			this.elements = (E[]) new Object[DEFAULT_CAPACITY];
		} else {
			size = elements.length;
			int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
			this.elements = (E[]) new Object[capacity];
			for (int i = 0; i < elements.length; i++) {
				this.elements[i] = elements[i];
			}
			heapify();
		}
	}

	public BinaryTreeHeap(E[] elements)  {
		this(elements, null);
	}
	
	public BinaryTreeHeap(Comparator<E> comparator) {
		this(null, comparator);
	}
	
	public BinaryTreeHeap() {
		this(null, null);
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public void add(E element) {
		elementNotNullCheck(element);
		// ensure enough space
		ensureCapacity(size + 1);
		elements[size++] = element;
		// sift up
		siftUp(size - 1);
	}

	@Override
	public E get() {
		emptyCheck();
		return elements[0];
	}

	@Override
	public E remove() {
		emptyCheck();
		int lastIndex = --size;
		// exchange root with last
		E root = elements[0];
		elements[0] = elements[lastIndex];
		elements[lastIndex] = null;
		// sift down
		siftDown(0);
		return root;
	}

	@Override
	public E replace(E element) {
		elementNotNullCheck(element);
		E root = null;
		if (size == 0) {
			// heap is empty
			elements[0] = element;
			size++;
		} else {
			root = elements[0];
			elements[0] = element;
			siftDown(0);
		}
		return root;
	}

	/**
	 * use a array to create a heap
	 */
	private void heapify() {
		// use from top to bottom do sift down
		for (int i = (size >> 1) - 1; i >= 0; i--) {
			siftDown(i);
		}
	}
	
	private void siftUp(int index) {
		// copy element
		E element = elements[index];
		while (index > 0) {
			int parentIndex = (index - 1) >> 1;
			E parent = elements[parentIndex];
			if (compare(element, parent) <= 0) {
				// element <= parent
				break;
			}
			// put parent at index
			elements[index] = parent;
			// change index
			index = parentIndex;
		}
		// put element at final index
		elements[index] = element;
	}
	
	private void siftDown(int index) {
		// copy element
		E element = elements[index];
		// get first not leaf node 
		int half = size >> 1;
		// sift down is for not leaf nodes
		while (index < half) {
			// node has 2 conditions
			// 1. only has left child
			// 2. has two children
			// first compare with its left child
			int childIndex = (index << 1) + 1;
			E child = elements[childIndex];
			// get right child
			int rightIndex = childIndex + 1;
			// find the bigger one
			if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
				child = elements[childIndex = rightIndex];
			}
			if (compare(element, child) >= 0) {
				// parent > children
				// no need to sift down
				break;
			}
			// put child at index
			elements[index] = child;
			// reset index
			index = childIndex;
		}
		// put element at final index
		elements[index] = element;
	}

	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		// newCapacity = 1.5*oldCapacity
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
	}

	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
	
	private void emptyCheck() {
		if (size == 0) {
			throw new IndexOutOfBoundsException("Heap is empty");
		}
	}
}
