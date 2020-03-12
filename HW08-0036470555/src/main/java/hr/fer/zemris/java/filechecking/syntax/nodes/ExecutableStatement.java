package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;

/**
 * Interface which is implemented by all statements that are executable.
 * 
 * @author Ivan Pavić
 * 
 */
public interface ExecutableStatement {
    /**
     * Method executes statement. Also it needs reference to executor which is
     * executing this statement.
     * 
     * @param executor
     *            {@link FCExecutor} class reference
     */
    void execute(FCExecutor executor);

}
