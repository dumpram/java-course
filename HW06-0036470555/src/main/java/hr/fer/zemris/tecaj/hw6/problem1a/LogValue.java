package hr.fer.zemris.tecaj.hw6.problem1a;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Instances of LogValue class for each notification open a log file whose path
 * is given in its constructor, appends a new row with current value obtained
 * from subject and closes the file.
 * 
 * @author Ivan Pavić
 * 
 */
public class LogValue implements IntegerStorageObserver {
    /**
     * Private path variable.
     */
    private final File logFile;

    /**
     * Constructor for LogValue accepts path to log file.
     * 
     * @param logPath
     *            path to log file
     */
    public LogValue(Path logPath) {
	if (logPath == null) {
	    throw new IllegalArgumentException("Path can't be null");
	}
	logFile = logPath.toFile();
    }

    @Override
    public void valueChanged(IntegerStorage istorage) {
	FileWriter output = null;
	try {
	    output = new FileWriter(logFile, true);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try {
	    output.append(Integer.valueOf(istorage.getValue()).toString()
		    + "\n");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try {
	    output.close();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
