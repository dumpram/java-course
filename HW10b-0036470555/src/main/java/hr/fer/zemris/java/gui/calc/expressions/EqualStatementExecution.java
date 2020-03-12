package hr.fer.zemris.java.gui.calc.expressions;

/**
 * Interface represent solution to operations with arguments. Contains execute
 * method.
 * 
 * @author Ivan Pavić
 * 
 */
public interface EqualStatementExecution {
    /**
     * Executes operation between operands.
     * 
     * @param operand1
     *            first operand
     * @param operand2
     *            second operand
     * @param invert
     *            if true inverse operation is calculated, if inverse function
     *            is defined
     * @return double value result
     */
    double execute(double operand1, double operand2, boolean invert);

}
