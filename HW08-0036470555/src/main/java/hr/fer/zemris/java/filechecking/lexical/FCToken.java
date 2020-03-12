package hr.fer.zemris.java.filechecking.lexical;

/**
 * Class model one token of input program.
 * 
 * @author Ivan Pavić
 */
public class FCToken {

    /**
     * Type of token.
     */
    private final FCTokenType tokenType;
    /**
     * Value of token.
     */
    private final Object value;

    /**
     * Constructor accepts two arguments.
     * 
     * @param tokenType
     *            given type
     * @param value
     *            given value
     */
    public FCToken(FCTokenType tokenType, Object value) {
	super();
	if (tokenType == null) {
	    throw new IllegalArgumentException("Token type can not be null.");
	}
	this.tokenType = tokenType;
	this.value = value;
    }

    /**
     * Getter for token type.
     * 
     * @return type of token.
     */
    public FCTokenType getTokenType() {
	return tokenType;
    }

    /**
     * Getter for token value.
     * 
     * @return value of token or <code>null</code> if token of this kind have no
     *         certain value
     */
    public Object getValue() {
	return value;
    }
}
