package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of program node which is root of program tree in execution.
 * 
 * @author Ivan Pavić
 */
public class ProgramNode extends FCNode {

    /**
     * List of statements.
     */
    protected final List<FCNode> statements;

    /**
     * Constructor.
     * 
     * @param statements
     *            list of statements
     */
    public ProgramNode(List<FCNode> statements) {
	this.statements = new ArrayList<>(statements);
    }

    /**
     * Adds statements to program node.
     * 
     * @param statement
     */
    public void addStatement(FCNode statement) {
	statements.add(statement);
    }

    /**
     * Getter for statements.
     * 
     * @return list of statements
     */
    public List<FCNode> getStatements() {
	return new ArrayList<>(statements);
    }

    @Override
    public void execute(FCExecutor executor) {
	for (FCNode i : statements) {
	    if (executor.isStop()) {
		return;
	    }
	    i.execute(executor);
	}
    }
}
