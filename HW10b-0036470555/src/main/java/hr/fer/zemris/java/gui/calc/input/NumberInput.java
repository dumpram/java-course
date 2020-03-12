package hr.fer.zemris.java.gui.calc.input;

/**
 * Class represents input of number in calculator, therefore extends
 * {@link InputOperation}.
 * 
 * @author Ivan Pavić
 * 
 */
public class NumberInput implements InputOperation {

    @Override
    public String execute(String text, String suffix, boolean isNew) {
	if (isNew) {
	    text = suffix;
	    return text;
	}
	text += suffix;
	return text;
    }
}
