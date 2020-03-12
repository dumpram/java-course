package hr.fer.zemris.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface ShellCommand {

    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments);

}
