package hr.fer.zemris.tecaj.hw6.problem1b;

/**
 * Instances of DoubleValue class write to the standard output double value of
 * the current value which is stored in subject, but only first two times since
 * its registration with subject; after writing the double value for the second
 * time, the observer automatically unregisters itself from the subject.
 * 
 * @author Ivan Pavić
 * 
 */
public class DoubleValue implements IntegerStorageObserver {
    /**
     * Counter is set to 2, after value changes it decreases by one.
     */
    private int counter = 2;
    /**
     * Stored value which is observed.
     */
    private int value;

    @Override
    public void valueChanged(IntegerStorageChange istorage) {
	if (counter == 0) {
	    istorage.getIstorage().removeObserver(this);
	} else {
	    value = istorage.getNewValue();
	    System.out.println("Double value: " + value * 2);
	    counter--;
	}

    }

}
