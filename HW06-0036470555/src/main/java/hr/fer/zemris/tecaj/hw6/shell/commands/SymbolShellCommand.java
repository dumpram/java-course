package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.MyShellSymbols;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class SymbolShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	String oldSymbol;
	if (arguments.length == 1) {
	    if (arguments[0].trim().compareTo("PROMPT") == 0) {
		MyShell.writeToBufferedWriter(out, "Symbol for prompt is: '"
			+ MyShellSymbols.PromptSymbol + "'\n");
	    } else if (arguments[0].trim().compareTo("MORELINES") == 0) {
		MyShell.writeToBufferedWriter(out, "Symbol for MORELINES is: '"
			+ MyShellSymbols.MoreLineSymbol + "'\n");
	    } else if (arguments[0].trim().compareTo("MULTILINE") == 0) {
		MyShell.writeToBufferedWriter(out, "Symbol for MULTILINE is: '"
			+ MyShellSymbols.MultiLineSymbol + "'\n");
	    } else {
		MyShell.writeToBufferedWriter(out,
			"Symbol command: wrong argument provided!\n");
	    }
	} else if (arguments.length == 2) {
	    if (arguments[0].trim().compareTo("PROMPT") == 0) {
		oldSymbol = MyShellSymbols.getPromptSymbol();
		MyShellSymbols.setPromptSymbol(arguments[1]);
		MyShell.writeToBufferedWriter(out,
			"Symbol for PROMPT changed from: '" + oldSymbol
				+ "' to '" + MyShellSymbols.getPromptSymbol()
				+ "'\n");
	    } else if (arguments[0].trim().compareTo("MORELINES") == 0) {
		oldSymbol = MyShellSymbols.getMoreLineSymbol();
		MyShellSymbols.setMoreLineSymbol(arguments[1]);
		MyShell.writeToBufferedWriter(out,
			"Symbol for MORELINES changed from: '" + oldSymbol
				+ "' to '" + MyShellSymbols.getMoreLineSymbol()
				+ "'\n");
	    } else if (arguments[0].trim().compareTo("MULTILINE") == 0) {
		oldSymbol = MyShellSymbols.getMultiLineSymbol();
		MyShellSymbols.setMultiLineSymbol(arguments[1]);
		MyShell.writeToBufferedWriter(
			out,
			"Symbol for MULTILINE changed from: '" + oldSymbol
				+ "' to '"
				+ MyShellSymbols.getMultiLineSymbol() + "'\n");
	    } else {
		MyShell.writeToBufferedWriter(out,
			"Symbol command: wrong argument provided!\n");
	    }
	} else {
	    MyShell.writeToBufferedWriter(out,
		    "Symbol command must receive 1 or 2 arguments!\n");
	}
	return ShellStatus.CONTINUE;
    }
}
