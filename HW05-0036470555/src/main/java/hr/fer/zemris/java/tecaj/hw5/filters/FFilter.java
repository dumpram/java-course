package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;
/**
 * FFilter accepts File object which is file.
 * @author Ivan Pavić
 *
 */
public class FFilter implements FileFilter {
	/**
	 * Control value for reversing condition.
	 */
	private final boolean controlValue;

	public FFilter(boolean control) {
		controlValue = control;
	}
	@Override
	public boolean accept(File pathname) {
		return pathname.isFile() && controlValue || !pathname.isFile() && !controlValue;
	}
}
