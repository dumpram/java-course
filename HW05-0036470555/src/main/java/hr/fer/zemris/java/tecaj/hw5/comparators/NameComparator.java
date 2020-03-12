package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;
/**
 * Comparison is made based on file name.
 * @author Ivan Pavić
 *
 */
public class NameComparator implements Comparator<File> {

	@Override
	/**
	 * Comparison is delegated to String compare.
	 */
	public int compare(File arg0, File arg1) {
		return arg0.getName().compareTo(arg1.getName());
	}
}
