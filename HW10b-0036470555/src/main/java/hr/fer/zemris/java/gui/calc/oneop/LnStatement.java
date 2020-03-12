package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Class represent natural logarithm. If inverted it exponential function.
 * 
 * @author Ivan Pavić
 * 
 */
public class LnStatement implements OneOperatorStatement {

    @Override
    public String execute(String text, boolean inv) {
	if (inv) {
	    return String.valueOf(Math.exp(Double.valueOf(text)));
	}
	return String.valueOf(Math.log(Double.valueOf(text)));
    }
}
