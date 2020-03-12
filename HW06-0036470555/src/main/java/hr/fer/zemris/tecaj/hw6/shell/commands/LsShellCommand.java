package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;
import hr.fer.zemris.tecaj.hw6.shell.ShellCommand;
import hr.fer.zemris.tecaj.hw6.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LsShellCommand implements ShellCommand {

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
	File dir = new File(arguments[0]);
	File[] files = dir.listFiles();
	for (File current : files) {
	    if (current.isDirectory()) {
		MyShell.writeToBufferedWriter(out, "d");
	    } else {
		MyShell.writeToBufferedWriter(out, "-");
	    }
	    if (current.canRead()) {
		MyShell.writeToBufferedWriter(out, "r");
	    } else {
		MyShell.writeToBufferedWriter(out, "-");
	    }
	    if (current.canWrite()) {
		MyShell.writeToBufferedWriter(out, "w");
	    } else {
		MyShell.writeToBufferedWriter(out, "-");
	    }
	    if (current.canExecute()) {
		MyShell.writeToBufferedWriter(out, "x");
	    } else {
		MyShell.writeToBufferedWriter(out, "-");
	    }
	    StringBuffer sb = new StringBuffer();
	    String fileSize = String.valueOf(current.length());
	    while ((fileSize.length() + sb.length()) != 10) {
		sb.append(" ");
	    }
	    sb.append(fileSize);
	    sb.append(" ");
	    MyShell.writeToBufferedWriter(out, sb.toString());
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    BasicFileAttributeView faView = Files.getFileAttributeView(
		    Paths.get(current.getPath()), BasicFileAttributeView.class,
		    LinkOption.NOFOLLOW_LINKS);
	    BasicFileAttributes attributes = null;
	    try {
		attributes = faView.readAttributes();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    FileTime fileTime = attributes.creationTime();
	    String formattedDateTime = sdf
		    .format(new Date(fileTime.toMillis()));
	    MyShell.writeToBufferedWriter(out, formattedDateTime);
	    MyShell.writeToBufferedWriter(out, " " + current.getName() + "\n");
	}
	return ShellStatus.CONTINUE;
    }

}
