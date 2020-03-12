package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Sine or inverse Sine function depending on flag provided in execute method.
 * 
 * @author Ivan Pavić
 * 
 */
public class SinStatement implements OneOperatorStatement {

    @Override
    public String execute(String text, boolean inv) {
	if (inv) {
	    return String.valueOf(Math.asin(Double.valueOf(text)));
	}
	return String.valueOf(Math.sin(Double.valueOf(text)));
    }

}
