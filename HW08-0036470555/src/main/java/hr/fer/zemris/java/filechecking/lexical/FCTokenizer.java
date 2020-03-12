package hr.fer.zemris.java.filechecking.lexical;

import hr.fer.zemris.java.filechecking.syntax.nodes.SubsArgument;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tokenizator of program written for file checking project.
 * 
 * @author Ivan Pavić
 */
public class FCTokenizer {

        /**
         * Char array representing program.
         */
        private final char[] data;
        /**
         * Pointer to first unprocessed character.
         */
        private int curPos;
        /**
         * Last token that was made by tokenizator.
         */
        private FCToken currentToken;

        /**
         * Static map created for mapping character in tokens which have value
         * null.
         */
        private static final Map<Character, FCTokenType> mapper;

        /**
         * Initialization of map.
         */
        static {
                mapper = new HashMap<>();
                mapper.put(Character.valueOf(':'), FCTokenType.COLON);
                mapper.put(Character.valueOf('{'), FCTokenType.OPENED_BRACE);
                mapper.put(Character.valueOf('}'), FCTokenType.CLOSED_BRACE);
                mapper.put(Character.valueOf('$'), FCTokenType.DOLLAR);
                mapper.put(Character.valueOf('!'), FCTokenType.NEGATOR);
                mapper.put(Character.valueOf('/'), FCTokenType.SLASH);
                mapper.put(Character.valueOf('@'), FCTokenType.MONKEY);
        }

        /**
         * Collection of keywords in program.
         */
        private static final Set<String> keywords;

        /**
         * Initialization of keywords in program.
         */
        static {
                keywords = new HashSet<>();
                keywords.add("def");
                keywords.add("exists");
                keywords.add("fail");
                keywords.add("terminate");
                keywords.add("format");
                keywords.add("filename");

        }

        /**
         * Constructor accepts code of program as <code>String</code>.
         * 
         * @param program
         *                source code of program
         * @throws FCTokenizerException
         *                 if error occurs
         */
        public FCTokenizer(String program) {
                data = program.toCharArray();
                curPos = 0;
                extractNextToken();
        }

        /**
         * Alternative constructor used in {@link SubsArgument} class for
         * parsing strings.
         * 
         * @param argument
         *                string argument
         * @param b
         *                irrelevant boolean
         */
        public FCTokenizer(String argument, boolean b) {
                data = argument.toCharArray();
                curPos = 0;
                nextStringToken();
        }

        /**
         * Method returns current token.
         * 
         * @return current token.
         */
        public FCToken getCurrentToken() {
                return currentToken;
        }

        /**
         * Method extracts nextToken(delegates to extractNextToken).
         * 
         * @return next token.
         * @throws FCTokenizerException
         *                 if error in process occurs.
         */
        public FCToken nextToken() {
                extractNextToken();
                return getCurrentToken();
        }

        /**
         * Method extract next token and changes current token.
         * 
         * @throws FCTokenizerException
         *                 if error occurs
         */
        private void extractNextToken() {
                if (currentToken != null
                                && currentToken.getTokenType() == FCTokenType.EOF) {
                        throw new FCTokenizerException("No tokens available.");
                }
                skipBlanks();

                if (curPos >= data.length) {
                        currentToken = new FCToken(FCTokenType.EOF, null);
                        return;
                }

                FCTokenType mappedType = mapper.get(Character
                                .valueOf(data[curPos]));
                if (mappedType != null) {
                        currentToken = new FCToken(mappedType, null);
                        curPos++;
                        return;
                }

                if (Character.isLetter(data[curPos]) || data[curPos] == '_'
                                || data[curPos] == '.'
                                || Character.isDigit(data[curPos])) {
                        int startIndex = curPos;
                        curPos++;
                        while (curPos < data.length
                                        && (Character.isLetter(data[curPos])
                                                        || Character.isDigit(data[curPos])
                                                        || data[curPos] == '.'
                                                        || data[curPos] == '_' || data[curPos] == '/')) {
                                curPos++;
                        }
                        int endIndex = curPos;
                        String value = new String(data, startIndex, endIndex
                                        - startIndex);
                        if (keywords.contains(value)) {
                                currentToken = new FCToken(FCTokenType.KEYWORD,
                                                value);
                                return;
                        }
                        if (!value.contains("/")
                                        && !Character.isDigit(value.charAt(0))) {
                                currentToken = new FCToken(FCTokenType.IDENT,
                                                value);
                        } else {
                                currentToken = new FCToken(FCTokenType.PATH,
                                                value);
                        }
                        return;
                } else if (data[curPos] == '"') {
                        int startIndex = ++curPos;
                        while (curPos < data.length && data[curPos] != '"') {
                                curPos++;
                        }
                        if (data[curPos] != '"') {
                                throw new FCTokenizerException(
                                                "Couldn't tokenize string!");
                        }
                        String value = new String(data, startIndex, curPos
                                        - startIndex);
                        curPos++;
                        currentToken = new FCToken(FCTokenType.STRING, value);
                        return;
                }
                throw new FCTokenizerException("Invalid character found: '"
                                + data[curPos] + "'.");
        }

        /**
         * Skips over blanks changing position of curPos. Blanks which are
         * skipped are: <li>' '</li> <li>'\t'</li> <li>'\r'</li> <li>'\n'</li>
         */
        private void skipBlanks() {
                while (curPos < data.length) {
                        char c = data[curPos];
                        if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                                curPos++;
                                continue;
                        }
                        break;
                }
        }

        /**
         * NextStringToken is alternative method of tokenization used in
         * {@link SubsArgument} class. Works only with string arguments.
         */
        public void nextStringToken() {
                if (currentToken != null
                                && currentToken.getTokenType() == FCTokenType.EOF) {
                        throw new FCTokenizerException("No tokens available.");
                }

                skipBlanks();

                if (curPos >= data.length) {
                        currentToken = new FCToken(FCTokenType.EOF, null);
                        return;
                }

                FCTokenType mappedType = mapper.get(Character
                                .valueOf(data[curPos]));
                if (mappedType != null || mappedType == FCTokenType.SLASH) {
                        currentToken = new FCToken(mappedType, null);
                        curPos++;
                        return;
                }
                int startIndex = curPos;
                while (curPos < data.length
                                && (mappedType == null || mappedType == FCTokenType.SLASH)) {
                        mappedType = mapper
                                        .get(Character.valueOf(data[curPos]));
                        if (mappedType == null
                                        || mappedType == FCTokenType.SLASH) {
                                curPos++;
                        }
                }
                String path = new String(data, startIndex, curPos - startIndex);
                currentToken = new FCToken(FCTokenType.PATH, path);
                return;
        }
}
