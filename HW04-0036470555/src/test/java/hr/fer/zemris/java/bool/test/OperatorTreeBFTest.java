package hr.fer.zemris.java.bool.test;
import java.util.Arrays;

import hr.fer.zemris.bool.*;
import hr.fer.zemris.bool.opimpl.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class OperatorTreeBFTest {
	
	@Test
	public void officalTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(
		BooleanConstant.FALSE, 
		varC,
		BooleanOperators.and(varA, BooleanOperators.not(varB))
		);
		BooleanFunction f1 = new OperatorTreeBF(
		"f1", 
		Arrays.asList(varA, varB, varC), 
		izraz1);
		String check ="";
		for(Integer i : f1.mintermIterable()) { // Ispis: 1, 3, 4, 5, 7
			check += i;
			}
		assertEquals("13457", check);
		check = "";
		for(Integer i : f1.maxtermIterable()) { // Ispis: 0, 2, 6
			check += i;
			}
		assertEquals("026", check);
		check = "";
		for(Integer i : f1.dontcareIterable()) { // Ispis: 
			check += i;
			}
		assertEquals("", check);
	}
	
	@Test
	public void getTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(
		BooleanConstant.FALSE, 
		varC,
		BooleanOperators.and(varA, BooleanOperators.not(varB))
		);
		BooleanFunction f1 = new OperatorTreeBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				izraz1);
		assertEquals(BooleanValue.FALSE, f1.getValue());
		assertEquals("f1", f1.getName());
		assertEquals(Arrays.asList(varA, varB, varC), f1.getDomain());
	}
	
	@Test
	public void hasTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(
		BooleanConstant.FALSE, 
		varC,
		BooleanOperators.and(varA, BooleanOperators.not(varB)),
		BooleanConstant.DONT_CARE
		);
		BooleanFunction f1 = new OperatorTreeBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				izraz1);
		assertTrue(f1.hasMinterm(1));
		assertFalse(f1.hasMaxterm(2));
		assertTrue(f1.hasDontCare(2));
		assertEquals("f1", f1.getName());
		assertEquals(Arrays.asList(varA, varB, varC), f1.getDomain());
	}
	@Test(expected = IllegalArgumentException.class)
	public void hasIllegal1Test() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(
		BooleanConstant.FALSE, 
		varC,
		BooleanOperators.and(varA, BooleanOperators.not(varB)),
		new BooleanVariable(BooleanValue.DONT_CARE)
		);
		@SuppressWarnings("unused")
		BooleanFunction f1 = new OperatorTreeBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				izraz1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void hasIllegal2Test() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(
		BooleanConstant.FALSE, 
		varC,
		BooleanOperators.and(varA, BooleanOperators.not(varB))
		);
		@SuppressWarnings("unused")
		BooleanFunction f1 = new OperatorTreeBF(
				null, 
				Arrays.asList(varA, varB, varC), 
				izraz1);
	}	
}
