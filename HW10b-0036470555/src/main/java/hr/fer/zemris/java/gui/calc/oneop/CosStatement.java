package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Cosine or inverse cosine function depending on flag provided in execute
 * method.
 * 
 * @author Ivan Pavić
 * 
 */
public class CosStatement implements OneOperatorStatement {

    @Override
    public String execute(String text, boolean inv) {
	if (inv) {
	    return String.valueOf(Math.acos(Double.valueOf(text)));
	}
	return String.valueOf(Math.cos(Double.valueOf(text)));
    }
}
