package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;
import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCSyntaxException;

/**
 * Auxiliary class for substitution of arguments while executing.
 * 
 * @author Ivan Pavić
 * 
 */
public class SubsArgument {
        /**
         * Substitutes variables in given string argument.
         * 
         * @param executor
         *                which is executing some statement that invoked this
         *                method
         * @param argument
         *                given string argument
         * @return String with replacements
         */
        public static String supsArgument(FCExecutor executor, String argument) {
                StringBuffer sb = new StringBuffer();
                FCTokenizer tokenizer = new FCTokenizer(argument, false);
                boolean nextIsPackage = false;
                while (tokenizer.getCurrentToken().getTokenType() != FCTokenType.EOF) {
                        if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.COLON) {
                                nextIsPackage = true;
                                sb.append("/");
                                tokenizer.nextStringToken();
                        }
                        if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.SLASH) {
                                sb.append("/");
                                tokenizer.nextStringToken();
                        }
                        if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.PATH) {
                                if (nextIsPackage) {
                                        sb.append(((String) tokenizer
                                                        .getCurrentToken()
                                                        .getValue())
                                                        .replaceAll("\\.", "/"));
                                } else {
                                        sb.append((String) tokenizer
                                                        .getCurrentToken()
                                                        .getValue());
                                }
                                tokenizer.nextStringToken();
                                continue;
                        }
                        if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.DOLLAR) {
                                String key = parseSups(tokenizer);
                                if (executor.getVariable(key) == null) {
                                        throw new FCSyntaxException("Variable "
                                                        + key
                                                        + " wasn't defined!");
                                }
                                String value = new String(
                                                executor.getVariable(key));
                                if (nextIsPackage) {
                                        sb.append(value.replaceAll("\\.", "/"));
                                } else {
                                        sb.append(value);
                                }
                                tokenizer.nextStringToken();
                                continue;
                        }
                }
                return sb.toString();
        }

        /**
         * Auxiliary method which parses substituted variables.
         * 
         * @param tokenizer
         *                given for parsing
         * @return String representing variable which needs to be substituted
         */
        private static String parseSups(FCTokenizer tokenizer) {
                String variable;
                tokenizer.nextToken();
                if (isTokenOfType(FCTokenType.OPENED_BRACE, tokenizer)) {
                        tokenizer.nextToken();
                        if (isTokenOfType(FCTokenType.IDENT, tokenizer)) {
                                variable = ((String) tokenizer
                                                .getCurrentToken().getValue());
                                tokenizer.nextToken();
                                if (!isTokenOfType(FCTokenType.CLOSED_BRACE,
                                                tokenizer)) {
                                        throw new FCSyntaxException(
                                                        "Expected closing brace!");
                                }
                        } else {
                                throw new FCSyntaxException(
                                                "Variable name expected!");
                        }
                } else {
                        throw new FCSyntaxException("Opened brace expected!");
                }
                return variable;
        }

        private static boolean isTokenOfType(FCTokenType type,
                        FCTokenizer tokenizer) {
                return tokenizer.getCurrentToken().getTokenType() == type;
        }

}
