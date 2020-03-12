package hr.fer.zemris.java.custom.collections;
/**
 * ArrayBackedIndexedCollection
 * <p>
 * This class implements collection of Objects. General contract of this
 * collection: duplicate elements are allowed; null references are not allowed.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 * 
 */
public class ArrayBackedIndexedCollection {
	private int size;
	private int capacity;
	private Object[] elements;

	/**
	 * Default constructor sets capacity to 16.
	 */
	public ArrayBackedIndexedCollection() {
		capacity = 16;
		elements = new Object[capacity];
	}

	/**
	 * Method constructor for ABICollection which sets capacity to
	 * initalCapacity
	 * 
	 * @param initialCapacity
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		this.capacity = initialCapacity;
		elements = new Object[capacity];
	}
	/**
	 * isEmpty
	 * 
	 * @return Return value is true if ABICollection size is zero, otherwise
	 *         false.
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * size
	 * 
	 * @return Return ABICollection size.
	 */
	public int size() {
		return size;
	}

	/**
	 * add
	 * <p>
	 * This method adds Object to ABICollection.
	 * </p>
	 * 
	 * @param value
	 *            class Object
	 */
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}

		if (size == capacity) {
			Object[] pom = new Object[capacity];

			/* Reallocation */

			for (int i = 0; i < capacity; i++) {
				pom[i] = elements[i];
			}
			elements = new Object[capacity * 2];

			for (int i = 0; i < capacity; i++) {
				elements[i] = pom[i];
			}
			capacity *= 2;
		}

		/* Adding new elements */

		elements[size] = value;
		size++;
	}

	/**
	 * get
	 * 
	 * @param index
	 * @return Method returns element from ABICollection on position index.
	 */
	public Object get(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	/**
	 * remove
	 * <p>
	 * Removes element form ABICollection on positon index.
	 * </p>
	 * 
	 * @param index
	 */
	public void remove(int index) {
		int i;
		for (i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[i] = null;
		size--;
	}
	/**
	 * insert
	 * <p>
	 * Inserts the given values given position in array. The legal positions are
	 * 0 to size. If position is invalid, an appropriate exception will be
	 * thrown.
	 * </p>
	 * 
	 * @param value
	 * @param position
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		Object[] pom = new Object[capacity];

		for (int i = 0; i < capacity; i++) {
			pom[i] = elements[i];
		}

		/* Reallocation */

		if (size == capacity) {
			elements = new Object[capacity * 2];
			capacity *= 2;
		}

		/* Insert */

		for (int i = 0; i < position; i++) {
			elements[i] = pom[i];
		}
		elements[position] = value;
		for (int i = position + 1; i < size + 1; i++) {
			elements[i] = pom[i - 1];
		}
		size++;
	}
	/**
	 * indexOf
	 * <p>
	 * Searches the collection and return the index of the first occurrence of
	 * given value or -1 if value is not found.
	 * </p>
	 * 
	 * @param value
	 * @return index in question
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * contains
	 * 
	 * @param value
	 * @return Returns true only if the collection contains given value.
	 */
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes all elements from collection.
	 */
	public void clear() {
		size = 0;
		capacity = 16;
		elements = new Object[capacity];
	}

}
