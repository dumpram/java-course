package hr.fer.zemris.java.filechecking.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HelpClass {

    public static String ucitaj(String string) {
	StringBuffer sb = new StringBuffer();
	List<String> lines = null;
	File file = new File(string);
	if (!file.isFile()) {
	    throw new IllegalArgumentException("Given file object isn't file!");
	}
	try {
	    lines = Files.readAllLines(Paths.get(string),
		    StandardCharsets.UTF_8);
	} catch (IOException e) {
	    System.out.println("Unsuccessful reading!");
	    e.printStackTrace();
	}
	for (String i : lines) {
	    if (!i.trim().startsWith("#")) {
		sb.append(i.trim());
		sb.append("\n");
	    }
	}
	return sb.toString();
    }

}
