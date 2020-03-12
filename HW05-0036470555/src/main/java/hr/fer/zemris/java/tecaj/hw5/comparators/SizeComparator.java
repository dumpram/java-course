package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;
/**
 * Compares files by size.
 * @author Ivan Pavić
 *
 */
public class SizeComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		return Long.compare(o1.length(), o2.length());
	}
}
