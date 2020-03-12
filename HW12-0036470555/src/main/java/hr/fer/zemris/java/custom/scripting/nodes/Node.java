package hr.fer.zemris.java.custom.scripting.nodes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Node
 * <p>
 * Base class for all graph nodes.
 * </p>
 * 
 * @author Ivan Pavić
 * @version 1.0
 */
public class Node {

	protected List<Node> children = new ArrayList<>();

	public void addChildNode(Node child) {
		children.add(child);
	}

	public int numberOfChildren() {
		return children.size();
	}

	public Node getChild(int index) {
		return children.get(index);
	}

	public void accept(INodeVisitor visitor) {

	}

	public List<Node> getChildren() {
		return Collections.unmodifiableList(children);
	}

}
