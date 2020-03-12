package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Class represents logarithm with base 10. Inverse function is power of 10.
 * 
 * @author Ivan Pavić
 * 
 */
public class LogStatement implements OneOperatorStatement {

    @Override
    public String execute(String text, boolean inv) {
	if (inv) {
	    return String.valueOf(Math.pow(10, Double.valueOf(text)));
	}
	return String.valueOf(Math.log10(Double.valueOf(text)));
    }

}
