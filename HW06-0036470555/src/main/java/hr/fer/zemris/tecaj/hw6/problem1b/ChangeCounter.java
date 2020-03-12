package hr.fer.zemris.tecaj.hw6.problem1b;

/**
 * Instances of ChangeCounter counts (and writes to the standard output) the
 * number of times value stored integer has been changed since the registration.
 * 
 * @author Ivan Pavić
 * 
 */
public class ChangeCounter implements IntegerStorageObserver {
    /**
     * Stores number of times value changed.
     */
    private int counter;

    @Override
    public void valueChanged(IntegerStorageChange istorage) {
	counter++;
	System.out
		.println("Number of value changes since tracking: " + counter);
    }

}
