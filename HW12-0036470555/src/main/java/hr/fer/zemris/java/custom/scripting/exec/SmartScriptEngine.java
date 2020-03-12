package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
/**
 * Computes parsed program. Parsed program is given like node tree. Root node is
 * document node.
 * 
 * @author Ivan Pavić
 * 
 */
public class SmartScriptEngine {
	/**
	 * Private document node.
	 */
	private final DocumentNode documentNode;
	/**
	 * Private request context.
	 */
	private final RequestContext requestContext;
	/**
	 * Private object multistack.
	 */
	private final ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * Private visitor for execution.
	 */
	private final INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String var = node.getVariable().asText();
			multistack.push(var, new ValueWrapper(node.getStartExpression().asText()));
			ValueWrapper step = (node.getStepExpression() == null) ? new ValueWrapper(1) : new ValueWrapper(node
					.getStepExpression().asText());
			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());
			while (multistack.peek(var).numCompare(end.getValue()) != 0) {
				for (Node child : node.getChildren()) {
					child.accept(visitor);
				}
				ValueWrapper current = multistack.pop(var);
				current.increment(step.getValue());
				multistack.push(var, current);
			}
			multistack.pop(var);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectMultistack temp = new ObjectMultistack();
			for (Token token : node.getTokens()) {

				if (token instanceof TokenVariable) {
					temp.push("temp", new ValueWrapper(multistack.peek(token.asText()).getValue()));
				}

				if (token instanceof TokenConstantDouble) {
					temp.push("temp", new ValueWrapper(token.asText()));
				}

				if (token instanceof TokenConstantInteger) {
					temp.push("temp", new ValueWrapper(token.asText()));
				}

				if (token instanceof TokenString) {
					temp.push("temp", new ValueWrapper(token.asText()));
				}

				if (token instanceof TokenOperator) {
					ValueWrapper secondOperand = new ValueWrapper(temp.pop("temp").getValue());
					ValueWrapper firstOperand = new ValueWrapper(temp.pop("temp").getValue());
					if (token.asText().compareTo("+") == 0) {
						firstOperand.increment(secondOperand.getValue());
					}
					if (token.asText().compareTo("-") == 0) {
						firstOperand.decrement(secondOperand.getValue());
					}
					if (token.asText().compareTo("/") == 0) {
						firstOperand.divide(secondOperand.getValue());
					}
					if (token.asText().compareTo("*") == 0) {
						firstOperand.multiply(secondOperand.getValue());
					}
					temp.push("temp", firstOperand);
				}

				if (token instanceof TokenFunction) {
					if (token.asText().compareToIgnoreCase("@sin") == 0) {
						ValueWrapper operand = temp.pop("temp");
						operand.sinFunction();
						temp.push("temp", operand);
					}
					if (token.asText().compareTo("@decfmt") == 0) {
						String format = (String) temp.pop("temp").getValue();
						DecimalFormat form = new DecimalFormat(format);
						ValueWrapper val = temp.pop("temp");
						String value = form.format(val.getValue());
						temp.push("temp", new ValueWrapper(value));
					}
					if (token.asText().compareToIgnoreCase("@dup") == 0) {
						temp.push("temp", temp.peek("temp"));
					}
					if (token.asText().compareToIgnoreCase("@swap") == 0) {
						ValueWrapper a = temp.pop("temp");
						ValueWrapper b = temp.pop("temp");
						temp.push("temp", a);
						temp.push("temp", b);
					}
					if (token.asText().compareToIgnoreCase("@setMimeType") == 0) {
						requestContext.setMimeType((String) temp.pop("temp").getValue());
					}
					if (token.asText().compareToIgnoreCase("@paramGet") == 0) {
						ValueWrapper defValue = temp.pop("temp");
						ValueWrapper name = temp.pop("temp");
						String parameter = requestContext.getParameter(name.getValue().toString());
						temp.push("temp", (parameter == null ? defValue : new ValueWrapper(parameter)));
					}
					if (token.asText().compareToIgnoreCase("@pparamGet") == 0) {
						ValueWrapper defValue = temp.pop("temp");
						ValueWrapper name = temp.pop("temp");
						String parameter = requestContext.getPersistentParameter(name.getValue().toString());

						if (parameter == null) {
							temp.push("temp", defValue);
						} else {
							temp.push("temp", new ValueWrapper(parameter));
						}
					}
					if (token.asText().compareToIgnoreCase("@tparamGet") == 0) {
						ValueWrapper defValue = temp.pop("temp");
						ValueWrapper name = temp.pop("temp");
						String parameter = requestContext.getTemporaryParameter(name.getValue().toString());
						temp.push("temp", (parameter == null ? defValue : new ValueWrapper(parameter)));
					}
					if (token.asText().compareToIgnoreCase("@pparamSet") == 0) {
						ValueWrapper name = temp.pop("temp");
						ValueWrapper defValue = temp.pop("temp");
						requestContext.setPersistentParameter(name.getValue().toString(), defValue.getValue()
								.toString());
					}
					if (token.asText().compareToIgnoreCase("@tparamSet") == 0) {
						ValueWrapper name = temp.pop("temp");
						ValueWrapper defValue = temp.pop("temp");
						requestContext
								.setTemporaryParameter(name.getValue().toString(), defValue.getValue().toString());
					}
					if (token.asText().compareToIgnoreCase("@pparamDel") == 0) {
						ValueWrapper name = temp.pop("temp");
						requestContext.removePersistentParameter(name.getValue().toString());
					}
					if (token.asText().compareToIgnoreCase("@tparamDel") == 0) {
						ValueWrapper name = temp.pop("temp");
						requestContext.removeTemporaryParameter(name.getValue().toString());
					}
				}

			}

			while (!temp.isEmpty("temp")) {
				temp.push("temp2", temp.pop("temp"));
			}
			while (!temp.isEmpty("temp2")) {
				try {
					requestContext.write(temp.pop("temp2").getValue().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (Node child : node.getChildren()) {
				child.accept(visitor);
			}

		}
	};

	/**
	 * Constructor accepts document node and request context.
	 * 
	 * @param documentNode
	 *            given document node
	 * @param requestContext
	 *            given context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	/**
	 * Executes parsed code given in constructor as document node.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
