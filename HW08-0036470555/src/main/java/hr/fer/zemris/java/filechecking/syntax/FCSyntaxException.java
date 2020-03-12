package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.FCException;

/**
 * Exception in syntax analysis of program.
 * 
 * @author Ivan Pavić
 */
public class FCSyntaxException extends FCException {
    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public FCSyntaxException() {
    }

    /**
     * Constructor with message.
     * 
     * @param message
     *            description of error
     */
    public FCSyntaxException(String message) {
	super(message);
    }

}
