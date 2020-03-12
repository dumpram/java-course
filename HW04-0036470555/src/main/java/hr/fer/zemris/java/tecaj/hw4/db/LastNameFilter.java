package hr.fer.zemris.java.tecaj.hw4.db;
/**
 * LastNameFilter implements filter. Here
 * the criteria is LastName for method accepts.
 * @author Ivan Pavić
 */
public class LastNameFilter implements IFilter {
	/**
	 * Private String which has criteria for method accepts
	 */
	private final String pattern;
	/**
	 * Constructs LastNameFilter with pattern.
	 * @param pattern representing criteria that has to be met.
	 */
	public LastNameFilter(String pattern) {
		if (pattern == null) {
			throw new IllegalArgumentException("Argument cant't be null");
		}
		pattern = pattern.trim();
		this.pattern = pattern;
	}
	/**
	 * Overrides method in interface by criteria of last name.
	 */
	@Override
	public boolean accepts(final StudentRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("Argument cant't be null");
		}
		if (pattern.indexOf("*") == 0) {
			return record.getLastName().endsWith(pattern.substring(1));
		} else if (pattern.indexOf("*") == pattern.length()-1) {
			return record.getLastName().startsWith(pattern.substring(0, pattern.length() - 1));
		} else if (pattern.indexOf("*") != -1) {
			final String[] parts = pattern.split("\\*");
			return record.getLastName().startsWith(parts[0]) && record.getLastName().endsWith(parts[1]);
		} else {
			return record.getLastName().equals(pattern);
		}
	}
}
