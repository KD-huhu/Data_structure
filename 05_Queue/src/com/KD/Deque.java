package com.KD;

import list.*;

public class Deque<E> {
	private List<E> list = new LinkedList_double_dir<>();
	
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
	 * add item to queue at rear
	 * use ArrayList add method to add item at last position
	 * @param element
	 */
	public void enQueueRear(E element) {
		list.add(element);
	}
	
	/**
	 * departure item in queue at front 
	 * use ArrayList remove method to remove index = 0 item
	 * @return
	 */
	public E deQueueFront() {
		return list.remove(0);
	}
	
	/**
	 * add item to queue at front
	 * use ArrayList add method to add item at index = 0 position
	 * @param element
	 */
	public void enQueueFront(E element) {
		list.add(0, element);
	}
	
	/**
	 * departure item in queue at rear 
	 * use ArrayList remove method to remove last item
	 * @return
	 */
	public E deQueueRear() {
		return list.remove(list.size() - 1);
	}
	
	/**
	 * get the first item in queue
	 * @return
	 */
	public E font() {
		return list.get(0);
	}
	
	/**
	 * get the last item in queue
	 * @return
	 */
	public E rear() {
		return list.get(list.size() - 1);
	}
}
