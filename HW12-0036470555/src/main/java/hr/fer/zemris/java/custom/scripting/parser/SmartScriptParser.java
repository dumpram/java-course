package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

import java.util.Scanner;
/**
 * SmartScriptParser
 * <p>
 * This class is used for parsing simple script which can contain plain text or
 * 3 types of tags. It supports FOR, ECHO, and END tags.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 */
public class SmartScriptParser {

	/* Private variables */

	private final ObjectStack stack = new ObjectStack();
	private final String body;
	private DocumentNode doc = new DocumentNode();
	private final ArrayBackedIndexedCollection TextandTags = new ArrayBackedIndexedCollection();

	/**
	 * TextTag
	 * <p>
	 * This class is used for dividing class from tags. It Contains type
	 * variable which is 0 for text, 1 for tag.
	 * 
	 * @author Ivan Pavić
	 * @version 1.0
	 */

	private class TextTag {
		boolean type;
		int begin;
		int size;

		public TextTag(boolean type, int begin, int size) {
			this.type = type; // 0 for text, 1 for tag
			this.begin = begin; // begin index in body String
			this.size = size; // size of text or tag
		}
	}

	public DocumentNode getDocumentNode() {
		return doc;
	}
	/**
	 * This is constructor for SmartScriptParser. It throws
	 * SmartScriptParserException in case of error in parsing.
	 * 
	 * @param document
	 */
	public SmartScriptParser(String document) {
		body = document;
		doc = new DocumentNode();
		stack.push(doc);
		/*
		 * Try to parse, if can't catch any Exception and throw
		 * SmartScriptParserException
		 */
		try {
			SmartParseMain();
		} catch (Exception e) {
			if (!(e instanceof SmartScriptParserException)) {
				throw new SmartScriptParserException("Something went wrong!");
			} else {
				throw new SmartScriptParserException(e.getMessage());
			}
		}
	}

	/**
	 * This method will delegate the work to other methods of this class
	 */
	private void SmartParseMain() {
		SetTagBoundaries();
		nodeDistribution();
		try {
			Node node = (Node) stack.pop();
			if (node instanceof ForLoopNode) {
				throw new SmartScriptParserException("Missing END tag");
			}
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("Extra END tag");
		}
	}

	/**
	 * SetTagBoundaries method is dividing Text from Tags by adding them to
	 * collection TextandTags.
	 */
	private void SetTagBoundaries() {
		int begin = 0;
		for (int i = 0; i < body.length(); i++) { // Reads from beginning
			if (OpenTag(i) == true) { // if tag is open
				if (begin != i) { // saves previous text (can't save nothing)
					TextandTags.add(new TextTag(false, begin, i - begin));
				}
				begin = i; // tag begins at begin
				i = findCloseTag(i) + 1; // finishes at i

				if ((i - 1) == -1) {
					throw new SmartScriptParserException("Missing $}");
				}
				TextandTags.add(new TextTag(true, begin, i - begin)); // adding
																		// tag
				begin = i + 1; // new text or tag begins
			}
		}
		TextandTags.add(new TextTag(false, begin, body.length() - begin)); // add
																			// what
																			// is
																			// left
	}

	/**
	 * OpenTag
	 * <p>
	 * Checks if tag is open at index i of String "body"
	 * </p>
	 * 
	 * @param i
	 *            index of String "body"
	 * @return Returns true if tag begins, otherwise returns false
	 */
	private boolean OpenTag(int i) {
		if ((i != 0 && body.indexOf("{$", i) == i && body.charAt(i - 1) != '\\')
				|| (i == 0 && body.indexOf("{$", i) == i)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * findCloseTag
	 * <p>
	 * This function is searching for close tag in String "body". at index i.
	 * </p>
	 * 
	 * @param i
	 * @return Return value is index of $} or -1 if $} is not found.
	 */
	private int findCloseTag(int i) {
		for (int j = i; j < body.length(); j++) {
			if (body.charAt(j) == '"') { // if there is a String opening
				j = jumpOver(body, j); // jump over string
			} else if (CloseTag(j) == true) { // if closer is found
				return j; // return position of closer
			}
		}
		return -1;
	}

	/**
	 * CloseTag
	 * <p>
	 * Method determines if "$}" is on position i of String body.
	 * 
	 * @param i
	 * @return Returns true if $} is on i-th position, otherwise false.
	 */
	private boolean CloseTag(int i) {
		if (body.indexOf("$}", i) == i) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * nodeDistribution It delegates the work of building the Document tree to
	 * textDistribution method and tagDistribution method.
	 */

	private void nodeDistribution() {
		TextTag temp;

		/* Idea is to search TextandTags collection and integrate them in tree */

		for (int i = 0; i < TextandTags.size(); i++) {
			temp = (TextTag) TextandTags.get(i);
			if (temp.type == false) {
				textDistribution(temp.begin, temp.size);
			} else {
				tagDistribution(temp.begin, temp.size);
			}
		}
	}

	/**
	 * textDistribution
	 * <p>
	 * Method creates text nodes from text in document and adds in the document
	 * tree.
	 * </p>
	 * 
	 * @param begin
	 *            start index of text
	 * @param size
	 *            represents size of text
	 */

	private void textDistribution(int begin, int size) {
		String text = body.substring(begin, begin + size);
		text = removeEscapes(text);
		Node trenutni = (Node) stack.peek();
		TextNode childText = new TextNode(text);
		trenutni.addChildNode(childText);
	}

	/**
	 * removeEscapes
	 * <p>
	 * Method removes escapes before tag openers("{$").
	 * </p>
	 * 
	 * @param text
	 * @return Return value is String without escape(s) before tag opener(s).
	 */
	private String removeEscapes(String text) {
		int i = 0, t = 0;
		try {
			while (i < text.length() && t != -1) { // finds escaper and removes
													// them if they are
				t = text.indexOf("\\"); // in front of tag opener
				if (t != -1 && t != text.length() - 1 && text.substring(t + 1, t + 3).compareTo("{$") == 0) {
					text = text.substring(0, t) + text.substring(t + 1); // removing
																			// from
																			// text
					i = t;
				}
				i++;
			}
		} catch (IndexOutOfBoundsException e) { // handling situation when there
												// are \\ on end
			return text; // of the string
		}
		return text;
	}

	/**
	 * tagDistribution
	 * <p>
	 * Delegates work to applicable method for the tag of certain type.
	 * </p>
	 * 
	 * @param begin
	 *            start index of tag in String body
	 * @param size
	 *            represents size of tag
	 */
	private void tagDistribution(int begin, int size) {
		String tag = body.substring(begin + 2, begin + size - 1); // remove
																	// opener
																	// and
																	// closer

		/* Depending on the type of tag different methodes are invoked */

		if (tagType(tag) == 1) {
			forNode(tag);
		} else if (tagType(tag) == 2) {
			echoNode(tag);
		} else if (tagType(tag) == 3) {
			endNode(tag);
		}
	}

	/**
	 * forTag
	 * 
	 * @param i
	 *            index where possibly "FOR" may occur
	 * @param tag
	 * @return Return true if parameter tag represents FOR tag, otherwise false.
	 */
	private boolean forTag(int i, String tag) {
		if (tag.length() > 4) {
			String part = tag.substring(i, i + 3);
			if (part.compareToIgnoreCase("for") == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * echoTag
	 * 
	 * @param i
	 *            index where possibly "Echo" may occur
	 * @param tag
	 * @return Return true if parameter tag represents Echo tag, otherwise
	 *         false.
	 */
	private boolean echoTag(int i, String tag) {
		String part = tag.substring(i, i + 1);
		if (part.compareTo("=") == i) {
			return true;
		}
		return false;
	}

	/**
	 * endTag
	 * 
	 * @param i
	 *            index where possibly "END" may occur
	 * @param tag
	 * @return Return true if parameter tag represents END tag, otherwise false.
	 */
	private boolean endTag(int i, String tag) {
		String part = tag.substring(i, i + 3);
		if (part.compareToIgnoreCase("end") == 0) {
			return true;
		}
		return false;
	}

	/**
	 * tagType
	 * <p>
	 * Determines tag type from string.
	 * </p>
	 * 
	 * @param tag
	 * @return Return value is 1 for FOR, 2 for ECHO and 3 for END tag.
	 */

	private int tagType(String tag) {
		for (int i = 0; i < tag.length(); i++) {
			char ch = tag.charAt(i);
			if (forTag(i, tag)) {
				return 1;
			} else if (echoTag(i, tag)) {
				return 2;
			} else if (endTag(i, tag)) {
				return 3;
			} else if (!(Character.isWhitespace(ch))) {
				break;
			}
		}
		throw new SmartScriptParserException("Tag format not supported");
	}
	/**
	 * forNode
	 * <p>
	 * This method creates for node from parameter tag. Adds it into document
	 * tree.
	 * </p>
	 * 
	 * @param tag
	 */
	private void forNode(String tag) {
		String part = tag.trim();
		part = part.substring(3); // odbaci "FOR"
		part = part.trim();
		String tokenString[] = part.split(" ", 4); // split to get tokens
		Token tokens[] = new Token[4];
		Node current = (Node) stack.peek();
		Node newNode;

		for (int i = 0; i < tokenString.length; i++) { // create tokens from
														// string via
														// simpleTokenizer
			tokens[i] = simpleTokenizer(tokenString[i]);
		}

		if (!(tokens[0] instanceof TokenVariable)) {
			throw new SmartScriptParserException("First argument in FOR tag must be variable");
		}

		/* Create Nodes */

		if (tokenString.length == 3) {
			newNode = new ForLoopNode((TokenVariable) tokens[0], tokens[1], tokens[2]);
		} else if (tokenString.length == 4) {
			newNode = new ForLoopNode((TokenVariable) tokens[0], tokens[1], tokens[2], tokens[3]);
		} else {
			throw new SmartScriptParserException("Too many arguments in FOR tag");
		}
		/* Add Node */
		current.addChildNode(newNode);
		stack.push(newNode);
	}
	/**
	 * endNode
	 * <p>
	 * This method handles END tag. Pops stack.
	 * </p>
	 * 
	 * @param tag
	 */
	private void endNode(String tag) {
		tag = tag.trim();
		if (tag.length() > 3) {
			throw new SmartScriptParserException("Invalid End Tag");
		} else {
			stack.pop();
		}
	}
	/**
	 * echoNode
	 * <p>
	 * This method handles echoNode. Collects tokens. Adds EchoNode in Document
	 * tree.
	 * </p>
	 * 
	 * @param tag
	 */
	private void echoNode(String tag) {
		String part = tag.trim();
		Node current = (Node) stack.peek();
		try {
			part = part.substring(1);
		} catch (IndexOutOfBoundsException e) {
			current.addChildNode(new EchoNode(null)); // if tag is empty add
														// null
		}
		ArrayBackedIndexedCollection tokens = new ArrayBackedIndexedCollection();
		Scanner scanner;
		String temp;
		for (int i = 0; i < part.length(); i++) {
			if (part.charAt(i) != '"' && !Character.isWhitespace(part.charAt(i))) {
				scanner = new Scanner(part.substring(i));
				temp = scanner.next(); // scan for token
				tokens.add(simpleTokenizer(temp));// add token
				i += temp.length();
			} else if (part.charAt(i) == '"') { // if in string
				temp = part.substring(i, jumpOver(part, i) + 1); // escape from
																	// string
				i += temp.length();
				temp = temp.substring(1, temp.length() - 1); // remove \" from
																// ends of
																// string
				temp = escapeString(temp); // remove escapes from string
				tokens.add(stringTokenizer(temp));
			}
		}
		Token[] tok = new Token[tokens.size()];
		for (int i = 0; i < tokens.size(); i++) {
			tok[i] = (Token) tokens.get(i);
		}
		current.addChildNode(new EchoNode(tok)); // add to tree
	}
	/**
	 * jumpOver
	 * 
	 * @param tag
	 * @param i
	 * @return Return value is index of end of string occurrence in tag.
	 */
	private int jumpOver(String tag, int i) {
		int j = 0;
		/* Idea is to look for closing \* */

		for (j = i + 1; j < tag.length() - 1 && tag.charAt(j) != '\"'; j++) {
			if (tag.charAt(j) == '\\' && tag.charAt(j + 1) == '"') {
				j++;
			} else if (tag.charAt(i) == '\\' && tag.charAt(i + 1) == '\\') {
				j++;
			} else if (tag.charAt(i) == '\\' && tag.charAt(i + 1) == '\n') {
				j++;
			} else if (tag.charAt(i) == '\\' && tag.charAt(i + 1) == '\r') {
				j++;
			} else if (tag.charAt(i) == '\\' && tag.charAt(i + 1) == '\t') {
				j++;
			}
		}
		return j;
	}

	/**
	 * simpleTokenizer
	 * 
	 * @param tag
	 *            String that can become token
	 * @return Return value is particular type of Token depending of tag
	 *         content.
	 */
	private Token simpleTokenizer(String tag) {
		if (isTokenInteger(tag)) {
			return new TokenConstantInteger(Integer.parseInt(tag));
		}
		if (isTokenDouble(tag)) {
			return new TokenConstantDouble(Double.parseDouble(tag));
		}
		if (isTokenOperator(tag)) {
			return new TokenOperator(tag);
		}
		if (isTokenVariable(tag)) {
			return new TokenVariable(tag);
		}
		if (isTokenFunction(tag)) {
			return new TokenFunction(tag);
		} else {
			throw new SmartScriptParserException("Invalid or unsupported token format");
		}
	}

	/**
	 * 
	 * @param token
	 * @return Return value is true if String token can be interpreted as
	 *         variable. False otherwise.
	 */
	private boolean isTokenVariable(String token) {
		char ch = token.charAt(0);
		if (Character.isLetter(ch)) {
			for (int i = 1; i < token.length(); i++) {
				ch = token.charAt(i);
				if (!(Character.isLetterOrDigit(ch) || Character.compare(ch, '_') == 0)) {
					return false;
				}
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param token
	 * @return Return value is true if String token can be parsed to integer.
	 *         False otherwise.
	 */
	private boolean isTokenInteger(String token) {
		try {
			Integer.parseInt(token, 10);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param token
	 * @return Return value is true if String token can be parsed to double.
	 *         False otherwise.
	 */
	private boolean isTokenDouble(String token) {
		try {
			Double.parseDouble(token);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param token
	 * @return Return value is true if String token can be interpreted as
	 *         function. False otherwise.
	 */
	private boolean isTokenFunction(String token) {
		char ch = token.charAt(0);
		if (Character.compare(ch, '@') == 0) {
			try {
				if (isTokenVariable(token.substring(1))) {
					return true;
				}
			} catch (IndexOutOfBoundsException e) {
				throw new SmartScriptParserException("Invalid function token");
			}
		}
		return false;
	}

	/**
	 * 
	 * @param token
	 * @return Return value is true if String token can be interpreted as
	 *         operator. False otherwise
	 */
	private boolean isTokenOperator(String token) {
		char ch = token.charAt(0);
		if (token.length() == 1) {
			if (ch == '*' || ch == '+' || ch == '-' || ch == '/' || ch == '%') {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param token
	 * @return Returns new TokenString of content token.
	 */
	private Token stringTokenizer(String token) {
		return new TokenString(token);
	}

	/**
	 * escapeString
	 * <p>
	 * This method provides following escaping:
	 * <ul>
	 * <li>\\sequence treat as a single string character \</li>
	 * <li>\"treat as a single string character "</li>
	 * <li>\n, \r and \t have its usual meaning (ascii 10, 13 and 9).</li>
	 * </ul>
	 * For example, "Joe \"Long\" Smith"represents a single string whose value
	 * is Joe "Long" Smith.
	 * </p>
	 * 
	 * @param temp
	 * @return Return value is new String with applied escaping.
	 */
	private String escapeString(String temp) {
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == '\\') {
				temp = temp.substring(0, i) + "\\" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == 'n') {
				temp = temp.substring(0, i) + "\n" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == 't') {
				temp = temp.substring(0, i) + "\t" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == 'r') {
				temp = temp.substring(0, i) + "\r" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == '"') {
				temp = temp.substring(0, i) + "\"" + temp.substring(i + 2);
			}
		}
		return temp;
	}
}
