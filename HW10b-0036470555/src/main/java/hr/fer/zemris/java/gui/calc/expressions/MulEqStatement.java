package hr.fer.zemris.java.gui.calc.expressions;

/**
 * Class represent multiplication between two operands.
 * 
 * @author Ivan Pavić
 * 
 */
public class MulEqStatement implements EqualStatementExecution {

    @Override
    public double execute(double operand1, double operand2, boolean invert) {
	return operand1 * operand2;
    }

}
