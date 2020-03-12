package hr.fer.zemris.java.gui.calc.expressions;

/**
 * Operation of addition between to operands.
 * 
 * @author Ivan Pavić
 * 
 */
public class AddEqStatement implements EqualStatementExecution {

    @Override
    public double execute(double operand1, double operand2, boolean invert) {
	return operand1 + operand2;
    }

}
