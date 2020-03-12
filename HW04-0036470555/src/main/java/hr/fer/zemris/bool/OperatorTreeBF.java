package hr.fer.zemris.bool;
import java.util.ArrayList;
import java.util.List;

/**
 * The class OperatorTreeBF allows us to construct a boolean function by specifying
 * an operator tree.
 * @author Ivan Pavić
 */
public class OperatorTreeBF implements BooleanFunction {
	/**
	 * name of OperatorTreeBF.
	 */
	String name;
	/**
	 * domain of OperatorTreeBF
	 */
	List<BooleanVariable> domain;
	/**
	 * operatorTree which is essence of OperatorTreeBF.
	 */
	BooleanOperator operatorTree;
	/**
	 * Contstruct OpretatorTreeBF.
	 * @param name of function.
	 * @param domain list of domain variables.
	 * @param operatorTree BooleanOperator which is "source" of function.
 	 */
	public OperatorTreeBF(final String name, final List<BooleanVariable> domain,
			final BooleanOperator operatorTree) {
		if (name == null || domain == null || operatorTree == null) {
			throw new IllegalArgumentException();
		}
		if (!domain.containsAll(operatorTree.getDomain())) {
			throw new IllegalArgumentException(
					"Domain must contain all variables of operatorTree");
		}
		this.name = name;
		this.domain = domain;
		this.operatorTree = operatorTree;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public BooleanValue getValue() {
		return operatorTree.getValue();
	}
	@Override
	public List<BooleanVariable> getDomain() {
		return domain;
	}
	@Override
	public boolean hasMinterm(final int index) {
		assignValuesFromIndex(index);
		if (operatorTree.getValue() == BooleanValue.TRUE) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean hasMaxterm(final int index) {
		assignValuesFromIndex(index);
		if (operatorTree.getValue() == BooleanValue.FALSE) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean hasDontCare(final int index) {
		assignValuesFromIndex(index);
		if (operatorTree.getValue() == BooleanValue.DONT_CARE) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Iterable<Integer> mintermIterable() {
		final ArrayList<Integer> minterms = new ArrayList<>();
		for (int i = 0, number = (int) Math.pow(2, domain.size()); i < number; i++) {
			if (hasMinterm(i)) {
				minterms.add(i);
			}
		}
		return minterms;
	}
	@Override
	public Iterable<Integer> maxtermIterable() {
		final ArrayList<Integer> maxterms = new ArrayList<>();
		for (int i = 0, number = (int) Math.pow(2, domain.size()); i < number; i++) {
			if (hasMaxterm(i)) {
				maxterms.add(i);
			}
		}
		return maxterms;
	}
	@Override
	public Iterable<Integer> dontcareIterable() {
		final ArrayList<Integer> dontCares = new ArrayList<>();
		for (int i = 0, number = (int) Math.pow(2, domain.size()); i < number; i++) {
			if (hasDontCare(i)) {
				dontCares.add(i);
			}
		}
		return dontCares;
	}
	/**
	 * Private method which changes values of domain variables
	 * for creating lists of minterms, maxterms or dontCares.
	 * @param index given index for adjusting values of variables.
	 */
	private void assignValuesFromIndex(final int index) {
		String represents = Integer.toBinaryString(index);
		while (represents.length() != domain.size()) {
			represents = "0" + represents;
		}
		for (int i = 0; i < represents.length(); i++) {
			if (represents.charAt(i) == '1') {
				domain.get(i).setValue(BooleanValue.TRUE);
			} else {
				domain.get(i).setValue(BooleanValue.FALSE);
			}
		}
	}
}
