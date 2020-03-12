package hr.fer.zemris.tecaj.hw6.problem1a;

/**
 * IntegerStorageObserver interface is abstraction of all
 * IntegerStorageObservers.
 * 
 * @author Ivan Pavić
 * 
 */
public interface IntegerStorageObserver {
    /**
     * Method that notifies observer that value of given istorage is changed.
     * 
     * @param istorage
     *            given IntegerStorage
     */
    public void valueChanged(IntegerStorage istorage);

}
