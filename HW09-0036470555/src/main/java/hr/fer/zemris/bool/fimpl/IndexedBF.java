package hr.fer.zemris.bool.fimpl;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Class IndexedBFrepresents a boolean function which is defined by specifying
 * indexes of minterms (or maxterms) and indexes of don't cares.
 * The idea is to enable user to specify functions formally defined
 * as:
 * <ul>
 * <li> f ( A , B , C )=∑ m ( 1,2,5 ) +∑ d ( 0,4,7)
 * <li> g ( A , B , C )=∏ M ( 3,6 ) ⋅ ∏ d ( 0,4,7 )
 * </ul>
 * Observe that these two functions are equal. Indexes, indexes2 and
 * dontCares are disjoint collections.
 * @author Ivan Pavić
 */
public class IndexedBF implements BooleanFunction  {
	/**
	 * name of IndexedBF.
	 */
	private String name;
	/**
	 * domain of IndexedBF.
	 */
	private List<BooleanVariable> domain;
	/**
	 * flag; if set, indexes are minterms, otherwise maxterms.
	 */
	private boolean indexesAreMinterms;
	/**
	 * list of indexes.
	 */
	private List<Integer> indexes;
	/**
	 * list of dontCares indexes.
	 */
	private List<Integer> dontCares;
	/**
	 * list of complementary indexes.
	 */
	private List<Integer> indexes2;
	/**
	 * Constructs IndexedBF.
	 * @param name name of IndexedBF
	 * @param domain list of domain variables
	 * @param indexsesAreMinterms flag, if set indexes are minterms, maxterms otherwise.
	 * @param indexes list of indexes
	 * @param dontCares list od dontCares
	 */
	public IndexedBF(String name, List<BooleanVariable> domain,
			boolean indexsesAreMinterms, List<Integer> indexes,
			List<Integer> dontCares) {
		this.name = name;
		this.domain = domain;
		this.indexesAreMinterms = indexsesAreMinterms;
		this.indexes = copyList(indexes);
		this.dontCares = copyList(dontCares);
		indexes2 = createIndexes2();
		if (!(Collections.disjoint(indexes, dontCares) && Collections.disjoint(indexes2, dontCares))) {
			throw new IllegalArgumentException("There was an intersection in masks!");
		}
	}
	@Override
	public boolean hasMinterm(int index) {
		if (indexesAreMinterms == true) {
			return indexes.contains(index);
		}
		return indexes2.contains(index);
	}
	@Override
	public boolean hasMaxterm(int index) {
		if (indexesAreMinterms == true) {
			return indexes2.contains(index);
		}
		return indexes.contains(index);
	}
	@Override
	public boolean hasDontCare(int index) {
		return dontCares.contains(index);
	}
	@Override
	public Iterable<Integer> mintermIterable() {
		if (indexesAreMinterms == true) {
			return Collections.unmodifiableList(indexes);
		}
		return Collections.unmodifiableList(indexes2);
	}
	@Override
	public Iterable<Integer> maxtermIterable() {
		if (indexesAreMinterms == true) {
			return Collections.unmodifiableList(indexes2);
		}
		return Collections.unmodifiableList(indexes);
	}
	@Override
	public Iterable<Integer> dontcareIterable() {
		return Collections.unmodifiableList(dontCares);
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public BooleanValue getValue() {
		return AuxiliaryClass.get(indexes, indexes2, dontCares, indexesAreMinterms, domain);
	}
	@Override
	public List<BooleanVariable> getDomain() {
		return domain;
	}
	/**
	 * Method which creates complementary indexes and that way
	 * ensures that complementary indexes will not be calculated
	 * twice.
	 * @return new list with created complementary indexes.
	 */
	private List<Integer> createIndexes2() {
		ArrayList<Integer> nova = new ArrayList<>();
		for (int i = 0; i < Math.pow(2, domain.size()); i++)
			nova.add(i);
			nova.removeAll(indexes);
			nova.removeAll(dontCares);
			return nova;
	}
	/**
	 * Copies input list and exports new list. Mainly designed
	 * for copying integer lists.
	 * @param input input list
	 * @return new list
	 */
	private List<Integer> copyList(List<Integer> input) {
		List<Integer> forExport = new ArrayList<>();
		for (Integer i : input) {
			forExport.add(i);
		}
		return forExport;
	}
}
