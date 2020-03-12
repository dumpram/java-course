package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * TokenFunction
 * <p>
 * Inherits Token and has single read-only String property: name.
 * </p>
 * @author Ivan Pavić
 * @version 1.0
 */
public class TokenFunction extends Token {
	private String name;
	
	
	public TokenFunction(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
