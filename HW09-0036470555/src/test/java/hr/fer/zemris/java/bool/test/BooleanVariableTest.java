package hr.fer.zemris.java.bool.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import hr.fer.zemris.bool.BooleanVariable;
import org.junit.Test;

public class BooleanVariableTest {
	
	@Test
	public void getTest() {
		BooleanVariable varA = new BooleanVariable("A");
		assertEquals(Arrays.asList(varA),varA.getDomain());
		assertEquals("A", varA.getName());
	}
}
