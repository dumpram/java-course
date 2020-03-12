package hr.fer.zemris.tecaj.hw6.shell.commands;

import hr.fer.zemris.tecaj.hw6.shell.MyShell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class TreeFileVisitor implements FileVisitor<Path> {

    private int indent = 0;
    private final BufferedWriter out;

    public TreeFileVisitor(BufferedWriter out) {
	this.out = out;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path arg0, IOException arg1)
	    throws IOException {
	indent -= 2;
	return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1)
	    throws IOException {
	if (indent == 0) {
	    MyShell.writeToBufferedWriter(out,
		    createSpaces() + arg0.getFileName() + "\n");
	} else {
	    MyShell.writeToBufferedWriter(out,
		    createSpaces() + arg0.getFileName() + "\n");
	}
	indent += 2;
	return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1)
	    throws IOException {
	MyShell.writeToBufferedWriter(out, createSpaces() + arg0.getFileName()
		+ "\n");
	return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path arg0, IOException arg1)
	    throws IOException {
	return FileVisitResult.CONTINUE;
    }

    private String createSpaces() {
	StringBuffer sb = new StringBuffer();
	while (sb.length() != indent) {
	    sb.append(" ");
	}
	return sb.toString();
    }
}