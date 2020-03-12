package hr.fer.zemris.tecaj.hw6.problem1b;

/**
 * Instances of IntegerStorageChange class encapsulate (as read-only properties)
 * following information: <li>(a) a reference to IntegerStorage</li> <li>(b) the
 * value of stored integer before the change has occurred</li> <li>
 * (c) the new value</li> of currently stored integer.
 * 
 * @author Ivan Pavić
 * 
 */
public class IntegerStorageChange {
    /**
     * Private internal integer storage.
     */
    private final IntegerStorage istorage;
    /**
     * Old value of storage.
     */
    private final int oldValue;
    /**
     * New Value of storage.
     */
    private final int newValue;

    /**
     * Getter for private integer storage.
     * 
     * @return integer storage
     */
    public IntegerStorage getIstorage() {
	return istorage;
    }

    public IntegerStorageChange(IntegerStorage istorage, int oldValue,
	    int newValue) {
	if (istorage == null) {
	    throw new IllegalArgumentException("Storage can't be null");
	}
	this.istorage = istorage;
	this.oldValue = oldValue;
	this.newValue = newValue;
    }

    /**
     * Getter for old value in storage.
     * 
     * @return
     */
    public int getOldValue() {
	return oldValue;
    }

    /**
     * Getter for new value in storage.
     * 
     * @return
     */
    public int getNewValue() {
	return newValue;
    }
}
