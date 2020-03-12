package hr.fer.zemris.bool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
/**
 * Class MaskedBasedBFrepresents a boolean function which is defined
 * by specifying minterm/maxterm and don't care masks. For example:
 * BooleanFunction bF = new MaskBasedBF(
 * <ul>"f",
 * domain,
 * true,
 * Masks.fromStrings("0x00","000x"), Masks.fromStrings("10x0"));</ul>
 * specifies:
 * <ul>f ( A , B , C , D )=∑ m ( 0,1,4 )+∑ d ( 8,10 )
 * @author Ivan Pavić
 *
 */
public class MaskBasedBF implements BooleanFunction {
	/**
	 * name of IndexedBF.
	 */
	private final String name;
	/**
	 * domain of MaskBasedBF.
	 */
	private final List<BooleanVariable> domain;
	/**
	 * flag; if set, masks are minterms, otherwise maxterms.
	 */
	private final boolean masksAreMinterms;
	/**
	 * list of masks.
	 */
	private final List<Mask> masks;
	/**
	 * list of dontCareMasks.
	 */
	private final List<Mask> dontCareMasks;
	/**
	 * list of indexes.
	 */
	private List<Integer> indexes;
	/**
	 * list of complementary indexes.
	 */
	private List<Integer> indexes2;
	/**
	 * list of dontCares indexes.
	 */
	private List<Integer> dontCares;
	/**
	 * Constructs MaskedBasedBF.
	 * @param name name of MaskedBF
	 * @param domain list of domain variables.
	 * @param masksAreMinterms flag, if set masks are minterms, maxterms otherwise.
	 * @param masks list of indexes
	 * @param dontCareMasks list of dontCares
	 */
	public MaskBasedBF(final String name, final List<BooleanVariable> domain,
			final boolean masksAreMinterms, final List<Mask> masks, final List<Mask> dontCareMasks) {
		if (name == null || domain == null || masks == null || dontCareMasks == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.domain = domain;
		this.masksAreMinterms = masksAreMinterms;
		this.masks = copyList(masks);
		this.dontCareMasks = copyList(dontCareMasks);
		createIndexes();
		createDontCares();
		createIndexes2();
		if (!(Collections.disjoint(indexes, dontCares))) {
			throw new IllegalArgumentException("There was an intersection in masks!");
		}
	}
	/**
	 * Creates dontCares indexes from dontCareMasks.
	 */
	private void createDontCares() {
		final LinkedHashSet<Integer> newOne = new LinkedHashSet<>();
		for (final Mask other : dontCareMasks) {
			for (int i = 0; i < Math.pow(2, domain.size()); i++) {
				if (other.dontCareEquals(Mask.fromIndex(other.getSize(), i))) {
					newOne.add(i);
				}
			}
		}
		dontCares = new ArrayList<Integer>(newOne);
	}
	/**
	 * Creates indexes from masks.
	 */
	private void createIndexes() {
		final LinkedHashSet<Integer> newOne = new LinkedHashSet<>();
		for (final Mask other : masks) {
			for (int i = 0; i < Math.pow(2, domain.size()); i++) {
				if (other.dontCareEquals(Mask.fromIndex(other.getSize(), i))) {
					newOne.add(i);
				}
			}
		}
		indexes = new ArrayList<Integer>(newOne);
	}
	/**
	 * Creates complementary indexes from masks and dontCareMasks.
	 */
	private void createIndexes2 () {
		indexes2 = new ArrayList<>();
		for(int i = 0; i < (int)Math.pow(2,domain.size()); i++) {
			indexes2.add(i);
		}
			indexes2.removeAll(indexes);
			indexes2.removeAll(dontCares);
	}
	/**
	 * Getter for function name.
	 */
	@Override
	public String getName() {
		return this.name;
	}
	/**
	 * Getter for value. Depends on AuxiliaryClass in same package.
	 */
	@Override
	public BooleanValue getValue() {
		return AuxiliaryClass.get(indexes, indexes2, dontCares, masksAreMinterms, domain);
	}
	/**
	 * Gets domain. List of variables which produce the value;
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return domain;
	}
	@Override
	public boolean hasMinterm(final int index) {
		if (masksAreMinterms == true) {
			return indexes.contains(index);
		}
		else {
			return indexes2.contains(index);
		}
	}
	@Override
	public boolean hasMaxterm(final int index) {
		if (masksAreMinterms == true) {
			return indexes2.contains(index);
		}
		else {
			return indexes.contains(index);
		}
	}
	@Override
	public boolean hasDontCare(final int index) {
		return dontCares.contains(index);
	}
	@Override
	public Iterable<Integer> mintermIterable() {
		if (masksAreMinterms == true) {
			return Collections.unmodifiableList(indexes);
		} else {
			return Collections.unmodifiableList(indexes2);
		}
	}
	@Override
	public Iterable<Integer> maxtermIterable() {
		if (masksAreMinterms == true) {
			return Collections.unmodifiableList(indexes2);
		} else {
			return Collections.unmodifiableList(indexes);
		}
	}
	@Override
	public Iterable<Integer> dontcareIterable() {
		return Collections.unmodifiableList(dontCares);
	}
	/**
	 * Method designed for purpose of copying mask list.
	 * @param input list of masks
	 * @return new list with elements of input list
	 */
	private List<Mask> copyList(final List<Mask> input) {
		final List<Mask> forExport = new ArrayList<>();
		for (final Mask i : input) {
			forExport.add(Mask.parse(i.toString()));
		}
		return forExport;
	}
}
