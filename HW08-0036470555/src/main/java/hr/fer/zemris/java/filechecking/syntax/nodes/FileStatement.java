package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.executors.FCExecutor;

import java.io.File;
import java.util.List;

/**
 * Model of exists statement for file checking program. Extends ProgramNode
 * because it can contain block of statements.
 * 
 * @author Ivan Pavić
 * 
 */
public class FileStatement extends ProgramNode implements ExecutableStatement {
    /**
     * True if comparison is case sensitive.
     */
    private boolean caseSensitivity;
    /**
     * String argument.
     */
    private String argument;
    /**
     * Failure message.
     */
    private String message;
    /**
     * Invert flag.
     */
    private boolean invert;
    /**
     * Flag for block.
     */
    private boolean isBlock;

    /**
     * Inherited constructor.
     * 
     * @param statements
     *            list of {@link FCNode} statements
     */
    public FileStatement(List<FCNode> statements) {
	super(statements);
    }

    /**
     * Constructor for this statement accepts caseSensitivity flag, String
     * argument, failure message, invert flag, isBlock flag and list of block
     * statements.
     * 
     * @param caseSensitivity
     *            given caseSensitvity flag
     * @param argument
     *            given argument
     * @param message
     *            given message
     * @param invert
     *            given invert flag
     * @param isBlock
     *            given block flag
     * @param statements
     *            given list of statements
     */
    public FileStatement(boolean caseSensitivity, String argument,
	    String message, boolean invert, boolean isBlock,
	    List<FCNode> statements) {
	super(statements);
	this.caseSensitivity = caseSensitivity;
	this.argument = argument;
	this.message = message;
	this.invert = invert;
	this.isBlock = isBlock;
    }

    /**
     * Getter for isBlock.
     * 
     * @return isBlock flag
     */
    public boolean isBlock() {
	return isBlock;
    }

    @Override
    public void execute(FCExecutor executor) {
	if (executor.isStop()) {
	    return;
	}
	argument = SubsArgument.supsArgument(executor, argument);
	File file = new File(executor.getVariable("file"));
	String fileName = file.getName();
	if (!invert) {
	    if (caseSensitivity) {
		if (fileName.compareTo(argument) != 0) {
		    if (message == null) {
			message = "File name " + argument + " doesn't match "
				+ fileName;
		    }
		    executor.getVerifier().addError(message);
		    return;
		} else {
		    for (FCNode i : statements) {
			i.execute(executor);
		    }
		}
	    } else {
		if (fileName.compareToIgnoreCase(argument) != 0) {
		    if (message == null) {
			message = "File name " + argument + " doesn't match "
				+ fileName;
		    }
		    executor.getVerifier().addError(message);
		    return;
		} else {
		    for (FCNode i : statements) {
			i.execute(executor);
		    }
		}
	    }
	} else {
	    if (!caseSensitivity) {
		if (fileName.compareToIgnoreCase(argument) == 0) {
		    if (message == null) {
			message = "File name " + argument + " matches "
				+ fileName;
		    }
		    executor.getVerifier().addError(message);
		    return;
		} else {
		    for (FCNode i : statements) {
			i.execute(executor);
		    }
		}
	    } else {
		if (fileName.compareToIgnoreCase(argument) == 0) {
		    if (message == null) {
			message = "File name " + argument + " matches "
				+ fileName;
		    }
		    executor.getVerifier().addError(message);
		    return;
		} else {
		    for (FCNode i : statements) {
			i.execute(executor);
		    }
		}
	    }
	}
    }
}
