package com.KD;

import list.ArrayList;
import list.List;

public class Queue<E> {
	private List<E> list = new ArrayList<>();
	
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void clear() {
		list.clear();
	}
	
	/**
	 * add item to queue
	 * use ArrayList add method to add item at last position
	 * @param element
	 */
	public void enQueue(E element) {
		list.add(element);
	}
	
	/**
	 * departure queue
	 * use ArrayList remove method to remove index = 0 item
	 * means remove the first item in queue
	 * @return
	 */
	public E deQueue() {
		return list.remove(0);
	}
	
	/**
	 * get the first item in queue
	 * @return
	 */
	public E font() {
		return list.get(0);
	}
}
