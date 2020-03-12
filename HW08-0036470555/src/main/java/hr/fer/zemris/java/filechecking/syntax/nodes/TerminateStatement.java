package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;

/**
 * Class represents terminate statement which only job is to terminate
 * execution.
 * 
 * @author Ivan Pavić
 * 
 */
public class TerminateStatement extends FCNode implements ExecutableStatement {
    /**
     * Constructor.
     */
    public TerminateStatement() {
    }

    @Override
    public void execute(FCExecutor executor) {
	executor.setStop(true);
    }

}
