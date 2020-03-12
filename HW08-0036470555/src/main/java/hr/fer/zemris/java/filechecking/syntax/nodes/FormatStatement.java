package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;
import hr.fer.zemris.java.filechecking.syntax.FCSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of format statement for file checking program. Extends ProgramNode
 * because it can contain block of statements. In this phase only zip file
 * format is supported.
 * 
 * @author Ivan Pavić
 * 
 */
public class FormatStatement extends ProgramNode implements ExecutableStatement {
        /**
         * Private format variable.
         */
        private String format;
        /**
         * Private message.
         */
        private String message;
        /**
         * Private invert flag.
         */
        private boolean invert;
        /**
         * Private isBlock flag.
         */
        private boolean isBlock;
        /**
         * Private list of supported formats.
         */
        private List<String> supportedFormats;

        /**
         * Inherited constructor.
         * 
         * @param statements
         *                list of {@link FCNode} statements
         */
        public FormatStatement(List<FCNode> statements) {
                super(statements);
        }

        /**
         * Constructor for this statement accepts format specifier, failure
         * message, invert flag, isBlock flag and list of block statements.
         * 
         * @param format2
         *                given format specifier
         * @param message
         *                given message
         * @param invert2
         *                given invert flag
         * @param isBlock
         *                given block flag
         * @param statements
         *                given list of statements
         */
        public FormatStatement(String format2, String message, boolean invert2,
                        boolean isBlock, List<FCNode> statements) {
                super(statements);
                this.format = format2;
                this.message = message;
                this.invert = invert2;
                this.isBlock = isBlock;
                support();
                if (!supportedFormats.contains(format) && !invert) {
                        throw new FCSyntaxException("Unsupported format!");
                }
        }

        /**
         * Private method which creates list of supported formats.
         */
        private void support() {
                supportedFormats = new ArrayList<>();
                supportedFormats.add("zip");

        }

        /**
         * Getter for isBlock flag.
         * 
         * @return the isBlock
         */
        public boolean isBlock() {
                return isBlock;
        }

        @Override
        public void execute(FCExecutor executor) {
                if (executor.isStop()) {
                        return;
                }
                String file = executor.getVariable("file");
                if (!invert) {
                        if (file.indexOf(".") == -1) {
                                if (message == null) {
                                        message = "Format mismatch!";
                                }
                                executor.getVerifier().addError(message);
                                return;
                        }
                        if (!supportedFormats.contains(file.substring(file
                                        .indexOf(".") + 1))) {
                                if (message == null) {
                                        message = "Format mismatch!";
                                }
                                executor.getVerifier().addError(message);
                                return;
                        }
                        for (FCNode i : statements) {
                                i.execute(executor);
                        }
                } else {
                        if (supportedFormats.contains(file.substring(file
                                        .indexOf(".") + 1))) {
                                if (message == null) {
                                        message = "Supported format!";
                                }
                                executor.getVerifier().addError(message);
                                return;
                        }
                        for (FCNode i : statements) {
                                i.execute(executor);
                        }
                }
        }
}
