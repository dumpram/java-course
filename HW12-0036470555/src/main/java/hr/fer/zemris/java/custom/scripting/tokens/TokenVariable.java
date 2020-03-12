package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * TokenVariable
 * <p>
 * Inherits Token and has single read-only String property: name.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 */
public class TokenVariable extends Token {
	private final String name;

	public TokenVariable(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}
}
