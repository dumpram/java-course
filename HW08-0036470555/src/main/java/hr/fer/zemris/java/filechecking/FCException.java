package hr.fer.zemris.java.filechecking;

/**
 * Class represents root exception for all exceptions in filechekcing package.
 * 
 * @author Ivan Pavić
 * 
 */
public class FCException extends RuntimeException {
        /**
         * Serial version.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         */
        public FCException() {
        }

        /**
         * Constructor with description.
         * 
         * @param message
         *                description
         */
        public FCException(final String message) {
                super(message);
        }

}
