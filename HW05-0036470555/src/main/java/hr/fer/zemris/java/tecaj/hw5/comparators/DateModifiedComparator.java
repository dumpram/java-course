package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;
/**
 * DateModifiedComparator compares based on date files were created(modified).
 * @author Ivan Pavić
 *
 */
public class DateModifiedComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		return Long.compare(o1.lastModified(), o2.lastModified());
	}
}
