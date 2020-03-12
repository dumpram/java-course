package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * TokenString
 * <p>
 * Inherits Token and has single read-only String property: value.
 * </p>
 * @author Ivan Pavić
 * @version 1.0
 */
public class TokenString extends Token {
	private String value;
	
	public TokenString(String token) {
		this.value = token;
	}

	@Override
	public String asText() {
		return value;
	}

}
