package hr.fer.zemris.java.gui.calc.expressions;

/**
 * ArithmeticStatment is expression statement which represents addition,
 * subtraction, division etc.
 * 
 * @author Ivan Pavić
 * 
 */
public class ArithmeticStatement implements ExpressionStatement {

    @Override
    public String execute(String expression, String suffix, boolean invert) {
	char last = expression.charAt(expression.length() - 1);
	if (Character.isDigit(last) || last == '.') {
	    return expression + suffix;
	}
	return expression.substring(0, expression.length() - 1) + suffix;
    }

}
