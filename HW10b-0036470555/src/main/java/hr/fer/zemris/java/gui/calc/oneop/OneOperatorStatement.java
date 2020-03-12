package hr.fer.zemris.java.gui.calc.oneop;

/**
 * Interface represents generic statement with one operand. Implementations
 * would be functions like sin, cos, ln etc.
 * 
 * @author Ivan Pavić
 * 
 */
public interface OneOperatorStatement {
    /**
     * Text is input argument for function from which return value is
     * calculated.
     * 
     * @param text
     *            input argument
     * @param inv
     *            flag, true if function is inverted, false otherwise
     * @return String representing result
     */
    String execute(String text, boolean inv);

}
