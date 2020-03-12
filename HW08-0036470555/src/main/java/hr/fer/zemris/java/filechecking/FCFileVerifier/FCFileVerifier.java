package hr.fer.zemris.java.filechecking.FCFileVerifier;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class FCFileVerifier collects errors in execution of given program. Errors
 * are added by {@link FCExecutor} class.
 * 
 * @author Ivan Pavić
 * 
 */
public class FCFileVerifier {
        /**
         * Private array of errors.
         */
        private final List<String> errors = new ArrayList<>();

        /**
         * Constructor for FCFileVerifier accepts 4 arguments. File which
         * content is checked, real fileName, program code and map of
         * InitialData which contains some of the basic variables for program.
         * They are delegated to {@link FCExecutor} class.
         * 
         * @param file
         *                file that is checked
         * @param fileName
         *                real file name
         * @param program
         *                program to execute
         * @param initialData
         *                initial data an basic variables
         */
        public FCFileVerifier(final File file, final String fileName,
                        final String program,
                        final Map<String, Object> initialData) {
                FCExecutor executor = new FCExecutor(new FCParser(
                                new FCTokenizer(program)).getProgramNode(),
                                this);
                executor.addVariable("file", file.getAbsoluteFile().toString());
                executor.addVariable("fileName", fileName);
                executor.addVariable("jmbag", (String) initialData.get("jmbag"));
                executor.addVariable("firstName",
                                (String) initialData.get("firstName"));
                executor.addVariable("lastName",
                                (String) initialData.get("lastName"));
                executor.execute(executor.getProgramNode());
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

        /**
         * Adds errors to list.
         * 
         * @param message
         *                error message
         */
        public final void addError(String message) {
                if (message == null) {
                        message = "Error occured! No message provided!";
                }
                errors.add(message);
        }

}
