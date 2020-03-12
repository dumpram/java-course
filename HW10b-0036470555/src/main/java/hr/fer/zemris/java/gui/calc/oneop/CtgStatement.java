package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Cot statement represent trigonometry function, or its inverse depending on
 * flag given in execute method.
 * 
 * @author Ivan Pavić
 * 
 */
public class CtgStatement implements OneOperatorStatement {

    @Override
    public String execute(String text, boolean inv) {
	if (inv) {
	    text = new TanStatement().execute(text, inv);
	    return new FlipStatement().execute(text, inv);
	} else {
	    text = new FlipStatement().execute(text, inv);
	    return new TanStatement().execute(text, inv);
	}
    }

}
