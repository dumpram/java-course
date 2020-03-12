package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.util.Map;

public class CharsetsShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	if (arguments.length != 0) {
	    MyShell.writeToBufferedWriter(out,
		    "Charsets doesn't receive arguments!\n");
	    return ShellStatus.CONTINUE;
	}
	Map<String, Charset> charsets = Charset.availableCharsets();
	for (String i : charsets.keySet()) {
	    MyShell.writeToBufferedWriter(out, charsets.get(i) + "\n");
	}
	return ShellStatus.CONTINUE;
    }

}
