package circle_list;

import com.KD.*;

public class SingleCircleList<E> extends AbstractList<E> {
		private Node<E> first;
		
		private static class Node<E> {
			E element;
			Node<E> next;
			public Node(E element, Node<E> next) {
				this.element = element;
				this.next = next;
			}
			
			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();				
				sb.append(element).append("_").append(next.element);	
				return sb.toString();
			}
		}
			
		@Override
		public void clear() {
			size = 0;
			first = null;
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
			if (index == 0) {
				// create new node as first
				Node<E> newFirst = new Node<>(element, first);
				// get the last node
				Node<E> last = (size == 0) ? newFirst : node(size - 1);
				// add node
				last.next = newFirst;
				first = newFirst;
			}else {
				Node<E> prev = node(index - 1);
				prev.next = new Node<>(element, prev.next);
			}
			size++;
		}
		
		@Override
		public E remove(int index) {
			rangeCheck(index);

			Node<E> node = first;
			if (index == 0) {
				// remove the first node
				if (size == 1) {
					// only one node in the list
					first = null;
				} else {
					Node<E> last = node(size - 1);
					first = first.next;
					last.next = first;
				}
			}else {
				Node<E> prev = node(index - 1);
				node = prev.next;
				prev.next = node.next;
			}
			size--;
			return node.element;
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
			Node<E> node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		}
}