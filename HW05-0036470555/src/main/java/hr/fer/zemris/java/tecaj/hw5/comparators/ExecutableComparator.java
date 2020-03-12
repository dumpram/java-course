package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;
/**
 * Comparison is done by in the way that File objects which are
 * executable are put first.
 * @author Ivan Pavić
 *
 */
public class ExecutableComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		if (o1.canExecute() && !o2.canExecute()) {
			return 1;
		} else if (!o1.canExecute() && o2.canExecute()) {
			return -1;
		}
		return 0;
	}
}
