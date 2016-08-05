package util.collection;


/**
 * A Double Linked List Implementation
 *
 **/

import java.util.*;

public class CustomDblLinkedList<E> implements Iterable<E>
{
	transient int size = 0;
	private Node<E> firstNode;
	private Node<E> LastNode;

	/**
	 *  empty Collection
	 */
	public CustomDblLinkedList()
	{
		firstNode = null;
		LastNode = null;
	}


	/*
	 *  The Node class
	 */
	private static class Node<E> {
		E item;
		Node<E> nextNode;
		Node<E> prevNode;

		Node(E element,Node<E> prev,  Node<E> next) {
			this.item = element;
			this.nextNode = next;
			this.prevNode = prev;
		}
	}
	/**
	 *  Returns true if the list is empty
	 *
	 */
	public boolean isEmpty()
	{
		return (size == 0);
	}
	/**
	 * Adds e as first element.
	 */
	public void addFirst(E e) {
		final Node<E> _first = firstNode; //temp node to hold firstNode
		final Node<E> newNode = new Node<>(e, null,_first); //create new Node to be added
		firstNode = newNode; //firstNode is the newNode now
		if (_first == null) //
			LastNode = newNode;
		else
			_first.prevNode = newNode;
		size++;
	}


	/**
	 * Adds e as last element.
	 */
	public void addLast(E e) {
		final Node<E> _lastNode = LastNode;
		final Node<E> newNode = new Node<>(e,_lastNode, null);
		LastNode = newNode;
		if (_lastNode == null)
			firstNode = newNode;
		else
			_lastNode.nextNode = newNode;
		size++;
	}


	/**
	 * Reomves and returns the last element from this list.
	 *
	 * @return the last element e from this list
	 */
	public E removeLast() {
		final E element = LastNode.item;
		final Node<E> prev = LastNode.prevNode;
		LastNode.item = null;
		LastNode.prevNode = null; 
		LastNode = prev;
		if (prev == null)
			firstNode = null;
		else
			prev.nextNode = null;
		size--;
		return element;
	}

	/**
	 * remove first node .
	 *  @return the first element e from this list
	 */
	public E removeFirst() {
		final E element = firstNode.item;
		final Node<E> next = firstNode.nextNode;
		firstNode.item = null;
		firstNode.nextNode = null; 
		firstNode = next;
		if (next == null)
			LastNode = null;
		else
			next.prevNode = null;
		size--;
		return element;
	}

	/**
	 * remove first element found .
	 */

	public boolean remove(E element) {

		//iterate starting from first Node till matching item/element is found
		for (Node<E> tempNode = firstNode; tempNode != null; tempNode = tempNode.nextNode) {
			if (element.equals(tempNode.item)) {
				remove(tempNode);
				return true;
			}
		}

		return false;
	}

	/**
	 * remove entire Node found .
	 */
	E remove(Node<E> targetNode) {
		final E element = targetNode.item;
		final Node<E> next = targetNode.nextNode;
		final Node<E> prev = targetNode.prevNode;

		if (prev == null) {
			firstNode = next;
		} else {
			prev.nextNode = next;
			targetNode.prevNode = null;
		}

		if (next == null) {
			LastNode = prev;
		} else {
			next.prevNode = prev;
			targetNode.nextNode = null;
		}

		targetNode.item = null;
		size--;
		return element;
	}

	/**
	 *  Removes all nodes from the list.
	 *
	 */
	public void clear()
	{
		for (Node<E> tempNode = firstNode; tempNode != null; ) {
			Node<E> next = tempNode.nextNode;
			tempNode.item = null;
			tempNode.nextNode = null;
			tempNode.prevNode = null;
			tempNode = next;
		}
		firstNode = LastNode = null;
		size = 0;
	}

	/**
	 * Enabling iterator functnoality for for-each loop access .
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new CustomDblLinkedListIterator();
	}

	private class CustomDblLinkedListIterator  implements Iterator<E>
	{
		private Node<E> nextNode;

		public CustomDblLinkedListIterator()
		{
			nextNode = firstNode;
		}

		public boolean hasNext()
		{
			return nextNode != null;
		}

		public E next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			E res = nextNode.item;
			nextNode = nextNode.nextNode;
			return res;
		}

		public void remove() { throw new UnsupportedOperationException(); }
	}

	public E getFirstElement() {
		final Node<E> _first = firstNode;
		return (_first == null) ? null : _first.item;
	}

	public E getLastElement() {
		final Node<E> _last = LastNode;
		return (_last == null) ? null : _last.item;
	}
}