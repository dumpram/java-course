package hr.fer.zemris.tecaj.hw6.problem1b;

/**
 * IntegerStorageObserver interface is abstraction of all
 * IntegerStorageObservers.
 * 
 * @author Ivan Pavić
 * 
 */
public interface IntegerStorageObserver {
    /**
     * Method that notifies observer that value of given is changed.
     * 
     * @param istorage
     *            given IntegerStorageChange object
     */
    public void valueChanged(IntegerStorageChange istorage);

}
