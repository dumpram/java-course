package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;

/**
 * Generic node of tree of program for file checking. Concrete implementations
 * represent certain statements which are executable.
 * 
 * @author Ivan Pavić
 */
public class FCNode implements ExecutableStatement {

    /**
     * Constructor.
     */
    public FCNode() {
    }

    @Override
    public void execute(FCExecutor executor) {
	// TODO Auto-generated method stub

    }

}
