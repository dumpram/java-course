package hr.fer.zemris.bool;
/**
 * BooleanSource with name. Extends BooleanSource.
 * @author Ivan Pavić *
 */
public interface NamedBooleanSource extends BooleanSource {
	/**
	 * Getter for name of NamedBooleanSource.
	 * @return name of BooleanSource.
	 */
	public String getName();
}
