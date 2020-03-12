package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;
/**
 * Size1Filter accepts File objects that have size smaller than
 * size given in constructor. With control value false condition is reversed.
 * @author Ivan Pavić
 *
 */
public class SizeSFilter implements FileFilter {
	/**
	 * Size for condition.
	 */
	private final int size;
	/**
	 * Control value for reversing condition.
	 */
	private final boolean controlValue;

	/**
	 * Constructor for SizeSFilter.
	 * @param size for condition
	 * @param control if control is false than the comparing condition is reversed.
	 */
	public SizeSFilter(int size, boolean control) {
		if (size < 0) {
			throw new IllegalArgumentException("Size can't be negative");
		}
		this.size = size;
		controlValue = control;
	}
	@Override
	public boolean accept(File pathname) {
		boolean forExport;
		if (pathname.length() <= size) {
			forExport = true;
		} else {
			forExport = false;
		}
		if (controlValue) {
			return forExport;
		} else {
			return !forExport;
		}
	}

}
