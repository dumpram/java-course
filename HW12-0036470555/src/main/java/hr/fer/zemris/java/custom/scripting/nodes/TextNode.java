package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * TextNode
 * <p>
 * Node representing a piece of text data. It inherits from Node class.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 */
public class TextNode extends Node {
	private final String text;

	public TextNode(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	@Override
	public String toString() {
		return text;
	}
}
