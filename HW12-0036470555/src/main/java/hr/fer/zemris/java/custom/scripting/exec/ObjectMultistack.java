package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * ObjectMultistack class allows the user to store multiple values for same key
 * and it provides a stack-like abstraction. Keys for ObjectMultistack are
 * instances of the class String. Values that are associated with those keys are
 * instances of class ValueWrapper.
 * 
 * @author Ivan Pavić
 * 
 */
public class ObjectMultistack {
    /**
     * Map representing multiStack.
     */
    private final Map<String, MultistackEntry> multiStack = new HashMap<>();

    /**
     * Static class(structure) which represents single element in MultiStack. It
     * acts like node in single linked list.
     * 
     * @author Ivan Pavić
     * 
     */
    private static class MultistackEntry {
	/**
	 * ValueWrapper value which is stored.
	 */
	private final ValueWrapper value;
	/**
	 * Reference to next node.
	 */
	private final MultistackEntry next;

	/**
	 * Constructs MultiStackEntry from given value and reference to next
	 * value.
	 * 
	 * @param value
	 *            given value
	 * @param next
	 *            reference to next value.
	 */
	public MultistackEntry(ValueWrapper value, MultistackEntry next) {
	    this.value = value;
	    this.next = next;
	}

	/**
	 * @return the value
	 */
	public ValueWrapper getValue() {
	    return value;
	}

	/**
	 * @return the next node
	 */
	public MultistackEntry getNext() {
	    return next;
	}

    }

    /**
     * Method is designed for pushing ValueWrapper value to ObjectMultiStack
     * depending on String key in O(1) complexity.
     * 
     * @param name
     *            given key
     * @param valueWrapper
     *            value to push
     */
    public void push(String name, ValueWrapper valueWrapper) {
	testInputString(name);
	if (!multiStack.containsKey(name)) {
	    multiStack.put(name, new MultistackEntry(valueWrapper, null));
	} else {
	    multiStack.put(name,
		    new MultistackEntry(valueWrapper, multiStack.get(name)));
	}

    }

    /**
     * Method pops last value from stack stored under given key. If stack is
     * empty or nothing under given key is stored exception is thrown.
     * 
     * @param name
     *            given key
     * @return value from stack
     */
    public ValueWrapper pop(String name) {
	testInputString(name);
	MultistackEntry forExport;
	if (isEmpty(name)) {
	    throw new IllegalArgumentException("Stack is empty");
	} else {
	    forExport = multiStack.get(name);
	    multiStack.put(name, forExport.getNext());
	    if (forExport.next == null) {
		multiStack.remove(name);
	    }
	    return forExport.getValue();
	}
    }

    /**
     * Method returns last value from stack stored under given key. If stack is
     * empty or nothing under given key is stored exception is thrown.
     * 
     * @param name
     *            given key
     * @return value from stack
     */
    public ValueWrapper peek(String name) {
	testInputString(name);
	MultistackEntry forExport;
	if (isEmpty(name)) {
	    throw new IllegalArgumentException("Stack is empty");
	} else {
	    forExport = multiStack.get(name);
	    return forExport.getValue();
	}
    }

    /**
     * Checks if ObjectMultiStack is empty for given key.
     * 
     * @param name
     *            given key
     * @return true if is empty, false otherwise.
     */
    public boolean isEmpty(String name) {
	testInputString(name);
	if (multiStack.containsKey(name)) {
	    return false;
	}
	return true;
    }

    /**
     * Method tests input string. If null exception is thrown.
     * 
     * @param name
     *            input string.
     */
    private void testInputString(String name) {
	if (name == null) {
	    throw new IllegalArgumentException(
		    "Given key string can't be null!");
	}
    }
}
