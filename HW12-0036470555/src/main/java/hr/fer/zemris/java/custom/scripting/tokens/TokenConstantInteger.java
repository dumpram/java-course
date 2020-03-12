package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * TokenConstantInteger
 * <p>
 * Inherits Token and has single read-only int property: value.
 * </p>
 * @author Ivan Pavić
 * @version 1.0
 */
public class TokenConstantInteger extends Token {
	private int value;
	
	
	
public TokenConstantInteger(int value) {
		super();
		this.value = value;
	}

@Override
	public String asText() {
		return Integer.toString(value);
	}
}
