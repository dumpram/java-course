package hr.fer.zemris.java.gui.calc.expressions;

/**
 * Expression statement represent any expression statement with arguments in
 * calculator.
 * 
 * @author Ivan Pavić
 * 
 */
public interface ExpressionStatement {
    /**
     * Executes given string expression(depending on implementation).
     * 
     * @param expression
     *            given expression
     * @param suffix
     *            given suffix
     * @param invert
     *            if true inverse is calculated, if there is inverse to function
     * @return String result
     */
    String execute(String expression, String suffix, boolean invert);

}
