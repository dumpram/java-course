package hr.fer.zemris.tecaj.hw6.shell;

import hr.fer.zemris.tecaj.hw6.shell.commands.CatShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.CopyShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.ExitShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.LsShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.MkdirShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.SymbolShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.commands.TreeShellCommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MyShell class is implementation of simple shell command line with several
 * commands which you can use for managing files, listing files, copying files
 * or opening files. Commands are: <li>exit: exits shell</li> <li>ls: lists
 * directory</li> <li>copy: copies file</li> <li>cat: opens file</li> <li>
 * hexdump: file in hexEditor format</li> <li>tree: writes tree of files</li>
 * <li>charsets: list of available charsets</li> <li>mkdir: makes directory</li>
 * <li>symbol: operates with symbols</li> <br>
 * For more information read commands documentation.
 * 
 * @author Ivan Pavić
 * 
 */
public class MyShell {
    /**
     * Main method for MyShell. It is initializes and runs the whole system of
     * MyShell by calling appropriate methods.
     * 
     * @param args
     *            do not provide args
     */
    public static void main(String[] args) {

	Map<String, ShellCommand> commands = new HashMap<>();
	String line;
	String commandName;
	String[] arguments = new String[0];
	ShellCommand command;

	/* Initialize commands */
	initCommands(commands);
	/* Initialize BufferedReader and BufferedWriter */

	BufferedReader in = new BufferedReader(new InputStreamReader(System.in,
		StandardCharsets.UTF_8));
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
		System.out, StandardCharsets.UTF_8));

	/* Print welcome screen */
	writeToBufferedWriter(out, "Welcome to MyShell v 1.0\n");

	/* Initialize ShellStatus */
	ShellStatus status = ShellStatus.TERMINATE;
	do {
	    writeToBufferedWriter(out, MyShellSymbols.getPromptSymbol());
	    line = readFromBufferedReader(in);
	    commandName = extractCommandFromLine(line);
	    arguments = extractArgumentsFromLine(line);

	    while (isMoreLines(line) && commandName != null) {
		writeToBufferedWriter(out, MyShellSymbols.getMultiLineSymbol());
		line = readFromBufferedReader(in);
		arguments = combine(arguments,
			extractArgumentsFromOtherLine(line));
	    }

	    command = commands.get(commandName);
	    status = commandExecution(command, in, out, arguments);
	} while (status != ShellStatus.TERMINATE);

    }

    /**
     * Check if moreLines symbol is in given string line.
     * 
     * @param line
     *            given string for checking
     * @return true if contains, false otherwise.
     */
    private static boolean isMoreLines(String line) {
	List<String> args = Arrays.asList(line.split(" "));
	return args.contains(MyShellSymbols.MoreLineSymbol);
    }

    /**
     * Executes command if command isn't null. If null, appropriate message is
     * written to BufferedWriter out.
     * 
     * @param command
     *            given command for execution
     * @param in
     *            given BufferedReader
     * @param out
     *            given BufferedWriter
     * @param arguments
     *            array of arguments.
     * @return ShellStatus depending on execution of command(CONTINUE or
     *         TERMINATE)
     */
    private static ShellStatus commandExecution(ShellCommand command,
	    BufferedReader in, BufferedWriter out, String[] arguments) {
	if (command == null) {
	    writeToBufferedWriter(out, "Invalid command provided!\n");
	    return ShellStatus.CONTINUE;
	}
	return command.executeCommand(in, out, arguments);
    }

    /**
     * Extracts command from given line. If command is not matched, returns
     * null. Supported commands are: exit, ls, charsets, cat, tree, copy,
     * hexdump, mkdir, symbol.
     * 
     * @param line
     * @return
     */
    private static String extractCommandFromLine(String line) {
	String commandNameRegex = "\\s*(exit|ls|charsets|cat|tree|copy|hexdump|mkdir|symbol)(\\s+.*)?";
	line.trim();
	Pattern pattern = Pattern.compile(commandNameRegex);
	Matcher matcher = pattern.matcher(line);
	if (matcher.matches()) {
	    return matcher.group(1);
	}
	return null;
    }

    /**
     * Extracts arguments from first line of input.
     * 
     * @param line
     *            given line
     * @return array of arguments.
     */
    private static String[] extractArgumentsFromLine(String line) {
	String argRegex = "(exit|ls|charsets|cat|tree|copy|hexdump|mkdir|symbol)(\\s+.*)?";
	line = line.trim();
	Pattern pattern = Pattern.compile(argRegex);
	Matcher matcher = pattern.matcher(line);
	if (matcher.matches()) {
	    return splitArguments(matcher.group(2));
	}
	return new String[0];
    }

    /**
     * Extracts arguments from line other than first.
     * 
     * @param line
     *            given line.
     * @return array of arguments.
     */
    private static String[] extractArgumentsFromOtherLine(String line) {
	return splitArguments(line);
    }

    /**
     * Splits String representing group of arguments into array of separate
     * arguments.
     * 
     * @param group
     *            given group of arguments.
     * @return array representing arguments.
     */
    private static String[] splitArguments(String group) {
	if (group != null) {
	    group = group.trim();
	    if (group.length() != 0) {
		return removeMoreLines(group.split(" "));
	    }
	}
	return new String[0];
    }

    /**
     * Removes MoreLines symbols from array of arguments.
     * 
     * @param arguments
     *            given array of arguments
     * @return array of arguments without remove moreLines symbols.
     */
    private static String[] removeMoreLines(String[] arguments) {
	List<String> argList = new ArrayList<>(Arrays.asList(arguments));
	if (argList.get(argList.size() - 1).compareTo(
		MyShellSymbols.MoreLineSymbol) == 0) {
	    argList.remove(argList.size() - 1);
	}
	String[] forExport = new String[argList.size()];
	for (int i = 0; i < argList.size(); i++) {
	    forExport[i] = argList.get(i);
	}
	return forExport;
    }

    /**
     * Name of method speaks for itself.
     * 
     * @param in
     *            input BufferedReader
     * @return line read from given BufferedReader in
     */
    public static String readFromBufferedReader(BufferedReader in) {
	String line = null;
	try {
	    line = in.readLine();
	} catch (IOException e) {
	    System.out
		    .println("Error occured while reading from BufferedReader");
	}
	return line;
    }

    /**
     * Method does initialization of commands.
     * 
     * @param commands
     *            Map of commands where commands will be added.
     */
    private static void initCommands(Map<String, ShellCommand> commands) {
	commands.put("exit", new ExitShellCommand());
	commands.put("ls", new LsShellCommand());
	commands.put("charsets", new CharsetsShellCommand());
	commands.put("cat", new CatShellCommand());
	commands.put("tree", new TreeShellCommand());
	commands.put("copy", new CopyShellCommand());
	commands.put("hexdump", new HexdumpShellCommand());
	commands.put("mkdir", new MkdirShellCommand());
	commands.put("symbol", new SymbolShellCommand());
    }

    /**
     * Prints welcome screen on given BufferedReader.
     * 
     * @param out
     * @throws IOException
     */
    public static void writeToBufferedWriter(BufferedWriter out, String message) {
	try {
	    out.write(message);
	    out.flush();
	} catch (IOException e) {
	    System.out
		    .println("Error occured while writing to BufferedWriter out!");
	}

    }

    /**
     * Combines two String arrays in one, and then returns it.
     * 
     * @param first
     *            String array
     * @param second
     *            String array
     * @return String made by combination.
     */
    public static String[] combine(String[] first, String[] second) {
	int length = first.length + second.length;
	String[] result = new String[length];
	System.arraycopy(first, 0, result, 0, first.length);
	System.arraycopy(second, 0, result, first.length, second.length);
	return result;
    }
}
