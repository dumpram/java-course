package hr.fer.zemris.java.filechecking.FCProgramChecker;

import hr.fer.zemris.java.filechecking.FCException;
import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;

import java.util.ArrayList;
import java.util.List;

/**
 * FCProgramChecker class checks given program string for errors. Errors are
 * memorized in private list of errors.
 * 
 * @author Ivan Pavić
 * 
 */
public class FCProgramChecker {
        /**
         * Private list of errors.
         */
        private final List<String> errors = new ArrayList<>();

        /**
         * Constructor for FCProgramChecker accepts string program. Job of
         * parsing is delegated to {@link FCParser} and {@link FCTokenizer}.
         * 
         * @param program
         *                given program string
         */
        public FCProgramChecker(final String program) {
                FCTokenizer tokenizer = new FCTokenizer(program);
                while (tokenizer.getCurrentToken().getTokenType() != FCTokenType.EOF) {
                        try {
                                @SuppressWarnings("unused")
                                FCParser parser = new FCParser(tokenizer);
                        } catch (Exception error) {
                                if (error instanceof FCException) {
                                        try {
                                                tokenizer.nextToken();
                                        } catch (Exception error1) {
                                                errors.add("Critical error:"
                                                                + error1.getMessage());
                                                break;
                                        }
                                        errors.add(error.getMessage());
                                        continue;
                                }
                        }
                }
        }

        /**
         * Returns true if there is more than zero errors, false otherwise.
         * 
         * @return as described above
         */
        public final boolean hasErrors() {
                return !errors.isEmpty();
        }

        /**
         * Getter for list of errors.
         * 
         * @return list of errors
         */
        public final List<String> errors() {
                return new ArrayList<>(errors);
        }
}
