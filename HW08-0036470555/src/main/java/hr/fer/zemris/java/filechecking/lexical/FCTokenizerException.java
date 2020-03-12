package hr.fer.zemris.java.filechecking.lexical;

import hr.fer.zemris.java.filechecking.FCException;

/**
 * Exception describes errors in tokenization of program.
 * 
 * @author Ivan Pavić
 */
public class FCTokenizerException extends FCException {
    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public FCTokenizerException() {
    }

    /**
     * Constructor with message.
     * 
     * @param message
     *            description of failure.
     */
    public FCTokenizerException(String message) {
	super(message);
    }

}
