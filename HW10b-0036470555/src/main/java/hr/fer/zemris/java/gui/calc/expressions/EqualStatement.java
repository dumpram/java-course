package hr.fer.zemris.java.gui.calc.expressions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EqualStatment is by far most important. Its task is to calculate given
 * expression that was typed into calculator. Also it has methods for getting
 * first operand, changing sign, changing second operand etc.
 * 
 * @author Ivan Pavić
 * 
 */
public class EqualStatement implements ExpressionStatement {
    /**
     * Map of statements that can be executed.
     */
    private final Map<Character, EqualStatementExecution> mapOfStatements = new HashMap<>();
    /**
     * Regex for parsing input.
     */
    private final static String REGEX = "([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)(\\+|-|/|\\*|\\^)([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)?";

    /**
     * Constructor creates map of statements.
     */
    public EqualStatement() {
	mapOfStatements.put(Character.valueOf('+'), new AddEqStatement());
	mapOfStatements.put(Character.valueOf('-'), new SubEqStatement());
	mapOfStatements.put(Character.valueOf('/'), new DivEqStatement());
	mapOfStatements.put(Character.valueOf('^'), new PowEqStatement());
	mapOfStatements.put(Character.valueOf('*'), new MulEqStatement());
    }

    @Override
    public String execute(String expression, String suffix, boolean invert) {
	Pattern p = Pattern.compile(REGEX);
	Matcher m = p.matcher(expression);
	m.matches();
	if (!m.matches()) {
	    return Double.valueOf(expression).toString();
	}
	Character key = m.group(3).charAt(0);
	if (m.group(4) == null) {
	    Double operand = Double.valueOf(m.group(1));
	    return String.valueOf(mapOfStatements.get(key).execute(operand,
		    operand, invert))
		    + key.toString() + String.valueOf(operand);
	}
	Double operand1 = Double.valueOf(m.group(1));
	Double operand2 = Double.valueOf(m.group(4));
	return String.valueOf(mapOfStatements.get(key).execute(operand1,
		operand2, invert))
		+ key.toString() + String.valueOf(operand2);
    }

    /**
     * Gets output by calculating expression.
     * 
     * @param expression
     *            given expression
     * @param invert
     *            invert flag
     * @return String representing output
     */
    public String getOutput(String expression, boolean invert) {
	Pattern p = Pattern.compile(REGEX);
	Matcher m = p.matcher(expression);
	m.matches();

	if (!m.matches()) {
	    return Double.valueOf(expression).toString();
	}
	Character key = m.group(3).charAt(0);

	if (m.group(4) == null) {
	    Double operand = Double.valueOf(m.group(1));
	    return String.valueOf(mapOfStatements.get(key).execute(operand,
		    operand, invert));
	}
	Double operand1 = Double.valueOf(m.group(1));
	Double operand2 = Double.valueOf(m.group(4));
	return String.valueOf(mapOfStatements.get(key).execute(operand1,
		operand2, invert));
    }

    /**
     * Gets first operand from some expression.
     * 
     * @param expression
     *            given expression
     * @return first operand
     */
    public String getFirstOperand(String expression) {
	Pattern p = Pattern.compile(REGEX);
	Matcher m = p.matcher(expression);
	m.matches();

	if (!m.matches()) {
	    return Double.valueOf(expression).toString();
	}
	Double operand = Double.valueOf(m.group(1));
	return String.valueOf(operand);
    }

    /**
     * Changes sign of current output text. It is important to make change in
     * expression as well. The change is made with this method.
     * 
     * @param text
     *            output
     * @param expression
     *            given expression
     * @return expression with changed sign
     */
    public String changeSign(String text, String expression) {
	int index = expression.indexOf(text);
	return expression.substring(0, index)
		+ String.valueOf(-Double.valueOf(text));
    }

    /**
     * Method changes second argument. It is useful for implementation of pop
     * command and clear command.
     * 
     * @param expression
     *            given expression
     * @return expression with changed argument
     */
    public String changeSecondArgument(String expression) {

	Pattern p = Pattern.compile(REGEX);
	Matcher m = p.matcher(expression);
	m.matches();

	if (!m.matches()) {
	    return Double.valueOf(expression).toString();
	}
	Character key = m.group(3).charAt(0);

	if (m.group(4) == null) {
	    return expression;
	}
	Double operand1 = Double.valueOf(m.group(1));
	return operand1 + key.toString();
    }

    /**
     * Mainly used for pop command. Method changes argument. It changes output
     * with given text in expression.
     * 
     * @param expression
     *            given expression
     * @param text
     *            given replacement text
     * @param output
     *            text to replace
     * @return expression with replacements
     */
    public String changeArgument(String expression, String text, String output) {
	int index = expression.indexOf(output);
	return expression.substring(0, index) + text;
    }
}
