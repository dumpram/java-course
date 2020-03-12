package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class represents implementation of boolean function minimizer with QMC
 * method. Minimal functions are defined with Pyne-McCluskey approach and then
 * additionally filtered by minimal number of logic gates criteria.
 * 
 * @author Ivan Pavić
 * 
 */
public class QMCMinimizer {
    /**
     * Static class representing combination of masks. It declares mask, terms
     * and flag isDontCare which is used to determine if implicant is consider
     * in final solution.
     * 
     * @author Ivan Pavić
     * 
     */
    static class Comb {
	/**
	 * Private instance of mask.
	 */
	private final Mask mask;
	/**
	 * List of terms that mask considers.
	 */
	private final List<Integer> terms;
	/**
	 * DontCare flag.
	 */
	private final boolean isDontCare;

	/**
	 * Constructor with default fields.
	 * 
	 * @param mask
	 *            mask of combination
	 * @param terms
	 *            terms of combination
	 * @param isDontCare
	 *            don't care flag
	 */
	public Comb(Mask mask, List<Integer> terms, boolean isDontCare) {
	    this.mask = mask;
	    this.terms = terms;
	    this.isDontCare = isDontCare;
	}

	/**
	 * Getter for mask.
	 * 
	 * @return reference to instance of mask.
	 */
	public Mask getMask() {
	    return mask;
	}

	/**
	 * Getter for isDontCare.
	 * 
	 * @return isDontCare private flag
	 */
	public boolean isDontCare() {
	    return isDontCare;
	}

	/**
	 * Getter for terms.
	 * 
	 * @return list of terms.
	 */
	public List<Integer> getTerms() {
	    return terms;
	}

	/**
	 * Combinations are equal if all field are equal. Terms must be same,
	 * not necessary in same order. This implementation of equals method is
	 * created only for this class, null references aren't checked, classes
	 * are supposed to be equal. This is reduced version of method which is
	 * implemented after lot of tests.
	 */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
		return true;
	    }
	    Comb other = (Comb) obj;
	    if (isDontCare != other.isDontCare) {
		return false;
	    }
	    if (!mask.equals(other.mask)) {
		return false;
	    } else if (!new HashSet<Integer>(terms)
		    .equals(new HashSet<Integer>(other.terms))) {
		return false;
	    }
	    return true;
	}
    }

    /**
     * The most important method of class. Delegates job to other methods and in
     * the end returns solutions in array of MaskBasedBF. Solutions represent
     * minimal forms of given BooleanFunction bf.
     * 
     * @param bf
     *            given BooleanFunction
     * @param weWantProducts
     *            if true masks represent min_terms, maxterms otherwise
     * @return array of mask based boolean functions
     */
    public static MaskBasedBF[] minimize(BooleanFunction bf,
	    boolean weWantProducts) {

	/* Number of variables in bf */
	int size = bf.getDomain().size();
	/* List of indexes */
	List<Integer> indexes;
	/* As defined in class doc */
	if (!weWantProducts) {
	    indexes = extractListOfIndexes(bf.maxtermIterable());
	} else {
	    indexes = extractListOfIndexes(bf.mintermIterable());
	}

	List<Comb> masks = new ArrayList<>(); // masks
	List<Comb> temp = new ArrayList<>(); // temporary combinations
	List<Comb> dump = new ArrayList<>(); // combinations that are no longer
					     // necessary
	List<Comb> result = new ArrayList<>(); // combinations included in
					       // result
	List<Mask> forExportDontCares = new ArrayList<>();

	for (Integer i : indexes) {
	    masks.add(new Comb(Mask.fromIndex(size, i), Arrays.asList(i), false));
	}
	List<Integer> dontCares = extractListOfIndexes(bf.dontcareIterable());

	for (Integer i : dontCares) {
	    masks.add(new Comb(Mask.fromIndex(size, i), Arrays.asList(i), true));
	}

	boolean control = true; // if true change occurred

	while (control) {
	    control = false;
	    for (int i = 0; i < masks.size(); i++) {
		Comb current = masks.get(i);
		for (int j = i + 1; j < masks.size(); j++) {
		    Comb test = masks.get(j);
		    Mask newOne = Mask.combine(current.getMask(),
			    test.getMask());
		    if (newOne != null) {
			Comb forTemp = new Comb(newOne, listOfTerms(current,
				test), current.isDontCare()
				&& test.isDontCare());
			if (!temp.contains(forTemp)) {
			    temp.add(forTemp);
			}
			control = true;
			dump.add(current);
			dump.add(test);
		    }
		}
	    }
	    masks.removeAll(dump);
	    result.addAll(masks);
	    masks.clear();
	    dump.clear();
	    masks.addAll(temp);
	    temp.clear();
	}
	for (Comb i : new ArrayList<>(result)) {
	    if (i.isDontCare()) {
		result.remove(i);
	    }
	}
	// map of indexes coverage, key is index, value is list of integers
	// representing result combs indexes
	Map<Integer, List<Integer>> coverage = new HashMap<>();
	for (Integer i : indexes) {
	    for (int j = 0; j < result.size(); j++) {
		if (result.get(j).getTerms().contains(i)) {
		    ArrayList<Integer> update = new ArrayList<>();
		    update.add(j);
		    if (coverage.get(i) == null) {
			coverage.put(i, update);
		    } else {
			update.addAll(coverage.get(i));
			coverage.put(i, update);
		    }
		}
	    }
	}

	List<Integer> solutionIndexes = new ArrayList<>();
	for (Integer i : new ArrayList<>(indexes)) {
	    if (coverage.get(i).size() == 1) {
		indexes.removeAll(result.get(coverage.get(i).get(0)).getTerms());
		if (!solutionIndexes.contains(coverage.get(i).get(0))) {
		    solutionIndexes.add(coverage.get(i).get(0));
		}
	    }
	}
	MaskBasedBF[] forExport;
	PyneMcCluskey method = new PyneMcCluskey(indexes, coverage, result,
		size);
	if (method.paths.size() != 0) {
	    forExport = new MaskBasedBF[method.paths.size()];
	} else {
	    forExport = new MaskBasedBF[1];
	}

	List<Mask> forExportPrim = new ArrayList<>();

	for (Integer i : solutionIndexes) {
	    forExportPrim.add(result.get(i).getMask());
	}
	int brojac = 0;
	for (Set<Integer> i : method.getPaths()) {
	    List<Mask> newOne = new ArrayList<>();
	    for (Integer j : i) {
		newOne.add(result.get(j).getMask());
	    }
	    newOne.addAll(forExportPrim);
	    forExport[brojac++] = new MaskBasedBF(bf.getName(), bf.getDomain(),
		    weWantProducts, newOne, forExportDontCares);
	}
	return forExport;
    }

    /**
     * PyneMcCluskey class does job of finding minimal forms by generating
     * functions of coverage.
     * 
     * @author Ivan Pavić
     * 
     */
    static class PyneMcCluskey {
	/**
	 * Dummy variable to avoid magic numbers error.
	 */
	private final int i = 0;
	/**
	 * Represent size of minimal forms.
	 */
	private int min;
	/**
	 * Indexes that are considered in finding optimum coverage path.
	 */
	private final List<Integer> indexes;
	/**
	 * Map of coverage. Key is index, value is list of integers representing
	 * indexes of result combs.
	 */
	private final Map<Integer, List<Integer>> coverage;
	/**
	 * Set of integers which represent path build in recursion through
	 * coverage map.
	 */
	private final Set<Integer> path = new HashSet<>();
	/**
	 * List of sets representing all paths considered in final solution.
	 */
	private final List<Set<Integer>> paths = new ArrayList<>();
	/**
	 * List of result combs generate in minimize method.
	 */
	private final List<Comb> result;
	/**
	 * Minimal number of logic gates.
	 */
	private int minGate;

	/**
	 * Constructor accepts all that is necessary for finding minimal forms.
	 * 
	 * @param indexes
	 *            list of indexes
	 * @param coverage
	 *            map of coverage
	 * @param result
	 *            list of result combs
	 * @param varNumber
	 *            variable number to calculate minimum amount of logic
	 *            gates.
	 */
	public PyneMcCluskey(List<Integer> indexes,
		Map<Integer, List<Integer>> coverage, List<Comb> result,
		int varNumber) {
	    this.indexes = indexes;
	    this.coverage = coverage;
	    this.result = result;
	    min = indexes.size();
	    findPaths(i);
	    minGate = min * varNumber;
	    leaveMinPaths();
	    leaveMinGates();
	}

	/**
	 * Leaves only paths with minimum number of logic gates in paths.
	 */
	private void leaveMinGates() {
	    for (Set<Integer> i : new ArrayList<>(paths)) {
		int numberOfGates = 0;
		for (Integer j : new HashSet<Integer>(i)) {
		    numberOfGates += result.get(j).getMask().getNumberOfOnes()
			    + result.get(j).getMask().getNumberOfZeros();
		}
		if (numberOfGates > minGate) {
		    paths.remove(i);
		}
	    }

	}

	/**
	 * Leaves paths with minimum amount of combs in paths. Also calculates
	 * minimum amount of gates.
	 */
	private void leaveMinPaths() {
	    for (Set<Integer> i : new ArrayList<Set<Integer>>(paths)) {
		if (i.size() > min) {
		    paths.remove(i);
		} else {
		    int numberOfGates = 0;
		    for (Integer j : new HashSet<Integer>(i)) {
			numberOfGates += result.get(j).getMask()
				.getNumberOfOnes()
				+ result.get(j).getMask().getNumberOfZeros();
		    }
		    if (numberOfGates < minGate) {
			minGate = numberOfGates;
		    }
		}
	    }
	}

	/**
	 * Recursive method for finding optimal path through the map(a.k.a.
	 * minimal amount of combs).
	 * 
	 * @param i
	 *            depth in recursion
	 */
	private void findPaths(int i) {
	    if (i == indexes.size()) {
		if (!paths.contains(path)) {
		    paths.add(new HashSet<Integer>(path));
		}
		if (path.size() < min) {
		    min = path.size();
		}
		return;
	    }

	    if (path.size() > min) {
		return;
	    }

	    List<Integer> current = coverage.get(indexes.get(i));
	    for (int j = 0; j < current.size(); j++) {
		boolean t = path.contains(current.get(j));
		path.add(current.get(j));
		findPaths(i + 1);
		if (!t) {
		    path.remove(current.get(j));
		}
	    }
	}

	/**
	 * Getter for paths.
	 * 
	 * @return list of sets representing paths
	 */
	public List<Set<Integer>> getPaths() {
	    return paths;
	}
    }

    /**
     * Combines two list of terms into new list and returns it. No duplicates
     * are allowed.
     * 
     * @param current
     *            first list
     * @param test
     *            second list
     * @return new list, combinations of two lists
     */
    private static List<Integer> listOfTerms(Comb current, Comb test) {
	List<Integer> forExport = new ArrayList<>();
	forExport.addAll(test.getTerms());
	forExport.removeAll(current.getTerms());
	forExport.addAll(current.getTerms());
	return forExport;
    }

    /**
     * Extracts list of indexes from given {@link Iterable} object.
     * 
     * @param mintermIterable
     *            given iterable object
     * @return list of elements of iterable object
     */
    private static List<Integer> extractListOfIndexes(
	    Iterable<Integer> mintermIterable) {
	List<Integer> newOne = new ArrayList<>();
	for (Integer i : mintermIterable) {
	    newOne.add(i);
	}
	return newOne;
    }

}
