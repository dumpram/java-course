package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TreeShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	if (arguments.length != 1) {
	    MyShell.writeToBufferedWriter(out,
		    "Invalid number of arguments provided!\n");
	    return ShellStatus.CONTINUE;
	}
	if (!(new File(arguments[0])).isDirectory()) {
	    MyShell.writeToBufferedWriter(out,
		    "Given argument isn't directory!\n");
	    return ShellStatus.CONTINUE;
	}
	try {
	    Files.walkFileTree(Paths.get(arguments[0]),
		    new TreeFileVisitor(out));
	} catch (IOException e) {
	    MyShell.writeToBufferedWriter(out,
		    "Tree couldn't be constructed!\n");
	    return ShellStatus.CONTINUE;
	}
	return ShellStatus.CONTINUE;
    }

}
