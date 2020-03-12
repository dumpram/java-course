package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * TokenConstantDouble
 * <p>
 * Inherits Token and has single read-only double property: value.
 * </p>
 * @author Ivan Pavić
 * @version 1.0
 */
public class TokenConstantDouble extends Token {
	private double value;
	
		
	public TokenConstantDouble(double value) {
		super();
		this.value = value;
	}


	@Override
	public String asText() {
		return Double.toString(value);
	}

}
