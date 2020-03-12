package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.FCException;
import hr.fer.zemris.java.filechecking.executors.FCExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Model of exists statement for file checking program. Extends ProgramNode
 * because it can contain block of statements.
 * 
 * @author Ivan Pavić
 * 
 */
public class ExistsStatement extends ProgramNode implements ExecutableStatement {
    /**
     * Private variable; in fact dir or file substrings are accepted.
     */
    private String variable;
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
     * IsBlock flag. If true block follows.
     */
    private boolean isBlock;

    /**
     * Inherited constructor.
     * 
     * @param statements
     *            list of {@link FCNode} statements
     */
    public ExistsStatement(List<FCNode> statements) {
	super(statements);
    }

    /**
     * Constructor for this statement accepts file specifier(variable), String
     * argument, failure message, invert flag, isBlock flag and list of block
     * statements.
     * 
     * @param variable2
     *            given file specifier
     * @param argument2
     *            given argument
     * @param message2
     *            given message
     * @param invert2
     *            given invert flag
     * @param isBlock
     *            given block flag
     * @param arrayList
     *            given list of statements
     */
    public ExistsStatement(String variable2, String argument2, String message2,
	    boolean invert2, boolean isBlock, ArrayList<FCNode> arrayList) {
	super(arrayList);
	this.variable = variable2;
	this.argument = argument2;
	this.message = message2;
	this.invert = invert2;
	this.isBlock = isBlock;
    }

    /**
     * Getter for block flag.
     * 
     * @return isBlock flag.
     */
    public boolean isBlock() {
	return isBlock;
    }

    @Override
    public void execute(FCExecutor executor) {
	if (executor.isStop()) {
	    return;
	}
	try {
	    argument = SubsArgument.supsArgument(executor, argument);
	} catch (FCException error) {
	    executor.getVerifier().addError(error.getMessage());
	    return;
	}
	if (!variable.matches("f|fi|fil|file|d|di|dir")) {
	    executor.getVerifier()
		    .addError(
			    "Wrong file specifier; Must match regex f|fi|fil|file|d|di|dir");
	    return;
	}
	if (variable.charAt(0) == 'd') {
	    argument += "/";
	}
	if (!invert) {
	    try {
		if (!checkEntry(executor)) {
		    if (message == null) {
			message = "File on path " + argument
				+ " doesn't exist!";
		    }
		    executor.getVerifier().addError(message);
		    return;
		} else {
		    for (FCNode i : statements) {
			i.execute(executor);
		    }
		}
	    } catch (IOException e) {
		executor.getVerifier().addError(
			"Given archive couldn't be read!");
		return;
	    }
	} else {
	    try {
		if (checkEntry(executor)) {
		    if (message == null) {
			message = "File on path " + argument + " exists!";
		    }
		    executor.getVerifier().addError(message);
		    return;
		} else {
		    for (FCNode i : statements) {
			i.execute(executor);
		    }
		}
	    } catch (IOException e) {
		executor.getVerifier().addError(
			"Given archive couldn't be read!");
		return;
	    }

	}
    }

    /**
     * Checks entry in zipFile.
     * 
     * @param executor
     *            given {@link FCExecutor} which is executing statement
     * @return true if argument exists in zip file.
     * @throws IOException
     *             if error in reading zip file occurs
     */
    private boolean checkEntry(FCExecutor executor) throws IOException {
	@SuppressWarnings("resource")
	ZipFile file = new ZipFile(executor.getVariable("file"));
	ZipEntry entry;
	Enumeration<? extends ZipEntry> entries = file.entries();
	while (entries.hasMoreElements()) {
	    entry = entries.nextElement();
	    if (variable.charAt(0) == 'd'
		    && entry.getName().startsWith(argument)) {
		return true;
	    }
	    if (variable.charAt(0) == 'f'
		    && entry.getName().startsWith(argument)) {
		return true;
	    }
	}
	return false;
    }
}
