package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CopyShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	if (arguments.length != 2) {
	    MyShell.writeToBufferedWriter(out,
		    "Invalid number of arguments provided!\n");
	    return ShellStatus.CONTINUE;
	}
	if (!Files.exists(Paths.get(arguments[0]))) {
	    MyShell.writeToBufferedWriter(out, "Src file doesn't exist!\n");
	    return ShellStatus.CONTINUE;
	}
	if (!(new File(arguments[0])).isFile()) {
	    MyShell.writeToBufferedWriter(out, "First argument must be file!\n");
	    return ShellStatus.CONTINUE;
	}
	File src = new File(arguments[0]);
	if (!Files.exists(Paths.get(arguments[1]))) {
	    return copyFinal(out, arguments);
	}
	File dst = new File(arguments[1]);
	if (dst.isFile()) {
	    MyShell.writeToBufferedWriter(out,
		    "Do you wan't to overwrite file " + dst.getName()
			    + "? (Y/N) \n");
	    String answer = MyShell.readFromBufferedReader(in);
	    if (answer.compareTo("Y") == 0) {
		return copyFinal(out, arguments);
	    } else if (answer.compareTo("N") == 0) {
		return ShellStatus.CONTINUE;
	    } else {
		MyShell.writeToBufferedWriter(out, "Wrong answer!\n");
		return ShellStatus.CONTINUE;
	    }
	}
	if (dst.isDirectory()) {
	    arguments[1] = dst.getAbsolutePath() + "/" + src.getName();
	    return copyFinal(out, arguments);
	}
	return ShellStatus.CONTINUE;
    }

    /**
     * @param out
     * @param arguments
     * @return
     */
    private ShellStatus copyFinal(BufferedWriter out, String[] arguments) {
	try {
	    copyFileToFile(arguments);
	} catch (IOException e) {
	    MyShell.writeToBufferedWriter(out, "Couldn't copy file!\n");
	    return ShellStatus.CONTINUE;
	}
	MyShell.writeToBufferedWriter(out, "File copied successfully!\n");
	return ShellStatus.CONTINUE;
    }

    private void copyFileToFile(String[] arguments) throws IOException {
	FileInputStream input = new FileInputStream(arguments[0]);
	FileOutputStream output = new FileOutputStream(arguments[1]);
	int dataRead;
	while ((dataRead = input.read()) != -1) {
	    output.write(dataRead);
	}
	input.close();
	output.close();
    }

}
