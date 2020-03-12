package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * TokenOperator
 * <p>
 * Inherits Token and has single read-only String property: symbol.
 * </p>
 * @author Ivan Pavić
 * @version 1.0
 */
public class TokenOperator extends Token {
	private String symbol;
	
	
	public TokenOperator(String symbol) {
		super();
		this.symbol = symbol;
	}


	@Override
	public String asText() {
		return symbol;
	}

}
