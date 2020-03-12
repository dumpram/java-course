package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * Interface defines visitor for nodes of smart script parser. There are 4 types
 * of nodes text node, for loop node, echo node, document node.
 * 
 * @author Ivan Pavić
 * 
 */
public interface INodeVisitor {
	/**
	 * Visits given text node.
	 * 
	 * @param node
	 *            given node
	 */
	void visitTextNode(TextNode node);
	/**
	 * Visits given for loop node.
	 * 
	 * @param node
	 *            given node
	 */
	void visitForLoopNode(ForLoopNode node);
	/**
	 * Visits given echo node.
	 * 
	 * @param node
	 *            given node
	 */
	void visitEchoNode(EchoNode node);
	/**
	 * Visits given document node.
	 * 
	 * @param node
	 *            given node.
	 */
	void visitDocumentNode(DocumentNode node);
}
