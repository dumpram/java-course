package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CatShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	List<String> file = null;
	if (arguments.length != 2) {
	    MyShell.writeToBufferedWriter(out,
		    "Invalid number of arguments provided!\n");
	    return ShellStatus.CONTINUE;
	}
	if (!Files.exists(Paths.get(arguments[0]))) {
	    MyShell.writeToBufferedWriter(out, "File doesn't exist!\n");
	    return ShellStatus.CONTINUE;
	}
	if (!Charset.isSupported(arguments[1])) {
	    MyShell.writeToBufferedWriter(out, "Charset not supported!\n");
	    return ShellStatus.CONTINUE;
	}
	try {
	    file = Files.readAllLines(Paths.get(arguments[0]),
		    Charset.forName(arguments[1]));
	} catch (IOException e) {
	    MyShell.writeToBufferedWriter(out, "File couldn't be open!\n");
	    return ShellStatus.CONTINUE;
	}
	for (String i : file) {
	    MyShell.writeToBufferedWriter(out, i + "\n");
	}
	return ShellStatus.CONTINUE;
    }

}
