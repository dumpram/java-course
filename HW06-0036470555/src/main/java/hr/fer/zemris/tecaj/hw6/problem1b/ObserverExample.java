package hr.fer.zemris.tecaj.hw6.problem1b;

import java.nio.file.Paths;

/**
 * Example of observer problem from HW06.
 * 
 * @author Ivan Pavić
 * 
 */
public class ObserverExample {
    /**
     * Main method which doesn't require args to run. Example for
     * IntegerStorageObserver with IntegerStorageChange class.
     * 
     * @param args
     *            no arguments are provided
     */
    public static void main(String[] args) {

	IntegerStorage istorage = new IntegerStorage(20);
	IntegerStorageObserver observer = new SquareValue();
	istorage.addObserver(new ChangeCounter());
	istorage.addObserver(new DoubleValue());
	istorage.addObserver(new LogValue(Paths.get("./log.txt")));
	istorage.addObserver(observer);
	istorage.setValue(5);
	istorage.setValue(2);
	istorage.setValue(25);
	istorage.removeObserver(observer);
	istorage.setValue(13);
	istorage.setValue(22);
	istorage.setValue(15);
    }

}
