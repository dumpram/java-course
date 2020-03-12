package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

public class ObjectMultistack {
    /**
     * Map representing multiStack.
     */
    Map<String, MultistackEntry> multiStack = new HashMap<>();

    static class MultistackEntry {

	ValueWrapper value;
	MultistackEntry next;

	public MultistackEntry(ValueWrapper value, MultistackEntry next) {
	    this.value = value;
	    this.next = next;
	}

	public ValueWrapper getValue() {
	    return value;
	}
    }

    public void push(String name, ValueWrapper valueWrapper) {
	testInputString(name);
	if (!multiStack.containsKey(name)) {
	    multiStack.put(name, new MultistackEntry(valueWrapper, null));
	} else {
	    multiStack.put(name,
		    new MultistackEntry(valueWrapper, multiStack.get(name)));
	}

    }

    public ValueWrapper pop(String name) {
	testInputString(name);
	MultistackEntry forExport;
	if (!multiStack.containsKey(name)) {
	    throw new IllegalArgumentException("Stack is empty");
	} else {
	    forExport = multiStack.get(name);
	    multiStack.put(name, forExport.next);
	    return forExport.value;
	}
    }

    public ValueWrapper peek(String name) {
	testInputString(name);
	MultistackEntry forExport;
	if (!multiStack.containsKey(name)) {
	    throw new IllegalArgumentException("Stack is empty");
	} else {
	    forExport = multiStack.get(name);
	    return forExport.value;
	}
    }

    public boolean isEmpty(String name) {
	testInputString(name);
	if (multiStack.containsKey(name)) {
	    return false;
	} else {
	    return true;
	}
    }

    private void testInputString(String name) {
	if (name == null) {
	    throw new IllegalArgumentException(
		    "Given key string can't be null!");
	}
    }
}
