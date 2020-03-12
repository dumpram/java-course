package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;

import java.util.List;

/**
 * Model of fail statement for file checking program. Extends ProgramNode
 * because it can contain block of statements.
 * 
 * @author Ivan Pavić
 * 
 */
public class FailStatement extends ProgramNode implements ExecutableStatement {
    /**
     * Failure message.
     */
    private String message;
    /**
     * Invert flag.
     */
    private boolean invert;
    /**
     * IsBlock flag. If true block follows.
     */
    private boolean isBlock;

    /**
     * Inherited constructor.
     * 
     * @param statements
     *            list of {@link FCNode} statements
     */
    public FailStatement(List<FCNode> statements) {
	super(statements);
    }

    /**
     * Constructor for this statement accepts failure message, invert flag,
     * isBlock flag and list of block statements.
     * 
     * @param message
     *            given message
     * @param invert
     *            given invert flag
     * @param isBlock
     *            given block flag
     * @param statements
     *            given list of statements
     */
    public FailStatement(String message, boolean invert, boolean isBlock,
	    List<FCNode> statements) {
	super(statements);
	this.message = message;
	this.invert = invert;
	this.isBlock = isBlock;
    }

    /**
     * Getter for block flag.
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
	if (!invert) {
	    executor.getVerifier().addError(message);
	} else {
	    for (FCNode i : statements) {
		i.execute(executor);
	    }
	}
	return;
    }

    /**
     * Getter for invert flag.
     * 
     * @return invert flag.
     */
    public boolean isInvert() {
	return invert;
    }
}
