package hr.fer.zemris.java.custom.scripting.nodes;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * ForLoopNode
 * <p>
 * Node representing a single for-loop construct. It inherits from Node class.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 */
public class ForLoopNode extends Node {
	private final TokenVariable variable;
	private final Token startExpression;
	private final Token endExpression;
	private Token stepExpression;

	public ForLoopNode(TokenVariable variable, Token startExpression, Token endExpression, Token stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ForLoopNode(TokenVariable variable, Token startExpression, Token endExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
	}

	public TokenVariable getVariable() {
		return variable;
	}
	public Token getStartExpression() {
		return startExpression;
	}
	public Token getEndExpression() {
		return endExpression;
	}
	public Token getStepExpression() {
		return stepExpression;
	}

	/**
	 * This method returns String representation of ForLoopNode
	 */
	@Override
	public String toString() {
		StringBuffer temp = new StringBuffer();
		temp.append("{$ FOR " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText());
		if (stepExpression != null) {
			temp.append(" " + stepExpression.asText());
		}
		temp.append(" $}");
		for (Node child : children) {
			temp.append(child.toString());
		}
		temp.append("{$ END $}");
		return temp.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
