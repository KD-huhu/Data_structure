package circle_list;

import com.KD.AbstractList;

public class CircleLinkedList<E> extends AbstractList<E> {
	private Node<E> first;
	private Node<E> last;
	private Node<E> current;
	
	private static class Node<E> {
		E element;
		Node<E> prev;
		Node<E> next;
		public Node(Node<E> prev, E element, Node<E> next) {
			this.prev = prev;
			this.element = element;
			this.next = next;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();		
			if (prev != null) {
				sb.append(prev.element);
			} else {
				sb.append("null");
			}			
			sb.append("_").append(element).append("_");
			if (next != null) {
				sb.append(next.element);
			} else {
				sb.append("null");
			}			
			return sb.toString();
		}
	}
	
	/**
	 * reset current
	 */
	public void reset() {
		current = first;
	}
	
	/**
	 * current move to next
	 * @return after move the element in current
	 */
	public E next() {
		if (current == null) return null;	
		current = current.next;
		return current.element;
	}
	
	public E remove() {
		if (current == null) {
			return null;
		}
		Node<E> next = current.next;
		E element = remove(current);
		if (size == 0) {
			current = null;
		} else {
			current = next;
		}
		return element;
	}
		
	@Override
	public void clear() {
		size = 0;
		first = null;
		last = null;
	}

	@Override
	public E get(int index) {
		return node(index).element;
	}
	
	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E old = node.element;
		node.element = element;
		return old;
	}
	
	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);		
		// index == size, add element at last position
		if (index == size) { 
			Node<E> oldLast = last;
			last = new Node<>(oldLast, element, first);
			if (oldLast == null) { 
				// add the first node in list
				first = last;
				first.next = first;
				first.prev = first;
			} else {
				oldLast.next = last;
				first.prev = last;
			}
		} else {
			Node<E> next = node(index); 
			Node<E> prev = next.prev; 
			Node<E> node = new Node<>(prev, element, next);
			next.prev = node;			
			prev.next = node;
			if (next == first) { 
				// index == 0
				first = node;
			} 
		}		
		size++;
	}
	
	private E remove(Node<E> node) {
		if (size == 1) {
			first = null;
			last = null;
		} else {
			Node<E> prev = node.prev;
			Node<E> next = node.next;
			// remove the node
			prev.next = next;
			next.prev = prev;
			if (node == first) {
				// index == 0
				first = next;
			}
			if (node == last) {
				// index == size - 1
				last = prev;
			}
		}
		size--;
		return node.element;	
	}
	
	@Override
	public E remove(int index) {
		rangeCheck(index);
		// node(index) -- get the node
		return remove(node(index));
	}
	
	@Override
	public int indexOf(E element) {
		if (element == null) {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (node.element == null) {
					return i;
				}
				node = node.next;
			}
		} else {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (element.equals(node.element)) {
					return i;
				}
				node = node.next;
			}
		}
		return ELEMENT_NOT_FOUND;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(", [");
		Node<E> node = first;
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(", ");
			}			
			string.append(node);			
			node = node.next;
		}
		string.append("]");
		return string.toString();
	}
	
	/**
	 * get the node element of the index
	 * @param index
	 * @return node on index
	 */
	private Node<E> node(int index) {
		rangeCheck(index);
		if (index < (size >> 1)) {
			Node<E> node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		} else {
			Node<E> node = last;
			for (int i = size-1; i > index; i--) {
				node = node.prev;
			}
			return node;
		}
	}
}

