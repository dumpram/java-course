package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Tan or inverse tan function depending on flag provided in execute method.
 * 
 * @author Ivan Pavić
 * 
 */
public class TanStatement implements OneOperatorStatement {

    @Override
    public String execute(String text, boolean inv) {
	if (inv) {
	    return String.valueOf(Math.atan(Double.valueOf(text)));
	}
	return String.valueOf(Math.tan(Double.valueOf(text)));
    }
}
