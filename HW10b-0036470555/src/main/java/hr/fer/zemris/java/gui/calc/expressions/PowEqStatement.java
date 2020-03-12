package hr.fer.zemris.java.gui.calc.expressions;

public class PowEqStatement implements EqualStatementExecution {

    @Override
    public double execute(double operand1, double operand2, boolean invert) {
	if (invert) {
	    return Math.pow(operand1, 1 / operand2);
	}
	return Math.pow(operand1, operand2);
    }
}
