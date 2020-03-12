package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Method flips provided number. Classical 1/x operation.
 * 
 * @author Ivan Pavić
 * 
 */
public class FlipStatement implements OneOperatorStatement {

    @Override
    public String execute(String text, boolean inv) {
	return String.valueOf(1 / Double.valueOf(text));
    }

}
