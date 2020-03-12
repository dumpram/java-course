package hr.fer.zemris.java.bool.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import hr.fer.zemris.bool.MaskBasedBF;
import hr.fer.zemris.bool.Masks;

import java.util.Arrays;

import org.junit.Test;

public class MaskedBasedBFTest {
	
	@Test
	public void officalTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction f1 = new MaskBasedBF(
				"f1", 
				Arrays.asList(varA, varB, varC, varD), 
				true,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
				);
		String check ="";
		for(Integer i : f1.mintermIterable()) { // Ispis:  0, 2, 9, 11, 13, 15
			check += i;
			}
		assertEquals("029111315", check);
		check = "";
		for(Integer i : f1.maxtermIterable()) { // Ispis: 1, 3, 4, 5, 6, 7, 12, 14
			check += i;
			}
		assertEquals("1345671214", check);
		check = "";
		for(Integer i : f1.dontcareIterable()) { // Ispis: 8, 10
			check += i;
			}
		assertEquals("810", check);
	}
	
	@Test 
	public void hasTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction f1 = new MaskBasedBF(
				"f1", 
				Arrays.asList(varA, varB, varC, varD), 
				true,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
				);
		assertFalse(f1.hasMinterm(1));
		assertFalse(f1.hasMaxterm(2));
		assertTrue(f1.hasDontCare(8));
		assertEquals("f1", f1.getName());
	}
	
	@Test 
	public void getTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction f1 = new MaskBasedBF(
				"f1", 
				Arrays.asList(varA, varB, varC, varD), 
				true,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
				);
		assertEquals(BooleanValue.TRUE, f1.getValue());
		assertEquals("f1", f1.getName());
		assertEquals(Arrays.asList(varA, varB, varC, varD), f1.getDomain());
	}
	@Test
	public void getValueTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction f1 = new MaskBasedBF(
				"f1", 
				Arrays.asList(varA, varB, varC, varD), 
				false,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
				);	
		varA.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.TRUE);
		varC.setValue(BooleanValue.DONT_CARE);
		assertEquals(BooleanValue.DONT_CARE, f1.getValue());
		varA.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.DONT_CARE);
		assertEquals(BooleanValue.FALSE, f1.getValue());
		String check ="";
		for(Integer i : f1.mintermIterable()) { 
			check += i;
			}
		assertEquals("1345671214", check);
		check = "";
		for(Integer i : f1.maxtermIterable()) { 
			check += i;
			}
		assertEquals("029111315", check);
		check = "";
		for(Integer i : f1.dontcareIterable()) {
			check += i;
			}
		assertEquals("810", check);
	}
	@Test 
	public void has2Test() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction f1 = new MaskBasedBF(
				"f1", 
				Arrays.asList(varA, varB, varC, varD), 
				false,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
				);
		assertFalse(f1.hasMinterm(0));
		assertFalse(f1.hasMaxterm(1));
		assertTrue(f1.hasDontCare(8));
		assertEquals("f1", f1.getName());
	}
}
