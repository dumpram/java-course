package hr.fer.zemris.tecaj.hw6.problem1b;

import java.util.ArrayList;
import java.util.List;

public class IntegerStorage {
    /**
     * Integer value which is stored.
     */
    private int value;
    /**
     * List of storage observers.
     */
    private final List<IntegerStorageObserver> observers;

    /**
     * Constructs IntegerStorage with given value.
     * 
     * @param initialValue
     */
    public IntegerStorage(int initialValue) {
	this.value = initialValue;
	observers = new ArrayList<IntegerStorageObserver>();
    }

    /**
     * Adds the given observer in observers if not already there.
     * 
     * @param observer
     *            given observer
     */
    public void addObserver(IntegerStorageObserver observer) {
	if (observer == null) {
	    throw new IllegalArgumentException("Observer can't be null");
	}
	if (!observers.contains(observer)) {
	    observers.add(observer);
	}
    }

    /**
     * Removes the given observer from observers if present.
     * 
     * @param observer
     *            given observer
     */
    public void removeObserver(IntegerStorageObserver observer) {
	if (observer == null) {
	    throw new IllegalArgumentException("Observer can't be null");
	}
	observers.remove(observer);
    }

    /**
     * Removes all observers from observers list.
     */
    public void clearObservers() {
	observers.removeAll(observers);
    }

    /**
     * Gets current value in integer storage.
     * 
     * @return
     */
    public int getValue() {
	return value;
    }

    /**
     * Changes current value of integer storage to given value and notifies all
     * observers.
     * 
     * @param value
     *            given value
     */
    public void setValue(int value) {
	// Only if new value is different than the current value:
	if (this.value != value) {
	    // Update current value
	    IntegerStorageChange forExport = new IntegerStorageChange(this,
		    getValue(), value);
	    this.value = value;
	    // Notify all registered observers
	    if (observers != null) {
		for (IntegerStorageObserver observer : new ArrayList<>(
			this.observers)) {
		    observer.valueChanged(forExport);
		}
	    }
	}
    }
}
