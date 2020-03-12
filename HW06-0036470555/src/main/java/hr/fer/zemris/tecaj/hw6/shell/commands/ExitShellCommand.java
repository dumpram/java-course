package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ExitShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	if (arguments.length != 0) {
	    MyShell.writeToBufferedWriter(out,
		    "Invalid command provided! Exit receive no arguments!\n");
	    return ShellStatus.CONTINUE;
	}
	return ShellStatus.TERMINATE;
    }

}
