package hr.fer.zemris.java.gui.calc.input;

/**
 * Class represents input of decimal separator in calculator, therefore it
 * implements InputOperation.
 * 
 * @author Ivan Pavić
 * 
 */
public class DecimalInput implements InputOperation {

    @Override
    public String execute(String text, String suffix, boolean isNew) {
	if (!text.contains(".")) {
	    return text += ".";
	} else if (!text.endsWith(".")) {
	    return text = "0.";
	}
	return text;
    }

}
