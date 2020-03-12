package hr.fer.zemris.java.tecaj.hw5;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main class for testing Dir commmand.
 * @author Ivan Pavić
 *
 */
public class Dir {
	/**
	 * Main method for class dir. Command line arguments should be provided. First
	 * should be directory path followed by specifiers.
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			throw new IllegalArgumentException("File not specified!");
		}
		if (!Files.exists(Paths.get(args[0]))) {
			throw new IllegalArgumentException("File doesn't exist!");
		}
		FileWork go = new FileWork(args);
		go.print();
	}
}
