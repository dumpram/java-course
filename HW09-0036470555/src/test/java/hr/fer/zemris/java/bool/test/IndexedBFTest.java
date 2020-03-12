package hr.fer.zemris.java.bool.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;




import hr.fer.zemris.bool.fimpl.IndexedBF;

import java.util.Arrays;

import org.junit.Test;

public class IndexedBFTest {
	
	@Test
	public void officalTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				true,
				Arrays.asList(0,1,5,7),
				Arrays.asList(2,3)
				);
		String check ="";
		for(Integer i : f1.mintermIterable()) { // Ispis: 0, 1, 5,  7
			check += i;
			}
		assertEquals("0157", check);
		check = "";
		for(Integer i : f1.maxtermIterable()) { // Ispis: 4 , 6
			check += i;
			}
		assertEquals("46", check);
		check = "";
		for(Integer i : f1.dontcareIterable()) { // Ispis: 2, 3
			check += i;
			}
		assertEquals("23", check);
	}
	
	@Test 
	public void hasTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				true,
				Arrays.asList(0,1,5,7),
				Arrays.asList(2,3)
				);
		assertTrue(f1.hasMinterm(1));
		assertFalse(f1.hasMaxterm(2));
		assertTrue(f1.hasDontCare(2));
		assertEquals("f1", f1.getName());
		assertEquals(Arrays.asList(varA, varB, varC), f1.getDomain());		
	}
	
	@Test 
	public void getTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				true,
				Arrays.asList(0,1,5,7),
				Arrays.asList(2,3)
				);
		assertEquals(BooleanValue.TRUE, f1.getValue());
		assertEquals("f1", f1.getName());
		assertEquals(Arrays.asList(varA, varB, varC), f1.getDomain());
	}
	@Test
	public void getValueTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				true,
				Arrays.asList(0,1,5,7),
				Arrays.asList(2,3)
				);
		varA.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.TRUE);
		varC.setValue(BooleanValue.DONT_CARE);
		assertEquals(BooleanValue.FALSE, f1.getValue());
		varA.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.DONT_CARE);
		assertEquals(BooleanValue.DONT_CARE, f1.getValue());
	}
	@Test
	public void getValue2Test() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				false,
				Arrays.asList(0,1,5,7),
				Arrays.asList(2,3)
				);
		varA.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.TRUE);
		assertEquals(BooleanValue.FALSE, f1.getValue());
		varA.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.TRUE);
		varC.setValue(BooleanValue.FALSE);
		assertEquals(BooleanValue.DONT_CARE, f1.getValue());
		String check ="";
		for(Integer i : f1.mintermIterable()) { // Ispis: 0, 1, 5,  7
			check += i;
			}
		assertEquals("46", check);
		check = "";
		for(Integer i : f1.maxtermIterable()) { // Ispis: 4 , 6
			check += i;
			}
		assertEquals("0157", check);
		check = "";
		for(Integer i : f1.dontcareIterable()) { // Ispis: 2, 3
			check += i;
			}
		assertEquals("23", check);
	}
	@Test
	public void has1Test() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF(
				"f1", 
				Arrays.asList(varA, varB, varC), 
				false,
				Arrays.asList(0,1,5,7),
				Arrays.asList(2,3)
				);
		assertFalse(f1.hasMinterm(1));
		assertFalse(f1.hasMaxterm(2));
		assertTrue(f1.hasDontCare(2));
		assertEquals("f1", f1.getName());
		assertEquals(Arrays.asList(varA, varB, varC), f1.getDomain());		
	}
	
}
