package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;
/**
 * Compares files by type. Files are "greater" than directories.
 * @author Ivan Pavić
 *
 */
public class TypeComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		if (o1.isFile() && o2.isDirectory()) {
			return 1;
		} else if (o1.isDirectory() && o2.isFile()) {
			return -1;
		}
		return 0;
	}

}
