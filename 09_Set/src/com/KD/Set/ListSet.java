package com.KD.Set;

import com.KD.List.LinkedList_double_dir;
import com.KD.List.List;

public class ListSet<E> implements Set<E> {
	private List<E> list = new LinkedList_double_dir<>();
	
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(E element) {
		return list.contains(element);
	}

	@Override
	public void add(E element) {
		int index = list.indexOf(element);
		if (index != List.ELEMENT_NOT_FOUND) {
			// if element already exist
			// overwrite it
			list.set(index, element);
		} else {
			// if element does not in the set
			// add element at tail
			list.add(element);
		}
	}

	@Override
	public void remove(E element) {
		// get elemnet`s index
		int index = list.indexOf(element);
		if (index != List.ELEMENT_NOT_FOUND) {
			// if element exist
			list.remove(index);
		}
		// if element does not exist
		// do nothing
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		if (visitor == null) {
			return;
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (visitor.visit(list.get(i))) {
				// if list is not empty
				return;
			}
		}
	}
}
