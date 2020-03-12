package hr.fer.zemris.java.tecaj.hw4.db;
/**
 * IFilter defines filter as interface with method accepts
 * which is boolean.
 * @author Ivan Pavić
 */
public interface IFilter {
	/**
	 * Method accepts returns true if some sort of criteria is
	 * met.
	 * @param record StudentRecord
	 * @return true if criteria is met, false otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
