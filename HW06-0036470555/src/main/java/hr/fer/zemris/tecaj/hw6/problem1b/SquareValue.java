package hr.fer.zemris.tecaj.hw6.problem1b;

/**
 * Instances of SquareValue class write a square of the integer stored in the
 * IntegerStorage to the standard output.
 * 
 * @author Ivan Pavić
 * 
 */
public class SquareValue implements IntegerStorageObserver {
    /**
     * Value that is assigned through observing.
     */
    private int value;

    @Override
    public void valueChanged(IntegerStorageChange istorage) {
	value = istorage.getNewValue();
	System.out.println("Provided new value: " + value + ", square is "
		+ value * value);
    }
}
