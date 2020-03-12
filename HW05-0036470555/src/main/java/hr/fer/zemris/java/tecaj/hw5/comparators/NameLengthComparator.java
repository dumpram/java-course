package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;
/**
 * Compares to files by their name length.
 * @author Ivan Pavić
 *
 */
public class NameLengthComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		return Integer.compare(o1.getName().length(), o2.getName().length());
	}

}
