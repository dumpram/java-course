package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.nodes.DefStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FCNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FileStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.TerminateStatement;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Implementation of parser for file checking program language.
 * 
 * @author Ivan Pavić
 */
public class FCParser {

    /**
     * Tokenizer of source code.
     */
    private final FCTokenizer tokenizer;
    /**
     * Node representing parsed program.
     */
    private final ProgramNode programNode = new ProgramNode(
	    new ArrayList<FCNode>());
    /**
     * Private instance of stack.
     */
    private final Stack<ProgramNode> stack = new Stack<>();

    /**
     * Constructor accepts {@link FCTokenizer} class.
     * 
     * @param tokenizer
     *            tokenizator of source code.
     * @throws FCSyntaxException
     *             if error occurs in parsing
     */
    public FCParser(FCTokenizer tokenizer) {
	this.tokenizer = tokenizer;
	parse();
    }

    /**
     * Getter for programNode.
     * 
     * @return programNode tree
     */
    public ProgramNode getProgramNode() {
	return programNode;
    }

    /**
     * Auxiliary method which checks if current token is of given type. Returns
     * <code>true</code> if it is, <code>false</code> otherwise.
     * 
     * @param type
     *            given type
     * @return <code>true</code> if it is
     */
    private boolean isTokenOfType(FCTokenType type) {
	return tokenizer.getCurrentToken().getTokenType() == type;
    }

    /**
     * Auxiliary method which parses with help of given {@link FCTokenizer}
     * class.
     * 
     * @return programNode tree
     */
    private ProgramNode parse() {
	stack.push(programNode);
	boolean invert = false;
	while (true) {
	    invert = false;
	    // Ako je kraj programa, gotovi smo:
	    if (isTokenOfType(FCTokenType.EOF)) {
		break;
	    }
	    if (isTokenOfType(FCTokenType.NEGATOR)) {
		invert = true;
		tokenizer.nextToken();
	    }
	    if (isTokenOfType(FCTokenType.CLOSED_BRACE)) {
		if (stack.peek().equals(programNode)) {
		    throw new FCSyntaxException("Block wasn't opened!");
		}
		stack.pop();
		tokenizer.nextToken();
		continue;
	    }

	    if (!isTokenOfType(FCTokenType.KEYWORD)) {
		throw new FCSyntaxException("Keyword expected.");
	    }
	    if ("def".equals(tokenizer.getCurrentToken().getValue())) {
		if (invert) {
		    throw new FCSyntaxException("Can't invert define!");
		}
		tokenizer.nextToken();
		stack.peek().addStatement(parseDef());
		continue;
	    }
	    if ("exists".equals(tokenizer.getCurrentToken().getValue())) {
		tokenizer.nextToken();
		ExistsStatement newOne = (ExistsStatement) parseExists(invert);
		stack.peek().addStatement(newOne);
		if (newOne.isBlock()) {
		    stack.push(newOne);
		}
		continue;
	    }
	    if ("filename".equals(tokenizer.getCurrentToken().getValue())) {
		tokenizer.nextToken();
		FileStatement newOne = parseFile(invert);
		stack.peek().addStatement(newOne);
		if (newOne.isBlock()) {
		    stack.push(newOne);
		}
		continue;
	    }
	    if ("fail".equals(tokenizer.getCurrentToken().getValue())) {
		tokenizer.nextToken();
		FailStatement newOne = parseFail(invert);
		stack.peek().addStatement(newOne);
		if (newOne.isBlock()) {
		    stack.push(newOne);
		}
		continue;
	    }
	    if ("format".equals(tokenizer.getCurrentToken().getValue())) {
		tokenizer.nextToken();
		FormatStatement newOne = parseFormat(invert);
		stack.peek().addStatement(newOne);
		if (newOne.isBlock()) {
		    stack.push(newOne);
		}
		continue;
	    }
	    if ("terminate".equals(tokenizer.getCurrentToken().getValue())) {
		if (invert) {
		    throw new FCSyntaxException("Can't invert terminate!");
		}
		tokenizer.nextToken();
		stack.peek().addStatement(new TerminateStatement());
		continue;
	    }

	    throw new FCSyntaxException("Unexpected keyword found.");
	}
	if (!stack.peek().equals(programNode)) {
	    throw new FCSyntaxException("Missing closing brace!!!");
	}
	return programNode;
    }

    /**
     * Method parses FormatStatement. Expects boolean invert as argument.
     * 
     * @param invert
     *            parameter which inverts condition of statement
     * @return new {@link FormatStatement}
     */
    private FormatStatement parseFormat(boolean invert) {
	String format;

	String message = null;

	boolean isBlock = false;

	if (!isTokenOfType(FCTokenType.IDENT)) {
	    throw new FCSyntaxException("Expected file format as argument!!");
	}

	format = (String) tokenizer.getCurrentToken().getValue();

	tokenizer.nextToken();

	if (isTokenOfType(FCTokenType.MONKEY)) {
	    tokenizer.nextToken();
	    if (isTokenOfType(FCTokenType.STRING)) {
		message = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
	    } else {
		throw new FCSyntaxException(
			"String argument expected in fail message!");
	    }
	}
	if (isTokenOfType(FCTokenType.OPENED_BRACE)) {
	    isBlock = true;
	    tokenizer.nextToken();
	}

	FormatStatement newOne = new FormatStatement(format, message, invert,
		isBlock, new ArrayList<FCNode>());

	return newOne;
    }

    /**
     * Method parses FailStatement. Expects boolean invert as argument.
     * 
     * @param invert
     *            parameter which inverts condition of statement
     * @return new {@link FailStatement}
     */
    private FailStatement parseFail(boolean invert) {
	String message = null;

	boolean isBlock = false;

	if (isTokenOfType(FCTokenType.MONKEY)) {
	    tokenizer.nextToken();
	    if (isTokenOfType(FCTokenType.STRING)) {
		message = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
	    } else {
		throw new FCSyntaxException(
			"String argument expected in fail message!");
	    }
	}
	if (isTokenOfType(FCTokenType.OPENED_BRACE)) {
	    isBlock = true;
	    tokenizer.nextToken();
	}

	FailStatement newOne = new FailStatement(message, invert, isBlock,
		new ArrayList<FCNode>());

	return newOne;
    }

    /**
     * Method parses FileStatement. Expects boolean invert as argument.
     * 
     * @param invert
     *            parameter which inverts condition of statement
     * @return new {@link FileStatement}
     */
    private FileStatement parseFile(boolean invert) {
	boolean caseSensitivity = true;

	String argument;

	String message = null;

	boolean isBlock = false;

	if (isTokenOfType(FCTokenType.IDENT)) {
	    if (((String) tokenizer.getCurrentToken().getValue())
		    .compareTo("i") != 0) {
		throw new FCSyntaxException(
			"Expected symbol  'i' for case sensitivity control!");
	    }
	    caseSensitivity = false;
	    tokenizer.nextToken();
	}

	if (!isTokenOfType(FCTokenType.STRING)) {
	    throw new FCSyntaxException("String argument expected!");
	}

	argument = (String) tokenizer.getCurrentToken().getValue();

	tokenizer.nextToken();

	if (isTokenOfType(FCTokenType.MONKEY)) {
	    tokenizer.nextToken();
	    if (isTokenOfType(FCTokenType.STRING)) {
		message = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
	    } else {
		throw new FCSyntaxException(
			"String argument expected in fail message!");
	    }
	}
	if (isTokenOfType(FCTokenType.OPENED_BRACE)) {
	    isBlock = true;
	    tokenizer.nextToken();
	}

	FileStatement newOne = new FileStatement(caseSensitivity, argument,
		message, invert, isBlock, new ArrayList<FCNode>());

	return newOne;
    }

    /**
     * Method parses ExistsStatement. Expects boolean invert as argument.
     * 
     * @param invert
     *            parameter which inverts condition of statement
     * @return new {@link ExistsStatement}
     */
    private FCNode parseExists(boolean invert) {

	String variable;

	String argument;

	String message = null;

	boolean isBlock = false;

	if (!isTokenOfType(FCTokenType.IDENT)) {
	    throw new FCSyntaxException("File specifier dir or file expected");
	}

	variable = (String) tokenizer.getCurrentToken().getValue();

	tokenizer.nextToken();

	if (!isTokenOfType(FCTokenType.STRING)) {
	    throw new FCSyntaxException("String argument expected");
	}

	argument = (String) tokenizer.getCurrentToken().getValue();

	tokenizer.nextToken();

	if (isTokenOfType(FCTokenType.MONKEY)) {
	    tokenizer.nextToken();
	    if (isTokenOfType(FCTokenType.STRING)) {
		message = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
	    } else {
		throw new FCSyntaxException("String argument expected");
	    }

	}

	if (isTokenOfType(FCTokenType.OPENED_BRACE)) {
	    isBlock = true;
	    tokenizer.nextToken();
	}

	ExistsStatement newOne = new ExistsStatement(variable, argument,
		message, invert, isBlock, new ArrayList<FCNode>());

	return newOne;
    }

    /**
     * Method parses DefStatement.
     * 
     * @return new {@link DefStatement}
     */
    private FCNode parseDef() {

	String variable;
	String argument;

	if (!isTokenOfType(FCTokenType.IDENT)) {
	    throw new FCSyntaxException("Variable name expected!");
	}
	variable = (String) tokenizer.getCurrentToken().getValue();

	tokenizer.nextToken();

	if (!isTokenOfType(FCTokenType.STRING)) {
	    throw new FCSyntaxException("String argument was expected!");
	}

	argument = (String) tokenizer.getCurrentToken().getValue();

	tokenizer.nextToken();

	return new DefStatement(variable, argument);
    }
}
