package hr.fer.zemris.java.gui.calc.input;

/**
 * Interface represents generic InputOperation in calculator output.
 * 
 * @author Ivan Pavić
 * 
 */
public interface InputOperation {
    /**
     * Executes input operation by alternating given text with provided suffix.
     * 
     * @param text
     *            given text
     * @param suffix
     *            provided suffix
     * @param isNew
     *            flag; if true result is on output and it cannot be changed,
     *            false otherwise
     * @return
     */
    String execute(String text, String suffix, boolean isNew);

}
