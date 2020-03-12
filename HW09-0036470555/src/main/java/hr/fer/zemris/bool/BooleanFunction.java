package hr.fer.zemris.bool;
/**
 * BooleanFunction represents interface. It declares methods like hasMinterm,
 * hasMaxterm and hasDontCare; they accept a single argument: index,
 * and check if function contains appropriate minterm/maxterm/dontcare.
 * @author Ivan Pavić
 *
 */
public interface BooleanFunction extends NamedBooleanSource {
	/**
	 * Checks if BooleanFunction has minterm on given index.
	 * @param index given index
	 * @return true if function has minterm, false otherwise;
	 */
	boolean hasMinterm(int index);
	/**
	 * Checks if BooleanFunction has maxterm on given index.
	 * @param index given index
	 * @return true if function has maxterm, false otherwise;
	 */
	boolean hasMaxterm(int index);
	/**
	 * Checks if BooleanFunction has dontCare on given index.
	 * @param index given index
	 * @return true if function has dontCare, false otherwise;
	 */
	boolean hasDontCare(int index);
	/**
	 * Returns Iterable. Dear User, you can use this if you
	 * want see which indexes contain minterms.
	 * @return Iterable object
	 */
	Iterable<Integer> mintermIterable();
	/**
	 * Returns Iterable. Dear User, you can use this if you
	 * want see which indexes contain maxterms.
	 * @return Iterable object
	 */
	Iterable<Integer> maxtermIterable();
	/**
	 * Returns Iterable. Dear User, you can use this if you
	 * want see which indexes contain dontCares.
	 * @return Iterable object
	 */
	Iterable<Integer> dontcareIterable();
}
