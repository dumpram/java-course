package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

public class MkdirShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	if (arguments.length != 1) {
	    MyShell.writeToBufferedWriter(out,
		    "Invalid number of arguments provided!\n");
	    return ShellStatus.CONTINUE;
	}
	if ((new File(arguments[0])).isDirectory()) {
	    MyShell.writeToBufferedWriter(out, "Directory already exist!\n");
	    return ShellStatus.CONTINUE;
	}
	File newDir = new File(arguments[0]);
	if (newDir.mkdir()) {
	    MyShell.writeToBufferedWriter(out,
		    "Directory created successfully!\n");
	}
	return ShellStatus.CONTINUE;
    }

}
