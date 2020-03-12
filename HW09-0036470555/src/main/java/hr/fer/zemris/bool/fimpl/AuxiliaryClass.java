package hr.fer.zemris.bool.fimpl;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * This is auxiliary class for package hr.fer.zemris.bool which
 * has methods that enable getValue for MaskBasedBF and
 * IndexedBF.
 * </p>
 * @author Ivan Pavić
 *
 */
public class AuxiliaryClass {
	/**
	 * <p>
	 * Method returns BooleanValue TRUE, FALSE, or DONT_CARE
	 * based on domain input which doesn't consist DONT_CARE value.
	 * </p>
	 * @param indexes minterms or maxterms
	 * @param indexes2 minterms or maxterms
	 * @param dontCares dontCares
	 * @param control masksAreMinterms or indexAreMinterms depending
	 * 	on class that invoked
	 * @param domain list of variables
	 * @return BooleanValue
	 */
	protected static BooleanValue get3(List<Integer> indexes, List<Integer> indexes2,
			List<Integer> dontCares, boolean control,
			List<BooleanVariable> domain) {
		String represent = "";
		for (BooleanVariable i : domain) {
			if (i.getValue() == BooleanValue.FALSE) {
				represent += '0';
			} else {
				represent += "1";
			}
		}
		Mask mask = Mask.parse(represent);
		for (Integer i : indexes) {
			if (mask.equals(Mask.fromIndex(domain.size(), i))) {
				return BooleanValue.TRUE;
			}
		}
		for (Integer i : dontCares) {
			if (mask.equals(Mask.fromIndex(domain.size(), i))) {
				return BooleanValue.DONT_CARE;
			}
		}
		return BooleanValue.FALSE;
	}
	/**
	 * <p>
	 * Method returns BooleanValue TRUE, FALSE, or DONT_CARE
	 * based on domain input which consist DONT_CARE value.
	 * </p>
	 * @param indexes minterms or maxterms
	 * @param indexes2 minterms or maxterms
	 * @param dontCares dontCares
	 * @param control masksAreMinterms or indexAreMinterms depending
	 * 	on class that invoked
	 * @param domain list of variables
	 * @return value of function that invoked
	 */
	protected static BooleanValue get(List<Integer> indexes, List<Integer> indexes2,
			List<Integer> dontCares, boolean control,
			List<BooleanVariable> domain) {
			List<BooleanVariable> andTree = new ArrayList<>();
			BooleanVariable[] forExport;
			List<BooleanVariable> domain1;
			BooleanVariable[] array = new BooleanVariable[domain.size()];
		if (!control) {
				indexes = indexes2;
			}
		if (!containsX(domain)) {
			return get3(indexes, indexes2, dontCares, control, domain);
			}
			for (int j = 0; j < Math.pow(2, domain.size()); j++) {
				if (indexes.contains(j) && !dontCares.contains(j)) {
					domain1 = assignValuesFromIndex(j, domain);
					for (int i = 0; i < domain1.size(); i++) {
							array[i] = domain1.get(i);
					}
				andTree.add(new BooleanVariable(BooleanOperators.and(array).getValue()));
				}
			}
			forExport = new BooleanVariable[andTree.size()];
			for (int i = 0; i < andTree.size(); i++) {
				forExport[i] = andTree.get(i);
			}
			return BooleanOperators.or(forExport).getValue();
	}
	/**
	 * <p>
	 * Method assignValesFromIndex creates new list from domain
	 * which has values that correspond to structure of minterm
	 * of given index. For example if variable values in domain
	 * are TRUE, FALSE, DONT_CARE, and minterm index is 1, it will
	 * return list with variables of values FALSE, TRUE, DONT_CARE.
	 * </p>
	 * @param index given minterm index
	 * @param domain list of variables
	 * @return list with modified variables
	 */
	private static List<BooleanVariable> assignValuesFromIndex(int index, List<BooleanVariable>domain) {
		String represents = Integer.toBinaryString(index);
		List<BooleanVariable> forExport = new ArrayList<>();
		while (represents.length() != domain.size()) {
			represents = "0" + represents;
		}
		for (int i = 0; i < represents.length(); i++) {
			forExport.add(new BooleanVariable(domain.get(i).getValue()));
			if (represents.charAt(i) == '0') {
				forExport.get(i).setValue(BooleanOperators.not(forExport.get(i)).getValue());
			}
		}
		return forExport;
	}
	/**
	 * @param domain list of variables
	 * @return true if any variable in domain contains true, false otherwise.
	 */
	private static boolean containsX(List<BooleanVariable> domain) {
		for (BooleanVariable i : domain) {
			if (i.getValue() == BooleanValue.DONT_CARE) {
				return true;
			}
		}
		return false;
	}
}

