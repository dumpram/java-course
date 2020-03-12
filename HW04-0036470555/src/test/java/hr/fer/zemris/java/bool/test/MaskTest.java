package hr.fer.zemris.java.bool.test;

import static org.junit.Assert.*;

import java.util.List;

import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.Masks;

import org.junit.Test;

public class MaskTest {
	
	@Test
	public void combineTest() {
		Mask Mask1 = Mask.parse("1x10x");
		Mask Mask2 = Mask.parse("0x11x");
		assertEquals("xx1xx", Mask.combine(Mask1, Mask2).toString());
		Mask1 = Mask.parse("1x11x");
		Mask2 = Mask.parse("0111x");
		assertNull(Mask.combine(Mask1, Mask2));
		Mask1 = Mask.parse("0x01x");
		Mask2 = Mask.parse("0x11x");
		assertEquals("0xx1x", Mask.combine(Mask1, Mask2).toString());
	}
	
	@Test
	public void isMoreGeneralThanTest() {
		Mask Mask1 = Mask.parse("1x10x");
		Mask Mask2 = Mask.parse("0x11x");
		assertFalse(Mask1.isMoreGeneralThan(Mask2));
		Mask1 = Mask.parse("xx11x");
		Mask2 = Mask.parse("0111x");
		assertTrue(Mask1.isMoreGeneralThan(Mask2));
	}
	@Test 
	public void getNumberOfTest() {
		Mask Mask1 = Mask.parse("1x10x");
		assertEquals(2, Mask1.getNumberOfOnes());
		assertEquals(1, Mask1.getNumberOfZeros());		
	}
	@Test(expected = IllegalArgumentException.class)
	public void parseTest() {
		@SuppressWarnings("unused")
		Mask Mask1 = Mask.parse("1009");
	}
	@Test(expected = IllegalArgumentException.class)
	public void parse1Test() {
		@SuppressWarnings("unused")
		Mask Mask1 = Mask.parse(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void parse2Test() {
		@SuppressWarnings("unused")
		Mask Mask1 = Mask.parse("");
	}
	@Test(expected = IllegalArgumentException.class)
	public void getValueTest() {
		Mask Mask1 = Mask.parse("1001");
		Mask1.getValue(-3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void getValue2Test() {
		Mask Mask1 = Mask.parse("1001");
		Mask1.getValue(5);
	}
	@Test(expected = IllegalArgumentException.class)
	public void dontCareEqualsTest() {
		Mask Mask1 = Mask.parse("1001");
		Mask1.dontCareEquals(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void fromIndex1Test() {
		@SuppressWarnings("unused")
		Mask Mask1 = Mask.fromIndex(0, -3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void fromIndex2Test() {
		@SuppressWarnings("unused")
		Mask Mask1 = Mask.fromIndex(1, 4);
	}
	@Test(expected = IllegalArgumentException.class)
	public void fromIndex3Test() {
		@SuppressWarnings("unused")
		Mask Mask1 = Mask.fromIndex(5, -3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void isMoreGeneralThan1Test() {
		Mask Mask1 = Mask.fromIndex(5, 3);
		Mask1.isMoreGeneralThan(null);
	}
	@Test
	public void isMoreGeneralThan2Test() {
		Mask Mask1 = Mask.fromIndex(5, 3);
		assertFalse(Mask1.isMoreGeneralThan(Mask.fromIndex(5, 3)));
		assertFalse(Mask1.isMoreGeneralThan(Mask.fromIndex(6, 4)));
	}
	@Test(expected = IllegalArgumentException.class)
	public void combine2Test() {
		Mask.combine(null, null);
	}
	@Test
	public void combine3Test() {
		Mask Mask1 = Mask.parse("1x10x");
		Mask Mask2 = Mask.parse("0x11x1");
		assertNull(Mask.combine(Mask1, Mask2));		
	}
	@Test
	public void equalsTest() { 
		Mask Mask1 = Mask.parse("1x10x");
		Mask Mask2 = Mask.parse("0x11x1");
		assertFalse(Mask1.equals(null));
		assertFalse(Mask1.equals("dsa"));
		assertFalse(Mask1.equals(Mask2));
		assertTrue(Mask1.equals(Mask1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void fromStringsTest() {
		Masks.fromStrings(null, null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void fromIndexesTest() {
		Masks.fromIndexes(-2, 3, 4, 5);
	}
	@Test
	public void fromIndexes2Test() {
		List<Mask> masks = Masks.fromIndexes(3, 2, 1);
		assertEquals("010", masks.get(0).toString());
		assertEquals("001", masks.get(1).toString());
	}

}
