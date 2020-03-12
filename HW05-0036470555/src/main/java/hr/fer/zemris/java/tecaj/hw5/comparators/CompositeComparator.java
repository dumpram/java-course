package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;
import java.util.List;
/**
 * Composite comparator enables comparison on more than one criteria. Does so by
 * receiving list of comparators in class constructor.
 * @author Ivan Pavić
 *
 */
public class CompositeComparator implements Comparator<File> {
	/**
	 * Private list of comparators.
	 */
	List<Comparator<File>> comparatorList;
	/**
	 * Constructor for CompostiteComparator.
	 * @param comparatorList list which contains comparators(criteria) for comparison.
	 */
	public CompositeComparator(List<Comparator<File>> comparatorList) {
		if (comparatorList == null) {
			throw new IllegalArgumentException("Comparator list can't be null");
		}
		this.comparatorList = comparatorList;
	}
	@Override
	public int compare(File o1, File o2) {
		for (Comparator<File> i : comparatorList) {
			int r = i.compare(o1, o2);
			if (r != -0) {
				return r;
			}
		}
		return 0;
	}
}
