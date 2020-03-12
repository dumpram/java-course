package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;

/**
 * Model of def statement for file checking program.
 * 
 * @author Ivan Pavić
 */
public class DefStatement extends FCNode implements ExecutableStatement {

    /**
     * Variables name.
     */
    private final String variable;
    /**
     * String which represents argument!
     */
    private String argument;

    /**
     * Constructor for {@link DefStatement}.
     * 
     * @param variable
     *            name of variable
     * @param argument
     *            argument for variable
     */
    public DefStatement(String variable, String argument) {
	this.variable = variable;
	this.argument = argument;
    }

    /**
     * Getter for variable.
     * 
     * @return variable of statement
     */
    public String getVariable() {
	return variable;
    }

    /**
     * Getter for argument.
     * 
     * @return argument of statement
     */
    public String getArgument() {
	return argument;
    }

    @Override
    public void execute(FCExecutor executor) {
	if (executor.isStop()) {
	    return;
	}
	argument = SubsArgument.supsArgument(executor, argument);
	executor.addVariable(variable, argument);
    }

}
