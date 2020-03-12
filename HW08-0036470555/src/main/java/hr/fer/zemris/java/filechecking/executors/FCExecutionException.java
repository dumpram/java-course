package hr.fer.zemris.java.filechecking.executors;

import hr.fer.zemris.java.filechecking.FCException;

/**
 * Exception represents failure in execution.
 * 
 * @author Ivan Pavić
 * 
 */
public class FCExecutionException extends FCException {

        /**
         * Serial version.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor with message.
         * 
         * @param message
         *                ceratin message
         */
        public FCExecutionException(final String message) {
                super(message);
        }

}
