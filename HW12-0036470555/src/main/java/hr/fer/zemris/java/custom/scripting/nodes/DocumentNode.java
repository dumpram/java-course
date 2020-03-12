package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * DocumentNode
 * <p>
 * Node representing an entire document. It inherits from Node class.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 */
public class DocumentNode extends Node {
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	@Override
	public String toString() {
		StringBuffer temp = new StringBuffer();
		for (Node child : children) {
			temp.append(child.toString());
		}
		return temp.toString();
	}
}
