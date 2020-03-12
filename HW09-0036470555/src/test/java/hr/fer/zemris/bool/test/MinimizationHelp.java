package hr.fer.zemris.bool.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;
import hr.fer.zemris.bool.qmc.QMCMinimizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinimizationHelp {
    public BooleanVariable varA;
    public BooleanVariable varB;
    public BooleanVariable varC;
    public BooleanVariable varD;
    public BooleanVariable varE;

    public MinimizationHelp(BooleanVariable varA, BooleanVariable varB,
	    BooleanVariable varC, BooleanVariable varD, BooleanVariable varE) {
	this.varA = varA;
	this.varB = varB;
	this.varC = varC;
	this.varD = varD;
	this.varE = varE;
    }

    public boolean verifyFunction(BooleanFunction function, int expectedSize,
	    List<List<Mask>> expectedFunctionsMasks) {
	return verifyFunction(function, expectedSize, expectedFunctionsMasks,
		true);
    }

    public boolean verifyFunction(BooleanFunction function, int expectedSize,
	    List<List<Mask>> expectedFunctionsMasks, boolean wannaHaveProducts) {
	MaskBasedBF[] functions = QMCMinimizer.minimize(function,
		wannaHaveProducts);
	assertEquals("Size mismatch: ", expectedSize, functions.length);

	List<List<Mask>> functionsMasks = new ArrayList<>();
	for (MaskBasedBF func : functions) {
	    functionsMasks.add(func.getMasks());
	    assertTrue(func.getDontCareMasks().isEmpty());
	}

	Set<Set<Mask>> expectedSet = new HashSet<>();
	for (List<Mask> list : expectedFunctionsMasks) {
	    expectedSet.add(new HashSet<Mask>(list));
	}

	Set<Set<Mask>> actualSet = new HashSet<>();
	for (List<Mask> list : functionsMasks) {
	    actualSet.add(new HashSet<Mask>(list));
	}

	assertEquals("Minimal forms mismatch: ", expectedSet, actualSet);

	return true;
    }
}