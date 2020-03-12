package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HexdumpShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
	    String[] arguments) {
	if (arguments.length != 1) {
	    MyShell.writeToBufferedWriter(out,
		    "Invalid number of arguments provided!\n");
	    return ShellStatus.CONTINUE;
	}
	if (!(new File(arguments[0])).isFile()) {
	    MyShell.writeToBufferedWriter(out, "Given argument isn't file!\n");
	    return ShellStatus.CONTINUE;
	}
	FileInputStream input;
	try {
	    input = new FileInputStream(arguments[0]);
	} catch (FileNotFoundException e) {
	    MyShell.writeToBufferedWriter(out, "Couldn't open file!\n");
	    return ShellStatus.CONTINUE;
	}
	byte[] dataRead = new byte[16];
	int flag, i = 0;
	try {
	    while ((flag = input.read(dataRead)) != -1) {

		MyShell.writeToBufferedWriter(out, lineFromIndex(i)
			+ bytetohex(dataRead, flag).toString());
		i++;
	    }
	} catch (IOException e) {
	    MyShell.writeToBufferedWriter(out, "Couldn't read file!\n");
	    return ShellStatus.CONTINUE;

	}
	try {
	    input.close();
	} catch (IOException e) {
	    MyShell.writeToBufferedWriter(out,
		    "Couldn't close file input stream!\n");
	}
	return ShellStatus.CONTINUE;
    }

    private String lineFromIndex(int i) {
	StringBuffer sb = new StringBuffer();
	String hex = Integer.toHexString(i);
	int size = hex.length();
	while (size + sb.length() <= 8) {
	    sb.append("0");
	}
	sb.append(hex);
	sb.append(": ");
	return sb.toString();
    }

    private static StringBuffer bytetohex(byte[] keytext, int flag) {
	StringBuffer sb = new StringBuffer("");
	for (int i = 0; i < 16; i++) {
	    if (i < flag) {
		sb.append(Integer.toString((keytext[i] & 0xff) + 0x100, 16)
			.substring(1).toUpperCase());
	    } else {
		sb.append("  ");
	    }
	    if (i == 7) {
		sb.append("|");
	    } else {
		sb.append(" ");
	    }
	    if (i == 15) {
		sb.append("| ");
		for (int j = 0; j < keytext.length; j++) {
		    if (keytext[j] < 32 || keytext[j] > 127) {
			sb.append(".");
		    } else {
			sb.append(new Character((char) keytext[j]));
		    }
		}

	    }
	}
	sb.append("\n");
	return sb;
    }
}
