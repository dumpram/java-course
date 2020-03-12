package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * EFilter accepts files with extension.
 *
 * @author Ivan Pavić
 *
 */
public class EFilter implements FileFilter {

	private final boolean controlValue;

	public EFilter(boolean control) {
		controlValue = control;
	}

	@Override
	public boolean accept(File pathname) {
		boolean forExport;
		if (pathname.isFile()) {
			forExport = pathname.getName().contains(".");
			if(controlValue) {
				return forExport;
			} else {
				return !forExport;
			}
		}
		return !controlValue;
	}
}
