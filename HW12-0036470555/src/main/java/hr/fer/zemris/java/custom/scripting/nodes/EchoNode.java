package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;

/**
 * EchoNode
 * <p>
 * Node representing a command which generates some textual output dynamically.
 * It inherits from Node class.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 */
public class EchoNode extends Node {
	private final Token[] tokens;

	public EchoNode(Token[] tokens) {
		super();
		this.tokens = tokens;
	}

	public Token[] getTokens() {
		return tokens;
	}

	/**
	 * This method returns String representation of EchoNode
	 */
	@Override
	public String toString() {
		String temp = "";
		temp += "{$= ";
		for (int j = 0; j < tokens.length; j++) {
			if (tokens[j] instanceof TokenString) {
				temp += " \"" + tokens[j].asText() + "\"";
			} else {
				temp += " " + tokens[j].asText();
			}
		}
		temp += " $}";
		return temp;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
